package eu.europeana.corelib.definitions.model;

import org.apache.commons.lang.StringUtils;

/**
 * Enumeration holding the Solr Structure and field definitions
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
// TODO presumably never used, deprecation candidate
public enum EdmLabel {

	TIMESTAMP("timestamp", SolrType.INDEXED), 
	EUROPEANA_COLLECTIONNAME("europeana_collectionName", SolrType.INDEXED), 
	EUROPEANA_ID("europeana_id", SolrType.INDEXED), 
	PROVIDER_EDM_TYPE("proxy_edm_type", SolrType.FACET),
	
	// Provider Aggregation Fields
	PROVIDER_AGGREGATION_ORE_AGGREGATION("provider_aggregation_ore_aggregation", SolrType.NOT_STORED), 
	PROVIDER_AGGREGATION_ORE_AGGREGATES("provider_aggregation_ore_aggregates", SolrType.NOT_STORED), 
	PROVIDER_AGGREGATION_EDM_AGGREGATED_CHO("provider_aggregation_edm_aggregatedCHO", SolrType.NOT_STORED), 
	PROVIDER_AGGREGATION_EDM_DATA_PROVIDER("provider_aggregation_edm_dataProvider", SolrType.FACET), 
	PROVIDER_AGGREGATION_EDM_HASVIEW("provider_aggregation_edm_hasView", SolrType.INDEXED),
	PROVIDER_AGGREGATION_EDM_IS_SHOWN_BY("provider_aggregation_edm_isShownBy", SolrType.INDEXED), 
	PROVIDER_AGGREGATION_EDM_IS_SHOWN_AT("provider_aggregation_edm_isShownAt", SolrType.INDEXED), 
	PROVIDER_AGGREGATION_EDM_OBJECT("provider_aggregation_edm_object", SolrType.INDEXED), 
	PROVIDER_AGGREGATION_EDM_PROVIDER("provider_aggregation_edm_provider", SolrType.INDEXED),
	PROVIDER_AGGREGATION_EDM_INTERMEDIATE_PROVIDER("provider_aggregation_edm_intermediateProvider",SolrType.NOT_STORED),
	PROVIDER_AGGREGATION_DC_RIGHTS("provider_aggregation_dc_rights", SolrType.FACET), 
	PROVIDER_AGGREGATION_EDM_RIGHTS("provider_aggregation_edm_rights", SolrType.FACET), 
	PROVIDER_AGGREGATION_EDM_UNSTORED("provider_aggregation_edm_unstored", SolrType.NOT_STORED),
	PROVIDER_AGGREGATION_CC_LICENSE("provider_aggregation_cc_license", SolrType.INDEXED),
	PROVIDER_AGGREGATION_ODRL_INHERITED_FROM("provider_aggregation_odrl_inherited_from",SolrType.INDEXED),
	PROVIDER_AGGREGATION_CC_DEPRECATED_ON("provider_aggregation_cc_deprecated_on",SolrType.INDEXED),

	// EUROPEANA AGGREGATION
	EDM_EUROPEANA_AGGREGATION("edm_europeana_aggregation", SolrType.INDEXED), 
	EUROPEANA_AGGREGATION_ORE_AGGREGATEDCHO("europeana_aggregation_edm_aggregatedCHO", SolrType.INDEXED),
	EUROPEANA_AGGREGATION_ORE_AGGREGATES("europeana_aggregation_ore_aggregates", SolrType.NOT_STORED), 
	EUROPEANA_AGGREGATION_DC_CREATOR("europeana_aggregation_dc_creator", SolrType.NOT_STORED), 
	EUROPEANA_AGGREGATION_EDM_LANDINGPAGE("europeana_aggregation_edm_landingPage", SolrType.INDEXED), 
	EUROPEANA_AGGREGATION_EDM_ISSHOWNBY("europeana_aggregation_edm_isShownBy", SolrType.INDEXED), 
	EUROPEANA_AGGREGATION_EDM_HASVIEW("europeana_aggregation_edm_hasView", SolrType.INDEXED), 
	EUROPEANA_AGGREGATION_EDM_COUNTRY("europeana_aggregation_edm_country", SolrType.INDEXED), 
	EUROPEANA_AGGREGATION_EDM_LANGUAGE("europeana_aggregation_edm_language", SolrType.INDEXED), 
	EUROPEANA_AGGREGATION_EDM_RIGHTS("europeana_aggregation_edm_rights", SolrType.NOT_STORED),
	EUROPEANA_AGGREGATION_EDM_PREVIEW("europeana_aggregation_edm_preview", SolrType.NOT_STORED),

	// WEB RESOURCE FIELDS
	EDM_WEB_RESOURCE("edm_webResource", SolrType.NOT_STORED), 
	WR_DC_RIGHTS("wr_dc_rights", SolrType.FACET), 
	WR_EDM_RIGHTS("wr_edm_rights",SolrType.FACET), 
	WR_DC_DESCRIPTION("wr_dc_description",SolrType.NOT_STORED), 
	WR_DC_FORMAT("wr_dc_format",SolrType.NOT_STORED), 
	WR_DC_SOURCE("wr_dc_source",SolrType.NOT_STORED), 
	WR_DC_CREATOR("wr_dc_creator",SolrType.NOT_STORED),
	WR_DC_TYPE("wr_dc_type",SolrType.NOT_STORED),
	WR_DCTERMS_EXTENT("wr_dcterms_extent",SolrType.NOT_STORED), 
	WR_DCTERMS_ISSUED("wr_dcterms_issued",SolrType.NOT_STORED), 
	WR_DCTERMS_CONFORMSTO("wr_dcterms_conformsTo", SolrType.NOT_STORED), 
	WR_DCTERMS_CREATED("wr_dcterms_created", SolrType.NOT_STORED), 
	WR_DCTERMS_ISFORMATOF("wr_dcterms_isFormatOf", SolrType.NOT_STORED), 
	WR_DCTERMS_HAS_PART("wr_dcterms_hasPart", SolrType.NOT_STORED), 
	WR_EDM_IS_NEXT_IN_SEQUENCE("wr_edm_isNextInSequence", SolrType.NOT_STORED),
	WR_OWL_SAMEAS("wr_owl_sameAs",SolrType.NOT_STORED),
	WR_CC_LICENSE("wr_cc_license", SolrType.INDEXED),
	WR_ODRL_INHERITED_FROM("wr_odrl_inherited_from",SolrType.INDEXED),
	WR_CC_DEPRECATED_ON("wr_cc_deprecated_on",SolrType.INDEXED),
    WR_SVCS_HAS_SERVICE("wr_svcs_hasservice",SolrType.NOT_STORED),
	WR_EDM_PREVIEW("wr_edm_preview",SolrType.INDEXED),
	WR_DCTERMS_ISREFERENCEDBY("wr_dcterms_isReferencedBy",SolrType.INDEXED),


	LIC_RDF_ABOUT("cc_license", SolrType.INDEXED),
	LIC_ODRL_INHERITED_FROM("odrl_inherited_from",SolrType.INDEXED),
	LIC_CC_DEPRECATED_ON("cc_deprecated_on",SolrType.INDEXED),

	//SERVICE
	SV_RDF_ABOUT("svcs_service",SolrType.INDEXED),
	SV_DCTERMS_CONFORMS_TO("sv_dcterms_conformsTo",SolrType.INDEXED),
	SV_DOAP_IMPLEMENTS("sv_doap_implements",SolrType.INDEXED),

	// PROVIDER PROXY
	ORE_PROXY("proxy_ore_proxy", SolrType.INDEXED), 
	EDM_ISEUROPEANA_PROXY("edm_europeana_proxy",SolrType.NOT_STORED),
	PROXY_OWL_SAMEAS("proxy_owl_sameAs", SolrType.INDEXED), 
	PROXY_DC_COVERAGE("proxy_dc_coverage", SolrType.FACET), 
	PROXY_DC_CONTRIBUTOR("proxy_dc_contributor", SolrType.NOT_STORED), 
	PROXY_DC_DESCRIPTION("proxy_dc_description", SolrType.NOT_STORED), 
	PROXY_DC_CREATOR("proxy_dc_creator", SolrType.FACET), 
	PROXY_DC_DATE("proxy_dc_date", SolrType.FACET), 
	PROXY_DC_FORMAT("proxy_dc_format", SolrType.NOT_STORED), 
	PROXY_DC_IDENTIFIER("proxy_dc_identifier", SolrType.NOT_STORED), 
	PROXY_DC_LANGUAGE("proxy_dc_language", SolrType.FACET), 
	PROXY_DC_PUBLISHER("proxy_dc_publisher", SolrType.NOT_STORED), 
	PROXY_DC_RELATION("proxy_dc_relation", SolrType.NOT_STORED), 
	PROXY_DC_RIGHTS("proxy_dc_rights", SolrType.FACET), 
	PROXY_EDM_RIGHTS("proxy_edm_rights", SolrType.FACET), 
	PROXY_DC_SOURCE("proxy_dc_source", SolrType.NOT_STORED), 
	PROXY_DC_SUBJECT("proxy_dc_subject", SolrType.NOT_STORED), 
	PROXY_DC_TITLE("proxy_dc_title", SolrType.NOT_STORED), 
	PROXY_DC_TYPE("proxy_dc_type", SolrType.FACET), 
	PROXY_DCTERMS_ALTERNATIVE("proxy_dcterms_alternative", SolrType.FACET), 
	PROXY_DCTERMS_CREATED("proxy_dcterms_created", SolrType.NOT_STORED), 
	PROXY_DCTERMS_CONFORMS_TO("proxy_dcterms_conformsTo", SolrType.NOT_STORED), 
	PROXY_DCTERMS_EXTENT("proxy_dcterms_extent", SolrType.NOT_STORED), 
	PROXY_DCTERMS_HAS_FORMAT("proxy_dcterms_hasFormat", SolrType.NOT_STORED), 
	PROXY_DCTERMS_HAS_PART("proxy_dcterms_hasPart", SolrType.NOT_STORED), 
	PROXY_DCTERMS_HAS_VERSION("proxy_dcterms_hasVersion", SolrType.NOT_STORED), 
	PROXY_DCTERMS_IS_FORMAT_OF("proxy_dcterms_isFormatOf", SolrType.NOT_STORED), 
	PROXY_DCTERMS_IS_PART_OF("proxy_dcterms_isPartOf", SolrType.NOT_STORED), 
	PROXY_DCTERMS_IS_REFERENCED_BY("proxy_dcterms_isReferencedBy", SolrType.NOT_STORED), 
	PROXY_DCTERMS_IS_REPLACED_BY("proxy_dcterms_isReplacedBy", SolrType.NOT_STORED), 
	PROXY_DCTERMS_IS_REQUIRED_BY("proxy_dcterms_isRequiredBy", SolrType.NOT_STORED), 
	PROXY_DCTERMS_ISSUED("proxy_dcterms_issued", SolrType.NOT_STORED), 
	PROXY_DCTERMS_IS_VERSION_OF("proxy_dcterms_isVersionOf", SolrType.NOT_STORED), 
	PROXY_DCTERMS_MEDIUM("proxy_dcterms_medium", SolrType.NOT_STORED), 
	PROXY_DCTERMS_PROVENANCE("proxy_dcterms_provenance", SolrType.NOT_STORED), 
	PROXY_DCTERMS_REFERENCES("proxy_dcterms_references", SolrType.NOT_STORED), 
	PROXY_DCTERMS_REPLACES("proxy_dcterms_replaces", SolrType.NOT_STORED), 
	PROXY_DCTERMS_REQUIRES("proxy_dcterms_requires", SolrType.NOT_STORED), 
	PROXY_DCTERMS_SPATIAL("proxy_dcterms_spatial", SolrType.NOT_STORED), 
	PROXY_DCTERMS_TABLE_OF_CONTENTS("proxy_dcterms_tableOfContents", SolrType.INDEXED), 
	PROXY_DCTERMS_TEMPORAL("proxy_dcterms_temporal", SolrType.FACET), 
	EDM_UGC("edm_UGC",SolrType.FACET), 
	PROXY_EDM_CURRENT_LOCATION_LAT("proxy_edm_currentLocation_lat", SolrType.NOT_STORED), 
	PROXY_EDM_CURRENT_LOCATION_LONG("proxy_edm_currentLocation_long", SolrType.NOT_STORED), 
	PROXY_EDM_CURRENT_LOCATION("proxy_edm_currentLocation", SolrType.NOT_STORED),
	PROXY_EDM_UNSTORED("proxy_edm_unstored", SolrType.NOT_STORED), 
	PROXY_EDM_IS_NEXT_IN_SEQUENCE("proxy_edm_isNextInSequence", SolrType.NOT_STORED), 
	PROXY_EDM_HAS_TYPE("proxy_edm_hasType", SolrType.NOT_STORED), 
	PROXY_EDM_HAS_MET("proxy_edm_hasMet", SolrType.NOT_STORED), 
	PROXY_EDM_INCORPORATES("proxy_edm_incorporates", SolrType.NOT_STORED), 
	PROXY_EDM_ISDERIVATIVE_OF("proxy_edm_isDerivativeOf", SolrType.NOT_STORED), 
	PROXY_EDM_ISRELATEDTO("proxy_edm_isRelatedTo", SolrType.NOT_STORED), 
	PROXY_EDM_ISREPRESENTATIONOF("proxy_edm_isRepresentationOf", SolrType.NOT_STORED), 
	PROXY_EDM_ISSIMILARTO("proxy_edm_isSimilarTo", SolrType.NOT_STORED), 
	PROXY_EDM_ISSUCCESSOROF("proxy_edm_isSuccessorOf", SolrType.NOT_STORED), 
	PROXY_EDM_REALIZES("proxy_edm_realizes", SolrType.NOT_STORED), 
	PROXY_EDM_WASPRESENTAT("proxy_edm_wasPresentAt", SolrType.NOT_STORED),
	PROXY_ORE_PROXY_IN("proxy_ore_proxyIn",SolrType.INDEXED), 
	PROXY_ORE_PROXY_FOR("proxy_ore_proxyFor", SolrType.INDEXED),
	PROXY_EDM_YEAR("proxy_edm_year",SolrType.INDEXED),
	

	//SKOS_CONCEPT
	SKOS_CONCEPT("skos_concept", SolrType.NOT_STORED), 
	CC_SKOS_PREF_LABEL("cc_skos_prefLabel", SolrType.FACET), 
	CC_SKOS_ALT_LABEL("cc_skos_altLabel", SolrType.NOT_STORED), 
	CC_SKOS_NOTE("cc_skos_note", SolrType.NOT_STORED), 
	CC_SKOS_BROADER("cc_skos_broader", SolrType.NOT_STORED), 
	CC_SKOS_HIDDEN_LABEL("cc_skos_hiddenLabel", SolrType.NOT_STORED),
	CC_SKOS_NARROWER("cc_skos_narrower", SolrType.NOT_STORED),
	CC_SKOS_RELATED("cc_skos_related", SolrType.NOT_STORED),
	CC_SKOS_BROADMATCH("cc_skos_broadMatch", SolrType.NOT_STORED),
	CC_SKOS_NARROWMATCH("cc_skos_narrowMatch", SolrType.NOT_STORED),
	CC_SKOS_RELATEDMATCH("cc_skos_relatedMatch", SolrType.NOT_STORED),
	CC_SKOS_EXACTMATCH("cc_skos_exactMatch", SolrType.NOT_STORED),
	CC_SKOS_CLOSEMATCH("cc_skos_closeMatch", SolrType.NOT_STORED),
	CC_SKOS_NOTATIONS("cc_skos_notation", SolrType.NOT_STORED),
	CC_SKOS_INSCHEME("cc_skos_inScheme", SolrType.NOT_STORED),
	
	//PLACE
	EDM_PLACE("edm_place",SolrType.NOT_STORED), 
	PL_SKOS_PREF_LABEL("pl_skos_prefLabel",SolrType.NOT_STORED), 
	PL_SKOS_ALT_LABEL("pl_skos_altLabel",SolrType.NOT_STORED), 
	PL_SKOS_NOTE("pl_skos_note",SolrType.NOT_STORED), 
	PL_DCTERMS_ISPART_OF("pl_dcterms_isPartOf",SolrType.NOT_STORED), 
	PL_WGS84_POS_LAT("pl_wgs84_pos_lat", SolrType.INDEXED),
	PL_WGS84_POS_LONG("pl_wgs84_pos_long", SolrType.INDEXED),
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
	AG_DC_DATE("ag_dc_date",SolrType.NOT_STORED),
	AG_DC_IDENTIFIER("ag_dc_identifier", SolrType.NOT_STORED),
	AG_SKOS_PREF_LABEL("ag_skos_prefLabel",	SolrType.FACET), 
	AG_SKOS_ALT_LABEL("ag_skos_altLabel",SolrType.NOT_STORED), 
	AG_SKOS_NOTE("ag_skos_note",SolrType.NOT_STORED), 
	AG_EDM_BEGIN("ag_edm_begin",SolrType.NOT_STORED),
	AG_EDM_END("ag_edm_end", SolrType.NOT_STORED), 
	AG_EDM_WASPRESENTAT("ag_edm_wasPresentAt", SolrType.NOT_STORED),
	AG_EDM_HASMET("ag_edm_hasMet", SolrType.NOT_STORED),
	AG_EDM_ISRELATEDTO("ag_edm_isRelatedTo", SolrType.NOT_STORED),
	AG_OWL_SAMEAS("ag_owl_sameAs", SolrType.NOT_STORED),
	AG_FOAF_NAME("ag_foaf_name", SolrType.NOT_STORED),
	AG_RDAGR2_DATEOFBIRTH("ag_rdagr2_dateOfBirth", SolrType.NOT_STORED),
	AG_RDAGR2_DATEOFDEATH("ag_rdagr2_dateOfDeath", SolrType.NOT_STORED),
	AG_RDAGR2_PLACEOFBIRTH("ag_rdagr2_placeOfBirth", SolrType.NOT_STORED),
	AG_RDAGR2_PLACEOFDEATH("ag_rdagr2_placeOfDeath", SolrType.NOT_STORED),
	AG_RDAGR2_DATEOFESTABLISHMENT("ag_rdagr2_dateOfEstablishment", SolrType.NOT_STORED),
	AG_RDAGR2_DATEOFTERMINATION("ag_rdagr2_dateOfTermination", SolrType.NOT_STORED),
	AG_RDAGR2_GENDER("ag_rdagr2_gender", SolrType.NOT_STORED),
	AG_RDAGR2_PROFESSIONOROCCUPATION("ag_rdagr2_professionOrOccupation", SolrType.NOT_STORED),
	AG_RDAGR2_BIOGRAPHICALINFORMATION("ag_rdagr2_biographicalInformation", SolrType.NOT_STORED),
	
	
	
	//ORGANISATION FIELDS
	FOAF_ORGANIZATION("foaf_organization",SolrType.FACET),
	ORG_DC_DATE("org_dc_date",SolrType.NOT_STORED),
	ORG_DC_IDENTIFIER("org_dc_identifier", SolrType.NOT_STORED),
	ORG_SKOS_PREF_LABEL("org_skos_prefLabel",	SolrType.FACET), 
	ORG_SKOS_ALT_LABEL("org_skos_altLabel",SolrType.NOT_STORED), 
	ORG_SKOS_NOTE("org_skos_note",SolrType.NOT_STORED), 
	ORG_EDM_BEGIN("org_edm_begin",SolrType.NOT_STORED),
	ORG_EDM_END("org_edm_end", SolrType.NOT_STORED), 
	ORG_EDM_WASPRESENTAT("org_edm_wasPresentAt", SolrType.NOT_STORED),
	ORG_EDM_HASMET("org_edm_hasMet", SolrType.NOT_STORED),
	ORG_EDM_ISRELATEDTO("org_edm_isRelatedTo", SolrType.NOT_STORED),
	ORG_OWL_SAMEAS("org_owl_sameAs", SolrType.NOT_STORED),
	ORG_FOAF_NAME("org_foaf_name", SolrType.NOT_STORED),
	ORG_RDAGR2_DATEOFBIRTH("org_rdagr2_dateOfBirth", SolrType.NOT_STORED),
	ORG_RDAGR2_DATEOFDEATH("org_rdagr2_dateOfDeath", SolrType.NOT_STORED),
	ORG_RDAGR2_PLACEOFBIRTH("org_rdagr2_placeOfBirth", SolrType.NOT_STORED),
	ORG_RDAGR2_PLACEOFDEATH("org_rdagr2_placeOfDeath", SolrType.NOT_STORED),
	ORG_RDAGR2_DATEOFESTABLISHMENT("org_rdagr2_dateOfEstablishment", SolrType.NOT_STORED),
	ORG_RDAGR2_DATEOFTERMINATION("org_rdagr2_dateOfTermination", SolrType.NOT_STORED),
	ORG_RDAGR2_GENDER("org_rdagr2_gender", SolrType.NOT_STORED),
	ORG_RDAGR2_PROFESSIONOROCCUPATION("org_rdagr2_professionOrOccupation", SolrType.NOT_STORED),
	ORG_RDAGR2_BIOGRAPHICALINFORMATION("org_rdagr2_biographicalInformation", SolrType.NOT_STORED),
	ORG_EDM_ACRONYM("org_edm_acronym",SolrType.NOT_STORED),
	ORG_EDM_ORGANIZATIONSCOPE("org_edm_organizationScope",SolrType.NOT_STORED),
	ORG_EDM_ORGANIZATIONDOMAIN("org_edm_organizationDomain",SolrType.FACET),
	ORG_EDM_ORGANIZATIONSECTOR("org_edm_organizationSector",SolrType.NOT_STORED),
	ORG_FOAF_LOGO("org_foaf_logo",SolrType.NOT_STORED),
	ORG_EDM_GEORGREAPHICLEVEL("org_edm_geographicLevel",SolrType.NOT_STORED),
	ORG_EDM_COUNTRY("org_edm_country",SolrType.FACET),
	ORG_EDM_EUROPEANA_ROLE("org_edm_europeanaRole",SolrType.FACET),
	ORG_FOAF_HOMEPAGE("org_foaf_homepage",SolrType.NOT_STORED),

    //TODO add or remove them
    /*
      BUSYMACHINES FACETS
     */

    FACET_TAGS("facet_tags, SolrType.FACET", SolrType.FACET),
    IS_FULLTEXT("is_fulltext", SolrType.FACET),
    HAS_MEDIA("has_media", SolrType.FACET),
    HAS_THUMBNAILS("has_thumbnails", SolrType.FACET),

	
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
	COMPLETENESS("COMPLETENESS",SolrType.INDEXED),
	EUROPEANA_COMPLETENESS("europeana_completeness",SolrType.INDEXED),
	NULL("null",null),
	ORIGINAL("original",null);

	private String value;
	private SolrType solrType;

	EdmLabel(String value, SolrType solrType) {
		this.value = value;
		this.solrType = solrType;
	}

	/**
	 * Return the field value
	 */
	@Override
	public String toString() {
		return value;
	}
	
	
	/**
	 * Return if a field is a facet, an indexed or a not stored field
	 * @return
	 */
	public SolrType getSolrType() {
		return solrType;
	}

	
	/**
	 * Get the EdmLabel for a specific value
	 * @param value The value to check
	 * @return The corresponding EdmLabel, or null
	 */
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

		@Override
		public String toString() {
			return solrTypeString;
		}
	}
}
