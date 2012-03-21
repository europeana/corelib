package eu.europeana.corelib.solr.denormalization.impl;

import java.util.ArrayList;
import java.util.List;

/**
 * Class holding the mappable Europeana Fields for denormalization
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

		EDM_DATAPROVIDER("edm:dataProvider"), EDM_PROVIDER("edm:provider"), DC_CREATOR(
				"dc:creator"), DC_CONTRIBUTOR("dc:contributor"), DC_DATE(
				"dc:date"), DC_DESCRIPTION("dc:description"), DC_PUBLISHER(
				"dc:publisher"), DC_RELATION("dc:relation"), DC_SUBJECT(
				"dc:subject"), DC_TYPE("dc:type"), DCTERMS_CREATED(
				"dcterms:created"), DCTERMS_EXTENT("dcterms:extent"), DCTERMS_ISSUED(
				"dcterms:issued"), DCTERMS_PROVENANCE("dcterms:provenance"), DCTERMS_REPLACES(
				"dcterms:replaces"), DCTERMS_SPATIAL("dcterms:spatial"), DCTERMS_TABLEOFCONTENTS(
				"dcterms:tableOfContents"), DCTERMS_TEMPORAL("dcterms:temporal"), EDM_BROADER(
				"edm:broader"), EDM_CURRENTLOCATION("edm:currentLocation"), DCTERMS_ISPARTOF(
				"dcterms:isPartOf");

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
}
