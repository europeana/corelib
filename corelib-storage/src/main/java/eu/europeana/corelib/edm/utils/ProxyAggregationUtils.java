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
                if (isProxyWithOutLineage(proxy) && !proxy.isEuropeanaProxy()) {
                    return proxy;
                }
            }
        }
        return null;
    }

    /**
     * Orders Proxies
     * Order : Europeana Proxy -> Aggregator Proxy -> Data Provider
     * Other proxies are ordered according to the lineage order present in europeana proxy
     *
     * @param bean
     * @return
     */
    public static List<Proxy> orderProxy(FullBean bean) {
        List<Proxy> orderedProxy = new ArrayList<>();
        orderedProxy.add(getEuropeanaProxy(bean.getProxies()));
        orderedProxy.addAll(orderNonEuropeanaProxy(isLineagePresentInEuropeanaProxy(bean), bean));

        return orderedProxy;
    }

    /**
     * Orders non-europeana Proxies
     * Aggregator Proxy -> Data Provider
     *
     * @param isLineagePresent true if lineage is present in europeana proxy
     * @param bean
     * @return
     */
    private static List<Proxy> orderNonEuropeanaProxy(boolean isLineagePresent, FullBean bean) {
        List<Proxy> orderedProxy = new ArrayList<>();
        if (isLineagePresent) {
            getNonEuropeanaProxies(orderedProxy, bean);
        } else {
            LOG.info("Only one non-europeana proxy exists for record {}.", bean.getId());
            for (Proxy proxy : bean.getProxies()) {
                if (!proxy.isEuropeanaProxy()) {
                    orderedProxy.add(proxy);
                }
            }
        }
        return orderedProxy;
    }

    /**
     * Adds non-europeana proxy via lineage values.
     * Order returned : Aggregator proxies -> data provider proxy
     *
     * @param bean
     * @return
     */
    private static void getNonEuropeanaProxies(List<Proxy> orderedProxy, FullBean bean) {
        Proxy proxy = getProxyWithOutLineage(bean);
        if (proxy != null) {
            orderedProxy.add(proxy);
            String lineageToCheck = proxy.getAbout();

            // get aggregator proxies through lineages
            for (int i = 0; i < bean.getProxies().size() - 2; i++) {
                Proxy nextProxy = getNextproxy(bean, lineageToCheck);
                if (nextProxy != null) {
                    lineageToCheck = nextProxy.getAbout();
                    orderedProxy.add(nextProxy);
                }
            }
            Collections.reverse(orderedProxy);
        } else {
            LOG.error("There is no proxy without ore:lineage for record {}.", bean.getId());
        }
    }

    private static Proxy getNextproxy(FullBean bean, String lineageTocheck) {
        for (Proxy proxy : bean.getProxies()) {
            if (!proxy.isEuropeanaProxy() && !isProxyWithOutLineage(proxy) && StringUtils.equals(proxy.getLineage()[0], lineageTocheck)) {
                return proxy;
            }
        }
        return null;
    }

    public static boolean isLineagePresentInEuropeanaProxy(FullBean bean) {
        if (bean.getProxies() != null) {
            for (Proxy proxy : bean.getProxies()) {
                if (proxy.isEuropeanaProxy() && proxy.getLineage() != null) {
                    return true;
                }
            }
        }
        return false;
    }

    private static Proxy getEuropeanaProxy(List<? extends Proxy> proxies) {
        for (Proxy proxy : proxies) {
            if (proxy.isEuropeanaProxy()) {
                return proxy;
            }
        }
        return null;
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
        if (dataProviderAgg != null) {
            orderAggregation.add(dataProviderAgg);
        }
        for (Aggregation aggregation : bean.getAggregations()) {
            if (dataProviderAgg != null) {
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
