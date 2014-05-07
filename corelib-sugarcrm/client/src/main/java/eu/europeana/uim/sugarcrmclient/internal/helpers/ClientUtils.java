/*
 * Copyright 2007 EDL FOUNDATION
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * you may not use this work except in compliance with the
 * Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://ec.europa.eu/idabc/eupl
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 */
package eu.europeana.uim.sugarcrmclient.internal.helpers;

import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import eu.europeana.uim.sugarcrmclient.enums.EuropeanaDatasetStates;
import eu.europeana.uim.sugarcrmclient.enums.EuropeanaOrgRole;
import eu.europeana.uim.sugarcrmclient.jibxbindings.Array;
import eu.europeana.uim.sugarcrmclient.jibxbindings.ArrayAttributes;
import eu.europeana.uim.sugarcrmclient.jibxbindings.ArrayAttributes.ArrayType;
import eu.europeana.uim.sugarcrmclient.jibxbindings.CommonAttributes;
import eu.europeana.uim.sugarcrmclient.jibxbindings.Login;
import eu.europeana.uim.sugarcrmclient.jibxbindings.SelectFields;
import eu.europeana.uim.sugarcrmclient.jibxbindings.UserAuth;
import eu.europeana.uim.sugarcrmclient.jibxbindings.NameValueList;
import eu.europeana.uim.sugarcrmclient.jibxbindings.NameValue;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.JiBXException;

/**
 * This Class provides auxiliary methods that can be used for the creation &
 * instantiation of SugarWSClient instances (see provided Junit tests for usage
 * examples).
 * 
 * @author Georgios Markakis
 */
public class ClientUtils {

	private static org.apache.log4j.Logger logger = Logger
			.getLogger(ClientUtils.class);

	private static Map<String, Map<String, String>> translateMap;

	/**
	 * Utility Class (Does not instantiate)
	 */
	private ClientUtils() {

	}

	/**
	 * This method marshals the contents of a JAXB Element and outputs the
	 * results to the Logger.
	 * 
	 * @param jaxbObject
	 *            A JIBX representation of a SugarCRM SOAP Element.
	 */
	public static void logMarshalledObject(Object jibxObject) {

		try {

			String xmlContent = unmarshallObject(jibxObject);

			logger.info("===========================================");
			StringBuffer sb = new StringBuffer("Soap Ouput for Class: ");
			sb.append(jibxObject.getClass().getSimpleName());
			logger.info(sb.toString());
			logger.info(xmlContent);
			logger.info("===========================================");
		} catch (JiBXException e) {

			logger.error(e.getMessage());
		}

	}

	/**
	 * This method marshals the contents of a JAXB Element and outputs the
	 * results to the Karaf output console.
	 * 
	 * @param out
	 *            the (Karaf console) printwriter
	 * @param jibxObject
	 */
	public static void logMarshalledObjectOsgi(PrintStream out,
			Object jibxObject) {

		try {

			String xmlContent = unmarshallObject(jibxObject);

			out.println("===========================================");
			StringBuffer sb = new StringBuffer("Soap Ouput for Class: ");
			sb.append(jibxObject.getClass().getSimpleName());
			out.println(sb.toString());
			out.println(xmlContent);
			out.println("===========================================");
		} catch (JiBXException e) {
			logger.error(e.getMessage());
		}

	}

	/**
	 * Private auxiliary method that performs the unmarshalling of the object
	 * 
	 * @param jibxObject
	 * @return
	 * @throws JiBXException
	 */
	private static String unmarshallObject(Object jibxObject)
			throws JiBXException {
		IBindingFactory context;
		context = BindingDirectory.getFactory(jibxObject.getClass());

		IMarshallingContext mctx = context.createMarshallingContext();
		mctx.setIndent(2);
		StringWriter stringWriter = new StringWriter();
		mctx.setOutput(stringWriter);
		mctx.marshalDocument(jibxObject);

		String xmlContents = stringWriter.toString();

		return xmlContents;
	}

	/**
	 * Encrypts a given String into a MD5 format.
	 * 
	 * @param value
	 *            The string to be encrypted
	 * @return the encrypted String
	 */
	public static String md5(String value) {
		// MessageDigest mdEnc;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(value.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * Creates a Login Soap Request given the user credentials.
	 * 
	 * @param username
	 * @param passwrd
	 * @return a Login Soap Request Jaxb representation
	 */
	public static Login createStandardLoginObject(String username,
			String passwrd) {

		Login login = new Login();
		UserAuth user = new UserAuth();

		user.setUserName(username);
		user.setPassword(md5(passwrd));
		user.setVersion("1.0");

		login.setApplicationName("EuropeanaSugarCRMClient");
		login.setUserAuth(user);

		return login;
	}

	/**
	 * Creates a SelectFields Soap Object given a List<String> fieldnames object
	 * which sets the fields to be retrieved.
	 * 
	 * @param fieldnames
	 * @return
	 */
	public static SelectFields generatePopulatedSelectFields(
			List<String> fieldnames) {

		SelectFields selfields = new SelectFields();
		StringBuffer arrayType = new StringBuffer();
		arrayType.append("string[");
		arrayType.append(fieldnames.size());
		arrayType.append("]");
		CommonAttributes commonAttributes = new CommonAttributes();
		commonAttributes.setHref(arrayType.toString());
		selfields.setCommonAttributes(commonAttributes);

		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder documentBuilder;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();

			for (String fieldname : fieldnames) {
				Element element = document.createElement("string");
				Array array = new Array();
				array.getAnyList();
				selfields.setArray(array);
				element.appendChild(document.createTextNode(fieldname));
			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		}

		return selfields;

	}

	/**
	 * Creates a NameValueList Soap Element given a List<String> namevalues
	 * object which sets the fields to be retrieved.
	 * 
	 * @param fieldnames
	 * @return
	 */
	public static NameValueList generatePopulatedNameValueList(
			List<NameValue> namevalues) {

		NameValueList namevalueList = new NameValueList();
		StringBuffer arrayType = new StringBuffer();
		arrayType.append("name_value[");
		arrayType.append(namevalues.size());
		arrayType.append("]");
		Array array = new Array();
		ArrayType arrTypeObj = new ArrayType();
		arrTypeObj.setArrayType(arrayType.toString());

		ArrayAttributes atts = new ArrayAttributes();
		atts.setArrayType(arrTypeObj);

		namevalueList.setArrayAttributes(atts);
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder documentBuilder;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();

			for (NameValue namevalue : namevalues) {
				Element name_value = document.createElement("name_value");

				Element name = document.createElement("name");
				Element value = document.createElement("value");

				name.appendChild(document.createTextNode(namevalue.getName()));
				value.appendChild(document.createTextNode(namevalue.getValue()));
				name_value.appendChild(name);
				name_value.appendChild(value);

				array.getAnyList().add(name_value);
			}

			namevalueList.setArray(array);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		}

		return namevalueList;

	}

	/**
	 * @param responseString
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws XPathExpressionException
	 */
	public static HashMap<String, HashMap<String, String>> responseFactory(
			String responseString) throws ParserConfigurationException,
			SAXException, IOException, XPathExpressionException {

		HashMap<String, HashMap<String, String>> returnMap = new HashMap<String, HashMap<String, String>>();

		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
				.newInstance();
		documentBuilderFactory.setNamespaceAware(true);
		DocumentBuilder documentBuilder = documentBuilderFactory
				.newDocumentBuilder();

		Document document = documentBuilder.parse(new InputSource(
				new StringReader(responseString)));

		//String messageName = document.getFirstChild().getNodeName();

		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();

		xpath.setNamespaceContext(new SugarCRMNamespaceContext());
		XPathExpression expr = xpath
				.compile("//ns1:get_entry_listResponse/return/entry_list/item");

		Object result = expr.evaluate(document, XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;

		for (int i = 0; i < nodes.getLength(); i++) {

			NodeList innerNodes = nodes.item(i).getChildNodes();

			String id = innerNodes.item(0).getTextContent();

			HashMap<String, String> elementData = new HashMap<String, String>();

			NodeList infoNodes = innerNodes.item(2).getChildNodes();

			for (int z = 0; z < infoNodes.getLength(); z++) {
				String name = infoNodes.item(z).getFirstChild()
						.getTextContent();
				String value = infoNodes.item(z).getLastChild()
						.getTextContent();

				elementData.put(name, value);
			}
			returnMap.put(id, elementData);
		}
		return returnMap;
	}

	/**
	 * @param responseString
	 * @return
	 */
	public static String extractSimpleResponse(String responseString) {

		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
				.newInstance();
		documentBuilderFactory.setNamespaceAware(true);
		DocumentBuilder documentBuilder;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(new InputSource(
					new StringReader(responseString)));
			String sessionID = null;
			NodeList nl = document.getElementsByTagName("id");

			if (nl.getLength() > 0) {
				sessionID = nl.item(0).getTextContent();
			}

			return sessionID;

		} catch (ParserConfigurationException e) {
			logger.error(e.getMessage());
		} catch (SAXException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}

		return null;
	}

	/**
	 * Auxiliary method for extracting information from DOM objects embedded in
	 * SOAP responses
	 * 
	 * @param value
	 *            The value to be extracted
	 * @param el
	 *            the DOM element
	 * @return the value
	 */
	public static String extractFromElement(String value, Element el) {
		NodeList nl = el.getElementsByTagName("item");

		for (int i = 0; i < nl.getLength(); i++) {
			Node nd = nl.item(i);
			String textcontent = nd.getChildNodes().item(0).getTextContent();
			if (value.equals(textcontent)) {
				return nd.getChildNodes().item(1).getTextContent();
			}

		}
		return null;
	}

	/**
	 * Returns a Map containing SUgarCRM identifiers
	 * 
	 * @param el
	 * @return the populated map
	 */
	public static Map<String, String> mapFromElement(Element el) {
		HashMap<String, String> retMap = new HashMap<String, String>();
		NodeList nl = el.getElementsByTagName("item");
		for (int i = 0; i < nl.getLength(); i++) {
			Node nd = nl.item(i);
			String id = nd.getChildNodes().item(0).getTextContent();
			String content = nd.getChildNodes().item(1).getTextContent();
			retMap.put(id, content);
		}
		return retMap;
	}

	/**
	 * Translates the status code returned by SugarCRM into a human readable
	 * form.
	 * 
	 * @param sugarcrmStatusStr
	 * @return
	 */
	public static String translateStatus(String sugarcrmStatusStr) {
		if (translateMap == null) {
			setUpDatasetStates();
		}

		if (sugarcrmStatusStr != null) {
			sugarcrmStatusStr = sugarcrmStatusStr.replace(" ", "%");
			if (translateMap.get("sys2desc").containsKey(sugarcrmStatusStr)) {
				return translateMap.get("sys2desc").get(sugarcrmStatusStr);
			} else {
				return "Unknown State";
			}
		} else {
			return "No State Defined";
		}
	}

	/**
	 * Translates the description of a Dataset status to its stored value.
	 * 
	 * @param description
	 * @return
	 */
	public static String translateDsStatusDescription(String description) {
		if (translateMap == null) {
			setUpDatasetStates();
		}

		if (StringUtils.isNotBlank(description) && translateMap.get("desc2sys").containsKey(description)) {
			return translateMap.get("desc2sys").get(description).replace("%", " ");
		} else {
			return null;
		}
	}

	private static void setUpDatasetStates() {
		if (translateMap == null) {
			translateMap = new HashMap<String, Map<String,String>>();
			translateMap.put("sys2desc", new HashMap<String, String>());
			translateMap.put("desc2sys", new HashMap<String, String>());
		}

		for (EuropeanaDatasetStates e : EuropeanaDatasetStates.values()) {
			translateMap.get("sys2desc").put(e.getSysId(), e.getDescription());
			translateMap.get("desc2sys").put(e.getDescription(), e.getSysId());
		}
	}

	/**
	 * Translates the status code returned by SugarCRM into a human readable
	 * form.
	 * 
	 * @param sugarcrmStatusStr
	 * @return
	 */
	public static String translateType(String provtype) {
		if (provtype != null) {
			EuropeanaOrgRole actualvalue = null;
			for (EuropeanaOrgRole e : EuropeanaOrgRole.values()) {
				if (e.getSysId().equals(provtype)) {
					actualvalue = e;
				}
			}

			if (actualvalue != null) {
				return actualvalue.getDescription();
			} else {
				return "Unknown Type";
			}
		} else {
			return "Undefined";
		}
	}
}
