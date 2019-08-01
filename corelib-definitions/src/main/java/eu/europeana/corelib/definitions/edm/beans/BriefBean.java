package eu.europeana.corelib.definitions.edm.beans;

import java.util.Date;
import java.util.List;
import java.util.Map;

import eu.europeana.corelib.definitions.solr.DocType;

/**
 * The BriefBean contains the fields exposed by the SOLR engine for presenting each item in the search results when
 * using the 'minimal' profile
 *
 * @author Yorgos Mamakis <yorgos.mamakis@kb.nl>
 */
public interface BriefBean extends IdBean {

	/**
	 *
	 * @return TITLE field
	 */
	String[] getTitle();

	/**
	 *
	 * @return edm:object
	 */
	String[] getEdmObject();

	/**
	 * @return edm:isShownBy
	 */
	String[] getEdmIsShownBy();

	/**
	 *
	 * @return dc:description
	 */
	String[] getDcDescription();

	/**
	 * Language aware dc:description
	 */
	Map<String, List<String>> getDcDescriptionLangAware();

	/**
	 *
	 * @return YEAR field
	 */
	String[] getYear(); // YEAR copyfield dc_date

	/**
	 *
	 * @return PROVIDER field
	 */
	String[] getProvider(); // PROVIDER copyfield edm_provider

	/**
	 *
	 * @return DATAPROVIDER field
	 */
	String[] getDataProvider(); // DATA_PROVIDER copyfield edm_dataProvider

	/**
	 *
	 * @return LANGUAGE field
	 */
	String[] getLanguage(); // LANGUAGE copyfield edm_language

	/**
	 *
	 * @return RIGHTS field
	 */
	String[] getRights(); // LANGUAGE copyfield dc_language

	/**
	 *
	 * @return TYPE field
	 */
	DocType getType(); // TYPE copyfield edm_type

	// here the dcterms namespaces starts

	/**
	 *
	 * @return dcterms:spatial
	 */
	String[] getDctermsSpatial();

	/**
	 *
	 * @return edm:isShownAt
	 */
	String[] getEdmIsShownAt();

	// Ranking and Enrichment terms

	/**
	 *
	 * @return europeana_completeness field
	 */
	int getEuropeanaCompleteness();

	/**
	 *
	 * @return content tier information if available (but only if debug profile is enabled)
	 */
	String getContentTier();

	/**
	 *
	 * @return metadata tier information if available (but only if debug profile is enabled)
	 */
	String getMetadataTier();

	/**
	 *
	 * @return edm:place
	 */
	String[] getEdmPlace();

	/**
	 *
	 * @return edm:place skos:prefLabel multilingual field
	 */
	List<Map<String, String>> getEdmPlaceLabel();

	/**
	 *
	 * @return edm:place wgs84:posLat
	 */
	List<String> getEdmPlaceLatitude();

	/**
	 *
	 * @return edm:place wgs84:posLong
	 */
	List<String> getEdmPlaceLongitude();

	/**
	 *
	 * @return edm:timespan
	 */
	String[] getEdmTimespan();

	/**
	 *
	 * @return edm:timespan skos:prefLabel multilingual field
	 */
	List<Map<String, String>> getEdmTimespanLabel();

	/**
	 *
	 * @return edm:timespan edm:begin
	 */
	String[] getEdmTimespanBegin();

	/**
	 *
	 * @return edm:timespan edm:end
	 */
	String[] getEdmTimespanEnd();

	/**
	 *
	 * @return edm:agent
	 */
	String[] getEdmAgent();

	/**
	 *
	 * @return edm:agent skos:prefLabel multilingual field
	 */
	List<Map<String, String>> getEdmAgentLabel();

	/**
	 *
	 * @return dcterms:hasPart
	 */
	String[] getDctermsHasPart();

	/**
	 *
	 * @return dc:creator
	 */
	String[] getDcCreator();

	/**
	 *
	 * @return dc:contributor
	 */
	String[] getDcContributor();

	/**
	 *
	 * @return dc:language
	 */
	String[] getDcLanguage();

	/**
	 *
	 * @return Time of indexing of the record
	 */
	Date getTimestamp();

	/**
	 *
	 * @return edm:preview field
	 */
	String[] getEdmPreview();

	/**
	 *
	 * @return the score of the result
	 */
	float getScore();

	/**
	 * Language aware version of the edm:Place->skos:prefLabel field
	 *
	 * @return
	 */
	Map<String, List<String>> getEdmPlaceLabelLangAware();

	/**
	 * Language aware version of the edm:Timespan->skos:prefLabel field
	 *
	 * @return
	 */
	Map<String, List<String>> getEdmTimespanLabelLangAware();

	/**
	 * Language aware version of the edm:Agent->skos:prefLabel field
	 *
	 * @return
	 */
	Map<String, List<String>> getEdmAgentLabelLangAware();

	/**
	 * Opt out fix
	 * @return
	 */
	Boolean getPreviewNoDistribute();

	/**
	 * Language aware dc:title
	 * @return
	 */
	Map<String, List<String>> getDcTitleLangAware();

	/**
	 * Language aware dc:creator
	 * @return
	 */
	Map<String, List<String>> getDcCreatorLangAware();

	/**
	 * Language aware dc:contributor
	 * @return
	 */
	Map<String, List<String>> getDcContributorLangAware();

	/**
	 * Language aware dc:language
	 * @return
	 */
	Map<String, List<String>> getDcLanguageLangAware();
}
