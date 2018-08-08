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
        mockPlaces(bean);
        mockConcepts(bean);
        mockTimespans(bean);

        return bean;
    }

    private void mockTimespans(FullBeanImpl bean) {
        List<TimespanImpl> timespans = new ArrayList<>();
        TimespanImpl timespan = new TimespanImpl();
        timespans.add(timespan);

        timespan.setBegin(new HashMap<>());
        timespan.getBegin().put("def", new ArrayList<>());
        timespan.getBegin().get("def").add("Tue Jan 01 00:19:32 CET 1901");

        timespan.setEnd(new HashMap<>());
        timespan.getEnd().put("def", new ArrayList<>());
        timespan.getEnd().get("def").add("Sun Dec 31 01:00:00 CET 2000");

        timespan.setAbout("http://semium.org/time/19xx");

        timespan = new TimespanImpl();
        timespans.add(timespan);

        timespan.setBegin(new HashMap<>());
        timespan.getBegin().put("def", new ArrayList<>());
        timespan.getBegin().get("def").add("1901-01-01");

        timespan.setEnd(new HashMap<>());
        timespan.getEnd().put("def", new ArrayList<>());
        timespan.getEnd().get("def").add("1902-01-01");

        timespan.setAbout("http://semium.org/time/1901");

        Mockito.when(bean.getTimespans()).thenReturn(timespans);
    }

    private void mockConcepts(FullBeanImpl bean) {
        List<ConceptImpl> concepts = new ArrayList<>();
        ConceptImpl concept = new ConceptImpl();
        concepts.add(concept);

        concept.setRelated(new String[] {"http://dbpedia.org/resource/Category:Agriculture"});
        concept.setExactMatch(new String[] { "http://bg.dbpedia.org/resource/Селско_стопанство",
                "http://ia.dbpedia.org/resource/Agricultura",
                "http://sl.dbpedia.org/resource/Kmetijstvo",
                "http://pnb.dbpedia.org/resource/وائی_بیجی",
                "http://el.dbpedia.org/resource/Γεωργία_(δραστηριότητα)"});

        concept.setPrefLabel(new HashMap<>());
        concept.getPrefLabel().put("no", new ArrayList<>());
        concept.getPrefLabel().get("no").add("Landbruk");
        concept.getPrefLabel().put("de", new ArrayList<>());
        concept.getPrefLabel().get("de").add("Landwirtschaft");

        concept.setNote(new HashMap<>());
        concept.getNote().put("no", new ArrayList<>());
        concept.getNote().get("no").add("Landbruk er en fellesbetegnelse for jordbruk og skogbruk som begge er primærnæringer, og omfatter en rekke næringsgrener der foredling av jord til kulturplanter eller beite er grunnleggende for produksjonen. Ordet har samme betydning som agrikultur, fra latin ager («åker») og cultura («dyrking»).I Norge blir 2,8 % av landarealet brukt til jordbruk (2005).");
        concept.getNote().put("de", new ArrayList<>());
        concept.getNote().get("de").add("Als Landwirtschaft wird der Wirtschaftsbereich der Urproduktion bezeichnet. Das Ziel der Urproduktion ist die zielgerichtete Herstellung pflanzlicher oder tierischer Erzeugnisse auf einer zu diesem Zweck bewirtschafteten Fläche. In der Wissenschaft sowie der fachlichen Praxis ist synonym der Begriff Agrarwirtschaft gebräuchlich.Die Landwirtschaft stellt einen der ältesten Wirtschaftsbereiche der Menschheit dar. Heute beläuft sich die landwirtschaftlich genutzte Fläche auf 48.827.330 km2, dies sind 9,6 % der Erdoberfläche. Somit wird etwa ein Drittel der Landfläche der Erde landwirtschaftlich genutzt.Der Wirtschaftsbereich Agrarwirtschaft wird zumeist in die beiden Sektoren Pflanzenproduktion Tierproduktioneingeteilt und dann weiter untergliedertDer Anbau von Nutzpflanzen dient zuallererst der Nahrungsmittelproduktion direkt wie indirekt. In letzterem Fall erfolgt die Herstellung von Rohstoffen zur weiteren Verarbeitung in Teilen des nachgelagerten Wirtschaftsbereichs des sogenannten Agribusiness (z. B. Weiterverarbeitung von Getreide zu Mehl für die Brotherstellung). Darüber hinaus werden landwirtschaftliche Rohstoffe (u. a. Faserpflanzen wie Baumwolle oder Leinen) auch in der Bekleidungsindustrie weiter veredelt.Die Haltung von Nutztieren dient in erster Linie der Nahrungsmittelproduktion (z. B. Milch, Eier), in zweiter Linie der Herstellung von Rohstoffen für die Herstellung von Bekleidung. Vor der Produktion von Kunstfasern schufen die Menschen noch ihre gesamte Bekleidung u. a. aus den tierischen Produkten Leder, Pelz und Wolle.Die Verwertung der durch die Agrarwirtschaft, zum Teil erst seit kürzerer Zeit, angebauten Biomasse aus nachwachsenden Rohstoffen (u. a. Holz, Mais) in Form von Vergasung, Karbonisierung und Raffinierung stellt eine erst seit kurzer Zeit mitunter stark zunehmende Form der Veredelung dar.Die Landwirtschaft ist Teilwirtschaftszweig eines größeren Gesamtsystems mit vor- und nachgelagerten Sektoren.Eine Person, die Landwirtschaft betreibt, bezeichnet man als Landwirt. Neben berufspraktischen Ausbildungen bestehen an zahlreichen Universitäten und Fachhochschulen eigene landwirtschaftliche Fachbereiche. Das dort gelehrte und erforschte Fach Agrarwissenschaft bereitet sowohl auf die Führung von landwirtschaftlichen Betrieben als auch auf Tätigkeiten in verwandten Wirtschaftsbereichen vor und ist ein ingenieurwissenschaftliches Fach.");

        concept.setAbout("http://data.europeana.eu/concept/base/134");

        Mockito.when(bean.getConcepts()).thenReturn(concepts);
    }

    private void mockPlaces(FullBeanImpl bean) {
        List<PlaceImpl> places = new ArrayList<>();
        PlaceImpl place = new PlaceImpl();
        places.add(place);

        place.setIsPartOf(new HashMap<>());
        place.getIsPartOf().put("def", new ArrayList<>());
        place.getIsPartOf().get("def").add("http://www.somewhere.eu/place/3");

        place.setLatitude(46.0F);
        place.setAltitude(70.0F);
        place.setLongitude(2.0F);

        place.setDcTermsHasPart(new HashMap<>());
        place.getDcTermsHasPart().put("def", new ArrayList<>());
        place.getDcTermsHasPart().get("def").add("http://www.somewhere.eu/place/2");

        place.setOwlSameAs(new String[] { "http://www.somewhere.eu/place/5" });

        place.setPrefLabel(new HashMap<>());
        place.getPrefLabel().put("en", new ArrayList<>());
        place.getPrefLabel().get("en").add("Paris");

        place.setAltLabel(new HashMap<>());
        place.getAltLabel().put("it", new ArrayList<>());
        place.getAltLabel().get("it").add("Paris");

        place.setNote(new HashMap<>());
        place.getNote().put("en", new ArrayList<>());
        place.getNote().get("en").add("Probably in Popicourt");

        place.setAbout("#place_Paris");

        Mockito.when(bean.getPlaces()).thenReturn(places);
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
        proxy.getDcType().get("def").add("http://data.europeana.eu/concept/base/48");

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

        proxy.setDctermsTemporal(new HashMap<>());
        proxy.getDctermsTemporal().put("def", new ArrayList<>());
        proxy.getDctermsTemporal().get("def").add("http://semium.org/time/19xx");
        proxy.getDctermsTemporal().get("def").add("http://semium.org/time/1901");

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
        aggregations.add(aggregation);
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