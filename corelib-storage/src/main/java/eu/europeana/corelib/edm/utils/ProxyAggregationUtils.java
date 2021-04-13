package eu.europeana.corelib.edm.utils;

import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.definitions.edm.entity.Aggregation;
import eu.europeana.corelib.definitions.edm.entity.Proxy;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class ProxyAggregationUtils {

    private ProxyAggregationUtils() {
        // private constructor
    }

    private static final Logger LOG = LogManager.getLogger(ProxyAggregationUtils.class);

    /**
     * Identifies if the proxy is the proxy without ore:lineage
     * if lineage is NOT present , return true
     * or else false
     *
     * @param bean
     * @return
     */
    public static Proxy getProxyWithOutLineage(FullBean bean) {
        if (bean.getProxies() != null) {
            for (Proxy proxy : bean.getProxies()) {
                if(isProxyWithOutLineage(proxy) && !proxy.isEuropeanaProxy()) {
                    return proxy;
                }
            }
        }
        return null;
    }

    /**
     * Orders Proxies
     * Order : Europeana Proxy, data provider proxy, then other proxies
     * Other proxies are ordered according to the lineage order present in europeana proxy
     *
     * @param bean
     * @return
     */
    public static List<Proxy> orderProxy(FullBean bean) {
        List<Proxy> orderedProxy = new ArrayList<>();
        orderedProxy.add(getEuropeanaProxy(bean.getProxies()));
        List<String> lineages = getLineageFromEuropeanaProxy(bean);
        orderedProxy.addAll(orderNonEuropeanaProxy(lineages, bean));
        
        return orderedProxy;
    }

    /**
     * Orders non-europeana Proxies
     * First adds the data provider proxy and then
     * orders the other proxies based on the lineage order
     *
     * @param lineages lineage values from europeana proxy
     * @param bean
     * @return
     */
    private static List<Proxy> orderNonEuropeanaProxy( List<String> lineages, FullBean bean) {
        List<Proxy> orderedProxy = new ArrayList<>();
        if(lineages.isEmpty()) {
            LOG.info("Only one non-europeana proxy exists for record {}.", bean.getId());
            for (Proxy proxy : bean.getProxies()) {
                if(!proxy.isEuropeanaProxy()) {
                    orderedProxy.add(proxy);
                }
            }
        } else {
            getNonEuropeanaProxies(orderedProxy, lineages, bean);
        }
        return orderedProxy;
    }
    
    private static void getNonEuropeanaProxies(List<Proxy> orderedProxy, List<String> lineages, FullBean bean) {
        // add the data provider proxy : proxy without ore:lineage
        for(Proxy proxy : bean.getProxies()) {
            if(isNonEuropeanaDataProviderProxy(proxy)) {
                orderedProxy.add(proxy);
            }
        }
        for (String lineage: lineages) {
            for(Proxy proxy : bean.getProxies()) {
                if(StringUtils.equals(proxy.getAbout(), lineage) && isNonEuropeanaProxy(proxy)) {
                    orderedProxy.add(proxy);
                }
            }
        }
    }

    public static List<String> getLineageFromEuropeanaProxy(FullBean bean) {
        if (bean.getProxies() != null) {
            for (Proxy proxy : bean.getProxies()) {
                if(proxy.isEuropeanaProxy() && proxy.getLineage() != null) {
                    return Arrays.asList(proxy.getLineage());
                }
            }
        }
        return new ArrayList<>();
    }

    private static Proxy getEuropeanaProxy(List<? extends Proxy> proxies) {
        for (Proxy proxy : proxies) {
            if (proxy.isEuropeanaProxy()) {
                return proxy;
            }
        }
        return null;
    }

    private static boolean isNonEuropeanaDataProviderProxy(Proxy proxy) {
        return (!proxy.isEuropeanaProxy() && isProxyWithOutLineage(proxy));
    }

    private static boolean isNonEuropeanaProxy(Proxy proxy) {
        return (!proxy.isEuropeanaProxy() && !isProxyWithOutLineage(proxy));
    }

    /**
     * Identifies if the proxy is the proxy without ore:lineage
     * if lineage is NOT present , return true
     * or else false
     *
     * @param proxy
     * @return
     */
    public static boolean isProxyWithOutLineage(Proxy proxy) {
        // checking for both null and empty String arrays
        return (proxy.getLineage() == null || (proxy.getLineage() != null && proxy.getLineage().length == 0));
    }

    /**
     * Order Aggregation list with data provider aggregation on top
     * if there is no data provider aggregation, return the bean.getAggregations() list as it is.
     *
     * @param bean
     * @return
     */
    public static List<Aggregation> orderAggregation(FullBean bean) {
        List<Aggregation> orderAggregation = new ArrayList<>();
        Aggregation dataProviderAgg = getDataProviderAggregation(bean);
        if(dataProviderAgg != null) {
            orderAggregation.add(dataProviderAgg);
        }
        for(Aggregation aggregation : bean.getAggregations()) {
            if (dataProviderAgg!= null) {
                if (!StringUtils.equals(dataProviderAgg.getAbout(), aggregation.getAbout())) {
                    orderAggregation.add(aggregation);
                }
            } else {
                return (List<Aggregation>) bean.getAggregations();
            }
        }
        return orderAggregation;
    }

    /**
     * Returns the dataProvider Aggregation
     * For proxy without lineage value, return the aggregation associated
     * with the proxyIn (of the main proxy)
     *
     * @param bean
     * @return
     */
    public static Aggregation getDataProviderAggregation(FullBean bean) {
        String[] proxyIn = getDataProviderFromProxyWithOutLineage(bean);
        if (bean.getAggregations() != null && (proxyIn != null && proxyIn.length > 0)) {
            for (Aggregation aggregation : bean.getAggregations()) {
                if (StringUtils.equalsIgnoreCase(aggregation.getAbout(), proxyIn[0].trim())) {
                    return aggregation;
                }
            }
            LOG.error("There is no data provider aggregation available for {} for record {}", proxyIn[0], bean.getAbout());
        } else {
            LOG.error("There is no aggregation available");
        }
        return null;
    }


    /**
     * get the proxyIn value from the main proxy
     *
     * @param bean
     * @return
     */
    private static String[] getDataProviderFromProxyWithOutLineage(FullBean bean) {
        if (bean.getProxies() != null) {
            for (Proxy proxy : bean.getProxies()) {
                if (isProxyWithOutLineage(proxy)) {
                    return proxy.getProxyIn();
                }
            }
        }
        return new String[]{};
    }



}
