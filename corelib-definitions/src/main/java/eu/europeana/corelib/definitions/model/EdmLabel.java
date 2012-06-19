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

import org.apache.commons.lang.StringUtils;

/**
 * Enumeration holding the Solr Structure and field definitions
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public enum EdmLabel {

	TIMESTAMP("timestamp", SolrType.INDEXED), 
	EUROPEANA_COLLECTIONNAME("europeana_collectionName", SolrType.INDEXED), 
	EUROPEANA_ID("europeana_id", SolrType.INDEXED), 
	PROVIDER_EDM_TYPE("provider_edm_type", SolrType.FACET),
	
	// Provider Aggregation Fields
	PROVIDER_AGGREGATION_ORE_AGGREGATION("provider_aggregation_ore_aggregation", SolrType.NOT_STORED), 
	PROVIDER_AGGREGATION_ORE_AGGREGATES("provider_aggregation_ore_aggregates", SolrType.NOT_STORED), 
	PROVIDER_AGGREGATION_EDM_AGGREGATED_CHO("provider_aggregation_edm_aggregatedCHO", SolrType.NOT_STORED), 
	PROVIDER_AGGREGATION_EDM_DATA_PROVIDER("provider_aggregation_edm_dataProvider", SolrType.FACET), 
	PROVIDER_AGGREGATION_EDM_HASVIEW("provider_aggregation_edm_hasView", SolrType.INDEXED),
	PROVIDER_AGGREGATION_EDM_IS_SHOWN_BY("provider_aggregation_edm_isShownBy", SolrType.INDEXED), 
	PROVIDER_AGGREGATION_EDM_IS_SHOWN_AT("provider_aggregation_edm_isShownAt", SolrType.INDEXED), 
	PROVIDER_AGGREGATION_EDM_OBJECT("provider_aggregation_edm_object", SolrType.INDEXED), 
	PROVIDER_AGGREGATION_EDM_PROVIDER("provider_aggregation_edm_provider", SolrType.NOT_STORED), 
	PROVIDER_AGGREGATION_DC_RIGHTS("provider_aggregation_dc_rights", SolrType.FACET), 
	PROVIDER_AGGREGATION_EDM_RIGHTS("provider_aggregation_edm_rights", SolrType.FACET), 
	PROVIDER_AGGREGATION_EDM_UNSTORED("provider_aggregation_edm_unstored", SolrType.NOT_STORED),

	// EUROPEANA AGGREGATION
	EDM_EUROPEANA_AGGREGATION("europeana_edm_aggregation", SolrType.INDEXED), 
	EUROPEANA_AGGREGATION_ORE_AGGREGATEDCHO("europeana_aggregation_ore_aggregatedCHO", SolrType.INDEXED), 
	EUROPEANA_AGGREGATION_ORE_AGGREGATES("europeana_aggregation_ore_aggregates", SolrType.NOT_STORED), 
	EUROPEANA_AGGREGATION_DC_CREATOR("europeana_aggregation_dc_creator", SolrType.NOT_STORED), 
	EUROPEANA_AGGREGATION_EDM_LANDINGPAGE("europeana_aggregation_edm_landingPage", SolrType.NOT_STORED), 
	EUROPEANA_AGGREGATION_EDM_ISSHOWNBY("europeana_aggregation_edm_isShownBy", SolrType.INDEXED), 
	EUROPEANA_AGGREGATION_EDM_HASVIEW("europeana_aggregation_edm_hasView", SolrType.INDEXED), 
	EUROPEANA_AGGREGATION_EDM_COUNTRY("europeana_aggregation_edm_country", SolrType.INDEXED), 
	EUROPEANA_AGGREGATION_EDM_LANGUAGE("europeana_aggregation_edm_language", SolrType.INDEXED), 
	EUROPEANA_AGGREGATION_EDM_RIGHTS("europeana_aggregation_edm_rights", SolrType.NOT_STORED),

	// WEB RESOURCE FIELDS
	EDM_WEB_RESOURCE("edm_webResource", SolrType.NOT_STORED), 
	WR_DC_RIGHTS("wr_dc_rights", SolrType.FACET), 
	WR_EDM_RIGHTS("wr_edm_rights",SolrType.FACET), 
	WR_DC_DESCRIPTION("wr_dc_description",SolrType.NOT_STORED), 
	WR_DC_FORMAT("wr_dc_format",SolrType.NOT_STORED), 
	WR_DC_SOURCE("wr_dc_source",SolrType.NOT_STORED), 
	WR_DCTERMS_EXTENT("wr_dcterms_extent",SolrType.NOT_STORED), 
	WR_DCTERMS_ISSUED("wr_dcterms_issued",SolrType.NOT_STORED), 
	WR_DCTERMS_CONFORMSTO("wr_dcterms_conformsTo", SolrType.NOT_STORED), 
	WR_DCTERMS_CREATED("wr_dcterms_created", SolrType.NOT_STORED), 
	WR_DCTERMS_ISFORMATOF("wr_dcterms_isFormatOf", SolrType.NOT_STORED), 
	WR_DCTERMS_HAS_PART("wr_dcterms_hasPart", SolrType.NOT_STORED), 
	WR_DCTERMS_IS_NEXT_IN_SEQUENCE("wr_edm_isNextInSequence", SolrType.NOT_STORED),

	// PROVIDER PROXY
	PROVIDER_ORE_PROXY("provider_ore_proxy", SolrType.INDEXED), 
	PROVIDER_OWL_SAMEAS("provider_owl_sameAs", SolrType.INDEXED), 
	PROVIDER_DC_COVERAGE("provider_dc_coverage", SolrType.FACET), 
	PROVIDER_DC_CONTRIBUTOR("provider_dc_contributor", SolrType.NOT_STORED), 
	PROVIDER_DC_DESCRIPTION("provider_dc_description", SolrType.NOT_STORED), 
	PROVIDER_DC_CREATOR("provider_dc_creator", SolrType.FACET), 
	PROVIDER_DC_DATE("provider_dc_date", SolrType.FACET), 
	PROVIDER_DC_FORMAT("provider_dc_format", SolrType.NOT_STORED), 
	PROVIDER_DC_IDENTIFIER("provider_dc_identifier", SolrType.NOT_STORED), 
	PROVIDER_DC_LANGUAGE("provider_dc_language", SolrType.NOT_STORED), 
	PROVIDER_DC_PUBLISHER("provider_dc_publisher", SolrType.NOT_STORED), 
	PROVIDER_DC_RELATION("provider_dc_relation", SolrType.NOT_STORED), 
	PROVIDER_DC_RIGHTS("provider_dc_rights", SolrType.FACET), 
	PROVIDER_EDM_RIGHTS("provider_edm_rights", SolrType.FACET), 
	PROVIDER_DC_SOURCE("provider_dc_source", SolrType.NOT_STORED), 
	PROVIDER_DC_SUBJECT("provider_dc_subject", SolrType.NOT_STORED), 
	PROVIDER_DC_TITLE("provider_dc_title", SolrType.NOT_STORED), 
	PROVIDER_DC_TYPE("provider_dc_type", SolrType.FACET), 
	PROVIDER_DCTERMS_ALTERNATIVE("provider_dcterms_alternative", SolrType.FACET), 
	PROVIDER_DCTERMS_CREATED("provider_dcterms_created", SolrType.NOT_STORED), 
	PROVIDER_DCTERMS_CONFORMS_TO("provider_dcterms_conformsTo", SolrType.NOT_STORED), 
	PROVIDER_DCTERMS_EXTENT("provider_dcterms_extent", SolrType.NOT_STORED), 
	PROVIDER_DCTERMS_HAS_FORMAT("provider_dcterms_hasFormat", SolrType.NOT_STORED), 
	PROVIDER_DCTERMS_HAS_PART("provider_dcterms_hasPart", SolrType.NOT_STORED), 
	PROVIDER_DCTERMS_HAS_VERSION("provider_dcterms_hasVersion", SolrType.NOT_STORED), 
	PROVIDER_DCTERMS_IS_FORMAT_OF("provider_dcterms_isFormatOf", SolrType.NOT_STORED), 
	PROVIDER_DCTERMS_IS_PART_OF("provider_dcterms_isPartOf", SolrType.NOT_STORED), 
	PROVIDER_DCTERMS_IS_REFERENCED_BY("provider_dcterms_isReferencedBy", SolrType.NOT_STORED), 
	PROVIDER_DCTERMS_IS_REPLACED_BY("provider_dcterms_isReplacedBy", SolrType.NOT_STORED), 
	PROVIDER_DCTERMS_IS_REQUIRED_BY("provider_dcterms_isRequiredBy", SolrType.NOT_STORED), 
	PROVIDER_DCTERMS_ISSUED("provider_dcterms_issued", SolrType.NOT_STORED), 
	PROVIDER_DCTERMS_IS_VERSION_OF("provider_dcterms_isVersionOf", SolrType.NOT_STORED), 
	PROVIDER_DCTERMS_MEDIUM("provider_dcterms_medium", SolrType.NOT_STORED), 
	PROVIDER_DCTERMS_PROVENANCE("provider_dcterms_provenance", SolrType.NOT_STORED), 
	PROVIDER_DCTERMS_REFERENCES("provider_dcterms_references", SolrType.NOT_STORED), 
	PROVIDER_DCTERMS_REPLACES("provider_dcterms_replaces", SolrType.NOT_STORED), 
	PROVIDER_DCTERMS_REQUIRES("provider_dcterms_requires", SolrType.NOT_STORED), 
	PROVIDER_DCTERMS_SPATIAL("provider_dcterms_spatial", SolrType.NOT_STORED), 
	PROVIDER_DCTERMS_TABLE_OF_CONTENTS("provider_dcterms_tableOfContents", SolrType.INDEXED), 
	PROVIDER_DCTERMS_TEMPORAL("provider_dcterms_temporal", SolrType.FACET), 
	EDM_UGC("edm_UGC",SolrType.FACET), 
	PROVIDER_EDM_CURRENT_LOCATION_LAT("provider_edm_currentLocation_lat", SolrType.NOT_STORED), 
	PROVIDER_EDM_CURRENT_LOCATION_LONG("provider_edm_currentLocation_long", SolrType.NOT_STORED), 
	PROVIDER_EDM_UNSTORED("provider_edm_unstored", SolrType.NOT_STORED), 
	PROVIDER_EDM_IS_NEXT_IN_SEQUENCE("provider_edm_isNextInSequence", SolrType.NOT_STORED), 
	PROVIDER_EDM_HAS_TYPE("provider_edm_hasType", SolrType.NOT_STORED), 
	PROVIDER_EDM_INCORPORATES("provider_edm_incorporates", SolrType.NOT_STORED), 
	PROVIDER_EDM_ISDERIVATIVE_OF("provider_edm_isDerivativeOf", SolrType.NOT_STORED), 
	PROVIDER_EDM_ISRELATEDTO("provider_edm_isRelatedTo", SolrType.NOT_STORED), 
	PROVIDER_EDM_ISREPRESENTATIONOF("provider_edm_isRepresentationOf", SolrType.NOT_STORED), 
	PROVIDER_EDM_ISSIMILARTO("provider_edm_isSimilarTo", SolrType.NOT_STORED), 
	PROVIDER_EDM_ISSUCCESSOROF("provider_edm_isSuccessorOf", SolrType.NOT_STORED), 
	PROVIDER_EDM_REALIZES("provider_edm_realizes", SolrType.NOT_STORED), 
	PROVIDER_EDM_WASPRESENTAT("provider_edm_wasPresentAt", SolrType.NOT_STORED),
	PROVIDER_ORE_PROXY_IN("provider_ore_proxyIn",SolrType.INDEXED), 
	PROVIDER_ORE_PROXY_FOR("provider_ore_proxyFor", SolrType.INDEXED),
	
	//EUROPEANA PROXY 
	EUROPEANA_ORE_PROXY("europeana_ore_proxy", SolrType.INDEXED), 
	EUROPEANA_OWL_SAMEAS("europeana_owl_sameAs", SolrType.INDEXED), 
	EUROPEANA_DC_COVERAGE("europeana_dc_coverage", SolrType.FACET), 
	EUROPEANA_DC_CONTRIBUTOR("europeana_dc_contributor", SolrType.NOT_STORED), 
	EUROPEANA_DC_DESCRIPTION("europeana_dc_description", SolrType.NOT_STORED), 
	EUROPEANA_DC_CREATOR("europeana_dc_creator", SolrType.FACET), 
	EUROPEANA_DC_DATE("europeana_dc_date", SolrType.FACET), 
	EUROPEANA_DC_FORMAT("europeana_dc_format", SolrType.NOT_STORED), 
	EUROPEANA_DC_IDENTIFIER("europeana_dc_identifier", SolrType.NOT_STORED), 
	EUROPEANA_DC_LANGUAGE("europeana_dc_language", SolrType.NOT_STORED), 
	EUROPEANA_DC_PUBLISHER("europeana_dc_publisher", SolrType.NOT_STORED), 
	EUROPEANA_DC_RELATION("europeana_dc_relation", SolrType.NOT_STORED), 
	EUROPEANA_DC_RIGHTS("europeana_dc_rights", SolrType.FACET), 
	EUROPEANA_EDM_RIGHTS("europeana_edm_rights", SolrType.FACET), 
	EUROPEANA_DC_SOURCE("europeana_dc_source", SolrType.NOT_STORED), 
	EUROPEANA_DC_SUBJECT("europeana_dc_subject", SolrType.NOT_STORED), 
	EUROPEANA_DC_TITLE("europeana_dc_title", SolrType.NOT_STORED), 
	EUROPEANA_DC_TYPE("europeana_dc_type", SolrType.FACET), 
	EUROPEANA_DCTERMS_ALTERNATIVE("europeana_dcterms_alternative", SolrType.FACET), 
	EUROPEANA_DCTERMS_CREATED("europeana_dcterms_created", SolrType.NOT_STORED), 
	EUROPEANA_DCTERMS_CONFORMS_TO("europeana_dcterms_conformsTo", SolrType.NOT_STORED), 
	EUROPEANA_DCTERMS_EXTENT("europeana_dcterms_extent", SolrType.NOT_STORED), 
	EUROPEANA_DCTERMS_HAS_FORMAT("europeana_dcterms_hasFormat", SolrType.NOT_STORED), 
	EUROPEANA_DCTERMS_HAS_PART("europeana_dcterms_hasPart", SolrType.NOT_STORED), 
	EUROPEANA_DCTERMS_HAS_VERSION("europeana_dcterms_hasVersion", SolrType.NOT_STORED), 
	EUROPEANA_DCTERMS_IS_FORMAT_OF("europeana_dcterms_isFormatOf", SolrType.NOT_STORED), 
	EUROPEANA_DCTERMS_IS_PART_OF("europeana_dcterms_isPartOf", SolrType.NOT_STORED), 
	EUROPEANA_DCTERMS_IS_REFERENCED_BY("europeana_dcterms_isReferencedBy", SolrType.NOT_STORED), 
	EUROPEANA_DCTERMS_IS_REPLACED_BY("europeana_dcterms_isReplacedBy", SolrType.NOT_STORED), 
	EUROPEANA_DCTERMS_IS_REQUIRED_BY("europeana_dcterms_isRequiredBy", SolrType.NOT_STORED), 
	EUROPEANA_DCTERMS_ISSUED("europeana_dcterms_issued", SolrType.NOT_STORED), 
	EUROPEANA_DCTERMS_IS_VERSION_OF("europeana_dcterms_isVersionOf", SolrType.NOT_STORED), 
	EUROPEANA_DCTERMS_MEDIUM("europeana_dcterms_medium", SolrType.NOT_STORED), 
	EUROPEANA_DCTERMS_PROVENANCE("europeana_dcterms_provenance", SolrType.NOT_STORED), 
	EUROPEANA_DCTERMS_REFERENCES("europeana_dcterms_references", SolrType.NOT_STORED), 
	EUROPEANA_DCTERMS_REPLACES("europeana_dcterms_replaces", SolrType.NOT_STORED), 
	EUROPEANA_DCTERMS_REQUIRES("europeana_dcterms_requires", SolrType.NOT_STORED), 
	EUROPEANA_DCTERMS_SPATIAL("europeana_dcterms_spatial", SolrType.NOT_STORED), 
	EUROPEANA_DCTERMS_TABLE_OF_CONTENTS("europeana_dcterms_tableOfContents", SolrType.INDEXED), 
	EUROPEANA_DCTERMS_TEMPORAL("europeana_dcterms_temporal", SolrType.FACET), 
	EUROPEANA_EDM_USERTAG("europeana_edm_userTag",SolrType.NOT_STORED), 
	EUROPEANA_EDM_YEAR("europeana_edm_year", SolrType.NOT_STORED),
	EUROPEANA_EDM_CURRENT_LOCATION_LAT("europeana_edm_currentLocation_lat", SolrType.NOT_STORED), 
	EUROPEANA_EDM_CURRENT_LOCATION_LONG("europeana_edm_currentLocation_long", SolrType.NOT_STORED), 
	EUROPEANA_EDM_UNSTORED("europeana_edm_unstored", SolrType.NOT_STORED), 
	EUROPEANA_EDM_IS_NEXT_IN_SEQUENCE("europeana_edm_isNextInSequence", SolrType.NOT_STORED), 
	EUROPEANA_EDM_HAS_TYPE("europeana_edm_hasType", SolrType.NOT_STORED), 
	EUROPEANA_EDM_INCORPORATES("europeana_edm_incorporates", SolrType.NOT_STORED), 
	EUROPEANA_EDM_ISDERIVATIVE_OF("europeana_edm_isDerivativeOf", SolrType.NOT_STORED), 
	EUROPEANA_EDM_ISRELATEDTO("europeana_edm_isRelatedTo", SolrType.NOT_STORED), 
	EUROPEANA_EDM_ISREPRESENTATIONOF("europeana_edm_isRepresentationOf", SolrType.NOT_STORED), 
	EUROPEANA_EDM_ISSIMILARTO("europeana_edm_isSimilarTo", SolrType.NOT_STORED), 
	EUROPEANA_EDM_ISSUCCESSOROF("europeana_edm_isSuccessorOf", SolrType.NOT_STORED), 
	EUROPEANA_EDM_REALIZES("europeana_edm_realizes", SolrType.NOT_STORED), 
	EUROPEANA_EDM_WASPRESENTAT("europeana_edm_wasPresentAt", SolrType.NOT_STORED),
	EUROPEANA_ORE_PROXY_IN("europeana_ore_proxyIn",SolrType.INDEXED), 
	EUROPEANA_ORE_PROXY_FOR("europeana_ore_proxyFor", SolrType.INDEXED),

	//SKOS_CONCEPT
	SKOS_CONCEPT("skos_concept", SolrType.NOT_STORED), 
	CC_SKOS_PREF_LABEL("cc_skos_prefLabel", SolrType.FACET), 
	CC_SKOS_ALT_LABEL("cc_skos_altLabel", SolrType.NOT_STORED), 
	CC_SKOS_NOTE("cc_skos_note", SolrType.NOT_STORED), 
	CC_SKOS_BROADER("cc_skos_broader", SolrType.NOT_STORED), 
	CC_SKOS_HIDDEN_LABEL("cc_skos_hiddenLabel", SolrType.NOT_STORED),
	CC_SKOS_NARROWER("cc_skos_narrower", SolrType.NOT_STORED),
	CC_SKOS_RELATED("cc_skos_related", SolrType.NOT_STORED),
	CC_SKOS_BROADDMATCH("cc_skos_broadMatch", SolrType.NOT_STORED),
	CC_SKOS_NARROWMATCH("cc_skos_narrowMatch", SolrType.NOT_STORED),
	CC_SKOS_RELATEDMATCH("cc_skos_relatedMatch", SolrType.NOT_STORED),
	CC_SKOS_EXACTMATCH("cc_skos_exactMatch", SolrType.NOT_STORED),
	CC_SKOS_CLOSEMATCH("cc_skos_closeMatch", SolrType.NOT_STORED),
	CC_SKOS_NOTATIONS("cc_skos_notations", SolrType.NOT_STORED),
	CC_SKOS_INSCHEME("cc_skos_inScheme", SolrType.NOT_STORED),
	
	//PLACE
	EDM_PLACE("edm_place",SolrType.NOT_STORED), 
	PL_SKOS_PREF_LABEL("pl_skos_prefLabel",SolrType.NOT_STORED), 
	PL_SKOS_ALT_LABEL("pl_skos_altLabel",SolrType.NOT_STORED), 
	PL_SKOS_NOTE("pl_skos_note",SolrType.NOT_STORED), 
	PL_DCTERMS_ISPART_OF("pl_dcterm_isPartOf",SolrType.NOT_STORED), 
	PL_WGS84_POS_LAT("pl_wgs84_pos_lat", SolrType.NOT_STORED),
	PL_WGS84_POS_LONG("pl_wgs84_pos_long", SolrType.NOT_STORED),
	PL_WGS84_POS_ALT("pl_wgs84_pos_alt", SolrType.NOT_STORED),
	PL_WGS84_POS_LAT_LONG("pl_wgs84_pos_lat_long", SolrType.NOT_STORED),
	PL_SKOS_HIDDENLABEL("pl_skos_hiddenLabel", SolrType.NOT_STORED),
	PL_DCTERMS_HASPART("pl_dcterms_hasPart", SolrType.NOT_STORED),
	PL_OWL_SAMEAS("pl_owl_sameAs", SolrType.NOT_STORED),
	
	
	//TIMESPAN
	EDM_TIMESPAN("edm_timespan",SolrType.NOT_STORED), 
	TS_SKOS_PREF_LABEL("ts_skos_prefLabel",	SolrType.NOT_STORED), 
	TS_SKOS_ALT_LABEL("ts_skos_altLabel",SolrType.NOT_STORED), 
	TS_SKOS_NOTE("ts_skos_note",SolrType.NOT_STORED), 
	TS_EDM_BEGIN("ts_edm_begin",SolrType.NOT_STORED), 
	TS_EDM_END("ts_edm_end", SolrType.NOT_STORED), 
	TS_DCTERMS_ISPART_OF("ts_dcterms_isPartOf", SolrType.NOT_STORED), 
	TS_SKOS_HIDDENLABEL("ts_skos_hiddenLabel", SolrType.NOT_STORED),
	TS_DCTERMS_HASPART("ts_dcterms_hasPart", SolrType.NOT_STORED),
	TS_OWL_SAMEAS("ts_owl_sameAs", SolrType.NOT_STORED),
	TS_CRM_P79F_BEGINNING_IS_QUALIFIED_BY("ts_crm_P79F_beginning_is_qualified_by",SolrType.NOT_STORED),
	TS_CRM_P80F_END_IS_QUALIFIED_BY("ts_crm_P80F_end_is_qualified_by", SolrType.NOT_STORED),
	
	//EDM_AGENT
	EDM_AGENT("edm_agent",SolrType.NOT_STORED), 
	AG_SKOS_PREF_LABEL("ag_skos_prefLabel",	SolrType.FACET), 
	AG_SKOS_ALT_LABEL("ag_skos_altLabel",SolrType.NOT_STORED), 
	AG_SKOS_NOTE("ag_skos_note",SolrType.NOT_STORED), 
	AG_EDM_BEGIN("ag_edm_begin",SolrType.NOT_STORED),
	AG_EDM_END("ag_edm_end", SolrType.NOT_STORED), 
	AG_EDM_WASPRESENTAT("ag_edm_wasPresentAt", SolrType.NOT_STORED),
	AG_EDM_HASMET("ag_edm_hasMet", SolrType.NOT_STORED),
	AG_EDM_ISRELATEDTO("ag_edm_isRelatedto", SolrType.NOT_STORED),
	AG_OWL_SAMEAS("ag_owl_sameAs", SolrType.NOT_STORED),
	AG_FOAF_NAME("ag_foaf_name", SolrType.NOT_STORED),
	AG_RDAGR2_DATEOFBIRTH("ag_rdagr2_dateOfBirth", SolrType.NOT_STORED),
	AG_RDAGR2_DATEOFDEATH("ag_rdagr2_dateOfDeath", SolrType.NOT_STORED),
	AG_RDAGR2_DATEOFESTABLISHMENT("ag_rdagr2_dateOfEstablishment", SolrType.NOT_STORED),
	AG_RDAGR2_DATEOFTERMINATION("ag_rdagr2_dateOfTermination", SolrType.NOT_STORED),
	AG_RDAGR2_GENDER("ag_rdagr2_gender", SolrType.NOT_STORED),
	AG_RDAGR2_PROFESSIONOROCCUPATION("ag_rdagr2_professionOrOccupation", SolrType.NOT_STORED),
	AG_RDAGR2_BIOGRAPHICALINFORMATION("ag_rdagr2_biographicalInformation", SolrType.NOT_STORED),
	
	TYPE("TYPE", SolrType.FACET), 
	LANGUAGE("LANGUAGE", SolrType.FACET), 
	YEAR("YEAR", SolrType.FACET), 
	PROVIDER("PROVIDER", SolrType.FACET), 
	RIGHTS("RIGHTS", SolrType.FACET), 
	COUNTRY("COUNTRY", SolrType.FACET), 
	UGC("UGC", SolrType.FACET), 
	TEXT("text", SolrType.FACET), 
	DESCRIPTION("description", SolrType.INDEXED), 
	LOCATION("location",SolrType.INDEXED), 
	DATE("date", SolrType.INDEXED), 
	SUBJECT("subject", SolrType.INDEXED), 
	CONTRIBUTOR("contributor", SolrType.INDEXED), 
	SOURCE("source", SolrType.INDEXED), 
	TITLE("title", SolrType.INDEXED), 
	IDENTIFIER("identifier", SolrType.INDEXED), 
	WHO("who", SolrType.INDEXED), 
	WHAT("what", SolrType.INDEXED), 
	WHEN("when", SolrType.INDEXED), 
	WHERE("where", SolrType.INDEXED), 
	DATAPROVIDER("dataProvider", SolrType.INDEXED), 
	RELATION("relation", SolrType.INDEXED),  
	PL_POSITION("pl_position_latLon", SolrType.INDEXED), 
	PREVIEW_NO_DISTRIBUTE("edm_previewNoDistribute", SolrType.INDEXED), 
	NULL("null",null);

	private String value;
	private SolrType solrType;

	EdmLabel(String value, SolrType solrType) {
		this.value = value;
		this.solrType = solrType;
	}

	public String toString() {
		return value;
	}

	public SolrType getSolrType() {
		return solrType;
	}

	public static EdmLabel getEdmLabel(String value) {
		for (EdmLabel edmLabel : EdmLabel.values()) {
			if (StringUtils.equals(edmLabel.toString(), value)) {
				return edmLabel;
			}
		}
		return null;
	}

	/**
	 * Enumeration that denotes the use of a solr field FACET: FACET field
	 * (implies INDEX and STORED and used for FACET) INDEXED: INDEXED and STORED
	 * NOT_STORED: INDEXED but not STORED
	 * 
	 * @author Yorgos.Mamakis@ kb.nl
	 * 
	 */
	private enum SolrType {
		FACET("facet"), INDEXED("indexed"), NOT_STORED("not stored");

		private String solrTypeString;

		SolrType(String solrTypeString) {
			this.solrTypeString = solrTypeString;
		}

		public String toString() {
			return solrTypeString;
		}
	}
}
