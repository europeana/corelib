/*
 * Copyright 2007-2012 The Europeana Foundation
 *
 *  Licenced under the EUPL, Version 1.1 (the "Licence") and subsequent versions as approved
 *  by the European Commission;
 *  You may not use this work except in compliance with the Licence.
 * 
 *  You may obtain a copy of the Licence at:
 *  http://joinup.ec.europa.eu/software/page/eupl
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under
 *  the Licence is distributed on an "AS IS" basis, without warranties or conditions of
 *  any kind, either express or implied.
 *  See the Licence for the specific language governing permissions and limitations under
 *  the Licence.
 */
package eu.europeana.corelib.tools.lookuptable.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.codec.digest.DigestUtils;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.mongodb.Mongo;
import eu.europeana.corelib.solr.MongoServer;
import eu.europeana.corelib.tools.lookuptable.EuropeanaId;
import eu.europeana.corelib.tools.lookuptable.EuropeanaIdMongoServer;
import eu.europeana.corelib.tools.lookuptable.EuropeanaIdRegistry;
import eu.europeana.corelib.tools.lookuptable.EuropeanaIdRegistryMongoServer;
import eu.europeana.corelib.tools.lookuptable.FailedRecord;
import eu.europeana.corelib.tools.lookuptable.LookupResult;
import eu.europeana.corelib.tools.lookuptable.LookupState;
import eu.europeana.corelib.utils.EuropeanaUriUtils;

/**
 * Class for setting and accessing the EuropeanaIdRegistry Lookup Table
 * 
 * @author yorgos.mamakis@ kb.nl
 * 
 */
public class EuropeanaIdRegistryMongoServerImpl implements MongoServer, EuropeanaIdRegistryMongoServer {

	private Mongo mongoServer;
	private String databaseName;
	private Datastore datastore;

	private final static String EID = "eid";
	private final static String ORID = "orid";
	private final static String DATE = "last_checked";
	private final static String SESSION = "sessionID";
	private final static String CID = "cid";
	private final static String XMLCHECKSUM = "xmlchecksum";
	private final static String ISDELETED = "deleted";
	private EuropeanaIdMongoServer europeanaIdMongoServer;

	/**
	 * Constructor of the EuropeanaIDRegistryMongoServer
	 * 
	 * @param mongoServer
	 *            The server to connect to
	 * @param databaseName
	 *            The database to connect to
	 */
	public EuropeanaIdRegistryMongoServerImpl(Mongo mongoServer, String databaseName){
		this.mongoServer = mongoServer;
		this.databaseName = databaseName;
		europeanaIdMongoServer = new EuropeanaIdMongoServerImpl(mongoServer,
				databaseName,"","");
		createDatastore();
	}

	/* (non-Javadoc)
	 * @see eu.europeana.corelib.tools.lookuptable.impl.EuropeanaIdRegistryMongoServer#getEuropeanaIdMongoServer()
	 */
	@Override
	public EuropeanaIdMongoServer getEuropeanaIdMongoServer(){
		return this.europeanaIdMongoServer;
	}
	private void createDatastore() {
		Morphia morphia = new Morphia();
		morphia.map(EuropeanaIdRegistry.class);
		morphia.map(FailedRecord.class);
		datastore = morphia.createDatastore(mongoServer, databaseName);

		datastore.ensureIndexes();
	}

	/**
	 * Get the datastore
	 */
	@Override
	public Datastore getDatastore() {
		return this.datastore;
	}

	/**
	 * Close the connection to the server
	 */
	@Override
	public void close() {
		mongoServer.close();
	}

	/* (non-Javadoc)
	 * @see eu.europeana.corelib.tools.lookuptable.impl.EuropeanaIdRegistryMongoServer#lookupUiniqueId(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public LookupResult lookupUiniqueId(String origID, String collectionID,
			String xml, String sessionID) {

		
		String xmlChecksum = generatechecksum(xml);
		LookupResult lookupresult = new LookupResult();

		// Generate EuropeanaID by originalid and collectionID
		String europeanaIDString = EuropeanaUriUtils.createEuropeanaId(
				collectionID, origID);

		lookupresult.setEuropeanaID(europeanaIDString);

		EuropeanaIdRegistry constructedeuropeanaId = new EuropeanaIdRegistry();
		constructedeuropeanaId.setCid(collectionID);
		constructedeuropeanaId.setEid(europeanaIDString);
		constructedeuropeanaId.setOrid(origID);
		constructedeuropeanaId.setLast_checked(new Date());
		constructedeuropeanaId.setSessionID(sessionID);
		constructedeuropeanaId.setXmlchecksum(xmlChecksum);
		constructedeuropeanaId.setDeleted(false);
		
		// Retrieve by the EuropeanaID to check if the item is already
		// registered
		EuropeanaIdRegistry retrievedeuropeanaID = retrieveEuropeanaIdFromNew(europeanaIDString);

		UpdateOperations<EuropeanaIdRegistry> updateops = datastore
				.createUpdateOperations(EuropeanaIdRegistry.class);

		// If it is not then save and return a new collectionID
		if (retrievedeuropeanaID == null) {
			if (!checkForChangedCollection(constructedeuropeanaId)) {
				datastore.save(constructedeuropeanaId);
				lookupresult.setState(LookupState.ID_REGISTERED);
				lookupresult.setEuropeanaID(europeanaIDString);
				return lookupresult;
			} else {
				lookupresult.setState(LookupState.COLLECTION_CHANGED);
				updateops.set(EID, constructedeuropeanaId.getEid());
				updateops.set(CID, collectionID);
				return lookupresult;
			}
		}
		else {
				constructedeuropeanaId.setId(retrievedeuropeanaID.getId());
		}

		// Otherwise proceed to UUID scenaria
		// First check if its an Update (eid cid origid are the same, but xml is
		// different)
		// Update the XML checksum
		if (constructedeuropeanaId.getCid().equals(
				retrievedeuropeanaID.getCid())
				&& constructedeuropeanaId.getEid().equals(
						retrievedeuropeanaID.getEid())
				&& constructedeuropeanaId.getOrid().equals(
						retrievedeuropeanaID.getOrid())
				&& !constructedeuropeanaId.getXmlchecksum().equals(
						retrievedeuropeanaID.getXmlchecksum()))		
				 {
			
			if(constructedeuropeanaId.getSessionID().equals(retrievedeuropeanaID.getSessionID())){
				generateFailedRecord(constructedeuropeanaId, xml,null,
						LookupState.DUPLICATE_INCOLLECTION);
				lookupresult.setState(LookupState.DUPLICATE_INCOLLECTION);
				updateops.set(XMLCHECKSUM, xmlChecksum);
				retrievedeuropeanaID.setSessionID(constructedeuropeanaId.getSessionID());
			}
			else{
				retrievedeuropeanaID.setSessionID(constructedeuropeanaId.getSessionID());
				lookupresult.setState(LookupState.UPDATE);
				updateops.set(XMLCHECKSUM, xmlChecksum);
			}

			return processlookupresult(retrievedeuropeanaID,updateops, lookupresult,sessionID);
		}
		
		
		
		
		// Check if it is a duplicate in the same collection (eid cid
		// origid xml and session) are the same

		else if (constructedeuropeanaId.getCid().equals(
				retrievedeuropeanaID.getCid())
				&& constructedeuropeanaId.getEid().equals(
						retrievedeuropeanaID.getEid())
				&& constructedeuropeanaId.getOrid().equals(
						retrievedeuropeanaID.getOrid())
				&& constructedeuropeanaId.getXmlchecksum().equals(
						retrievedeuropeanaID.getXmlchecksum())) {

			if(constructedeuropeanaId.getSessionID().equals(retrievedeuropeanaID.getSessionID())){
				lookupresult.setState(LookupState.DUPLICATE_INCOLLECTION);
				
				generateFailedRecord(constructedeuropeanaId, xml, null,
						LookupState.DUPLICATE_INCOLLECTION);
				retrievedeuropeanaID.setSessionID(constructedeuropeanaId.getSessionID());
				
			}
			else if(!constructedeuropeanaId.getSessionID().equals(
					retrievedeuropeanaID.getSessionID())){
				lookupresult.setState(LookupState.IDENTICAL);
				retrievedeuropeanaID.setSessionID(constructedeuropeanaId.getSessionID());
			}
			
			return processlookupresult(retrievedeuropeanaID,updateops, lookupresult,sessionID);
		}
		
		// There is a duplicate ID in a split collection containing different
		// information:
		// This implies that the eid and rid is the same even though the cid and
		// xml field values are different

		else if (!constructedeuropeanaId.getCid().equals(
				retrievedeuropeanaID.getCid())
				&& constructedeuropeanaId.getEid().equals(
						retrievedeuropeanaID.getEid())
				&& constructedeuropeanaId.getOrid().equals(
						retrievedeuropeanaID.getOrid())
				&& constructedeuropeanaId.getXmlchecksum().equals(
						retrievedeuropeanaID.getXmlchecksum())) {
			
			lookupresult
					.setState(LookupState.DUPLICATE_RECORD_ACROSS_COLLECTIONS);

			String message = "Duplicate record exists between collections:" + constructedeuropeanaId.getCid() +
					" and " + retrievedeuropeanaID.getCid();
			
			generateFailedRecord(constructedeuropeanaId, xml, message,
					LookupState.DUPLICATE_RECORD_ACROSS_COLLECTIONS);
			return processlookupresult(retrievedeuropeanaID,updateops, lookupresult,sessionID);
			
		} else if (!constructedeuropeanaId.getCid().equals(
				retrievedeuropeanaID.getCid())
				&& constructedeuropeanaId.getEid().equals(
						retrievedeuropeanaID.getEid())
				&& constructedeuropeanaId.getOrid().equals(
						retrievedeuropeanaID.getOrid())
				&& !constructedeuropeanaId.getXmlchecksum().equals(
						retrievedeuropeanaID.getXmlchecksum())) {
			lookupresult
					.setState(LookupState.DUPLICATE_IDENTIFIER_ACROSS_COLLECTIONS);
			
			
			String message = "Record with duplicate identifier exists between collections:" + constructedeuropeanaId.getCid() +
					" and " + retrievedeuropeanaID.getCid();
			
			generateFailedRecord(constructedeuropeanaId, xml, message,
					LookupState.DUPLICATE_IDENTIFIER_ACROSS_COLLECTIONS);
			return processlookupresult(retrievedeuropeanaID,updateops, lookupresult,sessionID);
		}
		else if (constructedeuropeanaId.getCid().equals(
				retrievedeuropeanaID.getCid())
				&& constructedeuropeanaId.getEid().equals(
						retrievedeuropeanaID.getEid())
				&& !constructedeuropeanaId.getOrid().equals(
						retrievedeuropeanaID.getOrid())
				&& !constructedeuropeanaId.getXmlchecksum().equals(
						retrievedeuropeanaID.getXmlchecksum())) 
		{
			
			lookupresult.setState(LookupState.DERIVED_DUPLICATE_INCOLLECTION);
						
			String message = "Records " + constructedeuropeanaId.getOrid() + " and " + retrievedeuropeanaID.getOrid() +
					" resulted in the creation of a common identifier during Europeana UUID generation (" +
					constructedeuropeanaId.getEid() + ")";
			
			generateFailedRecord(constructedeuropeanaId, xml, message, 
					LookupState.DERIVED_DUPLICATE_INCOLLECTION);

		}


		return lookupresult;
	}

	/**
	 * @param retrievedeuropeanaID
	 * @param updateops
	 * @param lookupresult
	 * @param sessionID
	 * @return
	 */
	private LookupResult  processlookupresult(EuropeanaIdRegistry retrievedeuropeanaID,
			UpdateOperations<EuropeanaIdRegistry> updateops,LookupResult lookupresult,String sessionID){
		// Update Session ID
		updateops.set(DATE, new Date());

		// Update Date
		updateops.set(SESSION, sessionID);

		datastore.update(retrievedeuropeanaID, updateops);

		return lookupresult;
	}
	
	
	/**
	 * @param constructedeuropeanaId
	 * @return
	 */
	private boolean checkForChangedCollection(
			EuropeanaIdRegistry constructedeuropeanaId) {
		EuropeanaIdRegistry retrievedId = retrieveFromOriginalXML(
				constructedeuropeanaId.getOrid(),
				constructedeuropeanaId.getXmlchecksum());
		if (retrievedId != null) {
			EuropeanaId eurId = new EuropeanaId();
			eurId.setNewId(constructedeuropeanaId.getEid());
			eurId.setOldId(retrievedId.getEid());
			eurId.setTimestamp(new Date().getTime());
			europeanaIdMongoServer.saveEuropeanaId(eurId);
			return true;

		}
		return false;
	}

	/**
	 * Creates and stores/updates a new failed record
	 * 
	 * @param eurId
	 *            The record that failed
	 * @param xml
	 *            The original EDM of the record
	 * @param lookupState
	 *            The reason it failed
	 */
	private void generateFailedRecord(EuropeanaIdRegistry eurId, String xml,String message,
			LookupState lookupState) {
		FailedRecord failedRecord = datastore.find(FailedRecord.class)
				.filter("originalId", eurId.getOrid())
				.filter("collectionId", eurId.getCid()).get();
		System.out.println("Generating failed Record " + eurId + ", Reason: "
				+ lookupState.toString());
		// If it has not been found then create
		if (failedRecord == null) {
			failedRecord = new FailedRecord();
			failedRecord.setCollectionId(eurId.getCid());
			failedRecord.setEuropeanaId(eurId.getEid());
			failedRecord.setOriginalId(eurId.getOrid());
			failedRecord.setXml(xml);
			failedRecord.setLookupState(lookupState);
			failedRecord.setDate(new Date());
			if(message != null){
				failedRecord.setMessage(message);
			}
			datastore.save(failedRecord);
		}
		// or else update the fields that might have changed (xml representation
		// and the lookupState)
		else {
			UpdateOperations<FailedRecord> updateops = datastore
					.createUpdateOperations(FailedRecord.class);
			updateops.set("xml", xml);
			updateops.set("lookupState", lookupState);
			updateops.set("date", new Date());
			datastore.update(failedRecord, updateops);
		}

	}

	
	/* (non-Javadoc)
	 * @see eu.europeana.corelib.tools.lookuptable.impl.EuropeanaIdRegistryMongoServer#createFailedRecord(eu.europeana.corelib.tools.lookuptable.LookupState, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void createFailedRecord(LookupState state,String collectionID,String oldID,String newId,String xml){
		
		FailedRecord failedRecord = datastore.find(FailedRecord.class)
				.filter("originalId", oldID)
				.filter("collectionId", collectionID).get();
		
		
		if (failedRecord == null) {
		    failedRecord = new FailedRecord();
			failedRecord.setCollectionId(collectionID);
			failedRecord.setEuropeanaId(newId);
			failedRecord.setOriginalId(oldID);
			failedRecord.setXml(xml);
			failedRecord.setLookupState(state);
			failedRecord.setDate(new Date());
			datastore.save(failedRecord);
		}
		else {
			UpdateOperations<FailedRecord> updateops = datastore
					.createUpdateOperations(FailedRecord.class);
			updateops.set("xml", xml);
			updateops.set("lookupState", state);
			updateops.set("date", new Date());
			datastore.update(failedRecord, updateops);
		}

	}
	
	
	
	/* (non-Javadoc)
	 * @see eu.europeana.corelib.tools.lookuptable.impl.EuropeanaIdRegistryMongoServer#deleteFailedRecord(java.lang.String, java.lang.String)
	 */
	@Override
	public void deleteFailedRecord(String europeanaId,String collectionID){
		Query<FailedRecord> deleteQuery = datastore
				.createQuery(FailedRecord.class).field("europeanaId").equal(europeanaId).
				field("collectionId").equal(collectionID);
		
		datastore.findAndDelete(deleteQuery);
	}
	
	
	/* (non-Javadoc)
	 * @see eu.europeana.corelib.tools.lookuptable.impl.EuropeanaIdRegistryMongoServer#deleteFailedRecord(java.lang.String, java.lang.String)
	 */
	@Override
	public void deleteFailedRecords(String collectionID){
		Query<FailedRecord> deleteQuery = datastore
				.createQuery(FailedRecord.class).field("collectionId").equal(collectionID);
		
		datastore.findAndDelete(deleteQuery);
	}
	
	
	
	/**
	 * Generates the checksum for the given string
	 * 
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private String generatechecksum(String xml) {
		return DigestUtils.shaHex(xml);
	}

	/* (non-Javadoc)
	 * @see eu.europeana.corelib.tools.lookuptable.impl.EuropeanaIdRegistryMongoServer#retrieveEuropeanaIdFromOriginal(java.lang.String, java.lang.String)
	 */
	@Override
	public List<EuropeanaIdRegistry> retrieveEuropeanaIdFromOriginal(
			String originalId, String collectionid) {
		List<EuropeanaIdRegistry> retlist = new ArrayList<EuropeanaIdRegistry>();
		
		List<EuropeanaIdRegistry> list = datastore.find(EuropeanaIdRegistry.class).field(ORID)
		.equal(originalId).asList();
		
		for(EuropeanaIdRegistry entry : list){
			if(!entry.isDeleted()){
				retlist.add(entry);
			}
		}
		
		return retlist;
	}

	/* (non-Javadoc)
	 * @see eu.europeana.corelib.tools.lookuptable.impl.EuropeanaIdRegistryMongoServer#retrieveEuropeanaIdFromNew(java.lang.String)
	 */
	@Override
	public EuropeanaIdRegistry retrieveEuropeanaIdFromNew(String newId) {

		List<EuropeanaIdRegistry> retrList = datastore
				.find(EuropeanaIdRegistry.class).field(EID).equal(newId)
				.asList();

		if (retrList.isEmpty()) {
			return null;
		} else {
			for(EuropeanaIdRegistry entry : retrList){
				if(!entry.isDeleted()){
					return entry;
				}
			}
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see eu.europeana.corelib.tools.lookuptable.impl.EuropeanaIdRegistryMongoServer#oldIdExists(java.lang.String)
	 */
	@Override
	public boolean oldIdExists(String newId) {
		return datastore.find(EuropeanaIdRegistry.class).field(EID)
				.equal(newId).get() != null ? true : false;
	}

	/* (non-Javadoc)
	 * @see eu.europeana.corelib.tools.lookuptable.impl.EuropeanaIdRegistryMongoServer#retrieveFromOriginalXML(java.lang.String, java.lang.String)
	 */
	@Override
	public EuropeanaIdRegistry retrieveFromOriginalXML(String orId, String xml) {
		return datastore.find(EuropeanaIdRegistry.class)
				.filter(XMLCHECKSUM, xml).filter(ORID, orId).get();

	}

	/* (non-Javadoc)
	 * @see eu.europeana.corelib.tools.lookuptable.impl.EuropeanaIdRegistryMongoServer#newIdExists(java.lang.String)
	 */
	@Override
	public boolean newIdExists(String oldId) {
		return datastore.find(EuropeanaIdRegistry.class).field(ORID)
				.equal(oldId).get() != null ? true : false;
	}

	/* (non-Javadoc)
	 * @see eu.europeana.corelib.tools.lookuptable.impl.EuropeanaIdRegistryMongoServer#deleteEuropeanaIdFromOld(java.lang.String)
	 */
	@Override
	public void deleteEuropeanaIdFromOld(String oldId){
		Query<EuropeanaId> deleteQuery = datastore
				.createQuery(EuropeanaId.class).field("oldId").equal(oldId);
			
		datastore.findAndDelete(deleteQuery);
	}
	
	/* (non-Javadoc)
	 * @see eu.europeana.corelib.tools.lookuptable.impl.EuropeanaIdRegistryMongoServer#deleteEuropeanaIdFromNew(java.lang.String)
	 */
	@Override
	public void deleteEuropeanaIdFromNew(String newId){
		Query<EuropeanaId> deleteQuery = datastore
				.createQuery(EuropeanaId.class).field("newId").equal(newId);
			
		datastore.findAndDelete(deleteQuery);
	}
	
	/* (non-Javadoc)
	 * @see eu.europeana.corelib.tools.lookuptable.impl.EuropeanaIdRegistryMongoServer#updateTime(java.lang.String, java.lang.String)
	 */
	@Override
	public void updateTime(String newId, String oldId){
		Query<EuropeanaId> updateQuery = datastore
				.createQuery(EuropeanaId.class).field("oldId").equal(oldId).field("newId").equal(newId);
		UpdateOperations<EuropeanaId> ops =datastore.createUpdateOperations(EuropeanaId.class)
				.set("timestamp",new Date().getTime());
		datastore.update(updateQuery, ops);
	}


	/* (non-Javadoc)
	 * @see eu.europeana.corelib.tools.lookuptable.impl.EuropeanaIdRegistryMongoServer#getFailedRecords(java.lang.String)
	 */
	@Override
	public List<Map<String, String>> getFailedRecords(String collectionId) {
		List<Map<String, String>> failedRecords = new ArrayList<Map<String, String>>();
		for (FailedRecord failedRecord : datastore.find(FailedRecord.class)
				.filter("collectionId", collectionId).asList()) {
			Map<String, String> record = new HashMap<String, String>();
			record.put("collectionId", failedRecord.getCollectionId());
			record.put("originalId", failedRecord.getOriginalId());
			record.put("europeanaId", failedRecord.getEuropeanaId());
			record.put("edm", failedRecord.getXml());
			record.put("lookupState", failedRecord.getLookupState().toString());
			record.put("date", failedRecord.getDate().toString());
			if(failedRecord.getMessage() != null){
				record.put("message", failedRecord.getMessage());
			}	
			failedRecords.add(record);
		}
		return failedRecords;
	}

	/* (non-Javadoc)
	 * @see eu.europeana.corelib.tools.lookuptable.EuropeanaIdRegistryMongoServer#markdeleted(java.lang.String, boolean)
	 */
	@Override
	public void markdeleted(String europeanaID, boolean isdeleted) {
		UpdateOperations<EuropeanaIdRegistry> ops =datastore.createUpdateOperations(EuropeanaIdRegistry.class)
				.set("deleted",isdeleted);
		
		Query<EuropeanaIdRegistry> query = datastore
				.createQuery(EuropeanaIdRegistry.class).field("eid").equal(europeanaID);
		datastore.findAndModify(query, ops);
	}

	/* (non-Javadoc)
	 * @see eu.europeana.corelib.tools.lookuptable.EuropeanaIdRegistryMongoServer#isdeleted(java.lang.String)
	 */
	@Override
	public boolean isdeleted(String europeanaID) {
		
		Query<EuropeanaIdRegistry> query = datastore
				.createQuery(EuropeanaIdRegistry.class).field("eid").equal(europeanaID);
		
		EuropeanaIdRegistry  idr = datastore.find(EuropeanaIdRegistry.class).filter("eid", europeanaID).get();
		return idr.isDeleted();
	}
	
	
	
}