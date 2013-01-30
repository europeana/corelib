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

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import eu.europeana.corelib.db.exception.DatabaseException;
import eu.europeana.corelib.definitions.exception.ProblemType;
import eu.europeana.corelib.definitions.jibx.Aggregation;
import eu.europeana.corelib.definitions.jibx.ProxyType;
import eu.europeana.corelib.definitions.jibx.RDF;
import eu.europeana.corelib.definitions.model.ThumbSize;
import org.apache.commons.lang.StringEscapeUtils;

/**
 * Utility classes for XMP manipulation/generation
 * 
 * @author Georgios Markakis <gwarkx@hotmail.com>
 * 
 * @since 31 Jul 2012
 */
public class XMPUtils {

	public static final String PORTALPREFIX = "http://www.europeana.eu/portal/record/";
	public static final String IMAGEPREFIX = "http://europeanastatic.eu/api/image?type=TEXT&uri=";

	/**
	 * Public method for creating an appropriate XMP XML document from an EDM
	 * instance
	 * 
	 * @param edmRecord
	 *            the JIBX EDM representation
	 * @return the XMP string
	 * @throws UnsupportedEncodingException
	 * @throws DatabaseException
	 */
	public static String fetchXMP(String thumbnailURL, RDF edmRecord,
			ThumbSize size) throws UnsupportedEncodingException,
			DatabaseException {
		Map<EDMXMPValues, List<LanguageValueBean>> vals = populatevalues(edmRecord);
		String xmp = produceXMPPXML(thumbnailURL, vals, size);
		return xmp;
	}

	/**
	 * Creates the actual XMP XML given a Map of values
	 * 
	 * @param values
	 *            the map of values
	 * 
	 * @return a String representing the XMP XML
	 * @throws UnsupportedEncodingException
	 * @throws DatabaseException
	 */
	private static String produceXMPPXML(String thumbnailURL,
			Map<EDMXMPValues, List<LanguageValueBean>> values, ThumbSize size)
			throws UnsupportedEncodingException, DatabaseException {

		List<LanguageValueBean> cc_attributionName = values
				.get(EDMXMPValues.cc_attributionName);
		List<LanguageValueBean> cc_morePermissions = values
				.get(EDMXMPValues.cc_morePermissions);
		List<LanguageValueBean> cc_useGuidelines = values
				.get(EDMXMPValues.cc_useGuidelines);
		List<LanguageValueBean> dc_rights = values.get(EDMXMPValues.dc_rights);
		List<LanguageValueBean> dc_title = values.get(EDMXMPValues.dc_title);
		List<LanguageValueBean> dc_description = values
				.get(EDMXMPValues.dc_description);
		List<LanguageValueBean> dc_subject = values
				.get(EDMXMPValues.dc_subject);
		List<LanguageValueBean> dc_coverage = values
				.get(EDMXMPValues.dc_coverage);
		List<LanguageValueBean> dcterms_spatial = values
				.get(EDMXMPValues.dcterms_spatial);
		List<LanguageValueBean> dcterms_temporal = values
				.get(EDMXMPValues.dcterms_temporal);
		List<LanguageValueBean> edm_dataProvider = values
				.get(EDMXMPValues.edm_dataProvider);
		List<LanguageValueBean> edm_provider = values
				.get(EDMXMPValues.edm_provider);
		List<LanguageValueBean> edm_rights = values
				.get(EDMXMPValues.edm_rights);
		String xmpMM_OriginalDocumentID = thumbnailURL;
		List<LanguageValueBean> xmpMM_DocumentID = values
				.get(EDMXMPValues.stref_DocumentID);
		List<LanguageValueBean> xmpRights_Marked = values
				.get(EDMXMPValues.xmpRights_Marked);
		List<LanguageValueBean> xmpRights_WebStatement = values
				.get(EDMXMPValues.xmpRights_WebStatement);

		StringBuffer xml = new StringBuffer();

		xml.append(" <?xpacket begin='' id='W5M0MpCehiHzreSzNTczkc9d'?>");

		xml.append("<x:xmpmeta xmlns:x='adobe:ns:meta/'>");

		xml.append(" <rdf:RDF xmlns:rdf='http://www.w3.org/1999/02/22-rdf-syntax-ns#'>");

		// DerivedFrom Element

		if (xmpMM_OriginalDocumentID != null || edm_provider != null) {
			xml.append("<rdf:Description rdf:about='' xmlns:xmpMM='http://ns.adobe.com/xap/1.0/mm/'  ");
			xml.append("xmlns:stRef= 'http://ns.adobe.com/xap/1.0/sType/ResourceRef#'>");

			xml.append("<xmpMM:DerivedFrom rdf:parseType=\"Resource\">");

			xml.append("<stRef:documentID >");

			StringBuffer imageuiriInPortal = new StringBuffer();

			imageuiriInPortal.append(IMAGEPREFIX);
			imageuiriInPortal.append(xmpMM_DocumentID.get(0).getValue());
			imageuiriInPortal.append("&size=");

			switch (size) {
			case TINY:
				imageuiriInPortal.append("TINY");
				break;
			case MEDIUM:
				imageuiriInPortal.append("BRIEF_DOC");
				break;
			case LARGE:
				imageuiriInPortal.append("FULL_DOC");
				break;
			}

			xml.append(escapeXML(imageuiriInPortal.toString()));

			xml.append("</stRef:documentID >");

			if (xmpMM_OriginalDocumentID != null) {
				xml.append("<stRef:originalDocumentID>");
				xml.append(escapeXML(xmpMM_OriginalDocumentID));
				xml.append("</stRef:originalDocumentID>");
			}

			xml.append("</xmpMM:DerivedFrom>");

			xml.append("</rdf:Description>");
		}

		if (dc_title != null || dc_rights != null || edm_rights != null) {

			xml.append("<rdf:Description rdf:about='' xmlns:dcterms='http://purl.org/dc/terms/'");
			xml.append(" xmlns:dc='http://purl.org/dc/elements/1.1/'>");

			if (dc_title != null) {
				if (dc_title.size() == 1) {
					xml.append("<dc:title xml:lang='");
					xml.append(dc_title.get(0).getLanguage());
					xml.append("'>");
					xml.append(dc_title.get(0).getValue());
					xml.append("</dc:title>");
				}
				if (dc_title.size() > 1) {
					xml.append("<dc:title>");
					xml.append("<rdf:Alt>");
					for (LanguageValueBean val : dc_title) {
						xml.append("<rdf:li xml:lang='");
						xml.append(val.getLanguage());
						xml.append("'>");
						xml.append(val.getValue());
						xml.append("</rdf:li>");
					}
					xml.append("</rdf:Alt>");
					xml.append("</dc:title>");
				}

				if (size == ThumbSize.TINY) {
					xml.append("</rdf:Description>");
				}
			}
			
			if (dc_rights != null || edm_rights != null) {
				xml.append("<dc:rights><rdf:Bag>");
				
				if (edm_rights != null) {
					for (LanguageValueBean val : edm_rights) {
						xml.append("<rdf:li>");
						xml.append(val.getValue());
						xml.append("</rdf:li>");
					}
				}
				
				if (dc_rights != null) {
					for (LanguageValueBean val : dc_rights) {
						xml.append("<rdf:li xml:lang='");
						xml.append(val.getLanguage());
						xml.append("'>");
						xml.append(val.getValue());
						xml.append("</rdf:li>");
					}
				}
				xml.append("</rdf:Bag></dc:rights>");
			}

			if (size != ThumbSize.TINY) {

				if (dc_description != null) {
					if (dc_description.size() == 1) {
						xml.append("<dc:description xml:lang='");
						xml.append(dc_description.get(0).getLanguage());
						xml.append("'>");
						xml.append(dc_description.get(0).getValue());
						xml.append("</dc:description>");
					}
					if (dc_description.size() > 1) {
						xml.append("<dc:description>");
						xml.append("<rdf:Alt>");
						for (LanguageValueBean val : dc_description) {
							xml.append("<rdf:li xml:lang='");
							xml.append(val.getLanguage());
							xml.append("'>");
							xml.append(val.getValue());
							xml.append("</rdf:li>");
						}
						xml.append("</rdf:Alt>");
						xml.append("</dc:description>");
					}

				}

				if (dc_subject != null) {
					if (dc_subject.size() == 1) {
						xml.append("<dc:subject xml:lang='");
						xml.append(dc_subject.get(0).getLanguage());
						xml.append("'>");
						xml.append(dc_subject.get(0).getValue());
						xml.append("</dc:subject>");
					}
					if (dc_title.size() > 1) {
						xml.append("<dc:subject>");
						xml.append("<rdf:Bag>");
						for (LanguageValueBean val : dc_subject) {
							xml.append("<rdf:li xml:lang='");
							xml.append(val.getLanguage());
							xml.append("'>");
							xml.append(val.getValue());
							xml.append("</rdf:li>");
						}
						xml.append("</rdf:Bag>");
						xml.append("</dc:subject>");
					}

				}

				if (dc_coverage != null) {
					if (dc_coverage.size() == 1) {
						xml.append("<dc:coverage xml:lang='");
						xml.append(dc_coverage.get(0).getLanguage());
						xml.append("'>");
						xml.append(dc_coverage.get(0).getValue());
						xml.append("</dc:coverage>");
					}
					if (dc_title.size() > 1) {
						xml.append("<dc:coverage>");
						xml.append("<rdf:Bag>");
						for (LanguageValueBean val : dc_coverage) {
							xml.append("<rdf:li xml:lang='");
							xml.append(val.getLanguage());
							xml.append("'>");
							xml.append(val.getValue());
							xml.append("</rdf:li>");
						}
						xml.append("</rdf:Bag>");
						xml.append("</dc:coverage>");
					}

				}

				if (dcterms_spatial != null) {
					if (dcterms_spatial.size() == 1) {
						xml.append("<dcterms:spatial xml:lang='");
						xml.append(dcterms_spatial.get(0).getLanguage());
						xml.append("'>");
						xml.append(dcterms_spatial.get(0).getValue());
						xml.append("</dcterms:spatial>");
					}
					if (dc_title.size() > 1) {
						xml.append("<dcterms:spatial>");
						xml.append("<rdf:Bag>");
						for (LanguageValueBean val : dcterms_spatial) {
							xml.append("<rdf:li xml:lang='");
							xml.append(val.getLanguage());
							xml.append("'>");
							xml.append(val.getValue());
							xml.append("</rdf:li>");
						}
						xml.append("</rdf:Bag>");
						xml.append("</dcterms:spatial>");
					}

				}

				if (dcterms_temporal != null) {
					if (dcterms_temporal.size() == 1) {
						xml.append("<dcterms:temporal xml:lang='");
						xml.append(dcterms_temporal.get(0).getLanguage());
						xml.append("'>");
						xml.append(dcterms_temporal.get(0).getValue());
						xml.append("</dcterms:temporal>");
					}
					if (dc_title.size() > 1) {
						xml.append("<dcterms:temporal>");
						xml.append("<rdf:Bag>");
						for (LanguageValueBean val : dcterms_temporal) {
							xml.append("<rdf:li xml:lang='");
							xml.append(val.getLanguage());
							xml.append("'>");
							xml.append(val.getValue());
							xml.append("</rdf:li>");
						}
						xml.append("</rdf:Bag>");
						xml.append("</dcterms:temporal>");
					}

				}


				xml.append("</rdf:Description>");

				if (edm_dataProvider != null || edm_provider != null) {
					xml.append("<rdf:Description rdf:about='' xmlns:edm='http://www.europeana.eu/schemas/edm/' xmlns:cc='http://creativecommons.org/ns#'>");
					if (edm_provider != null) {
						xml.append("<edm:dataProvider>");
						xml.append(edm_dataProvider.get(0).getValue());
						xml.append("</edm:dataProvider>");
					}
					if (edm_dataProvider != null) {
						xml.append("<edm:provider>");
						xml.append(edm_provider.get(0).getValue());
						xml.append("</edm:provider>");
					}
					xml.append("</rdf:Description>");
				}

				if (xmpRights_Marked != null || xmpRights_WebStatement != null) {
					xml.append("<rdf:Description rdf:about='' xmlns:xmpRights='http://ns.adobe.com/xap/1.0/rights/' xmlns:xmpMM='http://ns.adobe.com/xap/1.0/mm/'>");
					if (xmpRights_Marked != null) {
						xml.append("<xmpRights:Marked>");
						xml.append(xmpRights_Marked.get(0).getValue());
						xml.append("</xmpRights:Marked>");
					}
					if (xmpRights_WebStatement != null) {
						xml.append("<xmpRights:WebStatement>");
						xml.append(escapeXML(xmpRights_WebStatement.get(0).getValue()));
						xml.append("</xmpRights:WebStatement>");
					}
					xml.append("</rdf:Description>");
				}

				// CC segment

				if (cc_attributionName != null || cc_morePermissions != null
						|| cc_useGuidelines != null) {
					xml.append("<rdf:Description rdf:about='' xmlns:cc='http://creativecommons.org/ns#'>");
					if (cc_attributionName != null) {
						if (cc_attributionName.size() == 1) {
							xml.append("<cc:attributionName>");
							xml.append(cc_attributionName.get(0).getValue());
							xml.append("</cc:attributionName>");
						} else if (cc_attributionName.size() > 1) {
							xml.append("<cc:attributionName>");
							xml.append("<rdf:Bag>");
							for (LanguageValueBean val : cc_attributionName) {
								xml.append("<rdf:li>");
								xml.append(val.getValue());
								xml.append("</rdf:li>");
							}
							xml.append("</rdf:Bag>");
							xml.append("</cc:attributionName>");
						}
					}
					if (cc_morePermissions != null) {
						if (cc_morePermissions.size() == 1) {
							xml.append("<cc:morePermissions rdf:about='");
							xml.append(cc_morePermissions.get(0).getValue());
							xml.append("'/>");
						} else if (cc_morePermissions.size() > 1) {
							xml.append("<cc:morePermissions>");
							xml.append("<rdf:Bag>");
							for (LanguageValueBean val : cc_morePermissions) {
								xml.append("<rdf:li rdf:about='");
								xml.append(val.getValue());
								xml.append("'/>");
							}
							xml.append("</rdf:Bag>");
							xml.append("</cc:morePermissions>");
						}
					}
					if (cc_useGuidelines != null) {
						if (cc_useGuidelines.size() == 1) {
							xml.append("<cc:useGuidelines rdf:about='");
							xml.append(cc_useGuidelines.get(0).getValue());
							xml.append("'/>");
						} else if (cc_useGuidelines.size() > 1) {
							xml.append("<cc:useGuidelines>");
							xml.append("<rdf:Bag>");
							for (LanguageValueBean val : cc_useGuidelines) {
								xml.append("<rdf:li rdf:about='");
								xml.append(val.getValue());
								xml.append("'/>");
							}
							xml.append("</rdf:Bag>");
							xml.append("</cc:useGuidelines>");
						}

					}
					xml.append("</rdf:Description>");
				}
			}
		}

		xml.append(" </rdf:RDF>");
		xml.append("</x:xmpmeta>");
		xml.append("<?xpacket end='w'?>");

		try {
			return formatXml(xml.toString());
		} catch (Exception e) {

			throw new DatabaseException(e, ProblemType.XMPMETADATACREATION);

		}

	}

	/**
	 * Populates a hashmap containing the values that should be contained in the
	 * XMP document embedded in the europeana thumbnail. The values themselves
	 * are provided by the edmRecord which is a representation of the original
	 * EDM document which contains a reference to the annotated thumbnail.
	 * 
	 * @param edmRecord
	 *            the EDM RDF representation
	 * @return a map containing values to be embedded in the XMP document
	 */
	private static Map<EDMXMPValues, List<LanguageValueBean>> populatevalues(
			RDF edmRecord) {

		HashMap<EDMXMPValues, List<LanguageValueBean>> EDMXMPValuesMap = new HashMap<EDMXMPValues, List<LanguageValueBean>>();

		List<Aggregation> aggregationList = edmRecord.getAggregationList();

		List<ProxyType> proxyList = edmRecord.getProxyList();

		// Deal with Aggregation elements
		if (!aggregationList.isEmpty() && aggregationList != null) {

			for (Aggregation aggregation : aggregationList) {

				// Check isShownAt resource
				putInValuesMap(aggregation.getIsShownAt(),
						EDMXMPValues.cc_morePermissions, EDMXMPValuesMap);

				// Check the object resource or literal
				putInValuesMap(aggregation.getObject(),
						EDMXMPValues.stref_OriginalDocumentID, EDMXMPValuesMap);

				// Check the rights resource or literal
				LanguageValueBean rights = putInValuesMap(
						aggregation.getRights(), EDMXMPValues.edm_rights,
						EDMXMPValuesMap);

				// Determine the values of xmpRights_Marked & cc_useGuidelines
				// according to the contents of the rights field

				if ("http://creativecommons.org/publicdomain/mark/1.0/"
						.equals(rights.getValue())
						|| "http://creativecommons.org/publicdomain/zero/1.0/"
								.equals(rights.getValue())) {

					putInValuesMap("False", EDMXMPValues.xmpRights_Marked,
							EDMXMPValuesMap);

					putInValuesMap(
							"http://www.europeana.eu/rights/pd-usage-guide/",
							EDMXMPValues.cc_useGuidelines, EDMXMPValuesMap);

				} else {
					putInValuesMap("True", EDMXMPValues.xmpRights_Marked,
							EDMXMPValuesMap);
				}

				// Check the DataProvider resource or literal
				putInValuesMap(aggregation.getDataProvider(),
						EDMXMPValues.edm_dataProvider, EDMXMPValuesMap);

				// Check the Provider resource or literal
				putInValuesMap(aggregation.getProvider(),
						EDMXMPValues.edm_provider, EDMXMPValuesMap);
				
				// Check the isShownAt resource or literal
				putInValuesMap(aggregation.getIsShownAt(),
						EDMXMPValues.xmpRights_WebStatement, EDMXMPValuesMap);
			}

		}

		// Deal with Proxy elements
		if (!proxyList.isEmpty() && proxyList != null) {

			for (ProxyType pcho : proxyList) {

				// Set the document ID from rdf:about attribute
				putInValuesMap(pcho.getAbout(), EDMXMPValues.stref_DocumentID,
						EDMXMPValuesMap);

				List<eu.europeana.corelib.definitions.jibx.EuropeanaType.Choice> dclist = pcho
						.getChoiceList();

				for (eu.europeana.corelib.definitions.jibx.EuropeanaType.Choice dcchoice : dclist) {
					// Check the dc:creator resource or literal
					if (dcchoice.ifCreator()) {
						putInValuesMap(dcchoice.getCreator(),
								EDMXMPValues.cc_attributionName,
								EDMXMPValuesMap);
					}

					// Check the dc:title literal
					if (dcchoice.ifTitle()) {
						putInValuesMap(dcchoice.getTitle(),
								EDMXMPValues.dc_title, EDMXMPValuesMap);
					}

					// Check the dc:description literal
					if (dcchoice.ifDescription()) {
						if (dcchoice.getDescription() != null
								&& dcchoice.getDescription().getString() != null) {

							String desc = dcchoice.getDescription().getString();

							if (desc.length() < 100) {

								putInValuesMap(desc,
										EDMXMPValues.dc_description,
										EDMXMPValuesMap);
							} else {
								putInValuesMap(
										"http://www.europeana.eu/portal/record"+pcho.getAbout()+".html",
										EDMXMPValues.dc_description,
										EDMXMPValuesMap);
							}
						}
					}

					// Check the dc:rights resource or literal
					if (dcchoice.ifRights()) {
						putInValuesMap(dcchoice.getRights(),
								EDMXMPValues.dc_rights, EDMXMPValuesMap);
					}

					// Check the dc:subject resource or literal
					if (dcchoice.ifSubject()) {
						putInValuesMap(dcchoice.getSubject(),
								EDMXMPValues.dc_subject, EDMXMPValuesMap);
					}

					/*
					// Check the dc:coverage resource or literal
					if (dcchoice.ifCoverage()) {
						putInValuesMap(dcchoice.getCoverage(),
								EDMXMPValues.dc_coverage, EDMXMPValuesMap);
					}

					// Check the dc:spatial resource or literal
					if (dcchoice.ifSpatial()) {
						putInValuesMap(dcchoice.getSpatial(),
								EDMXMPValues.dcterms_spatial, EDMXMPValuesMap);
					}
					*/

					// Check the dc:temporal resource or literal
					if (dcchoice.ifTemporal()) {
						putInValuesMap(dcchoice.getTemporal(),
								EDMXMPValues.dcterms_temporal, EDMXMPValuesMap);
					}
				}

			}

		}
		return EDMXMPValuesMap;
	}

	/**
	 * @param object
	 * @param valuekey
	 * @param valuesMap
	 */
	private static <T> LanguageValueBean putInValuesMap(T object,
			EDMXMPValues valuekey,
			HashMap<EDMXMPValues, List<LanguageValueBean>> valuesMap) {
		if (object != null) {
			List<LanguageValueBean> lvmlist = valuesMap.get(valuekey);

			if (lvmlist == null) {
				lvmlist = new ArrayList<LanguageValueBean>();
				valuesMap.put(valuekey, lvmlist);
			}

			LanguageValueBean lvm = null;

			if (object instanceof String) {
				lvm = LanguageValueBean.getInstance();
				lvm.setValue((String) object);
				lvmlist.add(lvm);
			} else {
				lvm = returnVLBFromJibx(object);
				lvmlist.add(lvm);
			}
			return lvm;
		} else {
			return null;
		}
	}

	/**
	 * Invokes the getResource,getString,getLang methods on an object via reflection
	 * 
	 * @param object
	 * @return
	 */
	private static <T> LanguageValueBean returnVLBFromJibx(T object) {

		if (object == null)
			return null;

		LanguageValueBean vlb = LanguageValueBean.getInstance();

		Method[] methods = object.getClass().getMethods();

		for (int i = 0; i < methods.length; i++) {

			try {
				if (methods[i].getName().equals("getResource")) {
					if (vlb.getValue() == null) {
						String resource = (String) methods[i].invoke(object);
						vlb.setValue(resource);
					}

				}
				if (methods[i].getName().equals("getString")) {
					if (vlb.getValue() == null) {
						String resource = (String) methods[i].invoke(object);
						vlb.setValue(resource);
					}
				}
				if (methods[i].getName().equals("getLang()")) {
					String resource = (String) methods[i].invoke(object);
					vlb.setValue(resource);
				}

			} catch (IllegalArgumentException e) {

			} catch (IllegalAccessException e) {

			} catch (InvocationTargetException e) {

			}

		}

		return vlb;
	}

	/**
	 * Utility method for pretty printing the XML file created by String
	 * concatenation
	 * 
	 * @param origxml
	 *            the original string
	 * @return a formated XML string
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 * @throws TransformerException
	 * @throws Exception
	 */
	private static String formatXml(String origxml)
			throws ParserConfigurationException, SAXException, IOException,
			TransformerException {

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
		transformer.setOutputProperty(
				"{http://xml.apache.org/xslt}indent-amount", "2");

		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		DOMSource source = new DOMSource(doc);

		transformer.transform(source, result);

		xml = writer.toString();
		return xml;
	}
	
	
	/**
	 * Escapes an XML String
	 * @param tobeEscaped
	 * @return
	 */
	private static String escapeXML(String tobeEscaped){
		
		String escaped = StringEscapeUtils.escapeXml(tobeEscaped);
		
		return escaped;
		
	}

}
