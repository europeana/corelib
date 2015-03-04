package eu.europeana.corelib.tools.utils;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import eu.europeana.corelib.lookup.utils.HashUtils;
import eu.europeana.corelib.lookup.utils.PreSipCreatorUtils;
import eu.europeana.corelib.lookup.utils.SipCreatorUtils;

public class HashUtilsTest {

	// PRE-SIPCREATOR

	@Test 
	public void testHashSHA256(){
		System.out.println(HashUtils.createHashSHA256("http://www.europeana1914-1918.eu/attachments/22106/1517.22106.full.jpg"));
		System.out.println(HashUtils.createHash(" http://www.europeana1914-1918.eu/en/contributions/96"));
		System.out.println(HashUtils.createHash("http://bibliotecadigitalhispanica.bne.es/webclient/MetadataManager?pid=1883679"));
		
		assertEquals("9F86D081884C7D659A2FEAA0C55AD015A3BF4F1B2B0B822CD15D6C15B0F00A08", HashUtils.createHashSHA256("test"));
		assertEquals("67449F383489576A1FBDEF286EBA5415054F0DD9", HashUtils.createHash("http://kulturarvsdata.se/SMVK-VKM/fotografi/1226859"));
	}
	
	
	@Test
	public void testHashCreation() {
		// Using real life data

		System.out.println("**************GENERATING HASHES*********************************");
		String workingDir = StringUtils.endsWith(
				System.getProperty("user.dir"), "corelib") ? System
				.getProperty("user.dir") + "/corelib-solr-tools" : System
				.getProperty("user.dir");
		SipCreatorUtils sipCreatorUtils = new SipCreatorUtils();
		String repository = workingDir + "/src/test/resources/";
		sipCreatorUtils.setRepository(repository);
		System.out
				.println("**************READING COLLECTION MAPPING FROM SIP-CREATOR*******");
		assertEquals("europeana_isShownBy[0]", sipCreatorUtils.getHashField(
				"9200103", "9200103_Ag_EU_TEL_Gallica_a0142"));
		String sipCreatorHashField = sipCreatorUtils.getHashField("9200103",
				"9200103_Ag_EU_TEL_Gallica_a0142");
		System.out.println("The field used to create the SIP-Creator Hash is: "
				+ sipCreatorHashField+"\n\n");
		PreSipCreatorUtils preSipCreatorUtils = new PreSipCreatorUtils();
		preSipCreatorUtils.setRepository(repository);
		System.out
				.println("**************READING COLLECTION MAPPING PRESIP-CREATOR*********");
		assertEquals("europeana_isShownAt", preSipCreatorUtils.getHashField(
				"00735", "00735_A_DE_Landesarchiv_ese_5_0000013080"));
		String preSipCreatorHashField = preSipCreatorUtils.getHashField(
				"00735", "00735_A_DE_Landesarchiv_ese_5_0000013080");
		System.out
				.println("The field used to create the pre-SIPCreator Hash is: "
						+ preSipCreatorHashField+"\n\n");
		List<String> info = readFromFile(
				workingDir
						+ "/src/test/resources/9200103_/9200103_Ag_EU_TEL_Gallica_a0142_1.xml",
				sipCreatorHashField);
		String identifier = info.get(0);
		String hash = info.get(1);
		System.out.println("Hashing Record: " + identifier);
		System.out.println("Actual Europeana Hash: " + hash);
		System.out.println("Generated Europeana Hash: "
				+ HashUtils.createHash(identifier));
		List<String> info2 = readFromFile(
				workingDir
						+ "/src/test/resources/00735_/00735_A_DE_Landesarchiv_ese_5_0000013080_1.xml",
				preSipCreatorHashField);
		String identifier2 = info2.get(0);
		String hash2 = info2.get(1);
		System.out.println("Hashing Record: " + identifier2);
		assertEquals(hash, HashUtils.createHash(identifier));
		assertEquals(hash2, HashUtils.createHash(identifier2));
		System.out.println("Actual Europeana Hash: " + hash2);
		System.out.println("Generated Europeana Hash: "
				+ HashUtils.createHash(identifier2));
	}

	private List<String> readFromFile(String location, String field) {
		final String EUROPEANA_URI = "europeana:uri";
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new File(location));
			List<String> europeanaFields = new ArrayList<String>();
			Element root = doc.getDocumentElement();
			int fieldIndex = StringUtils.substringBetween(field, "[", "]") != null ? Integer
					.parseInt(StringUtils.substringBetween(field, "[", "]"))
					: 0;
			NodeList fieldList = root.getElementsByTagName(StringUtils.replace(
					StringUtils.split(field, '[')[0], "_", ":"));
			europeanaFields.add(fieldList.item(fieldIndex).getTextContent());
			String uri = root.getElementsByTagName(EUROPEANA_URI).item(0)
					.getTextContent();
			String[] fields = StringUtils.split(uri, "/");

			europeanaFields.add(fields[fields.length - 1]);
			return europeanaFields;
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
