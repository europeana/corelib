package eu.europeana.corelib.edm.utils;

import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.definitions.edm.entity.Aggregation;
import eu.europeana.corelib.definitions.edm.entity.Proxy;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ProxyAggregationUtilsTest {

    private ProxyAggregationUtils  proxyAggregationUtils;
    private FullBean bean = MockBeanUtils.mockMultipleProxyAggregationBean();
    private FullBean minimalBean = MockBeanUtils.mockMinimalBean();
    private FullBean invalidProxyBean = MockBeanUtils.mockBeanWithLineageProxy();


    @Test
    public void getProxyWithOutLineageTest() {
        Proxy proxy = proxyAggregationUtils.getProxyWithOutLineage(minimalBean);
        Assert.assertNotNull(proxy);
        Assert.assertNull(proxy.getLineage());
        Assert.assertEquals(MockBeanUtils.PROVIDER_PROXY_ABOUT, proxy.getAbout());

        proxy = proxyAggregationUtils.getProxyWithOutLineage(bean);
        Assert.assertNotNull(proxy);
        Assert.assertNull(proxy.getLineage());
        Assert.assertEquals(MockBeanUtils.PROVIDER_PROXY_ABOUT, proxy.getAbout());
    }

    @Test
    public void whenProxyWithoutLineageIsNotPresentTest() {
        Proxy proxy = proxyAggregationUtils.getProxyWithOutLineage(invalidProxyBean);
        Assert.assertNull(proxy);

        // check order
        List<Proxy> orderedProxy = proxyAggregationUtils.orderProxy(invalidProxyBean);
        Assert.assertNotNull(orderedProxy);
        Assert.assertEquals(invalidProxyBean.getProxies().size(), orderedProxy.size());
    }

    @Test
    public void getDataProviderAggregationTest() {
        Aggregation aggregation = proxyAggregationUtils.getDataProviderAggregation(bean, null);
        Assert.assertNotNull(aggregation);
        Assert.assertEquals(MockBeanUtils.DATAPROVIDER_AGGREGATION_ABOUT, aggregation.getAbout());
    }

    @Test
    public void orderProxyTest() {
        List<Proxy> orderedProxy = proxyAggregationUtils.orderProxy(bean);

        Assert.assertNotNull(orderedProxy);
        Assert.assertEquals(bean.getProxies().size(), orderedProxy.size());
        checkProxyOrder(orderedProxy);

    }

    @Test
    public void whenNoLineagePresentTest() {
        List<Proxy> orderedProxy = proxyAggregationUtils.orderProxy(minimalBean);
        // test order
        Assert.assertNotNull(orderedProxy);
        Assert.assertEquals(minimalBean.getProxies().size(), orderedProxy.size());
    }

    @Test
    public void whenNoDataProviderAggregationPresent() {
        Aggregation aggregation = proxyAggregationUtils.getDataProviderAggregation(minimalBean, null);
        Assert.assertNull(aggregation);

        // check the ordering
        List<Aggregation> aggregations = proxyAggregationUtils.orderAggregation(minimalBean);
        Assert.assertNotNull(aggregations);
        Assert.assertEquals(minimalBean.getAggregations().size(), aggregations.size());
        Assert.assertEquals(minimalBean.getAggregations().get(0).getAbout(), aggregations.get(0).getAbout());
    }

    @Test
    public void orderAgrregationTest(){
        List<Aggregation> aggregations = proxyAggregationUtils.orderAggregation(bean);
        Assert.assertNotNull(aggregations);
        Assert.assertEquals(bean.getAggregations().size(), aggregations.size());
        Assert.assertEquals(MockBeanUtils.DATAPROVIDER_AGGREGATION_ABOUT, aggregations.get(0).getAbout());
    }

    private void checkProxyOrder(List<Proxy> proxies) {
        // check europeana proxy
        Assert.assertTrue(proxies.get(0).isEuropeanaProxy());
        Assert.assertEquals(MockBeanUtils.EUROPEANA_PROXY_ABOUT, proxies.get(0).getAbout());

        // check aggregator proxy
        Assert.assertFalse(proxies.get(1).isEuropeanaProxy());
        Assert.assertEquals(MockBeanUtils.SECOND_PROXY_ABOUT, proxies.get(1).getAbout());
        Assert.assertEquals(MockBeanUtils.THIRD_PROXY_ABOUT, proxies.get(1).getLineage()[0]);

        Assert.assertFalse(proxies.get(2).isEuropeanaProxy());
        Assert.assertEquals(MockBeanUtils.THIRD_PROXY_ABOUT, proxies.get(2).getAbout());
        Assert.assertEquals(MockBeanUtils.PROVIDER_PROXY_ABOUT, proxies.get(2).getLineage()[0]);
        // check data provider proxy
        Assert.assertFalse(proxies.get(3).isEuropeanaProxy());
        Assert.assertEquals(MockBeanUtils.PROVIDER_PROXY_ABOUT, proxies.get(3).getAbout());
        Assert.assertNull(proxies.get(3).getLineage());
        Assert.assertEquals(MockBeanUtils.DATAPROVIDER_AGGREGATION_ABOUT, proxies.get(3).getProxyIn()[0]);



    }
}
