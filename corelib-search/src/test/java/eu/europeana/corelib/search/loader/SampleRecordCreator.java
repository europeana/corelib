package eu.europeana.corelib.search.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang.StringUtils;

import com.ctc.wstx.stax.WstxInputFactory;

/**
 * Utility to create records in the form expected by ContentLoader.
 *
 * NOTE!!!!: It only works with the sample EDM xml provided for local test
 * purposes as it makes the assumption that EDM records have a specific
 * structure
 *
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
public class SampleRecordCreator {

    private static final String START_DOCUMENT = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<rdf:RDF xmlns:dcterms=\"http://purl.org/dc/terms/\"\n"
            + "xmlns:edm=\"http://www.europeana.eu/schemas/edm/\"\n"
            + "xmlns:enrichment=\"http://www.europeana.eu/schemas/edm/enrichment/\"\n"
            + "xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n"
            + "xmlns:wgs84=\"http://www.w3.org/2003/01/geo/wgs84_pos#\"\n"
            + "xmlns:skos=\"http://www.w3.org/2004/02/skos/core#\"\n"
            + "xmlns:oai=\"http://www.openarchives.org/OAI/2.0/\"\n"
            + "xmlns:ore=\"http://www.openarchives.org/ore/terms/\"\n"
            + "xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
            + "xmlns:dc=\"http://purl.org/dc/elements/1.1/\"\n"
            + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
            + "xsi:schemaLocation=\"http://www.w3.org/1999/02/22-rdf-syntax-ns# EDM-INTERNAL.xsd\">\n";

    /**
     * @param args
     */
    public static void main(String[] args) {
        File file = new File("src/test/resources/09102_Ag_EU_MIMO_EDM.xml");
        File saveFolder = new File("src/test/resources/records");
        saveFolder.mkdir();
        XMLInputFactory inFactory = new WstxInputFactory();
        Source source;
        try {
            source = new StreamSource(new FileInputStream(file), "UTF-8");
            XMLStreamReader xml = inFactory.createXMLStreamReader(source);
            int records = 0;
            File recordFile = null;
            StringBuffer xmlString = new StringBuffer();
            while (xml.hasNext()) {
                switch (xml.getEventType()) {

                    case XMLStreamConstants.START_DOCUMENT:
                        System.out.println("Started parsing the document...");

                        break;
                    case XMLStreamConstants.START_ELEMENT:

                        if (StringUtils.equals("edm:ProvidedCHO", xml.getName().getPrefix() + ":" + xml.getName().getLocalPart())) {

                            recordFile = new File(saveFolder.getAbsolutePath()
                                    + "/09102_Ag_EU_MIMO_EDM_record" + records
                                    + ".xml");
                            xmlString = new StringBuffer();
                            xmlString.append(START_DOCUMENT);
                            records++;
                        }
                        if (!StringUtils.equals("rdf:RDF", xml.getName().getPrefix() + ":" + xml.getName().getLocalPart())) {
                            xmlString.append("<").append(xml.getName().getPrefix()).append(":").append(xml.getName().getLocalPart()).append("");
                            if (xml.getAttributeCount() > 0) {
                                xmlString.append(" ").append(xml.getAttributePrefix(0)).append(":").append(xml.getAttributeLocalName(0)).append("=\"").append(xml.getAttributeValue(0)).append("\">");
                            } else {
                                xmlString.append(">");
                            }
                        }

                        break;

                    case XMLStreamConstants.CHARACTERS:
                        String normalized = StringUtils.replace(xml.getText(), "&",
                                "&amp;");
                        normalized = StringUtils.replace(normalized, "\"",
                                StringUtils.replace(xml.getText(), "&", "&quot;"));
                        xmlString.append(normalized);
                        break;
                    case XMLStreamConstants.END_ELEMENT:

                        xmlString.append("</").append(xml.getName().getPrefix()).append(":").append(xml.getName().getLocalPart()).append(">");
                        if (StringUtils.equals("ore:Aggregation", xml.getName().getPrefix() + ":" + xml.getName().getLocalPart())) {
                            xmlString.append("</rdf:RDF>");
                            saveFile(recordFile, xmlString);
                        }

                        break;
                    case XMLStreamConstants.END_DOCUMENT:

                        break;
                }
                xml.next();
            }
            System.out.println("Finished parsing documents...");
            System.out.println("Found " + records + " records");
        } catch (XMLStreamException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * Save an XML file
     * @param recordFile the name of the file
     * @param xmlString the XML string
     * @throws IOException 
     */
    private static void saveFile(File recordFile, StringBuffer xmlString)
            throws IOException {
        FileOutputStream fos = new FileOutputStream(recordFile);
        fos.write(xmlString.toString().getBytes());
        fos.flush();
        fos.close();
    }
}
