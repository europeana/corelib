package eu.europeana.corelib.solr.server.importer.util;

import org.apache.solr.common.SolrInputDocument;

import eu.europeana.corelib.definitions.solr.entity.Organisation;
import eu.europeana.corelib.solr.MongoServer;
import eu.europeana.corelib.solr.entity.OrganisationImpl;
import eu.europeana.corelib.solr.server.EdmMongoServer;
import eu.europeana.corelib.solr.utils.MongoUtils;
import eu.europeana.corelib.solr.utils.SolrUtils;
//TODO: NOT TO BE USED
public class OrganizationFieldInput {

	public SolrInputDocument createOrganizationSolrFields(
			eu.europeana.corelib.definitions.jibx.Organisation organisation,
			SolrInputDocument solrInputDocument) {
		//TODO: to be implemented when required, not now 
		
		return solrInputDocument;
	}
	
	public Organisation createOrganisationMongoFields(
			eu.europeana.corelib.definitions.jibx.Organisation jibxOrganisation) {
		Organisation mongoOrganisation = new OrganisationImpl();
		mongoOrganisation.setAbout(jibxOrganisation.getAbout());
		mongoOrganisation.setAltLabel(MongoUtils
				.createLiteralMapFromList(jibxOrganisation.getAltLabelList()));
		mongoOrganisation.setBegin(MongoUtils
				.createLiteralMapFromString(jibxOrganisation.getBegin()));
		mongoOrganisation.setDcDate(MongoUtils
				.createResourceOrLiteralMapFromList(jibxOrganisation
						.getDateList()));
		mongoOrganisation
				.setDcIdentifier(MongoUtils
						.createLiteralMapFromList(jibxOrganisation
								.getIdentifierList()));
		mongoOrganisation.setEdmAcronym(MongoUtils
				.createLiteralMapFromList(jibxOrganisation.getAcronymList()));
		if (jibxOrganisation.getCountry() != null) {
			mongoOrganisation.setEdmCountry(jibxOrganisation.getCountry()
					.getCountry().xmlValue());
		}
		mongoOrganisation.setEdmEuropeanaRole(MongoUtils
				.createResourceOrLiteralMapFromList(jibxOrganisation
						.getEuropeanaRoleList()));
		mongoOrganisation.setEdmGeorgraphicLevel(MongoUtils
				.createResourceOrLiteralRefFromString(jibxOrganisation
						.getGeographicLevel()));
		mongoOrganisation.setEdmHasMet(MongoUtils
				.createResourceMapFromList(jibxOrganisation.getHasMetList()));
		mongoOrganisation.setEdmIsRelatedTo(MongoUtils
				.createResourceOrLiteralMapFromList(jibxOrganisation
						.getIsRelatedToList()));
		mongoOrganisation.setEdmOrganisationDomain(MongoUtils
				.createResourceOrLiteralRefFromString(jibxOrganisation
						.getOrganisationDomain()));
		mongoOrganisation.setEdmOrganisationScope(MongoUtils
				.createResourceOrLiteralRefFromString(jibxOrganisation
						.getOrganisationScope()));
		mongoOrganisation.setEdmOrganisationSector(MongoUtils
				.createResourceOrLiteralRefFromString(jibxOrganisation
						.getOrganisationSector()));
		mongoOrganisation.setEnd(MongoUtils.createLiteralMapFromString(jibxOrganisation.getEnd()));
		if(jibxOrganisation.getHomepage()!=null){
			mongoOrganisation.setFoafHomepage(jibxOrganisation.getHomepage().getResource());
		}
		mongoOrganisation.setFoafName(MongoUtils.createLiteralMapFromList(jibxOrganisation.getNameList()));
		mongoOrganisation.setNote(MongoUtils.createLiteralMapFromList(jibxOrganisation.getNoteList()));
		mongoOrganisation.setOwlSameAs(SolrUtils.resourceListToArray(jibxOrganisation.getSameAList()));
		mongoOrganisation.setRdaGr2BiographicalInformation(MongoUtils
				.createLiteralMapFromString(jibxOrganisation
						.getBiographicalInformation()));
		mongoOrganisation.setRdaGr2DateOfBirth(MongoUtils
				.createLiteralMapFromString(jibxOrganisation.getDateOfBirth()));
		mongoOrganisation.setRdaGr2DateOfDeath(MongoUtils
				.createLiteralMapFromString(jibxOrganisation.getDateOfDeath()));
		mongoOrganisation.setRdaGr2PlaceOfBirth(MongoUtils
				.createResourceOrLiteralMapFromString(jibxOrganisation.getPlaceOfBirth()));
		mongoOrganisation.setRdaGr2PlaceOfDeath(MongoUtils
				.createResourceOrLiteralMapFromString(jibxOrganisation.getPlaceOfDeath()));
		mongoOrganisation.setRdaGr2DateOfEstablishment(MongoUtils
				.createLiteralMapFromString(jibxOrganisation.getDateOfEstablishment()));
		mongoOrganisation.setRdaGr2DateOfTermination(MongoUtils
				.createLiteralMapFromString(jibxOrganisation.getDateOfTermination()));
		mongoOrganisation.setRdaGr2Gender(MongoUtils.createLiteralMapFromString(jibxOrganisation
				.getGender()));
		mongoOrganisation.setRdaGr2ProfessionOrOccupation(MongoUtils
				.createResourceOrLiteralMapFromString(jibxOrganisation
						.getProfessionOrOccupation()));
		return mongoOrganisation;
	}

	public Organisation createLicenseMongoFields(
			eu.europeana.corelib.definitions.jibx.Organisation jibxOrganisation,
			MongoServer mongoServer) {
		Organisation mongoOrganisation = ((EdmMongoServer) mongoServer).getDatastore()
				.find(Organisation.class).filter("about", jibxOrganisation.getAbout())
				.get();

		if (mongoOrganisation == null) {
			mongoOrganisation = createOrganisationMongoFields(jibxOrganisation);
			try {
				mongoServer.getDatastore().save(mongoOrganisation);
			} catch (Exception e) {
				mongoOrganisation = updateOrganisation(mongoOrganisation, jibxOrganisation,
						mongoServer);
			}
		} else {
			mongoOrganisation = updateOrganisation(mongoOrganisation, jibxOrganisation, mongoServer);
		}
		return mongoOrganisation;
	}

	private Organisation updateOrganisation(
			Organisation mongoOrganisation,
			eu.europeana.corelib.definitions.jibx.Organisation jibxOrganisation,
			MongoServer mongoServer) {
		
		return null;
	}
}
