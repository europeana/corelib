package eu.europeana.corelib.edm.server.importer.util;

import org.apache.solr.common.SolrInputDocument;

import eu.europeana.corelib.storage.MongoServer;
import eu.europeana.corelib.definitions.edm.entity.Organization;
import eu.europeana.corelib.edm.utils.MongoUtils;
import eu.europeana.corelib.edm.utils.SolrUtils;
import eu.europeana.corelib.solr.entity.OrganizationImpl;
//TODO: NOT TO BE USED
public class OrganizationFieldInput {

	public SolrInputDocument createOrganizationSolrFields(
			eu.europeana.corelib.definitions.jibx.Organization organization,
			SolrInputDocument solrInputDocument) {
		//TODO: to be implemented when required, not now 
		
		return solrInputDocument;
	}
	
	public Organization createOrganizationMongoFields(
			eu.europeana.corelib.definitions.jibx.Organization jibxOrganization) {
		Organization mongoOrganization = new OrganizationImpl();
		mongoOrganization.setAbout(jibxOrganization.getAbout());
		mongoOrganization.setAltLabel(MongoUtils
				.createLiteralMapFromList(jibxOrganization.getAltLabelList()));
		mongoOrganization.setBegin(MongoUtils
				.createLiteralMapFromString(jibxOrganization.getBegin()));
		mongoOrganization.setDcDate(MongoUtils
				.createResourceOrLiteralMapFromList(jibxOrganization
						.getDateList()));
		mongoOrganization
				.setDcIdentifier(MongoUtils
						.createLiteralMapFromList(jibxOrganization
								.getIdentifierList()));
		mongoOrganization.setEdmAcronym(MongoUtils
				.createLiteralMapFromList(jibxOrganization.getAcronymList()));
		if (jibxOrganization.getCountry() != null) {
			mongoOrganization.setEdmCountry(jibxOrganization.getCountry()
					.getCountry().xmlValue());
		}
		mongoOrganization.setEdmEuropeanaRole(MongoUtils
				.createResourceOrLiteralMapFromList(jibxOrganization
						.getEuropeanaRoleList()));
		mongoOrganization.setEdmGeorgraphicLevel(MongoUtils
				.createResourceOrLiteralRefFromString(jibxOrganization
						.getGeographicLevel()));
		mongoOrganization.setEdmHasMet(MongoUtils
				.createResourceMapFromList(jibxOrganization.getHasMetList()));
		mongoOrganization.setEdmIsRelatedTo(MongoUtils
				.createResourceOrLiteralMapFromList(jibxOrganization
						.getIsRelatedToList()));
		mongoOrganization.setEdmOrganizationDomain(MongoUtils
				.createResourceOrLiteralRefFromString(jibxOrganization
						.getOrganizationDomain()));
		mongoOrganization.setEdmOrganizationScope(MongoUtils
				.createResourceOrLiteralRefFromString(jibxOrganization
						.getOrganizationScope()));
		mongoOrganization.setEdmOrganizationSector(MongoUtils
				.createResourceOrLiteralRefFromString(jibxOrganization
						.getOrganizationSector()));
		mongoOrganization.setEnd(MongoUtils.createLiteralMapFromString(jibxOrganization.getEnd()));
		if(jibxOrganization.getHomepage()!=null){
			mongoOrganization.setFoafHomepage(jibxOrganization.getHomepage().getResource());
		}
		if(jibxOrganization.getLogo()!=null){
			mongoOrganization.setFoafLogo(jibxOrganization.getLogo().getResource());
		}
		mongoOrganization.setFoafName(MongoUtils.createLiteralMapFromList(jibxOrganization.getNameList()));
		mongoOrganization.setNote(MongoUtils.createLiteralMapFromList(jibxOrganization.getNoteList()));
		mongoOrganization.setOwlSameAs(SolrUtils.resourceListToArray(jibxOrganization.getSameAList()));
		mongoOrganization.setRdaGr2BiographicalInformation(MongoUtils
				.createLiteralMapFromString(jibxOrganization
						.getBiographicalInformation()));
		mongoOrganization.setRdaGr2DateOfBirth(MongoUtils
				.createLiteralMapFromString(jibxOrganization.getDateOfBirth()));
		mongoOrganization.setRdaGr2DateOfDeath(MongoUtils
				.createLiteralMapFromString(jibxOrganization.getDateOfDeath()));
		mongoOrganization.setRdaGr2PlaceOfBirth(MongoUtils
				.createResourceOrLiteralMapFromString(jibxOrganization.getPlaceOfBirth()));
		mongoOrganization.setRdaGr2PlaceOfDeath(MongoUtils
				.createResourceOrLiteralMapFromString(jibxOrganization.getPlaceOfDeath()));
		mongoOrganization.setRdaGr2DateOfEstablishment(MongoUtils
				.createLiteralMapFromString(jibxOrganization.getDateOfEstablishment()));
		mongoOrganization.setRdaGr2DateOfTermination(MongoUtils
				.createLiteralMapFromString(jibxOrganization.getDateOfTermination()));
		mongoOrganization.setRdaGr2Gender(MongoUtils.createLiteralMapFromString(jibxOrganization
				.getGender()));
		mongoOrganization.setRdaGr2ProfessionOrOccupation(MongoUtils
				.createResourceOrLiteralMapFromList(jibxOrganization
						.getProfessionOrOccupationList()));
		return mongoOrganization;
	}

	public Organization createLicenseMongoFields(
			eu.europeana.corelib.definitions.jibx.Organization jibxOrganization,
			MongoServer mongoServer) {
		Organization mongoOrganization = mongoServer.getDatastore()
				.find(Organization.class).filter("about", jibxOrganization.getAbout())
				.get();

		if (mongoOrganization == null) {
			mongoOrganization = createOrganizationMongoFields(jibxOrganization);
			try {
				mongoServer.getDatastore().save(mongoOrganization);
			} catch (Exception e) {
				mongoOrganization = updateOrganization(mongoOrganization, jibxOrganization,
						mongoServer);
			}
		} else {
			mongoOrganization = updateOrganization(mongoOrganization, jibxOrganization, mongoServer);
		}
		return mongoOrganization;
	}

	private Organization updateOrganization(
			Organization mongoOrganization,
			eu.europeana.corelib.definitions.jibx.Organization jibxOrganization,
			MongoServer mongoServer) {
		
		return null;
	}
}
