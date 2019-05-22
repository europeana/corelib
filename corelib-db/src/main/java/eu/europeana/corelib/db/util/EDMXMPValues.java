package eu.europeana.corelib.db.util;


/**
 * Enumeration containing the XMP values the have to be embedded in
 * the the europeana thumbnail.
 * 
 * @author Georgios Markakis <gwarkx@hotmail.com>
 *
 * @since 11 Jul 2012
 */
public enum  EDMXMPValues {
	// dc:title (with xml:lang attribute set to "x-default") from dc:title
	dc_title("dc:title"),
	// dc:description (with xml:lang attribute set to "x-default") from dc:description
	dc_description("dc:description"),
	// dc:subject (with xml:lang attribute set to "x-default") from dc:subject
	dc_subject("dc:subject"), 
	// dc:coverage (with xml:lang attribute set to "x-default") from dc:coverage
	dc_coverage("dc:coverage"), 
	// dcterms:spatial (with xml:lang attribute set to "x-default") from dcterms:spatial
	dcterms_spatial("dcterms:spatial"),  
	// dcterms:temporal (with xml:lang attribute set to "x-default") from dcterms:temporal
	dcterms_temporal("dcterms:temporal"),
	// dc:rights from edm:rights
	dc_rights("dc:rights"),
	// cc:attributionName from dc:creator
	cc_attributionName("cc:attributionName"),
	// dc:rights from edm:rights
	edm_rights("dc:rights"),
	// edm:dataProvider from europeana:dataProvider
	edm_dataProvider("edm:dataProvider"),
	// edm:provider from europeana:provider
	edm_provider("edm:provider"),
	// xmpRights:Marked from europeana:rights: "False" if europeana:rights
	// is http://creativecommons.org/publicdomain/mark/1.0/ or
	// http://creativecommons.org/publicdomain/zero/1.0/, "True" otherwise.
	xmpRights_Marked("xmpRights:Marked"),
	// xmpRights:WebStatement from europeana:isShownAt
	xmpRights_WebStatement("xmpRights:WebStatement"),
	// xmpMM_OriginalDocumentID from europeana:object
	xmpMM_OriginalDocumentID ("xmpMM:OriginalDocumentID"),
	// cc:morePermissions from europeana:isShownAt (as a value for the
	// rdf:resource attribute)
	cc_morePermissions("cc:morePermissions"),
	// xmpMM:OriginalDocumentID from europeana:object
	stref_OriginalDocumentID("stref:originalDocumentID"),
	// The actual uri of the object as displayed in the europeana portal
	stref_DocumentID("stref:documentID"),
	// cc:useGuidelines with http://www.europeana.eu/rights/pd-usage-guide/
	// (as a value for the rdf:resource attribute) if europeana:rights is
	// http://creativecommons.org/publicdomain/mark/1.0/ or
	// http://creativecommons.org/publicdomain/zero/
	cc_useGuidelines("cc:useGuidelines");

	
	
	
	private String fieldId;
	
	EDMXMPValues(String id) {
		this.setFieldId(id);
	}

	/**
	 * GETTERS and SETTERS
	 */
	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}


}
