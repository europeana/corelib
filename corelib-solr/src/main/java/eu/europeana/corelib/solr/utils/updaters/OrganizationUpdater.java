package eu.europeana.corelib.solr.utils.updaters;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

import eu.europeana.corelib.definitions.solr.entity.Organization;
import eu.europeana.corelib.solr.MongoServer;
import eu.europeana.corelib.solr.entity.OrganizationImpl;
import eu.europeana.corelib.solr.utils.MongoUtils;
import eu.europeana.corelib.solr.utils.SolrUtils;
//TODO: NOT TO BE USED
public class OrganizationUpdater implements Updater<Organization, eu.europeana.corelib.definitions.jibx.Organization> {

	@Override
	public void update(Organization mongoEntity,
			eu.europeana.corelib.definitions.jibx.Organization jibxEntity,
			MongoServer mongoServer) {
		Query<OrganizationImpl> updateQuery = mongoServer.getDatastore()
				.createQuery(OrganizationImpl.class).field("about")
				.equal(jibxEntity.getAbout());
		UpdateOperations<OrganizationImpl> ops = mongoServer.getDatastore()
				.createUpdateOperations(OrganizationImpl.class);
		boolean update = false;
		
		if (jibxEntity.getBegin() != null) {
			Map<String, List<String>> begin = MongoUtils
					.createLiteralMapFromString(jibxEntity.getBegin());
			if (begin != null) {
				if (mongoEntity.getBegin() == null
						|| !MongoUtils.mapEquals(begin, mongoEntity.getBegin())) {
					ops.set("begin", begin);
					update = true;
				}
			}
		} else {
			if (mongoEntity.getBegin()!=null){
				ops.unset("begin");
				update = true;
			}
		}

		if (jibxEntity.getDateList() != null) {
			Map<String, List<String>> date = MongoUtils
					.createResourceOrLiteralMapFromList(jibxEntity.getDateList());
			if (date != null) {
				if (mongoEntity.getDcDate() == null
						|| !MongoUtils.mapEquals(date, mongoEntity.getDcDate())) {
					ops.set("dcDate", date);
					update = true;
				}
			}
		} else {
			if (mongoEntity.getDcDate()!=null){
				ops.unset("dcDate");
				update = true;
			}
		}

		if (jibxEntity.getIdentifierList() != null) {
			Map<String, List<String>> identifier = MongoUtils
					.createLiteralMapFromList(jibxEntity.getIdentifierList());
			if (identifier != null) {
				if (mongoEntity.getDcIdentifier() == null
						|| !MongoUtils.mapEquals(identifier,
								mongoEntity.getDcIdentifier())) {
					ops.set("dcIdentifier", identifier);
					update = update | true;
				}
			}
		} else {
			if (mongoEntity.getDcIdentifier()!=null){
				ops.unset("dcIdentifier");
				update = true;
			}
		}

		if (jibxEntity.getBiographicalInformation() != null) {
			Map<String, List<String>> biographicalInformation = MongoUtils
					.createLiteralMapFromString(jibxEntity
							.getBiographicalInformation());
			if (biographicalInformation != null) {
				if (mongoEntity.getRdaGr2BiographicalInformation() == null
						|| !MongoUtils.mapEquals(biographicalInformation,
								mongoEntity.getRdaGr2BiographicalInformation())) {
					ops.set("rdaGr2BiographicalInformation",
							biographicalInformation);
					update = true;
				}
			}
		} else {
			if (mongoEntity.getRdaGr2BiographicalInformation()!=null){
				ops.unset("rdaGr2BiographicalInformation");
				update = true;
			}
		}
		

		if (jibxEntity.getDateOfBirth() != null) {
			Map<String, List<String>> dob = MongoUtils
					.createLiteralMapFromString(jibxEntity.getDateOfBirth());
			if (dob != null) {
				if (mongoEntity.getRdaGr2DateOfBirth() == null
						|| !MongoUtils.mapEquals(dob,
								mongoEntity.getRdaGr2DateOfBirth())) {

					ops.set("rdaGr2DateOfBirth", dob);
					update = true;
				}
			}
		} else {
			if (mongoEntity.getRdaGr2DateOfBirth()!=null){
				ops.unset("rdaGr2DateOfBirth");
				update = true;
			}
		}

		if (jibxEntity.getDateOfDeath() != null) {
			Map<String, List<String>> dod = MongoUtils
					.createLiteralMapFromString(jibxEntity.getDateOfDeath());
			if (dod != null) {
				if (mongoEntity.getRdaGr2DateOfDeath() == null
						|| !MongoUtils.mapEquals(dod,
								mongoEntity.getRdaGr2DateOfDeath())) {
					ops.set("rdaGr2DateOfDeath", dod);
					update = true;
				}
			}
		} else {
			if (mongoEntity.getRdaGr2DateOfDeath()!=null){
				ops.unset("rdaGr2DateOfDeath");
				update = true;
			}
		}

		
		if (jibxEntity.getPlaceOfBirth() != null) {
			Map<String, List<String>> dob = MongoUtils
					.createResourceOrLiteralMapFromString(jibxEntity.getPlaceOfBirth());
			if (dob != null) {
				if (mongoEntity.getRdaGr2PlaceOfBirth() == null
						|| !MongoUtils.mapEquals(dob,
								mongoEntity.getRdaGr2PlaceOfBirth())) {

					ops.set("rdaGr2PlaceOfBirth", dob);
					update = true;
				}
			}
		} else {
			if (mongoEntity.getRdaGr2PlaceOfBirth()!=null){
				ops.unset("rdaGr2PlaceOfBirth");
				update = true;
			}
		}

		if (jibxEntity.getPlaceOfDeath() != null) {
			Map<String, List<String>> dod = MongoUtils
					.createResourceOrLiteralMapFromString(jibxEntity.getPlaceOfDeath());
			if (dod != null) {
				if (mongoEntity.getRdaGr2PlaceOfDeath() == null
						|| !MongoUtils.mapEquals(dod,
								mongoEntity.getRdaGr2PlaceOfDeath())) {
					ops.set("rdaGr2PlaceOfDeath", dod);
					update = true;
				}
			}
		} else {
			if (mongoEntity.getRdaGr2PlaceOfDeath()!=null){
				ops.unset("rdaGr2PlaceOfDeath");
				update = true;
			}
		}
		
		
		if (jibxEntity.getDateOfEstablishment() != null) {
			Map<String, List<String>> doe = MongoUtils
					.createLiteralMapFromString(jibxEntity
							.getDateOfEstablishment());
			if (doe != null) {
				if (mongoEntity.getRdaGr2DateOfEstablishment() == null
						|| !MongoUtils.mapEquals(doe,
								mongoEntity.getRdaGr2DateOfEstablishment())) {
					ops.set("rdaGr2DateOfEstablishment", doe);
					update = true;
				}
			}
		} else {
			if (mongoEntity.getRdaGr2DateOfEstablishment()!=null){
				ops.unset("rdaGr2DateOfEstablishment");
				update = true;
			}
		}
		

		if (jibxEntity.getDateOfTermination() != null) {
			Map<String, List<String>> dot = MongoUtils
					.createLiteralMapFromString(jibxEntity
							.getDateOfTermination());
			if (dot != null) {
				if (mongoEntity.getRdaGr2DateOfTermination() == null
						|| !MongoUtils.mapEquals(dot,
								mongoEntity.getRdaGr2DateOfTermination())) {
					ops.set("rdaGr2DateOfTermination", dot);
					update = update | true;
				}
			}
		} else {
			if (mongoEntity.getRdaGr2DateOfTermination()!=null){
				ops.unset("rdaGr2DateOfTermination");
				update = true;
			}
		}

		if (jibxEntity.getGender() != null) {
			Map<String, List<String>> gender = MongoUtils
					.createLiteralMapFromString(jibxEntity.getGender());
			if (gender != null) {
				if (mongoEntity.getRdaGr2Gender() == null
						|| !MongoUtils.mapEquals(gender,
								mongoEntity.getRdaGr2Gender())) {
					ops.set("rdaGr2Gender", gender);
					update = true;
				}
			}
		} else {
			if (mongoEntity.getRdaGr2Gender()!=null){
				ops.unset("rdaGr2Gender");
				update = true;
			}
		}

		if (jibxEntity.getProfessionOrOccupation() != null) {
			Map<String, List<String>> professionOrOccupation = MongoUtils
					.createResourceOrLiteralMapFromString(jibxEntity
							.getProfessionOrOccupation());
			if (professionOrOccupation != null) {
				if (mongoEntity.getRdaGr2ProfessionOrOccupation() == null
						|| !MongoUtils.mapEquals(professionOrOccupation,
								mongoEntity.getRdaGr2ProfessionOrOccupation())) {
					ops.set("rdaGr2ProfessionOrOccupation",
							professionOrOccupation);
					update = true;
				}
			}
		} else {
			if (mongoEntity.getRdaGr2ProfessionOrOccupation()!=null){
				ops.unset("rdaGr2ProfessionOrOccupation");
				update = true;
			}
		}

		if (jibxEntity.getHasMetList() != null) {
			Map<String, List<String>> hasMet = MongoUtils
					.createResourceMapFromList(jibxEntity.getHasMetList());
			if (hasMet != null) {
				if (mongoEntity.getEdmHasMet() == null
						|| !MongoUtils.mapEquals(hasMet, mongoEntity.getEdmHasMet())) {
					ops.set("edmHasMet", hasMet);
					update = true;
				}
			}
		} else {
			if (mongoEntity.getEdmHasMet()!=null){
				ops.unset("edmHasMet");
				update = true;
			}
		}

		if (jibxEntity.getIsRelatedToList() != null) {
			Map<String, List<String>> isRelatedTo = MongoUtils
					.createResourceOrLiteralMapFromList(jibxEntity
							.getIsRelatedToList());
			if (isRelatedTo != null) {
				if (mongoEntity.getEdmIsRelatedTo() == null
						|| !MongoUtils.mapEquals(isRelatedTo,
								mongoEntity.getEdmIsRelatedTo())) {
					ops.set("edmIsRelatedTo", isRelatedTo);
					update = true;
				}
			}
		} else {
			if (mongoEntity.getEdmIsRelatedTo()!=null){
				ops.unset("edmIsRelatedTo");
				update = true;
			}
		}

		if (jibxEntity.getNameList() != null) {
			Map<String, List<String>> name = MongoUtils
					.createLiteralMapFromList(jibxEntity.getNameList());
			if (name != null) {
				if (mongoEntity.getFoafName() == null
						|| !MongoUtils.mapEquals(name, mongoEntity.getFoafName())) {
					ops.set("foafName", name);
					update = true;
				}
			}

		} else {
			if (mongoEntity.getFoafName()!=null){
				ops.unset("foafName");
				update = true;
			}
		}

		if (jibxEntity.getSameAList() != null) {
			String[] sameAs = SolrUtils.resourceListToArray(jibxEntity
					.getSameAList());
			if (sameAs != null) {
				if (mongoEntity.getOwlSameAs() == null
						|| !MongoUtils
								.arrayEquals(sameAs, mongoEntity.getOwlSameAs())) {
					ops.set("owlSameAs", sameAs);
					update = true;
				}
			}
		} else {
			if (mongoEntity.getOwlSameAs()!=null){
				ops.unset("owlSameAs");
				update = true;
			}
		}

		if (jibxEntity.getEnd() != null) {
			Map<String, List<String>> end = MongoUtils
					.createLiteralMapFromString(jibxEntity.getEnd());
			if (end != null) {
				if (mongoEntity.getEnd() == null
						|| !MongoUtils.mapEquals(end, mongoEntity.getEnd())) {
					ops.set("end", end);
					update = true;
				}
			}
		} else {
			if (mongoEntity.getEnd()!=null){
				ops.unset("end");
				update = true;
			}
		}

		if (jibxEntity.getNoteList() != null) {
			Map<String, List<String>> note = MongoUtils
					.createLiteralMapFromList(jibxEntity.getNoteList());
			if (note != null) {
				if (mongoEntity.getNote() == null
						|| !MongoUtils.mapEquals(note, mongoEntity.getNote())) {
					ops.set("note", note);
					update = true;
				}
			}
		} else {
			if (mongoEntity.getNote()!=null){
				ops.unset("note");
				update = true;
			}
		}

		if (jibxEntity.getAltLabelList() != null) {
			Map<String, List<String>> altLabel = MongoUtils
					.createLiteralMapFromList(jibxEntity.getAltLabelList());
			if (altLabel != null) {
				if (mongoEntity.getAltLabel() == null
						|| !MongoUtils.mapEquals(altLabel, mongoEntity.getAltLabel())) {
					ops.set("altLabel", altLabel);
					update = true;
				}
			}
		} else {
			if (mongoEntity.getAltLabel()!=null){
				ops.unset("altLabel");
				update = true;
			}
		}

		if (jibxEntity.getPrefLabelList() != null) {
			Map<String, List<String>> prefLabel = MongoUtils
					.createLiteralMapFromList(jibxEntity.getPrefLabelList());
			if (prefLabel != null) {
				if (mongoEntity.getPrefLabel() == null
						|| !MongoUtils.mapEquals(prefLabel,
								mongoEntity.getPrefLabel())) {
					ops.set("prefLabel", prefLabel);
					update = true;
				}
			}
		}else {
			if (mongoEntity.getPrefLabel()!=null){
				ops.unset("prefLabel");
				update = true;
			}
		}
		
		if(jibxEntity.getAcronymList()!=null){
			Map<String, List<String>> acronym = MongoUtils
					.createLiteralMapFromList(jibxEntity.getAcronymList());
			if (acronym != null) {
				if (mongoEntity.getEdmAcronym() == null
						|| !MongoUtils.mapEquals(acronym,
								mongoEntity.getEdmAcronym())) {
					ops.set("edmAcronym", acronym);
					update = true;
				}
			}
		}else {
			if (mongoEntity.getEdmAcronym()!=null){
				ops.unset("edmAcronym");
				update = true;
			}
		}
		
		if(jibxEntity.getOrganizationScope()!=null){
			Map<String, String> organizationScope = MongoUtils
					.createResourceOrLiteralRefFromString(jibxEntity.getOrganizationScope());
			if (organizationScope != null) {
				if (mongoEntity.getEdmOrganizationScope() == null
						|| !MongoUtils.mapRefEquals(organizationScope,
								mongoEntity.getEdmOrganizationScope())) {
					ops.set("edmOrganizationScope", organizationScope);
					update = true;
				}
			}
		}else {
			if (mongoEntity.getEdmOrganizationScope()!=null){
				ops.unset("edmOrganizationScope");
				update = true;
			}
		}
		
		if(jibxEntity.getOrganizationDomain()!=null){
			Map<String, String> organizationDomain= MongoUtils
					.createResourceOrLiteralRefFromString(jibxEntity.getOrganizationDomain());
			if (organizationDomain != null) {
				if (mongoEntity.getEdmOrganizationDomain() == null
						|| !MongoUtils.mapRefEquals(organizationDomain,
								mongoEntity.getEdmOrganizationDomain())) {
					ops.set("edmOrganizationDomain", organizationDomain);
					update = true;
				}
			}
		}else {
			if (mongoEntity.getEdmOrganizationDomain()!=null){
				ops.unset("edmOrganizationDomain");
				update = true;
			}
		}
		
		if(jibxEntity.getOrganizationSector()!=null){
			Map<String, String> organizationSector= MongoUtils
					.createResourceOrLiteralRefFromString(jibxEntity.getOrganizationSector());
			if (organizationSector != null) {
				if (mongoEntity.getEdmOrganizationSector() == null
						|| !MongoUtils.mapRefEquals(organizationSector,
								mongoEntity.getEdmOrganizationSector())) {
					ops.set("edmOrganizationSector", organizationSector);
					update = true;
				}
			}
		}else {
			if (mongoEntity.getEdmOrganizationSector()!=null){
				ops.unset("edmOrganizationSector");
				update = true;
			}
		}
		
		if(jibxEntity.getGeographicLevel()!=null){
			Map<String, String> geographicLevel = MongoUtils
					.createResourceOrLiteralRefFromString(jibxEntity.getGeographicLevel());
			if (geographicLevel != null) {
				if (mongoEntity.getEdmGeographicLevel() == null
						|| !MongoUtils.mapRefEquals(geographicLevel,
								mongoEntity.getEdmGeographicLevel())) {
					ops.set("edmGeographicLevel", geographicLevel);
					update = true;
				}
			}
		}else {
			if (mongoEntity.getEdmGeographicLevel()!=null){
				ops.unset("edmGeograpchicLevel");
				update = true;
			}
		}
		
		if(jibxEntity.getCountry()!=null){
			String country = jibxEntity.getCountry().getCountry().xmlValue();
			
				if (!StringUtils.equals(mongoEntity.getEdmCountry(),country)) {
					ops.set("edmCountry", country);
					update = true;
				}
		}else {
			if (mongoEntity.getEdmCountry()!=null){
				ops.unset("edmCountry");
				update = true;
			}
		}
		
		if(jibxEntity.getHomepage()!=null){
			String homepage = jibxEntity.getHomepage().getResource();
			
				if (!StringUtils.equals(mongoEntity.getFoafHomepage(),homepage)) {
					ops.set("foafHomepage", homepage);
					update = true;
				}
		}else {
			if (mongoEntity.getFoafHomepage()!=null){
				ops.unset("foafHomepage");
				update = true;
			}
		}
		
		if (jibxEntity.getEuropeanaRoleList() != null) {
			Map<String, List<String>> roles = MongoUtils
					.createResourceOrLiteralMapFromList(jibxEntity.getEuropeanaRoleList());
			if (roles != null) {
				if (mongoEntity.getEdmEuropeanaRole() == null
						|| !MongoUtils.mapEquals(roles, mongoEntity.getEdmEuropeanaRole())) {
					ops.set("edmEuropeanaRole", roles);
					update = true;
				}
			}
		} else {
			if (mongoEntity.getEdmEuropeanaRole()!=null){
				ops.unset("edmEuropeanaRole");
				update = true;
			}
		}
		
		if (update) {
			mongoServer.getDatastore().update(updateQuery, ops);
		}
		
	}

}
