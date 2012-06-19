package eu.europeana.corelib.dereference.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * Class holding the mappable Europeana Fields for denormalization
 * @author yorgos.mamakis@ kb.nl
 *
 */
public class EuropeanaField {
	/**
	 * Enumeration holding the fields that should be checked for normalization
	 * (plus the contextual entities... they are missing now, but should be included)
	 * Also check the reference field prior to checking dereference
	 * 
	 * @author Yorgos.Mamakis@ kb.nl
	 * 
	 */
	
	private UriField uriField;
	enum UriField {

		EDM_DATAPROVIDER("provider_aggregation_edm_dataProvider"), EDM_PROVIDER("provider_aggregation_edm_provider"), DC_CREATOR(
				"provider_dc_creator"), DC_CONTRIBUTOR("provider_dc_contributor"), DC_DATE(
				"provider_dc_date"), DC_DESCRIPTION("provider_dc_description"), DC_PUBLISHER(
				"provider_dc_publisher"), DC_RELATION("provider_dc_relation"), DC_SUBJECT(
				"provider_dc_subject"), DC_TYPE("provider_dc_type"), DCTERMS_CREATED(
				"provider_dcterms_created"), DCTERMS_EXTENT("provider_dcterms_extent"), DCTERMS_ISSUED(
				"provider_dcterms_issued"), DCTERMS_PROVENANCE("provider_dcterms_provenance"), DCTERMS_REPLACES(
				"provider_dcterms_replaces"), DCTERMS_SPATIAL("provider_dcterms_spatial"), DCTERMS_TABLEOFCONTENTS(
				"provider_dcterms_tableOfContents"), DCTERMS_TEMPORAL("provider_dcterms_temporal"), SKOS_BROADER(
				"cc_skos_broader"), EDM_CURRENTLOCATION_LAT("provider_edm_currentLocation_lat"), DCTERMS_ISPARTOF(
				"provider_dcterms_isPartOf");

		private String uriField;

		private UriField(String uriField) {
			this.uriField = uriField;
		}

		public String toString() {
			return this.uriField;
		}
	}
	
	public String getFieldValue(){
		return this.uriField.toString();
	}
	public UriField[] getFields(){
		return UriField.values();
	}
	
	public String[] getFieldValues(){
		List<String> values = new ArrayList<String>();
		for(UriField uriField:UriField.values()){
			values.add(uriField.toString());
		}
		return values.toArray(new String[values.size()]);
	}
	
	UriField getField(String field){
		return this.uriField;
	}
	
	public void setField(UriField field){
		this.uriField = field;
	}
	
	public static boolean contains (String field){
		for(UriField uriField: UriField.values()){
			if (StringUtils.equals(uriField.toString(), field)){
				return true;
			}
		}
		return false;
	}
}
