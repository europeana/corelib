package eu.europeana.corelib.record.schemaorg.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import eu.europeana.corelib.definitions.edm.entity.Organization;
import eu.europeana.corelib.solr.entity.AddressImpl;
import eu.europeana.corelib.solr.entity.OrganizationImpl;

public class MockEdmOrganization {

    public static Organization getEdmOrganization() {
	OrganizationImpl organization = new OrganizationImpl();
	
	organization.setAbout("http://data.test.eu/organization/1482250000002112001");
	organization.setDcIdentifier(new HashMap<>());
	organization.getDcIdentifier().put("def", Arrays.asList("1482250000002112001"));
	
	organization.setFoafDepiction("http://commons.wikimedia.org/wiki/Special:FilePath/BnF.jpg");
	organization.setFoafHomepage("http://www.bnf.fr");
	
	organization.setPrefLabel(new HashMap<>());
	organization.getPrefLabel().put("de", new ArrayList<>());
	organization.getPrefLabel().get("de").add("Bibliothèque nationale de France");
	organization.getPrefLabel().put("en", new ArrayList<>());
	organization.getPrefLabel().get("en").add("National Library of France");
	
	organization.setAltLabel(new HashMap<>());
	organization.getAltLabel().put("de", new ArrayList<>());
	organization.getAltLabel().get("de").add("Bibliothèque de l'Arsenal");
	organization.getAltLabel().put("en", new ArrayList<>());
	organization.getAltLabel().get("en").add("National library of France");
	
	organization.setFoafLogo("http://commons.wikimedia.org/wiki/Special:FilePath/Logo_BnF.svg");
	organization.setEdmCountry(new HashMap<>());
	organization.getEdmCountry().put("def", "FR");
	
	organization.setDcDescription(new HashMap<>());
	organization.getDcDescription().put("en", "National Library of France");
	
	AddressImpl address = new AddressImpl();
	organization.setAddress(address);
	address.setAbout("http://data.europeana.eu/organization/1482250000002112001#address");
	address.setVcardStreetAddress("Quai François Mauriac");
	address.setVcardPostalCode("75706");
	address.setVcardPostOfficeBox("Paris Cedex 13");
	address.setVcardLocality("Paris");
	address.setVcardCountryName("France");

	organization.setOwlSameAs(new String[] { "http://isni.org/isni/0000000123531945", "https://g.co/kg/m/01cb6r",
		"https://www.freebase.com/m/01cb6r", "http://viaf.org/viaf/137156173",
		"http://www.wikidata.org/entity/Q193563", "http://id.loc.gov/authorities/names/no95028191",
		"http://babelnet.org/rdf/s01268347n", "http://d-nb.info/gnd/5156217-0",
		"http://vocab.getty.edu/ulan/500309981", "http://data.bnf.fr/ark:/12148/cb12381002j",
		"http://sws.geonames.org/6452876/", "http://datos.bne.es/resource/XX179415" });
	
	return organization;
    }
    
}
