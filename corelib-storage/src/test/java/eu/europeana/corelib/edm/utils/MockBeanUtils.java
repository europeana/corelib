package eu.europeana.corelib.edm.utils;

import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import eu.europeana.corelib.solr.entity.*;

import java.util.ArrayList;
import java.util.List;

public class MockBeanUtils {


    public static final String ABOUT = "/multipleProxy/1234/test_5678";
    public static final String EUROPEANA_PROXY_ABOUT = "/proxy/europeana/15/fashion";
    public static final String PROXY_FOR = "/item/1234/test_5678";
    public static final String EUROPEANA_PROXY_IN = "/aggregation/europeana/1/fashion_894";
    public static final String PROVIDER_PROXY_ABOUT = "/proxy/provider/1/fashion_45";
    public static final String SECOND_PROXY_ABOUT = "/proxy/aggregator/2/fashion_24";
    public static final String THIRD_PROXY_ABOUT = "/proxy/aggregator/3/fashion_24";

    public static final String DATAPROVIDER_AGGREGATION_ABOUT = "/aggregation/provider/1234/test_5678";
    public static final String SECOND_AGGREGATION_ABOUT  = "/aggregation/fashion/859/test_5678";
    public static final String IS_SHOWN_BY  = "isShownBy_test";

    public static final String IS_SHOWN_AT  = "isShownAt_test";

    public static final String EUROPEANA_AGGREGATION_ABOUT  = "/aggregation/europeana/1234/test_5678";
    public static final String EUROPEANA_COLLECTION_NAME  ="1234_test" ;


    static FullBeanImpl MockMultipleProxyAggregationBean() {
        FullBeanImpl bean = new FullBeanImpl();
        bean.setAbout(ABOUT);

        // europeana proxy
        bean.setProxies(new ArrayList<>());
        ProxyImpl proxy = new ProxyImpl();
        proxy.setAbout(EUROPEANA_PROXY_ABOUT);
        proxy.setProxyFor(PROXY_FOR);
        proxy.setEdmType(DocType.IMAGE.getEnumNameValue());
        proxy.setProxyIn(new String [] {EUROPEANA_PROXY_IN});
        proxy.setLineage(new String [] {THIRD_PROXY_ABOUT, PROVIDER_PROXY_ABOUT, SECOND_PROXY_ABOUT});
        proxy.setEuropeanaProxy(true);
        bean.getProxies().add(proxy);

        // 1st proxy
        proxy = new ProxyImpl();
        proxy.setAbout(PROVIDER_PROXY_ABOUT);
        proxy.setProxyFor(PROXY_FOR);
        proxy.setEdmType(DocType.IMAGE.getEnumNameValue());
        proxy.setProxyIn(new String [] {DATAPROVIDER_AGGREGATION_ABOUT});
        bean.getProxies().add(proxy);

        // 2nd proxy
        proxy = new ProxyImpl();
        proxy.setAbout(SECOND_PROXY_ABOUT);
        proxy.setProxyFor(PROXY_FOR);
        proxy.setEdmType(DocType.IMAGE.getEnumNameValue());
        proxy.setProxyIn(new String [] {SECOND_AGGREGATION_ABOUT});
        proxy.setLineage(new String [] {THIRD_PROXY_ABOUT});
        bean.getProxies().add(proxy);

        // 3rd proxy
        proxy = new ProxyImpl();
        proxy.setAbout(THIRD_PROXY_ABOUT);
        proxy.setProxyFor(PROXY_FOR);
        proxy.setEdmType(DocType.IMAGE.getEnumNameValue());
        proxy.setLineage(new String [] {PROVIDER_PROXY_ABOUT});
        bean.getProxies().add(proxy);

        bean.setProvidedCHOs(new ArrayList<>());
        ProvidedCHOImpl providedCHO = new ProvidedCHOImpl();
        providedCHO.setAbout(ABOUT);
        bean.getProvidedCHOs().add(providedCHO);

        bean.setAggregations(new ArrayList<>());

        // 2nd aggregation
        AggregationImpl aggregation = new AggregationImpl();
        aggregation.setAbout(SECOND_AGGREGATION_ABOUT);
        aggregation.setEdmIsShownAt(IS_SHOWN_AT);
        aggregation.setEdmIsShownBy(IS_SHOWN_BY);
        bean.getAggregations().add(aggregation);

        // data provider aggregation
        aggregation = new AggregationImpl();
        aggregation.setAbout(DATAPROVIDER_AGGREGATION_ABOUT);
        aggregation.setEdmIsShownAt(IS_SHOWN_AT);
        aggregation.setEdmIsShownBy(IS_SHOWN_BY);
        bean.getAggregations().add(aggregation);

        // empty webresources
        List<WebResourceImpl> webResources = new ArrayList<>();
        aggregation.setWebResources(webResources);

        EuropeanaAggregationImpl europeanaAggregation = new EuropeanaAggregationImpl();
        europeanaAggregation.setAbout(EUROPEANA_AGGREGATION_ABOUT);
        bean.setEuropeanaAggregation(europeanaAggregation);

        bean.setEuropeanaCollectionName(new String[] { EUROPEANA_COLLECTION_NAME});

        return bean;
    }

    static FullBeanImpl MockMinimalBean() {
        FullBeanImpl bean = new FullBeanImpl();
        bean.setAbout(ABOUT);

        // europeana proxy
        bean.setProxies(new ArrayList<>());
        ProxyImpl proxy = new ProxyImpl();
        proxy.setAbout(EUROPEANA_PROXY_ABOUT);
        proxy.setProxyFor(PROXY_FOR);
        proxy.setEdmType(DocType.IMAGE.getEnumNameValue());
        proxy.setProxyIn(new String [] {EUROPEANA_PROXY_IN});
        proxy.setEuropeanaProxy(true);
        bean.getProxies().add(proxy);

        // 1st proxy
        proxy = new ProxyImpl();
        proxy.setAbout(PROVIDER_PROXY_ABOUT);
        proxy.setProxyFor(PROXY_FOR);
        proxy.setEdmType(DocType.IMAGE.getEnumNameValue());
        proxy.setProxyIn(new String [] {DATAPROVIDER_AGGREGATION_ABOUT});
        bean.getProxies().add(proxy);

        bean.setProvidedCHOs(new ArrayList<>());
        ProvidedCHOImpl providedCHO = new ProvidedCHOImpl();
        providedCHO.setAbout(ABOUT);
        bean.getProvidedCHOs().add(providedCHO);

        bean.setAggregations(new ArrayList<>());

        // 2nd aggregation
        AggregationImpl aggregation = new AggregationImpl();
        aggregation.setAbout(SECOND_AGGREGATION_ABOUT);
        aggregation.setEdmIsShownAt(IS_SHOWN_AT);
        aggregation.setEdmIsShownBy(IS_SHOWN_BY);
        bean.getAggregations().add(aggregation);

        // empty webresources
        List<WebResourceImpl> webResources = new ArrayList<>();
        aggregation.setWebResources(webResources);

        EuropeanaAggregationImpl europeanaAggregation = new EuropeanaAggregationImpl();
        europeanaAggregation.setAbout(EUROPEANA_AGGREGATION_ABOUT);
        bean.setEuropeanaAggregation(europeanaAggregation);

        bean.setEuropeanaCollectionName(new String[] { EUROPEANA_COLLECTION_NAME});

        return bean;
    }

    static FullBeanImpl MockBeanWithLineageProxy() {
        FullBeanImpl bean = new FullBeanImpl();
        bean.setAbout(ABOUT);

        // europeana proxy
        bean.setProxies(new ArrayList<>());
        ProxyImpl proxy = new ProxyImpl();
        proxy.setAbout(EUROPEANA_PROXY_ABOUT);
        proxy.setProxyFor(PROXY_FOR);
        proxy.setEdmType(DocType.IMAGE.getEnumNameValue());
        proxy.setProxyIn(new String [] {EUROPEANA_PROXY_IN});
        proxy.setLineage(new String [] {PROVIDER_PROXY_ABOUT});
        proxy.setEuropeanaProxy(true);
        bean.getProxies().add(proxy);

        // 1st proxy
        proxy = new ProxyImpl();
        proxy.setAbout(PROVIDER_PROXY_ABOUT);
        proxy.setProxyFor(PROXY_FOR);
        proxy.setEdmType(DocType.IMAGE.getEnumNameValue());
        proxy.setProxyIn(new String [] {DATAPROVIDER_AGGREGATION_ABOUT});
        proxy.setLineage(new String [] {"dummy"});
        bean.getProxies().add(proxy);

        bean.setProvidedCHOs(new ArrayList<>());
        ProvidedCHOImpl providedCHO = new ProvidedCHOImpl();
        providedCHO.setAbout(ABOUT);
        bean.getProvidedCHOs().add(providedCHO);

        bean.setAggregations(new ArrayList<>());

        // 2nd aggregation
        AggregationImpl aggregation = new AggregationImpl();
        aggregation.setAbout(SECOND_AGGREGATION_ABOUT);
        aggregation.setEdmIsShownAt(IS_SHOWN_AT);
        aggregation.setEdmIsShownBy(IS_SHOWN_BY);
        bean.getAggregations().add(aggregation);

        // empty webresources
        List<WebResourceImpl> webResources = new ArrayList<>();
        aggregation.setWebResources(webResources);

        EuropeanaAggregationImpl europeanaAggregation = new EuropeanaAggregationImpl();
        europeanaAggregation.setAbout(EUROPEANA_AGGREGATION_ABOUT);
        bean.setEuropeanaAggregation(europeanaAggregation);

        bean.setEuropeanaCollectionName(new String[] { EUROPEANA_COLLECTION_NAME});

        return bean;
    }

}
