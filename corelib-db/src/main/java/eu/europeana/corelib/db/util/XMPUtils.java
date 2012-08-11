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
package eu.europeana.corelib.db.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import eu.europeana.corelib.definitions.jibx.Aggregation;
import eu.europeana.corelib.definitions.jibx.ProxyType;
import eu.europeana.corelib.definitions.jibx.RDF;
import eu.europeana.corelib.definitions.jibx.RDF.Choice;

/**
 * Utility classes for XMP manipulation/generation
 * 
 * @author Georgios Markakis <gwarkx@hotmail.com>
 * 
 * @since 31 Jul 2012
 */
public class XMPUtils {

	
	public static final String PORTALPREFIX = "http://www.europeana.eu/portal/record/";
	
	
	/**
	 * Public method for creating an appropriate XMP XML document from an EDM instance
	 * @param edmRecord the JIBX EDM representation
	 * @return the XMP string
	 */
	public static String fetchXMP(RDF edmRecord){
		Map<EDMXMPValues, String> vals = populatevalues(edmRecord);
		String xmp = produceXMPPXML(vals);
		return xmp;
	}
	
	
	
	/**
	 * Creates the actual XMP XML given a Map of values
	 * @param values the map of values
	 * 
	 * @return a String representing the XMP XML
	 */
	private static String produceXMPPXML(Map<EDMXMPValues, String> values) {

		String cc_attributionName = values.get(EDMXMPValues.cc_attributionName);
		String cc_morePermissions = values.get(EDMXMPValues.cc_morePermissions);
		String cc_useGuidelines = values.get(EDMXMPValues.cc_useGuidelines);
		String dc_rights = values.get(EDMXMPValues.dc_rights);
		String dc_title = values.get(EDMXMPValues.dc_title);
		String edm_dataProvider = values.get(EDMXMPValues.edm_dataProvider);
		String edm_provider = values.get(EDMXMPValues.edm_provider);
		String edm_rights = values.get(EDMXMPValues.edm_rights);
		String xmpMM_OriginalDocumentID = values
				.get(EDMXMPValues.stref_OriginalDocumentID);
		String xmpMM_DocumentID = values
				.get(EDMXMPValues.stref_DocumentID);
		String xmpRights_Marked = values.get(EDMXMPValues.xmpRights_Marked);
		String xmpRights_WebStatement = values
				.get(EDMXMPValues.xmpRights_WebStatement);

		StringBuffer xml = new StringBuffer();
		
		xml.append(" <?xpacket begin='' id='W5M0MpCehiHzreSzNTczkc9d'?>");
		
		xml.append("<x:xmpmeta xmlns:x='adobe:ns:meta/'>");
		
		
		xml.append(" <rdf:RDF xmlns:rdf='http://www.w3.org/1999/02/22-rdf-syntax-ns#'>");
		
		// CC segment

		if (cc_attributionName != null || cc_morePermissions != null
				|| cc_useGuidelines != null) {
			xml.append("<rdf:Description rdf:about='' xmlns:cc='http://creativecommons.org/ns#'>");
			if (cc_attributionName != null) {
				xml.append("<cc:attributionName>");
				xml.append(cc_attributionName);
				xml.append("</cc:attributionName>");
			}
			if (cc_morePermissions != null) {
				xml.append("<cc:morePermissions>");
				xml.append(cc_morePermissions);
				xml.append("</cc:morePermissions>");
			}
			if (cc_useGuidelines != null) {
				xml.append("<cc:useGuidelines>");
				xml.append(cc_useGuidelines);
				xml.append("</cc:useGuidelines>");
			}
			xml.append("</rdf:Description>");
		}

		if (dc_title != null || dc_rights != null || edm_rights != null) {
			xml.append("<rdf:Description rdf:about='' xmlns:dc='http://purl.org/dc/elements/1.1/'>");
			if (dc_title != null) {
				xml.append("<dc:title><rdf:Alt><rdf:li xml:lang='x-default'>");
				xml.append(dc_title);
				xml.append("</rdf:li></rdf:Alt></dc:title>");
			}
			if (dc_rights != null ||  edm_rights != null) {
				xml.append("<dc:rights><rdf:Alt>");
				if(dc_rights != null){
					xml.append("<rdf:li xml:lang='x-default'>");
					xml.append(dc_rights);
					xml.append("</rdf:li>");
				}
				if(edm_rights != null){
					xml.append("<rdf:li xml:lang='x-default'>");
					xml.append(edm_rights);
					xml.append("</rdf:li>");
				}
				xml.append(dc_rights);
				xml.append("</rdf:Alt></dc:rights>");
			}
			xml.append("</rdf:Description>");
		}

		if (edm_dataProvider != null || edm_provider != null) 
		{
			xml.append("<rdf:Description rdf:about='' xmlns:edm='http://www.europeana.eu/schemas/edm/' xmlns:cc='http://creativecommons.org/ns#'>");
			if (edm_provider != null) {
				xml.append("<edm:dataProvider>");
				xml.append(edm_dataProvider);
				xml.append("</edm:dataProvider>");
			}
			if (edm_dataProvider != null) {
				xml.append("<edm:provider>");
				xml.append(edm_provider);
				xml.append("</edm:provider>");
			}
			xml.append("</rdf:Description>");
		}
		
		if (xmpMM_OriginalDocumentID != null || edm_provider != null) 
		{
			xml.append("<rdf:Description rdf:about='' xmlns:xmpMM='http://ns.adobe.com/xap/1.0/mm/'  ");
			xml.append("xmlns:stRef= 'http://ns.adobe.com/xap/1.0/sType/ResourceRef#'>");
			
			xml.append("<xmpMM:DerivedFrom rdf:parseType=\"Resource\">");
			
			xml.append("<stRef:DocumentID>");
			xml.append(PORTALPREFIX);
			xml.append(xmpMM_DocumentID);
			xml.append(".html");
			xml.append("</stRef:DocumentID>");
			
			
			if (xmpMM_OriginalDocumentID != null) {
				xml.append("<stRef:OriginalDocumentID>");
				xml.append(xmpMM_OriginalDocumentID);
				xml.append("</stRef:OriginalDocumentID>");
			}
			
			xml.append("</xmpMM:DerivedFrom>");
			
			xml.append("</rdf:Description>");
		}
		
		if (xmpRights_Marked != null || xmpRights_WebStatement != null) 
		{
			xml.append("<rdf:Description rdf:about='' xmlns:xmpRights='http://ns.adobe.com/xap/1.0/rights/' xmlns:xmpMM='http://ns.adobe.com/xap/1.0/mm/'>");
			if (xmpRights_Marked != null) {
				xml.append("<xmpRights:Marked>");
				xml.append(xmpRights_Marked);
				xml.append("</xmpRights:Marked>");
			}
			if (xmpRights_WebStatement != null) {
				xml.append("<xmpRights:WebStatement>");
				xml.append(xmpRights_WebStatement);
				xml.append("</xmpRights:WebStatement>");
			}
			xml.append("</rdf:Description>");
		}
		
		xml.append(" </rdf:RDF>");
		xml.append("</x:xmpmeta>");
		xml.append("<?xpacket end='w'?>");
		
		
		try {
			return formatXml(xml.toString());
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * Populates a hashmap containing the values that should be contained in the XMP
	 * document  embedded in the europeana thumbnail. The values themselves are 
	 * provided by the edmRecord which is a representation of the original EDM document 
	 * which contains a reference to the annotated thumbnail.  
	 * 
	 * @param edmRecord the EDM RDF representation
	 * @return a map containing values to be embedded in the XMP document
	 */
	private static Map<EDMXMPValues, String> populatevalues(RDF edmRecord) {

		HashMap<EDMXMPValues, String> EDMXMPValuesMap = new HashMap<EDMXMPValues, String>();

		List<Choice> elements = edmRecord.getChoiceList();

		for (Choice element : elements) {

			// Deal with Aggregation elements
			if (element.ifAggregation()) {

				Aggregation aggregation = element.getAggregation();

				if (aggregation.getIsShownAt() != null) {
					EDMXMPValuesMap.put(EDMXMPValues.xmpRights_Marked,
							aggregation.getIsShownAt().getResource());
					EDMXMPValuesMap.put(EDMXMPValues.cc_morePermissions,
							aggregation.getIsShownAt().getResource());
				}

				if (aggregation.getObject() != null) {
					EDMXMPValuesMap.put(EDMXMPValues.stref_OriginalDocumentID,
							aggregation.getObject().getResource());
				}

				if (aggregation.getRights() != null) {
					EDMXMPValuesMap.put(EDMXMPValues.edm_rights, aggregation
							.getRights().getResource());

					if (aggregation
							.getRights()
							.getResource()
							.equals("http://creativecommons.org/publicdomain/mark/1.0/")
							|| aggregation
									.getRights()
									.getResource()
									.equals("http://creativecommons.org/publicdomain/zero/1.0/")) {

						EDMXMPValuesMap.put(EDMXMPValues.xmpRights_Marked,
								"False");
						EDMXMPValuesMap
								.put(EDMXMPValues.cc_useGuidelines,
										"http://www.europeana.eu/rights/pd-usage-guide/");
					} else {
						EDMXMPValuesMap.put(EDMXMPValues.xmpRights_Marked,
								"True");
					}

				}

				if (aggregation.getDataProvider() != null) {
					EDMXMPValuesMap.put(EDMXMPValues.edm_dataProvider,
							aggregation.getDataProvider().getString());
				}

				if (aggregation.getProvider() != null) {
					EDMXMPValuesMap.put(EDMXMPValues.edm_provider, aggregation
							.getProvider().getString());
				}

			}

			// Deal with Proxy elements
			if (element.ifProxy()) {
				ProxyType pcho = element.getProxy();

				//Set the document ID from rdf:about attribute
				EDMXMPValuesMap.put(EDMXMPValues.stref_DocumentID,pcho.getAbout());
				
				List<eu.europeana.corelib.definitions.jibx.EuropeanaType.Choice> dclist = pcho
						.getChoiceList();

				for (eu.europeana.corelib.definitions.jibx.EuropeanaType.Choice dcchoice : dclist) {
					if (dcchoice.ifCreator()) {
						EDMXMPValuesMap.put(EDMXMPValues.cc_attributionName,
								dcchoice.getCreator().getString());
					}
					if (dcchoice.ifTitle()) {
						EDMXMPValuesMap.put(EDMXMPValues.dc_title, dcchoice
								.getTitle().getString());
					}
					if (dcchoice.ifRights()) {
						EDMXMPValuesMap.put(EDMXMPValues.dc_rights, dcchoice
								.getRights().getString());
					}
				}

			}

		}

		return EDMXMPValuesMap;
	}
	
	
	
    /**
     * Utility method for pretty printing the XML file created
     * by String concatenation 
     * 
     * @param origxml the original string
     * @return a formated XML string
     * @throws Exception
     */
    private static String formatXml(String origxml) throws Exception {
    	
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(origxml));
        Document doc = db.parse(is);
    	
        String xml = "";
        TransformerFactory transFactory = TransformerFactory.newInstance();
        Transformer transformer = transFactory.newTransformer();

        transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        DOMSource source = new DOMSource(doc);

        transformer.transform(source, result);

        xml = writer.toString();
        return xml;
    }

}
