package eu.europeana.corelib.edm.utils;

/**
 * The mappings between ESE and EDM
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public enum FieldMapping {

	EUROPEANA_URI("europeana_uri", "europeana_id", false, false),
	EUROPEANA_COLLECTIONNAME("europeana_collectionName", "europeana_collectionName", false, false),
	EUROPEANA_TYPE("europeana_type", "proxy_edm_type", false, false),
	EUROPEANA_OBJECT("europeana_object", "provider_aggregation_edm_object", false, false),
	EUROPEANA_ISSHOWNAT("europeana_isShownAt", "provider_aggregation_edm_isShownAt", false, false),
	EUROPEANA_ISSHOWNBY("europeana_isShownBy", "provider_aggregation_edm_isShownBy", false, false),
	EUROPEANA_PROVIDER("europeana_provider", "PROVIDER", true, false),
	// EUROPEANA_PROVIDER("europeana_provider", "provider_aggregation_edm_provider", true, false),
	EUROPEANA_DATAPROVIDER("europeana_dataProvider", "provider_aggregation_edm_dataProvider", true, false),
	EUROPEANA_RIGHTS("europeana_rights", "RIGHTS", true, false),
	// EUROPEANA_RIGHTS("europeana_rights", "provider_aggregation_edm_rights", true, false),
	EUROPEANA_UGC("europeana_UGC", "edm_UGC", false, false),
	EUROPEANA_COMPLETENESS("europeana_completeness", "europeana_completeness", false, false),
	EUROPEANA_PREVIEWNODISTRIBUTE("europeana_previewNoDistribute", "europeana_previewNoDistribute", false, false),
	DC_COVERAGE("dc_coverage", "proxy_dc_coverage", true, true),
	DC_CONTRIBUTOR("dc_contributor", "proxy_dc_contributor", true, true),
	DC_DESCRIPTION("dc_description", "proxy_dc_description", true, false),
	DC_CREATOR("dc_creator", "proxy_dc_creator", true, true),
	DC_DATE("dc_date", "proxy_dc_date", true, true),
	DC_FORMAT("dc_format", "proxy_dc_format", true, false),
	DC_IDENTIFIER("dc_identifier", "proxy_dc_identifier", true, false),
	DC_LANGUAGE("dc_language", "proxy_dc_language", true, false),
	DC_PUBLISHER("dc_publisher", "proxy_dc_publisher", true, false),
	DC_RELATION("dc_relation", "proxy_dc_relation", true, false),
	DC_RIGHTS("dc_rights", "proxy_dc_rights", true, false),
	DC_SOURCE("dc_source", "proxy_dc_source", true, false),
	DC_SUBJECT("dc_subject", "proxy_dc_subject", true, true),
	DC_TITLE("dc_title", "proxy_dc_title", true, false),
	DC_TYPE("dc_type", "proxy_dc_type", true, true),
	DCTERMS_ALTERNATIVE("dcterms_alternative", "proxy_dcterms_alternative", true, false),
	DCTERMS_CREATED("dcterms_created", "proxy_dcterms_created", true, false),
	DCTERMS_CONFORMSTO("dcterms_conformsTo", "proxy_dcterms_conformsTo", true, false),
	DCTERMS_EXTENT("dcterms_extent", "proxy_dcterms_extent", true, false),
	DCTERMS_HASFORMAT("dcterms_hasFormat", "proxy_dcterms_hasFormat", true, false),
	DCTERMS_HASVERSION("dcterms_hasVersion", "proxy_dcterms_hasVersion", true, false),
	DCTERMS_ISFORMATOF("dcterms_isFormatOf", "proxy_dcterms_isFormatOf", true, false),
	DCTERMS_ISPARTOF("dcterms_isPartOf", "proxy_dcterms_isPartOf", true, false),
	DCTERMS_ISREFERENCEDBY("dcterms_isReferencedBy", "proxy_dcterms_isReferencedBy", true, false),
	DCTERMS_ISREPLACEDBY("dcterms_isReplacedBy", "proxy_dcterms_isReplacedBy", true, false),
	DCTERMS_ISREQUIREDBY("dcterms_isRequiredBy", "proxy_dcterms_isRequiredBy", true, false),
	DCTERMS_ISSUED("dcterms_issued", "proxy_dcterms_issued", true, false),
	DCTERMS_ISVERSIONOF("dcterms_isVersionOf", "proxy_dcterms_isVersionOf", true, false),
	DCTERMS_MEDIUM("dcterms_medium", "proxy_dcterms_medium", true, false),
	DCTERMS_PROVENANCE("dcterms_provenance", "proxy_dcterms_provenance", true, false),
	DCTERMS_REFERENCES("dcterms_references", "proxy_dcterms_references", true, false),
	DCTERMS_REPLACES("dcterms_replaces", "proxy_dcterms_replaces", true, false),
	DCTERMS_REQUIRES("dcterms_requires", "proxy_dcterms_requires", true, false),
	DCTERMS_SPATIAL("dcterms_spatial", "proxy_dcterms_spatial", true, true),
	DCTERMS_TOC("dcterms_tableOfContents", "proxy_dcterms_tableOfContents", true, false),
	DCTERMS_TEMPORAL("dcterms_temporal", "proxy_dcterms_temporal", true, true),
	SKOS_PREFLABEL("skos_prefLabel", "cc_skos_prefLabel", true),
	SKOS_ALTLABEL("skos_altLabel", "cc_skos_altLabel", true),
	SKOS_BROADER("skos_broader", "cc_skos_broader", false),
	PERIOD_BEGIN("period_begin", "ts_edm_begin", true),
	PERIOD_END("period_end", "ts_edm_end", true),
	WGS_LAT("wgs_lat", "pl_wgs84_pos_lat", true),
	WGS_LON("wgs_lon", "pl_wgs84_pos_long", true),
	ENRICHMENT_PLACE_TERM("enrichment_place_term", "edm_place", false),
	ENRICHMENT_PLACE_LABEL("enrichment_place_label", "pl_skos_prefLabel", true),
	ENRICHMENT_PLACE_LAT("enrichment_place_latitude", "pl_wgs84_pos_lat", false),
	ENRICHMENT_PLACE_LON("enrichment_place_longitude", "pl_wgs84_pos_long", false),
	ENRICHMENT_PERIOD_TERM("enrichment_period_term", "edm_timespan", false),
	ENRICHMENT_PERIOD_LABEL("enrichment_period_label", "ts_skos_prefLabel", true),
	ENRICHMENT_PERIOD_BEGIN("enrichment_period_begin", "ts_edm_begin", true),
	ENRICHMENT_PERIOD_END("enrichment_period_end", "ts_edm_end", true),
	ENRICHMENT_CONCEPT_TERM("enrichment_concept_term", "skos_concept", false),
	ENRICHMENT_CONCEPT_LABEL("enrichment_concept_label", "cc_skos_prefLabel", true),
	ENRICHMENT_AGENT_TERM("enrichment_agent_term", "edm_agent", false),
	ENRICHMENT_AGENT_LABEL("enrichment_agent_label", "ag_skos_prefLabel", true),
	ENRICHMENT_PLACE_BROADER_TERM("enrichment_place_broader_term", "pl_dcterms_isPartOf", true),
	ENRICHMENT_PLACE_BROADER_LABEL("enrichment_place_broader_label", "pl_dcterms_isPartOf_label", true),
	ENRICHMENT_PERIOD_BROADER_TERM("enrichment_period_broader_term", "ts_dcterms_isPartOf", true),
	ENRICHMENT_PERIOD_BROADER_LABEL("enrichment_period_broader_label", "ts_dcterms_isPartOf_label", true),
	ENRICHMENT_CONCEPT_BROADER_TERM("enrichment_concept_broader_term", "cc_skos_broader", false, true),
	ENRICHMENT_CONCEPT_BROADER_LABEL("enrichment_concept_broader_label", "cc_skos_broaderLabel", true, true),
	EUROPEANA_YEAR("europeana_year", "proxy_edm_year", true, true),
	EUROPEANA_LANGUAGE("europeana_language", "europeana_aggregation_edm_language", true, false),
	EUROPEANA_COUNTRY("europeana_country", "europeana_aggregation_edm_country", true, false),
	DCTERMS_HASPART("dcterms_hasPart", "proxy_dcterms_hasPart", true, false);

	private String eseField;
	private String edmField;
	private boolean hasDynamicField;
	private boolean isEnrichmentField;

	FieldMapping(String eseField, String edmField, boolean hasDynamicField) {
		this(eseField, edmField, hasDynamicField, true);
	}

	FieldMapping(String eseField, String edmField, boolean hasDynamicField, boolean isEnrichmentField) {
		this.eseField = eseField;
		this.edmField = edmField;
		this.hasDynamicField = hasDynamicField;
		this.isEnrichmentField = isEnrichmentField;
	}

	/**
	 * Get the ESE field
	 * 
	 * @return
	 */
	public String getEseField() {
		return eseField;
	}

	/**
	 * Get the EDM field
	 * 
	 * @return
	 */
	public String getEdmField() {
		return edmField;
	}

	/**
	 * Check if that field is a Dynamic Field
	 * 
	 * @return
	 */
	public boolean getHasDynamicField() {
		return hasDynamicField;
	}

	public boolean isEnrichmentField(){
		return isEnrichmentField;
	}
}