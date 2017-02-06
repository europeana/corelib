/*
 * Copyright 2007-2012 The Europeana Foundation
 *
 *  Licenced under the EUPL, Version 1.1 (the "Licence") and subsequent versions as approved 
 *  by the European Commission;
 *  You may not use this work except in compliance with the Licence.
 *  
 *  You may obtain a copy of the Licence at:
 *  http://joinup.ec.europa.eu/software/page/eupl
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under 
 *  the Licence is distributed on an "AS IS" basis, without warranties or conditions of 
 *  any kind, either express or implied.
 *  See the Licence for the specific language governing permissions and limitations under 
 *  the Licence.
 */

package eu.europeana.corelib.web.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import eu.europeana.corelib.definitions.exception.ProblemType;
import eu.europeana.corelib.web.exception.WebConfigurationException;
import eu.europeana.corelib.web.model.PageData;
import eu.europeana.corelib.web.support.Configuration;

/**
 * This interceptor used by Portal catches all request after processing the controller and populates all default
 * configuration values, which are used on every page.
 * 
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public class ConfigInterceptor extends HandlerInterceptorAdapter {

	private final Logger log = Logger.getLogger(ConfigInterceptor.class);
	
	@Resource
	private Configuration config;

	@Value("#{europeanaProperties['portal.indexable']}")
	private boolean indexable;

	@Value("#{europeanaProperties['portal.google.analytics.id']}")
	private String portalGoogleAnalyticsId;

	@Value("#{europeanaProperties['portal.google.maps.key']}")
	private String portalGoogleMapsId;

	@Value("#{europeanaProperties['portal.addthis.pubid']}")
	private String portalAddthisId;

	@Value("#{europeanaProperties['portal.sharethis.pubid']}")
	private String portalSharethisId;
	
	@Value("#{europeanaProperties['portal.facebook.appid']}")
	private String portalFacebookId;

	@Value("#{europeanaProperties['portal.bing.translate.key']}")
	private String portalBingTranslateId;

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView)
			throws Exception {
		super.postHandle(request, response, o, modelAndView);
		if (modelAndView != null && !modelAndView.getViewName().startsWith("redirect:")
				&& modelAndView.getModel().containsKey(PageData.PARAM_MODEL)) {
			PageData model = (PageData) modelAndView.getModel().get(PageData.PARAM_MODEL);
			
			StringBuilder currentUrl = new StringBuilder(config.getPortalServer());

			currentUrl.append(request.getRequestURI());
			
			
			if (request.getQueryString() != null) {
				currentUrl.append("?").append(request.getQueryString());
			}
			model.setCurrentUrl(currentUrl.toString().replace("portal/portal/", "portal/"));
			
			// BOOLEANS
			model.setDebug(config.getDebugMode());
			model.setIndexable(indexable);
			// is minify=true is set, force minify anyway, ignoring debug settings!
			if (request.getParameterMap().containsKey("minify")) {
				model.setMinify(StringUtils.equals("true", request.getParameter("minify")));
			}

			// MANDATORY VALUES
			model.setPortalServer(checkMandatoryValue(config.getPortalServer(), "portal.server"));
			model.setMetaCanonicalUrl(config.getCannonicalPortalServer());
			//model.setPortalName(checkMandatoryValue(config.getPortalName(), "portal.name"));
			model.setCacheUrl(checkMandatoryValue(config.getImageCacheUrl(), "imageCacheUrl"));

			// OPTIONALS, TRIMMED TO EMPTY STRING (preventing nullpointers)
			model.setGoogleAnalyticsId(StringUtils.trimToEmpty(portalGoogleAnalyticsId));
			model.setGoogleMapsId(StringUtils.trimToEmpty(portalGoogleMapsId));
			model.setAddThisId(StringUtils.trimToEmpty(portalAddthisId));
			model.setShareThisId(StringUtils.trimToEmpty(portalSharethisId));
			model.setFacebookId(StringUtils.trimToEmpty(portalFacebookId));
			model.setBingTranslateId(StringUtils.trimToEmpty(portalBingTranslateId));
		}
	}

	/**
	 * Checks if the property has a value, empty string is not allowed
	 * 
	 * @param value
	 *            The value to check
	 * @return A trimmed version of the given value
	 * @throws WebConfigurationException
	 *             Exception is thrown if the given value does not contain a value.
	 */
	private String checkMandatoryValue(String value, String name) throws WebConfigurationException {
		if (StringUtils.isBlank(value)) {
			ProblemType problem = ProblemType.INVALIDARGUMENTS;
			String message = null;
			if (StringUtils.isNotBlank(name)) {
				message = "Inexisting property: " + name;
				log.error(message);
			}
			throw new WebConfigurationException(problem, message);
		}
		return StringUtils.trim(value);
	}
}
