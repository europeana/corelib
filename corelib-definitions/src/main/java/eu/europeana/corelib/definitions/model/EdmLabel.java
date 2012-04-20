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
 * Enumeration holding the Solr Structure and field definitions
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public enum EdmLabel {

	TIMESTAMP("timestamp",SolrType.INDEXED), 
	EUROPEANA_COLLECTIONNAME("europeana_collectionName", SolrType.INDEXED),
	EUROPEANA_ID("europeana_id",SolrType.INDEXED), 
	EDM_TYPE("edm_type",SolrType.NOT_STORED), 
	ORE_AGGREGATION("ore_aggregation",SolrType.NOT_STORED), 
	EDM_EUROPEANA_AGGREGATION("edm_europeana_aggregation",SolrType.NOT_STORED), 
	EDM_AGGREGATED_CHO(	"edm_aggregatedCHO", SolrType.NOT_STORED), 
	EDM_DATA_PROVIDER("edm_dataProvider", SolrType.NOT_STORED), 
	EDM_HASVIEW("edm_hasView", SolrType.INDEXED), 
	EDM_IS_SHOWN_BY("edm_isShownBy", SolrType.INDEXED), 
	EDM_IS_SHOWN_AT("edm_isShownAt", SolrType.INDEXED),
	EDM_OBJECT("edm_object", SolrType.INDEXED), 
	EDM_PROVIDER("edm_provider", SolrType.NOT_STORED), 
	AGGR_DC_RIGHTS("aggr_dc_rights", SolrType.INDEXED),
	AGGR_EDM_RIGHTS("aggr_edm_rights", SolrType.INDEXED), 
	EDM_WEB_RESOURCE("edm_webResource", SolrType.NOT_STORED),
	WR_DC_RIGHTS("wr_dc_rights", SolrType.INDEXED), 
	WR_EDM_RIGHTS("wr_edm_rights", SolrType.NOT_STORED), 
	ORE_PROXY("ore_proxy", SolrType.INDEXED), 
	OWL_SAMEAS("owl_sameAs", SolrType.INDEXED), 
	DC_COVERAGE("dc_coverage", SolrType.NOT_STORED), 
	DC_CONTRIBUTOR("dc_contributor", SolrType.NOT_STORED), 
	DC_DESCRIPTION("dc_description", SolrType.NOT_STORED), 
	DC_CREATOR("dc_creator", SolrType.NOT_STORED), 
	DC_DATE("dc_date", SolrType.NOT_STORED), 
	DC_FORMAT("dc_format", SolrType.NOT_STORED), 
	DC_IDENTIFIER("dc_identifier", SolrType.NOT_STORED), 
	DC_LANGUAGE("dc_language", SolrType.NOT_STORED),
	DC_PUBLISHER("dc_publisher", SolrType.NOT_STORED), 
	DC_RELATION("dc_relation", SolrType.NOT_STORED), 
	PRX_DC_RIGHTS("prx_dc_rights", SolrType.INDEXED), 
	PRX_EDM_RIGHTS("prx_edm_rights", SolrType.INDEXED),
	DC_SOURCE("dc_source", SolrType.NOT_STORED), 
	DC_SUBJECT("dc_subject", SolrType.NOT_STORED), 
	DC_TITLE("dc_title", SolrType.NOT_STORED), 
	DC_TYPE("dc_type", SolrType.NOT_STORED), 
	DCTERMS_ALTERNATIVE("dcterms_alternative",SolrType.NOT_STORED), 
	DCTERMS_CREATED("dcterms_created", SolrType.NOT_STORED), 
	DCTERMS_CONFORMS_TO("dcterms_conformsTo", SolrType.NOT_STORED), 
	DCTERMS_EXTENT("dcterms_extent", SolrType.NOT_STORED), 
	DCTERMS_HAS_FORMAT("dcterms_hasFormat", SolrType.NOT_STORED), 
	DCTERMS_HAS_PART("dcterms_hasPart", SolrType.NOT_STORED), 
	DCTERMS_HAS_VERSION("dcterms_hasVersion", SolrType.NOT_STORED), 
	DCTERMS_IS_FORMAT_OF("dcterms_isFormatOf", SolrType.NOT_STORED), 
	DCTERMS_IS_PART_OF("dcterms_isPartOf",SolrType.NOT_STORED), 
	DCTERMS_IS_REFERENCED_BY("dcterms_isReferencedBy",SolrType.NOT_STORED), 
	DCTERMS_IS_REPLACED_BY("dcterms_isReplacedBy", SolrType.NOT_STORED),
	DCTERMS_IS_REQUIRED_BY("dcterms_isRequiredBy", SolrType.NOT_STORED), 
	DCTERMS_ISSUED("dcterms_issued", SolrType.NOT_STORED),
	DCTERMS_IS_VERSION_OF("dcterms_isVersionOf", SolrType.NOT_STORED), 
	DCTERMS_MEDIUM("dcterms_medium",SolrType.NOT_STORED), 
	DCTERMS_PROVENANCE("dcterms_provenance", SolrType.NOT_STORED), 
	DCTERMS_REFERENCES("dcterms_references", SolrType.NOT_STORED),
	DCTERMS_REPLACES("dcterms_replaces",SolrType.NOT_STORED), 
	DCTERMS_REQUIRES("dcterms_requires", SolrType.NOT_STORED), 
	DCTERMS_SPATIAL("dcterms_spatial", SolrType.NOT_STORED), 
	DCTERMS_TABLE_OF_CONTENTS("dcterms_tableOfContents",SolrType.INDEXED),
	DCTERMS_TEMPORAL("dcterms_temporal",SolrType.NOT_STORED), 
	EDM_UGC("edm_UGC",SolrType.NOT_STORED), 
	EDM_CURRENT_LOCATION("edm_currentLocation_latLon",SolrType.NOT_STORED),
	EDM_IS_NEXT_IN_SEQUENCE("edm_isNextInSequence",SolrType.NOT_STORED), 
	SKOS_CONCEPT("skos_concept", SolrType.NOT_STORED), 
	CC_SKOS_PREF_LABEL("cc_skos_prefLabel", SolrType.NOT_STORED), 
	CC_SKOS_ALT_LABEL("cc_skos_altLabel",SolrType.NOT_STORED), 
	CC_SKOS_NOTE("cc_skos_note",SolrType.NOT_STORED), 
	CC_SKOS_BROADER("cc_skos_broader",SolrType.NOT_STORED), 
	EDM_PLACE("edm_place",SolrType.NOT_STORED), 
	PL_SKOS_PREF_LABEL("pl_skos_prefLabel", SolrType.NOT_STORED), 
	PL_SKOS_ALT_LABEL("pl_skos_altLabel",SolrType.NOT_STORED), 
	PL_SKOS_NOTE("pl_skos_note", SolrType.NOT_STORED), 
	PL_DCTERMS_ISPART_OF("pl_dcterm_isPartOf",SolrType.NOT_STORED), 
	EDM_TIMESPAN("edm_timespan",SolrType.NOT_STORED), 
	TS_SKOS_PREF_LABEL("ts_skos_prefLabel",SolrType.NOT_STORED), 
	TS_SKOS_ALT_LABEL("ts_skos_altLabel", SolrType.NOT_STORED), 
	TS_SKOS_NOTE("ts_skos_note", SolrType.NOT_STORED), 
	TS_EDM_BEGIN("ts_edm_begin",SolrType.NOT_STORED), 
	TS_EDM_END("ts_edm_end",SolrType.NOT_STORED), 
	TS_DCTERMS_ISPART_OF("ts_dcterms_isPartOf",SolrType.NOT_STORED), 
	EDM_AGENT("edm_agent",SolrType.NOT_STORED), 
	AG_SKOS_PREF_LABEL("ag_skos_prefLabel",SolrType.NOT_STORED), 
	AG_SKOS_ALT_LABEL("ag_skos_altLabel",SolrType.NOT_STORED), 
	AG_SKOS_NOTE("ag_skos_note",SolrType.NOT_STORED), 
	AG_EDM_BEGIN("ag_edm_begin",SolrType.NOT_STORED), 
	AG_EDM_END("ag_edm_end",SolrType.NOT_STORED), 
	TYPE("TYPE",SolrType.FACET), 
	LANGUAGE("LANGUAGE",SolrType.FACET), 
	YEAR("YEAR", SolrType.FACET), 
	PROVIDER("PROVIDER", SolrType.FACET),	
	RIGHTS("RIGHTS", SolrType.FACET), 
	COUNTRY("COUNTRY", SolrType.FACET), 
	UGC("UGC", SolrType.FACET),
	TEXT("text", SolrType.FACET),
	DESCRIPTION("description",SolrType.INDEXED), 
	LOCATION("location", SolrType.INDEXED),
	DATE("date", SolrType.INDEXED), 
	SUBJECT("subject", SolrType.INDEXED), 
	CONTRIBUTOR("contributor", SolrType.INDEXED),	
	SOURCE("source",SolrType.INDEXED), 
	TITLE("title", SolrType.INDEXED), 
	IDENTIFIER("identifier", SolrType.INDEXED), 
	WHO("who", SolrType.INDEXED), 
	WHAT("what",SolrType.INDEXED),
	WHEN("when", SolrType.INDEXED), 
	WHERE("where", SolrType.INDEXED),
	DATAPROVIDER("dataProvider",SolrType.INDEXED),
	RELATION("relation", SolrType.INDEXED),
	ORE_PROXY_IN("ore_proxyIn",SolrType.INDEXED),
	ORE_PROXY_FOR("ore_proxyFor",SolrType.INDEXED),
	PL_POSITION("pl_position_latLon",SolrType.INDEXED),
	NULL("null",null)
	;


	private String value;
	private SolrType solrType;

	EdmLabel(String value,SolrType solrType) {
		this.value = value;
		this.solrType = solrType;
	}

	public String toString() {
		return value;
	}
	
	public SolrType getSolrType(){
		return solrType;
	}
	
	/**
	 * Enumeration that denotes the use of a solr field 
	 * FACET: FACET field (implies INDEX and STORED and used for FACET)
	 * INDEXED: INDEXED and STORED
	 * NOT_STORED: INDEXED but not STORED
	 * 
	 * @author Yorgos.Mamakis@ kb.nl
	 *
	 */
	private enum SolrType{
		FACET("facet"),
		INDEXED("indexed"),
		NOT_STORED("not stored");
		
		private String solrTypeString;
		
		SolrType(String solrTypeString){
			this.solrTypeString = solrTypeString;
		}
		public String toString() {
			return solrTypeString;
		}
	}
}
