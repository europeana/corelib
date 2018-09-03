package eu.europeana.corelib.edm.utils;

import eu.europeana.corelib.definitions.edm.model.metainfo.ImageOrientation;
import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.edm.model.metainfo.ImageMetaInfoImpl;
import eu.europeana.corelib.edm.model.metainfo.WebResourceMetaInfoImpl;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import eu.europeana.corelib.solr.entity.*;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class SchemaOrgUtilsTest {

    @Test
    public void toSchemaOrgTest() {
        FullBeanImpl bean = mockFullBean();
        String output = SchemaOrgUtils.toSchemaOrg(bean);
        Assert.assertNotNull(output);
    }

    private FullBeanImpl mockFullBean() {
        FullBeanImpl bean = Mockito.mock(FullBeanImpl.class);
        Mockito.when(bean.getAbout()).thenReturn("/2021618/internetserver_Details_kunst_25027");
        Mockito.when(bean.getTitle()).thenReturn(new String[] {"Mona Lisa"});
        Mockito.when(bean.getLanguage()).thenReturn(new String[] {"nl"});
        Mockito.when(bean.getTimestampCreated()).thenReturn(new Date(1395759054479L));
        Mockito.when(bean.getTimestampUpdated()).thenReturn(new Date(1395759054479L));
        Mockito.when(bean.getEuropeanaCollectionName()).thenReturn(new String[] { "2021618_Ag_NL_DigitaleCollectie_TeylersKunst"});

        mockProvidedCHO(bean);
        mockAgents(bean);
        mockAggregations(bean);
        mockEuropeanaAggregation(bean);
        mockProxies(bean);

        return bean;
    }

    private void mockProxies(FullBeanImpl bean) {
        List<ProxyImpl> proxies = new ArrayList<>();
        ProxyImpl proxy = new ProxyImpl();
        proxies.add(proxy);

        proxy.setEdmType(DocType.IMAGE);
        proxy.setProxyIn(new String[] {"/aggregation/provider/2021618/internetserver_Details_kunst_25027"});
        proxy.setProxyFor("/item/2021618/internetserver_Details_kunst_25027");

        proxy.setDcCreator(new HashMap<>());
        proxy.getDcCreator().put("def", new ArrayList<>());
        proxy.getDcCreator().get("def").add("Calamatta, Luigi (1801-1869)");
        proxy.getDcCreator().get("def").add("Leonardo da Vinci (1452 - 1519)");
        proxy.getDcCreator().get("def").add(" graveur");
        proxy.getDcCreator().get("def").add(" inventor");

        proxy.setDcDate(new HashMap<>());
        proxy.getDcDate().put("def", new ArrayList<>());
        proxy.getDcDate().get("def").add("1821 - 1869");

        proxy.setDcFormat(new HashMap<>());
        proxy.getDcFormat().put("def", new ArrayList<>());
        proxy.getDcFormat().get("def").add("papier: hoogte: 675 mm");
        proxy.getDcFormat().get("def").add("papier: breedte: 522 mm");
        proxy.getDcFormat().get("def").add("plaat: hoogte: 565 mm");
        proxy.getDcFormat().get("def").add("plaat: breedte: 435 mm");
        proxy.getDcFormat().get("def").add("beeld: hoogte: 376 mm");
        proxy.getDcFormat().get("def").add("beeld: breedte: 275 mm");

        proxy.setDcIdentifier(new HashMap<>());
        proxy.getDcIdentifier().put("def", new ArrayList<>());
        proxy.getDcIdentifier().get("def").add("TvB G 3674");

        proxy.setDcTitle(new HashMap<>());
        proxy.getDcTitle().put("def", new ArrayList<>());
        proxy.getDcTitle().get("def").add("Mona Lisa");

        proxy.setDcType(new HashMap<>());
        proxy.getDcType().put("def", new ArrayList<>());
        proxy.getDcType().get("def").add("grafiek");

        proxy.setAbout("/proxy/provider/2021618/internetserver_Details_kunst_25027");

        proxy = new ProxyImpl();
        proxies.add(proxy);

        proxy.setEdmType(DocType.IMAGE);
        proxy.setProxyIn(new String[] {"/aggregation/europeana/2021618/internetserver_Details_kunst_25027"});
        proxy.setProxyFor("/item/2021618/internetserver_Details_kunst_25027");

        proxy.setDcCreator(new HashMap<>());
        proxy.getDcCreator().put("def", new ArrayList<>());
        proxy.getDcCreator().get("def").add("http://data.europeana.eu/agent/base/103195");
        proxy.getDcCreator().get("def").add("http://data.europeana.eu/agent/base/6");
        proxy.getDcCreator().get("def").add("http://data.europeana.eu/agent/base/94182");
        proxy.getDcCreator().get("def").add("http://data.europeana.eu/agent/base/146741");

        proxy.setAbout("/proxy/europeana/2021618/internetserver_Details_kunst_25027");

        Mockito.when(bean.getProxies()).thenReturn(proxies);
    }

    private void mockEuropeanaAggregation(FullBeanImpl bean) {
        EuropeanaAggregationImpl europeanaAggregation = new EuropeanaAggregationImpl();
        europeanaAggregation.setAbout("/aggregation/europeana/2021618/internetserver_Details_kunst_25027");
        europeanaAggregation.setAggregatedCHO("/item/2021618/internetserver_Details_kunst_25027");

        europeanaAggregation.setDcCreator(new HashMap<>());
        europeanaAggregation.getDcCreator().put("def", new ArrayList<>());
        europeanaAggregation.getDcCreator().get("def").add("Europeana");

        europeanaAggregation.setEdmLandingPage("http://www.europeana.eu/portal/record/2021618/internetserver_Details_kunst_25027.html");

        europeanaAggregation.setEdmCountry(new HashMap<>());
        europeanaAggregation.getEdmCountry().put("def", new ArrayList<>());
        europeanaAggregation.getEdmCountry().get("def").add("netherlands");

        europeanaAggregation.setEdmLanguage(new HashMap<>());
        europeanaAggregation.getEdmLanguage().put("def", new ArrayList<>());
        europeanaAggregation.getEdmLanguage().get("def").add("nl");

        europeanaAggregation.setEdmRights(new HashMap<>());
        europeanaAggregation.getEdmRights().put("def", new ArrayList<>());
        europeanaAggregation.getEdmLanguage().get("def").add("http://creativecommons.org/licenses/by-nc/3.0/nl/");

        europeanaAggregation.setEdmPreview("http://teylers.adlibhosting.com/wwwopacx/wwwopac.ashx?command=getcontent&server=images&value=TvB%20G%203674.jpg");
        Mockito.when(bean.getEuropeanaAggregation()).thenReturn(europeanaAggregation);
    }

    private void mockAggregations(FullBeanImpl bean) {
        List<AggregationImpl> aggregations = new ArrayList<>();
        AggregationImpl aggregation = new AggregationImpl();

        aggregation.setEdmDataProvider(new HashMap<>());
        aggregation.getEdmDataProvider().put("def", new ArrayList<>());
        aggregation.getEdmDataProvider().get("def").add("Teylers Museum");

        aggregation.setEdmIsShownBy("http://teylers.adlibhosting.com/wwwopacx/wwwopac.ashx?command=getcontent&server=images&value=TvB%20G%203674.jpg");
        aggregation.setEdmIsShownAt("http://teylers.adlibhosting.com/internetserver/Details/kunst/25027");
        aggregation.setEdmObject("http://teylers.adlibhosting.com/wwwopacx/wwwopac.ashx?command=getcontent&server=images&value=TvB%20G%203674.jpg");

        aggregation.setEdmProvider(new HashMap<>());
        aggregation.getEdmProvider().put("def", new ArrayList<>());
        aggregation.getEdmProvider().get("def").add("Digitale Collectie");

        aggregation.setEdmRights(new HashMap<>());
        aggregation.getEdmRights().put("def", new ArrayList<>());
        aggregation.getEdmRights().get("def").add("http://creativecommons.org/licenses/by-nc/3.0/nl/");

        aggregation.setAggregatedCHO("/item/2021618/internetserver_Details_kunst_25027");
        aggregation.setAbout("/aggregation/provider/2021618/internetserver_Details_kunst_25027");

        List<WebResourceImpl> webResources = new ArrayList<>();
        WebResourceImpl webResource = new WebResourceImpl();
        webResources.add(webResource);

        webResource.setAbout("http://teylers.adlibhosting.com/internetserver/Details/kunst/25027");

        webResource = new WebResourceImpl();
        webResources.add(webResource);

        webResource.setAbout("http://teylers.adlibhosting.com/wwwopacx/wwwopac.ashx?command=getcontent&server=images&value=TvB%20G%203674.jpg");
        webResource.setWebResourceMetaInfo(new WebResourceMetaInfoImpl());
        ((WebResourceMetaInfoImpl)webResource.getWebResourceMetaInfo()).setImageMetaInfo(new ImageMetaInfoImpl());
        ((ImageMetaInfoImpl)webResource.getWebResourceMetaInfo().getImageMetaInfo()).setWidth(598);
        ((ImageMetaInfoImpl)webResource.getWebResourceMetaInfo().getImageMetaInfo()).setHeight(768);
        ((ImageMetaInfoImpl)webResource.getWebResourceMetaInfo().getImageMetaInfo()).setMimeType("image/jpeg");
        ((ImageMetaInfoImpl)webResource.getWebResourceMetaInfo().getImageMetaInfo()).setFileFormat("JPEG");
        ((ImageMetaInfoImpl)webResource.getWebResourceMetaInfo().getImageMetaInfo()).setColorSpace("sRGB");
        ((ImageMetaInfoImpl)webResource.getWebResourceMetaInfo().getImageMetaInfo()).setFileSize(61347L);
        ((ImageMetaInfoImpl)webResource.getWebResourceMetaInfo().getImageMetaInfo()).setColorPalette(new String[] {"#DCDCDC", "#2F4F4F", "#FAEBD7", "#FAF0E6", "#F5F5DC", "#696969"} );
        ((ImageMetaInfoImpl)webResource.getWebResourceMetaInfo().getImageMetaInfo()).setOrientation(ImageOrientation.PORTRAIT);

        aggregation.setWebResources(webResources);
        Mockito.when(bean.getAggregations()).thenReturn(aggregations);
    }

    private void mockAgents(FullBeanImpl bean) {
        List<AgentImpl> agents = new ArrayList<>();
        AgentImpl agent = new AgentImpl();
        agents.add(agent);

        agent.setEnd(new HashMap<>());
        agent.getEnd().put("def", new ArrayList<>());
        agent.getEnd().get("def").add("1519-05-02");

        agent.setOwlSameAs(new String[] {"http://purl.org/collections/nl/am/p-10456",
                "http://rdf.freebase.com/ns/m.011bn9nx",
                "http://sv.dbpedia.org/resource/Leonardo_da_Vincis_uppfinningar",
                "http://pl.dbpedia.org/resource/Wynalazki_i_konstrukcje_Leonarda_da_Vinci"});

        agent.setRdaGr2DateOfBirth(new HashMap<>());
        agent.getRdaGr2DateOfBirth().put("def", new ArrayList<>());
        agent.getRdaGr2DateOfBirth().get("def").add("1452-04-15");

        agent.setRdaGr2DateOfDeath(new HashMap<>());
        agent.getRdaGr2DateOfDeath().put("def", new ArrayList<>());
        agent.getRdaGr2DateOfDeath().get("def").add("1519-05-02");

        agent.setRdaGr2PlaceOfDeath(new HashMap<>());
        agent.getRdaGr2PlaceOfDeath().put("def", new ArrayList<>());
        agent.getRdaGr2PlaceOfDeath().get("def").add("http://dbpedia.org/resource/France");
        agent.getRdaGr2PlaceOfDeath().get("def").add("http://dbpedia.org/resource/Indre-et-Loire");
        agent.getRdaGr2PlaceOfDeath().get("def").add("http://dbpedia.org/resource/Amboise");

        agent.setRdaGr2BiographicalInformation(new HashMap<>());
        agent.getRdaGr2BiographicalInformation().put("en", new ArrayList<>());
        agent.getRdaGr2BiographicalInformation().get("en").add("Leonardo da Vinci (1452–1519) was an Italian polymath, regarded as the epitome of the \"Renaissance Man\", displaying skills in numerous diverse areas of study. Whilst most famous for his paintings such as the Mona Lisa and the Last Supper, Leonardo is also renowned in the fields of civil engineering, chemistry, geology, geometry, hydrodynamics, mathematics, mechanical engineering, optics, physics, pyrotechnics, and zoology.While the full extent of his scientific studies has only become recognized in the last 150 years, he was, during his lifetime, employed for his engineering and skill of invention. Many of his designs, such as the movable dikes to protect Venice from invasion, proved too costly or impractical. Some of his smaller inventions entered the world of manufacturing unheralded. As an engineer, Leonardo conceived ideas vastly ahead of his own time, conceptually inventing a helicopter, a tank, the use of concentrated solar power, a calculator, a rudimentary theory of plate tectonics and the double hull. In practice, he greatly advanced the state of knowledge in the fields of anatomy, astronomy, civil engineering, optics, and the study of water (hydrodynamics).Leonardo's most famous drawing, the Vitruvian Man, is a study of the proportions of the human body, linking art and science in a single work that has come to represent Renaissance Humanism.");
        agent.getRdaGr2BiographicalInformation().put("pl", new ArrayList<>());
        agent.getRdaGr2BiographicalInformation().get("pl").add("Leonardo da Vinci na prawie sześciu tysiącach stron notatek wykonał znacznie więcej projektów technicznych niż prac artystycznych, co wskazuje na to, że inżynieria była niezmiernie ważną dziedziną jego zainteresowań.Giorgio Vasari w Żywotach najsławniejszych malarzy, rzeźbiarzy i architektów pisał o Leonardzie:");

        agent.setPrefLabel(new HashMap<>());
        agent.getPrefLabel().put("en", new ArrayList<>());
        agent.getPrefLabel().get("en").add("Science and inventions of Leonardo da Vinci");
        agent.getPrefLabel().put("pl", new ArrayList<>());
        agent.getPrefLabel().get("pl").add("Wynalazki i konstrukcje Leonarda da Vinci");

        agent.setAltLabel(new HashMap<>());
        agent.getAltLabel().put("en", new ArrayList<>());
        agent.getAltLabel().get("en").add("Leonardo da Vinci");

        agent.setAbout("http://data.europeana.eu/agent/base/6");
        Mockito.when(bean.getAgents()).thenReturn(agents);
    }

    private void mockProvidedCHO(FullBeanImpl bean) {
        List<ProvidedCHOImpl> providedCHOS = new ArrayList<>();
        ProvidedCHOImpl providedCHO = new ProvidedCHOImpl();
        providedCHO.setAbout("/item/2021618/internetserver_Details_kunst_25027");
        providedCHOS.add(providedCHO);
        Mockito.when(bean.getProvidedCHOs()).thenReturn(providedCHOS);
    }

}