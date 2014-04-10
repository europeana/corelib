package eu.europeana.corelib.solr.utils.construct;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.solr.common.SolrInputDocument;

import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.solr.entity.AgentImpl;
/**
 * Generate Agent SOLR fields from Mongo
 * @author Yorgos.Mamakis@ europeana.eu
 *
 */
public class AgentSolrCreator {
	/**
	 * Create SOLR fields from a Mongo Agent
	 * @param doc The solr document to modify
	 * @param agent The agent mongo entity to append
	 */
	public void create(SolrInputDocument doc, AgentImpl agent){
		Collection<Object> values = doc.getFieldValues(
				EdmLabel.EDM_AGENT.toString());
		if (values == null) {
			values = new ArrayList<Object>();
		}
		values.add(agent.getAbout());
		doc.setField(EdmLabel.EDM_AGENT.toString(), values);

		if (agent.getPrefLabel() != null) {
			for (String key : agent.getPrefLabel().keySet()) {
				values = doc.getFieldValues(
						EdmLabel.AG_SKOS_PREF_LABEL.toString() + "." + key);
				if (values == null) {
					values = new ArrayList<Object>();
				}
				values.addAll(agent.getPrefLabel().get(key));
				doc.setField(
						EdmLabel.AG_SKOS_PREF_LABEL.toString() + "." + key,
						values);
			}
		}
		if (agent.getAltLabel() != null) {
			for (String key : agent.getAltLabel().keySet()) {
				values = doc.getFieldValues(
						EdmLabel.AG_SKOS_ALT_LABEL.toString() + "." + key);
				if (values == null) {
					values = new ArrayList<Object>();
				}
				values.addAll(agent.getAltLabel().get(key));
				doc.setField(EdmLabel.AG_SKOS_ALT_LABEL.toString() + "." + key,
						values);
			}
		}
		
		if (agent.getNote() != null) {
			for (String key : agent.getNote().keySet()) {
				values = doc.getFieldValues(
						EdmLabel.AG_SKOS_NOTE.toString());
				if (values == null) {
					values = new ArrayList<Object>();
				}
				values.addAll(agent.getNote().get(key));
				doc.setField(EdmLabel.AG_SKOS_NOTE.toString(),
						values);
			}
		}
		
		if (agent.getDcDate() != null) {
			for (String key : agent.getDcDate().keySet()) {
				values = doc.getFieldValues(
						EdmLabel.AG_DC_DATE.toString() + "." + key);
				if (values == null) {
					values = new ArrayList<Object>();
				}
				values.addAll(agent.getDcDate().get(key));
				doc.setField(EdmLabel.AG_DC_DATE.toString() + "." + key,
						values);
			}
		}
		
		if (agent.getOwlSameAs() != null) {
			for (String key : agent.getOwlSameAs()) {
				values = doc.getFieldValues(
						EdmLabel.AG_OWL_SAMEAS.toString());
				if (values == null) {
					values = new ArrayList<Object>();
				}
				values.add(key);
				doc.setField(EdmLabel.AG_OWL_SAMEAS.toString(),
						values);
			}
		}
		
		if (agent.getDcIdentifier() != null) {
			for (String key : agent.getDcIdentifier().keySet()) {
				values = doc.getFieldValues(
						EdmLabel.AG_DC_IDENTIFIER.toString());
				if (values == null) {
					values = new ArrayList<Object>();
				}
				values.addAll(agent.getDcIdentifier().get(key));
				doc.setField(EdmLabel.AG_DC_IDENTIFIER.toString(),
						values);
			}
		}
		
		if (agent.getBegin() != null) {
			for (String key : agent.getBegin().keySet()) {
				values = doc.getFieldValues(
						EdmLabel.AG_EDM_BEGIN.toString());
				if (values == null) {
					values = new ArrayList<Object>();
				}
				values.addAll(agent.getBegin().get(key));
				doc.setField(EdmLabel.AG_EDM_BEGIN.toString(),
						values);
			}
		}
		
		if (agent.getEnd() != null) {
			for (String key : agent.getEnd().keySet()) {
				values = doc.getFieldValues(
						EdmLabel.AG_EDM_END.toString());
				if (values == null) {
					values = new ArrayList<Object>();
				}
				values.addAll(agent.getEnd().get(key));
				doc.setField(EdmLabel.AG_EDM_END.toString(),
						values);
			}
		}
		
		
		if (agent.getEnd() != null) {
			for (String key : agent.getEnd().keySet()) {
				values = doc.getFieldValues(
						EdmLabel.AG_EDM_END.toString());
				if (values == null) {
					values = new ArrayList<Object>();
				}
				values.addAll(agent.getEnd().get(key));
				doc.setField(EdmLabel.AG_EDM_END.toString(),
						values);
			}
		}
		
		if (agent.getEdmWasPresentAt() != null) {
			for (String key : agent.getEdmWasPresentAt()) {
				values = doc.getFieldValues(
						EdmLabel.AG_EDM_WASPRESENTAT.toString());
				if (values == null) {
					values = new ArrayList<Object>();
				}
				values.add(key);
				doc.setField(EdmLabel.AG_EDM_WASPRESENTAT.toString(),
						values);
			}
		}
		

		if (agent.getEdmHasMet() != null) {
			for (String key : agent.getEdmHasMet().keySet()) {
				values = doc.getFieldValues(
						EdmLabel.AG_EDM_HASMET.toString());
				if (values == null) {
					values = new ArrayList<Object>();
				}
				values.addAll(agent.getEdmHasMet().get(key));
				doc.setField(EdmLabel.AG_EDM_HASMET.toString(),
						values);
			}
		}
		

		if (agent.getEdmIsRelatedTo() != null) {
			for (String key : agent.getEdmIsRelatedTo().keySet()) {
				values = doc.getFieldValues(
						EdmLabel.AG_EDM_ISRELATEDTO.toString());
				if (values == null) {
					values = new ArrayList<Object>();
				}
				values.addAll(agent.getEdmIsRelatedTo().get(key));
				doc.setField(EdmLabel.AG_EDM_ISRELATEDTO.toString(),
						values);
			}
		}
		

		if (agent.getFoafName() != null) {
			for (String key : agent.getFoafName().keySet()) {
				values = doc.getFieldValues(
						EdmLabel.AG_FOAF_NAME.toString());
				if (values == null) {
					values = new ArrayList<Object>();
				}
				values.addAll(agent.getFoafName().get(key));
				doc.setField(EdmLabel.AG_FOAF_NAME.toString(),
						values);
			}
		}
		

		if (agent.getRdaGr2DateOfBirth() != null) {
			for (String key : agent.getRdaGr2DateOfBirth().keySet()) {
				values = doc.getFieldValues(
						EdmLabel.AG_RDAGR2_DATEOFBIRTH.toString());
				if (values == null) {
					values = new ArrayList<Object>();
				}
				values.addAll(agent.getRdaGr2DateOfBirth().get(key));
				doc.setField(EdmLabel.AG_RDAGR2_DATEOFBIRTH.toString(),
						values);
			}
		}
		
		if (agent.getRdaGr2DateOfDeath() != null) {
			for (String key : agent.getRdaGr2DateOfDeath().keySet()) {
				values = doc.getFieldValues(
						EdmLabel.AG_RDAGR2_DATEOFDEATH.toString());
				if (values == null) {
					values = new ArrayList<Object>();
				}
				values.addAll(agent.getRdaGr2DateOfDeath().get(key));
				doc.setField(EdmLabel.AG_RDAGR2_DATEOFDEATH.toString(),
						values);
			}
		}
		
		
//		if (agent.getRdaGr2PlaceOfBirth() != null) {
//			for (String key : agent.getRdaGr2PlaceOfBirth().keySet()) {
//				values = doc.getFieldValues(
//						EdmLabel.AG_RDAGR2_PLACEOFBIRTH.toString());
//				if (values == null) {
//					values = new ArrayList<Object>();
//				}
//				values.addAll(agent.getRdaGr2PlaceOfBirth().get(key));
//				doc.setField(EdmLabel.AG_RDAGR2_PLACEOFBIRTH.toString(),
//						values);
//			}
//		}
//		
//		if (agent.getRdaGr2PlaceOfDeath() != null) {
//			for (String key : agent.getRdaGr2PlaceOfDeath().keySet()) {
//				values = doc.getFieldValues(
//						EdmLabel.AG_RDAGR2_PLACEOFDEATH.toString());
//				if (values == null) {
//					values = new ArrayList<Object>();
//				}
//				values.addAll(agent.getRdaGr2PlaceOfDeath().get(key));
//				doc.setField(EdmLabel.AG_RDAGR2_PLACEOFDEATH.toString(),
//						values);
//			}
//		}
		
		
		if (agent.getRdaGr2DateOfEstablishment() != null) {
			for (String key : agent.getRdaGr2DateOfEstablishment().keySet()) {
				values = doc.getFieldValues(
						EdmLabel.AG_RDAGR2_DATEOFESTABLISHMENT.toString());
				if (values == null) {
					values = new ArrayList<Object>();
				}
				values.addAll(agent.getRdaGr2DateOfEstablishment().get(key));
				doc.setField(EdmLabel.AG_RDAGR2_DATEOFESTABLISHMENT.toString(),
						values);
			}
		}
		
		if (agent.getRdaGr2DateOfTermination() != null) {
			for (String key : agent.getRdaGr2DateOfTermination().keySet()) {
				values = doc.getFieldValues(
						EdmLabel.AG_RDAGR2_DATEOFTERMINATION.toString());
				if (values == null) {
					values = new ArrayList<Object>();
				}
				values.addAll(agent.getRdaGr2DateOfTermination().get(key));
				doc.setField(EdmLabel.AG_RDAGR2_DATEOFTERMINATION.toString(),
						values);
			}
		}
		
		if (agent.getRdaGr2Gender() != null) {
			for (String key : agent.getRdaGr2Gender().keySet()) {
				values = doc.getFieldValues(
						EdmLabel.AG_RDAGR2_GENDER.toString());
				if (values == null) {
					values = new ArrayList<Object>();
				}
				values.addAll(agent.getRdaGr2Gender().get(key));
				doc.setField(EdmLabel.AG_RDAGR2_GENDER.toString(),
						values);
			}
		}
		
		if (agent.getRdaGr2ProfessionOrOccupation() != null) {
			for (String key : agent.getRdaGr2ProfessionOrOccupation().keySet()) {
				values = doc.getFieldValues(
						EdmLabel.AG_RDAGR2_PROFESSIONOROCCUPATION.toString());
				if (values == null) {
					values = new ArrayList<Object>();
				}
				values.addAll(agent.getRdaGr2ProfessionOrOccupation().get(key));
				doc.setField(EdmLabel.AG_RDAGR2_PROFESSIONOROCCUPATION.toString(),
						values);
			}
		}
		
		if (agent.getRdaGr2BiographicalInformation() != null) {
			for (String key : agent.getRdaGr2BiographicalInformation().keySet()) {
				values = doc.getFieldValues(
						EdmLabel.AG_RDAGR2_BIOGRAPHICALINFORMATION.toString());
				if (values == null) {
					values = new ArrayList<Object>();
				}
				values.addAll(agent.getRdaGr2BiographicalInformation().get(key));
				doc.setField(EdmLabel.AG_RDAGR2_BIOGRAPHICALINFORMATION.toString(),
						values);
			}
		}
	}
}
