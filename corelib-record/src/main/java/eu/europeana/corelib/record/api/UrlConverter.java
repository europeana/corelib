package eu.europeana.corelib.record.api;

import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.definitions.edm.entity.Aggregation;
import eu.europeana.corelib.definitions.edm.entity.EuropeanaAggregation;
import eu.europeana.corelib.definitions.edm.entity.ProvidedCHO;
import eu.europeana.corelib.definitions.edm.entity.Proxy;
import eu.europeana.corelib.web.service.impl.EuropeanaUrlBuilder;
import eu.europeana.corelib.web.utils.UrlBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Locale;

/**
 * Modify the urls retrieved from Mongo for usage by Record API or other services that need it (e.g. OAI-PMH)
 */
public final class UrlConverter {

    private static final Logger LOG = LogManager.getLogger(UrlConverter.class);

    private static final String SLASH_ITEM = "/item";

    private UrlConverter() {
        // empty constructor to prevent initialization
    }

    /**
     * Modify original edmPreview image urls from Mongo into API thumbnail urls
     * @param bean the fullbean to modify
     * @param thumbnailBaseUrl optional, alternative FQDN for the generated thumbnail urls. If null the
     *                         default FQDN is used (api.europeana.eu)
     */
    public static void setEdmPreview(FullBean bean, String thumbnailBaseUrl) {
        String edmPreviewUrl = null;

        // first try edmPreview, else edmObject and else edmIsShownBy
        if (StringUtils.isNotEmpty(bean.getEuropeanaAggregation().getEdmPreview())) {
            edmPreviewUrl = bean.getEuropeanaAggregation().getEdmPreview();
            LOG.debug("edmPreview found: {}", edmPreviewUrl);
        } else if (StringUtils.isNotEmpty(bean.getAggregations().get(0).getEdmObject())) {
            edmPreviewUrl = bean.getAggregations().get(0).getEdmObject();
            LOG.debug("No edmPreview, but edmObject found: {}", edmPreviewUrl);
        } else if (StringUtils.isNotEmpty(bean.getAggregations().get(0).getEdmIsShownBy())) {
            edmPreviewUrl = bean.getAggregations().get(0).getEdmIsShownBy();
            LOG.debug("No edmPreview or edmObject, but edmIsShownBy found: {}", edmPreviewUrl);
        } else {
            LOG.debug("No edmPreview, edmObject or edmIsShownBy found");
        }

        if (StringUtils.isNotEmpty(edmPreviewUrl)) {
            UrlBuilder urlBuilder = EuropeanaUrlBuilder.getThumbnailUrl(edmPreviewUrl, bean.getType());
            if (StringUtils.isNotEmpty(thumbnailBaseUrl)) {
                urlBuilder.setDomain(thumbnailBaseUrl);
            }
            bean.getEuropeanaAggregation().setEdmPreview(urlBuilder.toString());
        }
    }

    /**
     * Set a proper landing page value
     * @param bean the fullbean to modify
     * @param portalBaseUrl optional, alternative FQDN for the generated portal url. If null the
     *                      default FQDN is used (api.europeana.eu)
     */
    public static void setEdmLandingPage(FullBean bean, String portalBaseUrl) {
        UrlBuilder urlBuilder = EuropeanaUrlBuilder.getRecordPortalUrl(bean.getAbout());
        if (StringUtils.isNotEmpty(portalBaseUrl)) {
            urlBuilder.setDomain(portalBaseUrl);
        }
        bean.getEuropeanaAggregation().setEdmLandingPage(urlBuilder.toString());
    }

    /**
     * Make sure the ProvideCHO, AggregatedCHO and ProxyFor values start with '/item' (this was removed for records
     * ingested with Metis system, see also EA-1257)
     * @param bean the fullbean to modify
     */
    public static void addSlashItem(FullBean bean) {
        // ProvidedCHO
        List<ProvidedCHO> items = (List<ProvidedCHO>) bean.getProvidedCHOs();
        for (ProvidedCHO item : items) {
            if (!item.getAbout().toLowerCase(Locale.getDefault()).startsWith(SLASH_ITEM)) {
                item.setAbout(SLASH_ITEM + item.getAbout());
            }
        }
        // AggregatedCHO
        for (Aggregation aggr : bean.getAggregations()) {
            if (!aggr.getAggregatedCHO().toLowerCase(Locale.getDefault()).startsWith(SLASH_ITEM)) {
                aggr.setAggregatedCHO(SLASH_ITEM + aggr.getAggregatedCHO());
            }
        }
        EuropeanaAggregation euAggr = bean.getEuropeanaAggregation();
        if (!euAggr.getAggregatedCHO().toLowerCase(Locale.getDefault()).startsWith(SLASH_ITEM)) {
            euAggr.setAggregatedCHO(SLASH_ITEM + euAggr.getAggregatedCHO());
        }
        // ProxyFor
        for (Proxy proxy: bean.getProxies()) {
            if (!proxy.getProxyFor().toLowerCase(Locale.getDefault()).startsWith(SLASH_ITEM)) {
                proxy.setProxyFor(SLASH_ITEM + proxy.getProxyFor());
            }
        }
    }
}
