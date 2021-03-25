package eu.europeana.corelib.edm.utils;

import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.definitions.edm.entity.Aggregation;
import eu.europeana.corelib.definitions.edm.entity.Proxy;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
                if(isProxyWithOutLineage(proxy)) {
                    return proxy;
                }
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
                if (StringUtils.equalsIgnoreCase(aggregation.getAbout(), proxyIn[0])) {
                    return aggregation;
                } else {
                    LOG.error("There is no data provider aggregation available for {}", proxyIn[0]);
                }
            }
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
