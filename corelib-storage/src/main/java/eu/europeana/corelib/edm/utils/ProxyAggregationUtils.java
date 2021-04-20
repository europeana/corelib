package eu.europeana.corelib.edm.utils;

import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.definitions.edm.entity.Aggregation;
import eu.europeana.corelib.definitions.edm.entity.Proxy;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class ProxyAggregationUtils {

    private static final Logger LOG = LogManager.getLogger(ProxyAggregationUtils.class);

    private ProxyAggregationUtils() {
        // private constructor
    }

    /**
     * Returns the proxy if the proxy without ore:lineage
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
     * Orders the Proxies
     * Order : Europeana Proxy -> Aggregator Proxy -> Data Provider
     *
     * @param bean
     * @return
     */
    public static List<Proxy> orderProxy(FullBean bean) {
        List<Proxy> orderedProxy = new ArrayList<>();
        Map<String, Proxy> nonEuropeanaProxy = new HashMap<>();
        List<String> lineages = new ArrayList<>();

        getProxyAndLineage(bean.getProxies(), nonEuropeanaProxy, orderedProxy, lineages);
        if (lineages.isEmpty()) {
            if (nonEuropeanaProxy.size() > 1) {
                LOG.warn("More than one non-europeana proxy exist for record {} without lineage.", bean.getId());
            }
            orderedProxy.addAll(nonEuropeanaProxy.values());
        } else {
            Collections.reverse(lineages);
            orderedProxy.addAll(orderNonEuropeanaProxy(lineages, nonEuropeanaProxy));
        }
        // this is fail-safe check, in case we miss any proxy
        // will return the same order as present
        if (orderedProxy.size() == bean.getProxies().size()) {
            return orderedProxy;
        }
        LOG.warn("Record {} contains unexpected proxy data.", bean.getId());
        return (List<Proxy>) bean.getProxies();
    }

    /**
     * Orders non-europeana Proxies
     * Order : Aggregator Proxy -> Data Provider
     *
     * @param lineages          true if lineage is present in europeana proxy
     * @param nonEuropeanaProxy map with all the non-europeana Proxies
     * @return
     */
    private static List<Proxy> orderNonEuropeanaProxy(List<String> lineages, Map<String, Proxy> nonEuropeanaProxy) {
        List<Proxy> orderedProxy = new ArrayList<>();
        for (String lineage : lineages) {
            if (nonEuropeanaProxy.get(lineage) != null) {
                orderedProxy.add(nonEuropeanaProxy.get(lineage));
            } else {
                LOG.error("Non-europeana proxy {} not found", lineage);
            }
        }
        return orderedProxy;
    }

    /**
     * Adds europeana proxy in the ordered proxy list.
     * Adds the lineage values of the Europeana Proxy in lineages list
     * Adds non-europeana proxy in the nonEuropeanaProxy map
     *
     * @param proxies
     * @param nonEuropeanaProxy map for non-europeana proxies
     * @param orderedProxy      ordered proxy list
     * @return
     */
    private static void getProxyAndLineage(List<? extends Proxy> proxies, Map<String, Proxy> nonEuropeanaProxy, List<Proxy> orderedProxy,
                                           List<String> lineages) {
        for (Proxy proxy : proxies) {
            if (proxy.isEuropeanaProxy()) {
                orderedProxy.add(proxy);
                if (proxy.getLineage() != null) {
                    lineages.addAll(Arrays.asList(proxy.getLineage()));
                }
            } else {
                nonEuropeanaProxy.put(proxy.getAbout(), proxy);
            }
        }
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
        List<Aggregation> nonDataProvdrAggregation = new ArrayList<>();

        Aggregation dataProviderAgg = getDataProviderAggregation(bean, nonDataProvdrAggregation);
        // if there is no data provider aggregation return the aggregations as it is
        if (dataProviderAgg == null) {
            LOG.error("There is no data provider aggregation available for record {}", bean.getAbout());
            return (List<Aggregation>) bean.getAggregations();
        }
        orderAggregation.add(dataProviderAgg);
        orderAggregation.addAll(nonDataProvdrAggregation);
        // this is fail-safe check, in case we miss any aggregation
        // will return the same order as present
        if (orderAggregation.size() == bean.getAggregations().size()) {
            return orderAggregation;
        }
        return (List<Aggregation>) bean.getAggregations();
    }


    /**
     * Returns the dataProvider Aggregation
     * For proxy without lineage value, return the aggregation associated
     * with the proxyIn (of the main proxy)
     * <p>
     * Also adds the other aggregations present in nonDataProvdrAggregations list.
     *
     * @param bean
     * @return
     */
    public static Aggregation getDataProviderAggregation(FullBean bean, List<Aggregation> nonDataProvdrAggregations) {
        String[] proxyIn = getDataProviderFromProxyWithOutLineage(bean);
        Aggregation dataProviderAggregation = null;
        if (bean.getAggregations() != null && (proxyIn != null && proxyIn.length > 0)) {
            for (Aggregation aggregation : bean.getAggregations()) {
                if (StringUtils.equalsIgnoreCase(aggregation.getAbout(), proxyIn[0].trim())) {
                    dataProviderAggregation = aggregation;
                } else {
                    if (nonDataProvdrAggregations != null) {
                        nonDataProvdrAggregations.add(aggregation);
                    }
                }
            }
        } else {
            LOG.error("There is no aggregation available");
        }
        return dataProviderAggregation;
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
                if (isProxyWithOutLineage(proxy) && !proxy.isEuropeanaProxy()) {
                    return proxy.getProxyIn();
                }
            }
        }
        return new String[]{};
    }
}
