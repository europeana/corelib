package eu.europeana.corelib.edm.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import eu.europeana.corelib.definitions.edm.model.metainfo.ImageOrientation;
import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.edm.model.metainfo.ImageMetaInfoImpl;
import eu.europeana.corelib.edm.model.metainfo.VideoMetaInfoImpl;
import eu.europeana.corelib.edm.model.metainfo.WebResourceMetaInfoImpl;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import eu.europeana.corelib.solr.entity.AgentImpl;
import eu.europeana.corelib.solr.entity.AggregationImpl;
import eu.europeana.corelib.solr.entity.ConceptImpl;
import eu.europeana.corelib.solr.entity.EuropeanaAggregationImpl;
import eu.europeana.corelib.solr.entity.PlaceImpl;
import eu.europeana.corelib.solr.entity.ProvidedCHOImpl;
import eu.europeana.corelib.solr.entity.ProxyImpl;
import eu.europeana.corelib.solr.entity.TimespanImpl;
import eu.europeana.corelib.solr.entity.WebResourceImpl;
import eu.europeana.corelib.utils.DateUtils;
import org.mockito.MockitoAnnotations;

/**
 * Given our current FullBean implementation it's not easy to deserialize a FullBean from json string, so for now we
 * create a test fullbean by hand.
 *
 * @author Patrick Ehlert
 * <p>
 * Created on 10-09-2018
 */
public final class MockFullBean {

    private MockFullBean() {
        // empty constructor to prevent initialization
    }

    public static FullBeanImpl mock() {
        FullBeanImpl bean = new FullBeanImpl();
        bean.setAbout(MockBeanConstants.ABOUT);
        bean.setTitle(new String[]{MockBeanConstants.DC_TITLE});
        bean.setLanguage(new String[]{MockBeanConstants.LANGUAUGE_NL});
        bean.setTimestampCreated(new Date(MockBeanConstants.TIMESTAMP_CREATED));
        bean.setTimestampUpdated(new Date(MockBeanConstants.TIMESTAMP_UPDATED));
        bean.setEuropeanaCollectionName(new String[]{MockBeanConstants.EUROPEANA_COLLECTION});

        setProvidedCHO(bean);
        setAgents(bean);
        setAggregations(bean);
        setEuropeanaAggregation(bean);
        setProxies(bean);
        setPlaces(bean);
        setConcepts(bean);
        setTimespans(bean);

        return bean;
    }

    private static void setProxies(FullBeanImpl bean) {
        List<ProxyImpl> proxies = new ArrayList<>();
        ProxyImpl proxy = new ProxyImpl();
        proxies.add(proxy);

        proxy.setEdmType(DocType.IMAGE);
        proxy.setProxyIn(new String[]{MockBeanConstants.AGGREGATION_ABOUT});
        proxy.setProxyFor(MockBeanConstants.ABOUT);

        proxy.setDcCreator(new HashMap<>());
        proxy.getDcCreator().put(MockBeanConstants.DEF, new ArrayList<>());
        proxy.getDcCreator().get(MockBeanConstants.DEF).add(MockBeanConstants.DC_CREATOR_1);
        proxy.getDcCreator().get(MockBeanConstants.DEF).add(MockBeanConstants.DC_CREATOR_2);
        proxy.getDcCreator().get(MockBeanConstants.DEF).add(MockBeanConstants.DC_CREATOR_3);
        proxy.getDcCreator().get(MockBeanConstants.DEF).add(MockBeanConstants.DC_CREATOR_4);

        proxy.setDcDate(new HashMap<>());
        proxy.getDcDate().put(MockBeanConstants.DEF, new ArrayList<>());
        proxy.getDcDate().get(MockBeanConstants.DEF).add(MockBeanConstants.DC_DATE);

        proxy.setDcFormat(new HashMap<>());
        proxy.getDcFormat().put(MockBeanConstants.DEF, new ArrayList<>());
        proxy.getDcFormat().get(MockBeanConstants.DEF).add("papier: hoogte: 675 mm");
        proxy.getDcFormat().get(MockBeanConstants.DEF).add("papier: breedte: 522 mm");
        proxy.getDcFormat().get(MockBeanConstants.DEF).add("plaat: hoogte: 565 mm");
        proxy.getDcFormat().get(MockBeanConstants.DEF).add("plaat: breedte: 435 mm");
        proxy.getDcFormat().get(MockBeanConstants.DEF).add("beeld: hoogte: 376 mm");
        proxy.getDcFormat().get(MockBeanConstants.DEF).add("beeld: breedte: 275 mm");

        proxy.setDcIdentifier(new HashMap<>());
        proxy.getDcIdentifier().put(MockBeanConstants.DEF, new ArrayList<>());
        proxy.getDcIdentifier().get(MockBeanConstants.DEF).add(MockBeanConstants.DC_IDENTIFIER);

        proxy.setDcTitle(new HashMap<>());
        proxy.getDcTitle().put(MockBeanConstants.DEF, new ArrayList<>());
        proxy.getDcTitle().get(MockBeanConstants.DEF).add(MockBeanConstants.DC_TITLE);

        proxy.setDcType(new HashMap<>());
        proxy.getDcType().put(MockBeanConstants.DEF, new ArrayList<>());
        proxy.getDcType().get(MockBeanConstants.DEF).add(MockBeanConstants.DC_TYPE_1);
        proxy.getDcType().get(MockBeanConstants.DEF).add(MockBeanConstants.DC_TYPE_2);

        proxy.setDctermsAlternative(new HashMap<>());
        proxy.getDctermsAlternative().put(MockBeanConstants.DEF, new ArrayList<>());
        proxy.getDctermsAlternative().get(MockBeanConstants.DEF).add(MockBeanConstants.DC_ALTERNATIVE);

        proxy.setAbout(MockBeanConstants.PROXY_ABOUT_1);

        proxy = new ProxyImpl();
        proxies.add(proxy);

        proxy.setEdmType(DocType.IMAGE);
        proxy.setProxyIn(new String[]{MockBeanConstants.EUROPEANA_AGG_ABOUT});
        proxy.setProxyFor(MockBeanConstants.ABOUT);

        proxy.setDcCreator(new HashMap<>());
        proxy.getDcCreator().put(MockBeanConstants.DEF, new ArrayList<>());
        proxy.getDcCreator().get(MockBeanConstants.DEF).add(MockBeanConstants.DC_CREATOR_5);
        proxy.getDcCreator().get(MockBeanConstants.DEF).add(MockBeanConstants.DC_CREATOR_6);
        proxy.getDcCreator().get(MockBeanConstants.DEF).add(MockBeanConstants.DC_CREATOR_7);
        proxy.getDcCreator().get(MockBeanConstants.DEF).add(MockBeanConstants.DC_CREATOR_8);

        proxy.setDctermsTemporal(new HashMap<>());
        proxy.getDctermsTemporal().put(MockBeanConstants.DEF, new ArrayList<>());
        proxy.getDctermsTemporal().get(MockBeanConstants.DEF).add(MockBeanConstants.DC_TERMS_TEMPORAL_1);
        proxy.getDctermsTemporal().get(MockBeanConstants.DEF).add(MockBeanConstants.DC_TERMS_TEMPORAL_2);
        proxy.getDctermsTemporal().get(MockBeanConstants.DEF).add(MockBeanConstants.DC_TERMS_TEMPORAL_3); //invalid
        proxy.getDctermsTemporal().get(MockBeanConstants.DEF).add(MockBeanConstants.DC_TERMS_TEMPORAL_4); //valid
        proxy.getDctermsTemporal().get(MockBeanConstants.DEF).add(MockBeanConstants.DC_TERMS_TEMPORAL_4); //duplicate

        proxy.setDctermsCreated(new HashMap<>());
        proxy.getDctermsCreated().put(MockBeanConstants.DEF, new ArrayList<>());
        proxy.getDctermsCreated().get(MockBeanConstants.DEF).add(MockBeanConstants.DC_TERMS_CREATED);

        proxy.setDcCoverage(new HashMap<>());
        proxy.getDcCoverage().put(MockBeanConstants.DEF, new ArrayList<>());
        proxy.getDcCoverage().get(MockBeanConstants.DEF).add(MockBeanConstants.DC_COVERAGE_TEMPORAL);
        proxy.getDcCoverage().get(MockBeanConstants.DEF).add(MockBeanConstants.DC_COVERAGE_ABOUT); //allow invalid is false for dcCoverage and this should not get added in temporal coverage, But then it will get added in about

        //to test https mapping
        proxy.setDcDescription(new HashMap<>());
        proxy.getDcDescription().put(MockBeanConstants.DEF, new ArrayList<>());
        proxy.getDcDescription().get(MockBeanConstants.DEF).add(MockBeanConstants.DC_DESCRIPTION);

        proxy.setAbout(MockBeanConstants.PROXY_ABOUT_2);

        bean.setProxies(proxies);
    }

    private static void setEuropeanaAggregation(FullBeanImpl bean) {
        EuropeanaAggregationImpl europeanaAggregation = new EuropeanaAggregationImpl();
        europeanaAggregation.setAbout(MockBeanConstants.EUROPEANA_AGG_ABOUT);
        europeanaAggregation.setAggregatedCHO(MockBeanConstants.ABOUT);

        europeanaAggregation.setDcCreator(new HashMap<>());
        europeanaAggregation.getDcCreator().put(MockBeanConstants.DEF, new ArrayList<>());
        europeanaAggregation.getDcCreator().get(MockBeanConstants.DEF).add(MockBeanConstants.DC_CREATOR_9);

        europeanaAggregation.setEdmCountry(new HashMap<>());
        europeanaAggregation.getEdmCountry().put(MockBeanConstants.DEF, new ArrayList<>());
        europeanaAggregation.getEdmCountry().get(MockBeanConstants.DEF).add(MockBeanConstants.EDM_COUNTRY);

        europeanaAggregation.setEdmLanguage(new HashMap<>());
        europeanaAggregation.getEdmLanguage().put(MockBeanConstants.DEF, new ArrayList<>());
        europeanaAggregation.getEdmLanguage().get(MockBeanConstants.DEF).add(MockBeanConstants.LANGUAUGE_NL);

        europeanaAggregation.setEdmRights(new HashMap<>());
        europeanaAggregation.getEdmRights().put(MockBeanConstants.DEF, new ArrayList<>());
        europeanaAggregation.getEdmLanguage().get(MockBeanConstants.DEF).add(MockBeanConstants.EDM_RIGHTS);

        europeanaAggregation.setEdmPreview(MockBeanConstants.EDM_PREVIEW);

        bean.setEuropeanaAggregation(europeanaAggregation);
    }

    private static void setAggregations(FullBeanImpl bean) {
        List<AggregationImpl> aggregations = new ArrayList<>();
        AggregationImpl aggregation = new AggregationImpl();

        aggregation.setEdmDataProvider(new HashMap<>());
        aggregation.getEdmDataProvider().put(MockBeanConstants.DEF, new ArrayList<>());
        aggregation.getEdmDataProvider().get(MockBeanConstants.DEF).add(MockBeanConstants.EDM_PROVIDER_1);

        //should be present in associated media as Image object
        aggregation.setEdmIsShownBy(MockBeanConstants.EDM_IS_SHOWN_BY);

        //should be present in associatedMedia as Video object
        String[] hasViews = {MockBeanConstants.EDM_IS_SHOWN_AT};
        aggregation.setHasView(hasViews);

        aggregation.setEdmIsShownAt(MockBeanConstants.EDM_IS_SHOWN_AT);
        aggregation.setEdmObject(MockBeanConstants.EDM_IS_SHOWN_BY);

        aggregation.setEdmProvider(new HashMap<>());
        aggregation.getEdmProvider().put(MockBeanConstants.DEF, new ArrayList<>());
        aggregation.getEdmProvider().get(MockBeanConstants.DEF).add(MockBeanConstants.EDM_PROVIDER_2);

        aggregation.setEdmRights(new HashMap<>());
        aggregation.getEdmRights().put(MockBeanConstants.DEF, new ArrayList<>());
        aggregation.getEdmRights().get(MockBeanConstants.DEF).add(MockBeanConstants.EDM_RIGHTS);

        aggregation.setAggregatedCHO(MockBeanConstants.ABOUT);
        aggregation.setAbout(MockBeanConstants.AGGREGATION_ABOUT);

        List<WebResourceImpl> webResources = new ArrayList<>();
        WebResourceImpl webResource = new WebResourceImpl();
        webResources.add(webResource);

        webResource.setDctermsCreated(new HashMap<>());
        webResource.getDctermsCreated().put(MockBeanConstants.DEF, new ArrayList<>());
        webResource.getDctermsCreated().get(MockBeanConstants.DEF).add("1936-05-11");

        //should be present in videoObject
        webResource.setAbout(MockBeanConstants.EDM_IS_SHOWN_AT);
        webResource.setWebResourceMetaInfo(new WebResourceMetaInfoImpl());
        ((WebResourceMetaInfoImpl) webResource.getWebResourceMetaInfo()).setVideoMetaInfo(new VideoMetaInfoImpl());
        ((VideoMetaInfoImpl) webResource.getWebResourceMetaInfo().getVideoMetaInfo()).setMimeType(MockBeanConstants.MIME_TYPE_VIDEO);
        ((VideoMetaInfoImpl) webResource.getWebResourceMetaInfo().getVideoMetaInfo()).setBitRate(400);
        ((VideoMetaInfoImpl) webResource.getWebResourceMetaInfo().getVideoMetaInfo()).setDuration(2000L);
        ((VideoMetaInfoImpl) webResource.getWebResourceMetaInfo().getVideoMetaInfo()).setHeight(500);
        ((VideoMetaInfoImpl) webResource.getWebResourceMetaInfo().getVideoMetaInfo()).setWidth(650);

        webResource = new WebResourceImpl();
        webResources.add(webResource);

        //this weresource will not be added in the schema.org as there is no associatedMedia present for value "testing"
        webResource.setAbout("testing");
        webResource.setWebResourceMetaInfo(new WebResourceMetaInfoImpl());
        ((WebResourceMetaInfoImpl) webResource.getWebResourceMetaInfo()).setVideoMetaInfo(new VideoMetaInfoImpl());
        ((VideoMetaInfoImpl) webResource.getWebResourceMetaInfo().getVideoMetaInfo()).setMimeType(MockBeanConstants.MIME_TYPE_VIDEO);

        webResource = new WebResourceImpl();
        webResources.add(webResource);

        //should be present in ImageObject
        webResource.setAbout(MockBeanConstants.EDM_IS_SHOWN_BY);
        webResource.setWebResourceMetaInfo(new WebResourceMetaInfoImpl());
        ((WebResourceMetaInfoImpl) webResource.getWebResourceMetaInfo()).setImageMetaInfo(new ImageMetaInfoImpl());
        ((ImageMetaInfoImpl) webResource.getWebResourceMetaInfo().getImageMetaInfo()).setWidth(598);
        ((ImageMetaInfoImpl) webResource.getWebResourceMetaInfo().getImageMetaInfo()).setHeight(768);
        ((ImageMetaInfoImpl) webResource.getWebResourceMetaInfo().getImageMetaInfo()).setMimeType(MockBeanConstants.MIME_TYPE_IMAGE);
        ((ImageMetaInfoImpl) webResource.getWebResourceMetaInfo().getImageMetaInfo()).setFileFormat("JPEG");
        ((ImageMetaInfoImpl) webResource.getWebResourceMetaInfo().getImageMetaInfo()).setColorSpace("sRGB");
        ((ImageMetaInfoImpl) webResource.getWebResourceMetaInfo().getImageMetaInfo()).setFileSize(61347L);
        ((ImageMetaInfoImpl) webResource.getWebResourceMetaInfo().getImageMetaInfo()).setColorPalette(new String[]{"#DCDCDC", "#2F4F4F", "#FAEBD7", "#FAF0E6", "#F5F5DC", "#696969"});
        ((ImageMetaInfoImpl) webResource.getWebResourceMetaInfo().getImageMetaInfo()).setOrientation(ImageOrientation.PORTRAIT);

        aggregation.setWebResources(webResources);
        aggregations.add(aggregation);

        bean.setAggregations(aggregations);
    }

    private static void setAgents(FullBeanImpl bean) {
        List<AgentImpl> agents = new ArrayList<>();
        AgentImpl agent = new AgentImpl();
        agents.add(agent);

        // first agent Person
        agent.setEnd(new HashMap<>());
        agent.getEnd().put(MockBeanConstants.DEF, new ArrayList<>());
        agent.getEnd().get(MockBeanConstants.DEF).add(MockBeanConstants.DEATH_DATE);

        agent.setOwlSameAs(new String[]{"http://purl.org/collections/nl/am/p-10456",
                "http://rdf.freebase.com/ns/m.011bn9nx",
                "http://sv.dbpedia.org/resource/Leonardo_da_Vincis_uppfinningar",
                "http://pl.dbpedia.org/resource/Wynalazki_i_konstrukcje_Leonarda_da_Vinci"});

        agent.setRdaGr2DateOfBirth(new HashMap<>());
        agent.getRdaGr2DateOfBirth().put(MockBeanConstants.DEF, new ArrayList<>());
        agent.getRdaGr2DateOfBirth().get(MockBeanConstants.DEF).add(MockBeanConstants.BIRTH_DATE);

        agent.setRdaGr2DateOfDeath(new HashMap<>());
        agent.getRdaGr2DateOfDeath().put(MockBeanConstants.DEF, new ArrayList<>());
        agent.getRdaGr2DateOfDeath().get(MockBeanConstants.DEF).add(MockBeanConstants.DEATH_DATE);

        agent.setRdaGr2PlaceOfBirth(new HashMap<>());
        agent.getRdaGr2PlaceOfBirth().put(MockBeanConstants.DEF, new ArrayList<>());
        agent.getRdaGr2PlaceOfBirth().get(MockBeanConstants.DEF).add(MockBeanConstants.BIRTH_PLACE_1);
        agent.getRdaGr2PlaceOfBirth().get(MockBeanConstants.DEF).add(MockBeanConstants.BIRTH_PLACE_2);
        agent.getRdaGr2PlaceOfBirth().put(MockBeanConstants.EN, new ArrayList<>());
        agent.getRdaGr2PlaceOfBirth().get(MockBeanConstants.EN).add(MockBeanConstants.BIRTH_PLACE_3);

        agent.setRdaGr2PlaceOfDeath(new HashMap<>());
        agent.getRdaGr2PlaceOfDeath().put(MockBeanConstants.DEF, new ArrayList<>());
        agent.getRdaGr2PlaceOfDeath().get(MockBeanConstants.DEF).add(MockBeanConstants.DEATH_PLACE_1);
        agent.getRdaGr2PlaceOfDeath().get(MockBeanConstants.DEF).add(MockBeanConstants.DEATH_PLACE_2);

        agent.setRdaGr2BiographicalInformation(new HashMap<>());
        agent.getRdaGr2BiographicalInformation().put(MockBeanConstants.EN, new ArrayList<>());
        agent.getRdaGr2BiographicalInformation().get(MockBeanConstants.EN).add("Leonardo da Vinci (1452–1519) was an Italian polymath, regarded as the epitome of the \"Renaissance Man\", displaying skills in numerous diverse areas of study. Whilst most famous for his paintings such as the Mona Lisa and the Last Supper, Leonardo is also renowned in the fields of civil engineering, chemistry, geology, geometry, hydrodynamics, mathematics, mechanical engineering, optics, physics, pyrotechnics, and zoology.While the full extent of his scientific studies has only become recognized in the last 150 years, he was, during his lifetime, employed for his engineering and skill of invention. Many of his designs, such as the movable dikes to protect Venice from invasion, proved too costly or impractical. Some of his smaller inventions entered the world of manufacturing unheralded. As an engineer, Leonardo conceived ideas vastly ahead of his own time, conceptually inventing a helicopter, a tank, the use of concentrated solar power, a calculator, a rudimentary theory of plate tectonics and the double hull. In practice, he greatly advanced the state of knowledge in the fields of anatomy, astronomy, civil engineering, optics, and the study of water (hydrodynamics).Leonardo's most famous drawing, the Vitruvian Man, is a study of the proportions of the human body, linking art and science in a single work that has come to represent Renaissance Humanism.");
        agent.getRdaGr2BiographicalInformation().put("pl", new ArrayList<>());
        agent.getRdaGr2BiographicalInformation().get("pl").add("Leonardo da Vinci na prawie sześciu tysiącach stron notatek wykonał znacznie więcej projektów technicznych niż prac artystycznych, co wskazuje na to, że inżynieria była niezmiernie ważną dziedziną jego zainteresowań.Giorgio Vasari w Żywotach najsławniejszych malarzy, rzeźbiarzy i architektów pisał o Leonardzie:");

        agent.setPrefLabel(new HashMap<>());
        agent.getPrefLabel().put(MockBeanConstants.EN, new ArrayList<>());
        agent.getPrefLabel().get(MockBeanConstants.EN).add("Science and inventions of Leonardo da Vinci");
        agent.getPrefLabel().put(MockBeanConstants.PL, new ArrayList<>());
        agent.getPrefLabel().get(MockBeanConstants.PL).add("Wynalazki i konstrukcje Leonarda da Vinci");

        agent.setAltLabel(new HashMap<>());
        agent.getAltLabel().put(MockBeanConstants.EN, new ArrayList<>());
        agent.getAltLabel().get(MockBeanConstants.EN).add("Leonardo da Vinci");

        agent.setAbout(MockBeanConstants.DC_CREATOR_6);

        //adding second agent Orgainsation
        agent = new AgentImpl();
        agents.add(agent);

        agent.setPrefLabel(new HashMap<>());
        agent.getPrefLabel().put(MockBeanConstants.EN, new ArrayList<>());
        agent.getPrefLabel().get(MockBeanConstants.EN).add("European museums");
        agent.getPrefLabel().put(MockBeanConstants.FR, new ArrayList<>());
        agent.getPrefLabel().get(MockBeanConstants.FR).add("Musées européens");

        agent.setRdaGr2DateOfTermination(new HashMap<>());
        agent.getRdaGr2DateOfTermination().put(MockBeanConstants.DEF, new ArrayList<>());
        agent.getRdaGr2DateOfTermination().get(MockBeanConstants.DEF).add(MockBeanConstants.DISOLUTION_DATE);

        agent.setAbout(MockBeanConstants.DC_CREATOR_8);

        bean.setAgents(agents);
    }

    private static void setProvidedCHO(FullBeanImpl bean) {
        List<ProvidedCHOImpl> providedCHOs = new ArrayList<>();
        ProvidedCHOImpl providedCHO = new ProvidedCHOImpl();
        providedCHO.setAbout(MockBeanConstants.ABOUT);
        providedCHOs.add(providedCHO);

        bean.setProvidedCHOs(providedCHOs);
    }

    public static void setTimespans(FullBeanImpl bean) {
        List<TimespanImpl> timespans = new ArrayList<>();
        TimespanImpl timespan = new TimespanImpl();
        timespans.add(timespan);

        timespan.setBegin(new HashMap<>());
        timespan.getBegin().put(MockBeanConstants.DEF, new ArrayList<>());
        timespan.getBegin().get(MockBeanConstants.DEF).add("Tue Jan 01 00:19:32 CET 1901");

        timespan.setEnd(new HashMap<>());
        timespan.getEnd().put(MockBeanConstants.DEF, new ArrayList<>());
        timespan.getEnd().get(MockBeanConstants.DEF).add("Sun Dec 31 01:00:00 CET 2000");

        timespan.setAbout(MockBeanConstants.DC_TERMS_TEMPORAL_1);

        timespan = new TimespanImpl();
        timespans.add(timespan);

        timespan.setBegin(new HashMap<>());
        timespan.getBegin().put(MockBeanConstants.DEF, new ArrayList<>());
        timespan.getBegin().get(MockBeanConstants.DEF).add("1901-01-01");

        timespan.setEnd(new HashMap<>());
        timespan.getEnd().put(MockBeanConstants.DEF, new ArrayList<>());
        timespan.getEnd().get(MockBeanConstants.DEF).add("1902-01-01");

        timespan.setAbout(MockBeanConstants.DC_TERMS_TEMPORAL_2);

        bean.setTimespans(timespans);
    }

    private static void setConcepts(FullBeanImpl bean) {
        List<ConceptImpl> concepts = new ArrayList<>();
        ConceptImpl concept = new ConceptImpl();
        concepts.add(concept);

        concept.setRelated(new String[]{"http://dbpedia.org/resource/Category:Agriculture"});
        concept.setExactMatch(new String[]{"http://bg.dbpedia.org/resource/Селско_стопанство",
                "http://ia.dbpedia.org/resource/Agricultura",
                "http://sl.dbpedia.org/resource/Kmetijstvo",
                "http://pnb.dbpedia.org/resource/وائی_بیجی",
                "http://el.dbpedia.org/resource/Γεωργία_(δραστηριότητα)"});

        concept.setPrefLabel(new HashMap<>());
        concept.getPrefLabel().put("no", new ArrayList<>());
        concept.getPrefLabel().get("no").add(MockBeanConstants.CONCEPT_PREF_LABEL_1);
        concept.getPrefLabel().put("de", new ArrayList<>());
        concept.getPrefLabel().get("de").add(MockBeanConstants.CONCEPT_PREF_LABEL_2);

        concept.setNote(new HashMap<>());
        concept.getNote().put("no", new ArrayList<>());
        concept.getNote().get("no").add(MockBeanConstants.CONCEPT_NOTE_1);
        concept.getNote().put("de", new ArrayList<>());
        concept.getNote().get("de").add(MockBeanConstants.CONCEPT_NOTE_2);
        concept.setAbout(MockBeanConstants.DC_CREATOR_4);

        bean.setConcepts(concepts);
    }

    private static void setPlaces(FullBeanImpl bean) {
        List<PlaceImpl> places = new ArrayList<>();
        PlaceImpl place = new PlaceImpl();
        places.add(place);

        place.setIsPartOf(new HashMap<>());
        place.getIsPartOf().put(MockBeanConstants.DEF, new ArrayList<>());
        place.getIsPartOf().get(MockBeanConstants.DEF).add(MockBeanConstants.PLACE_IS_PART);

        place.setLatitude(46.0F);
        place.setAltitude(70.0F);
        place.setLongitude(2.0F);

        place.setDcTermsHasPart(new HashMap<>());
        place.getDcTermsHasPart().put(MockBeanConstants.DEF, new ArrayList<>());
        place.getDcTermsHasPart().get(MockBeanConstants.DEF).add(MockBeanConstants.PLACE_HAS_PART);

        place.setOwlSameAs(new String[]{MockBeanConstants.PLACE_SAME_OWL_AS});

        place.setPrefLabel(new HashMap<>());
        place.getPrefLabel().put(MockBeanConstants.EN, new ArrayList<>());
        place.getPrefLabel().get(MockBeanConstants.EN).add(MockBeanConstants.PLACE_PREF_LABEL);

        place.setAltLabel(new HashMap<>());
        place.getAltLabel().put(MockBeanConstants.IT, new ArrayList<>());
        place.getAltLabel().get(MockBeanConstants.IT).add(MockBeanConstants.PLACE_PREF_LABEL);

        place.setNote(new HashMap<>());
        place.getNote().put(MockBeanConstants.EN, new ArrayList<>());
        place.getNote().get(MockBeanConstants.EN).add(MockBeanConstants.PLACE_NOTE);

        place.setAbout(MockBeanConstants.DC_CREATOR_7);

        bean.setPlaces(places);
    }
}
