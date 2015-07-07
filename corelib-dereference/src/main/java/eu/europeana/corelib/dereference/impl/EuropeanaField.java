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
package eu.europeana.corelib.dereference.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * Class holding the mappable Europeana Fields for denormalization
 * 
 * @author yorgos.mamakis@ kb.nl
 * 
 */
public class EuropeanaField {
	/**
	 * Enumeration holding the fields that should be checked for normalization
	 * 
	 * @author Yorgos.Mamakis@ kb.nl
	 * 
	 */

	private UriField uriField;

	enum UriField {

		EDM_DATAPROVIDER("provider_aggregation_edm_dataProvider"), EDM_PROVIDER(
				"provider_aggregation_edm_provider"), DC_CREATOR(
				"provider_dc_creator"), DC_CONTRIBUTOR(
				"provider_dc_contributor"), DC_DATE("provider_dc_date"), DC_DESCRIPTION(
				"provider_dc_description"), DC_PUBLISHER(
				"provider_dc_publisher"), DC_RELATION("provider_dc_relation"), DC_SUBJECT(
				"provider_dc_subject"), DC_TYPE("provider_dc_type"), DCTERMS_CREATED(
				"provider_dcterms_created"), DCTERMS_EXTENT(
				"provider_dcterms_extent"), DCTERMS_ISSUED(
				"provider_dcterms_issued"), DCTERMS_PROVENANCE(
				"provider_dcterms_provenance"), DCTERMS_REPLACES(
				"provider_dcterms_replaces"), DCTERMS_SPATIAL(
				"provider_dcterms_spatial"), DCTERMS_TABLEOFCONTENTS(
				"provider_dcterms_tableOfContents"), DCTERMS_TEMPORAL(
				"provider_dcterms_temporal"), SKOS_BROADER("cc_skos_broader"), EDM_CURRENTLOCATION_LAT(
				"provider_edm_currentLocation_lat"), DCTERMS_ISPARTOF(
				"provider_dcterms_isPartOf"), EDM_AGENT("edm_agent"), EDM_AGENT_PREFLABEL(
				"ag_skos_prefLabel"), EMD_AGENT_ALT_LABEL("ag_skos_altLabel"), EDM_TIMESPAN(
				"edm_timespan"), EDM_TIMESPAN_SKOS_PREFLABEL(
				"ts_skos_prefLabel"), EDM_TIMESPAN_SKOS_ALTLABEL(
				"ts_skos_altLabel"), EDM_PLACE("edm_place"), EDM_PLACE_SKOS_PREFLABEL(
				"pl_skos_prefLabel"), EDM_PLACE_SKOS_ALTLABEL(
				"pl_skos_altLabel"), SKOS_CONCEPT("skos_concept"), SKOS_CONCEPT_SKOS_PREFLABEL(
				"cc_skos_prefLabel"), SKOS_CONCEPT_SKOS_ALTLABEL(
				"cc_skos_altLabel"), SKOS_CONCEPT_BROADER("cc_skos_broader")

		;
		private String uriField;

		UriField(String uriField) {
			this.uriField = uriField;
		}

		public String toString() {
			return this.uriField;
		}
	}

	/**
	 * Get the value of the enumeration
	 * @return
	 */
	public String getFieldValue() {
		return this.uriField.toString();
	}

	/**
	 * Get all the fields of the enumeration
	 * @return
	 */
	public UriField[] getFields() {
		return UriField.values();
	}

	/**
	 * Get the values of the enumerated fields
	 * @return An array containing the field values
	 */
	public String[] getFieldValues() {
		List<String> values = new ArrayList<String>();
		for (UriField uriField : UriField.values()) {
			values.add(uriField.toString());
		}
		return values.toArray(new String[values.size()]);
	}

	/**
	 * Return a URI field 
	 * @param field
	 * @return
	 */
	public UriField getField(String field) {
		return this.uriField;
	}

	/**
	 * Setter for internal string field
	 * @param field
	 */
	public void setField(UriField field) {
		this.uriField = field;
	}

	/**
	 * Method to check if a field is contained in the enumeration
	 * @param field - the field to check
	 * @return true if exists, false otherwise
	 */
	public static boolean contains(String field) {
		for (UriField uriField : UriField.values()) {
			if (StringUtils.equals(uriField.toString(), field)) {
				return true;
			}
		}
		return false;
	}
}
