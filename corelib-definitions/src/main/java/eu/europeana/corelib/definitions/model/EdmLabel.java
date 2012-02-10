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

package eu.europeana.corelib.definitions.model;

/**
 * TO BE UPDATED
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public enum EdmLabel {
	
	TIMESTAMP("timestamp"),
	UID("uid"),
	EDM_TYPE("edm_type"),
	ORE_AGGREGATION("ore_aggregation"),
	EDM_AGGREGATION("edm_aggregation"),
	EDM_AGGREGATED_CHO("edm_aggregatedCHO"),
	EDM_DATA_PROVIDER("edm_dataProvider"),
	EDM_HASVIEW("edm_hasView"),
	EDM_IS_SHOWN_BY("edm_isShownBy"),
	EDM_IS_SHOWN_AT("edm_isShownAt"),
	EDM_OBJECT("edm_object"),
	EDM_PROVIDER("edm_provider"),
	AGGR_DC_RIGHTS("aggr_dc_rights"),
	AGGR_EDM_RIGHTS("aggr_edm_rights"),
	EDM_WEB_RESOURCE("edm_webResource"),
	WR_DC_RIGHTS("wr_dc_rights"),
	WR_EDM_RIGHTS("wr_edm_rights"),
	EDM_PROVIDED_CHO("edm_providedCHO"),
	ORE_PROXY_IN("ore_proxyIn"),
	ORE_PROXY("ore_proxy"),
	OWL_SAMEAS("owl_sameAs"),
	DC_COVERAGE("dc_coverage"),
	DC_CONTRIBUTOR("dc_contributor"),
	DC_DESCRIPTION("dc_description"),
	DC_CREATOR("dc_creator"),
	DC_DATE("dc_date"),
	DC_FORMAT("dc_format"),
	DC_IDENTIFIER("dc_identifier"),
	DC_LANGUAGE("dc_language"),
	DC_PUBLISHER("dc_publisher"),
	DC_RELATION("dc_relation"),
	DC_RIGHTS("dc_rights"),
	DC_SOURCE("dc_source"),
	DC_SUBJECT("dc_subject"),
	DC_TITLE("dc_title"),
	DC_TYPE("dc_type"),
	DCTERMS_ALTERNATIVE("dcterms_alternative"),
	DCTERMS_CREATED("dcterms_created"),
	DCTERMS_CONFORMS_TO("dcterms_conformsTo"),
	DCTERMS_EXTENT("dcterms_extent"),
	DCTERMS_HAS_FORMAT("dcterms_hasFormat"),
	DCTERMS_HAS_PART("dcterms_hasPart"),
	DCTERMS_HAS_VERSION("dcterms_hasVersion"),
	DCTERMS_IS_FORMAT_OF("dcterms_isFormatOf"),
	DCTERMS_IS_PART_OF("dcterms_isPartOf"),
	DCTERMS_IS_REFERENCED_BY("dcterms_isReferencedBy"),
	DCTERMS_IS_REPLACED_BY("dcterms_isReplacedBy"),
	DCTERMS_IS_REQUIRED_BY("dcterms_isRequiredBy"),
	DCTERMS_ISSUED("dcterms_issued"),
	DCTERMS_IS_VERSION_OF("dcterms_isVersionOf"),
	DCTERMS_MEDIUM("dcterms_medium"),
	DCTERMS_PROVENANCE("dcterms_provenance"),
	DCTERMS_REFERENCES("dcterms_references"),
	DCTERMS_REPLACES("dcterms_replaces"),
	DCTERMS_REQUIRES("dcterms_requires"),
	DCTERMS_SPATIAL("dcterms_spatial"),
	DCTERMS_TABLE_OF_CONTENTS("dcterms_tableOfContents"),
	DCTERMS_TEMPORAL("dcterms_temporal"),
	EDM_UGC("edm_UGC"),
	EDM_CURRENT_LOCATION("edm_currentLocation"),
	LANGUAGE("language"),
	EDM_IS_NEXT_IN_SEQUENCE("edm_isNextInSequence"),
	SKOS_CONCEPT("skos_concept"),
	CC_SKOS_PREF_LABEL("cc_skos_prefLabel"),
	CC_SKOS_ALT_LABEL("cc_skos_altLabel"),
	CC_SKOS_NOTE("cc_skos_note"),
	CC_SKOS_BROADER("cc_skos_broader"),
	EDM_PLACE("edm_place"),
	PL_SKOS_PREF_LABEL("pl_skos_prefLabel"),
	PL_SKOS_ALT_LABEL("pl_skos_altLabel"),
	PL_SKOS_NOTE("pl_skos_note"),
	PL_POSITION("pl_position"),
	PL_DCTERMS_ISPART_OF("pl_dcterm_isPartOf"),
	EDM_TIMESPAN("edm_timespan"),
	TS_SKOS_PREF_LABEL("ts_skos_prefLabel"),
	TS_SKOS_ALT_LABEL("ts_skos_altLabel"),
	TS_SKOS_NOTE("ts_skos_note"),
	TS_EDM_BEGIN("ts_edm_begin"),
	TS_EDM_END("ts_edm_end"),
	TS_DCTERMS_ISPARTS_OF("ts_dcterms_isPartOf"),
	EDM_AGENT("edm_agent"),
	AG_SKOS_PREF_LABEL("ag_skos_prefLabel"),
	AG_SKOS_ALT_LABEL("ag_skos_altLabel"),
	AG_SKOS_NOTE("ag_skos_note"),
	AG_EDM_BEGIN("ag_edm_begin"),
	AG_EDM_END("ag_edm_end");

	private String value;

	EdmLabel(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
