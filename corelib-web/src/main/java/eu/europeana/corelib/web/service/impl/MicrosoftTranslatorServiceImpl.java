package eu.europeana.corelib.web.service.impl;

import javax.annotation.Resource;

import com.google.gson.Gson;

import eu.europeana.corelib.definitions.ApplicationContextContainer;
import eu.europeana.corelib.web.model.ApiResult;
import eu.europeana.corelib.web.service.MicrosoftTranslatorService;
import eu.europeana.corelib.web.support.Configuration;
import eu.europeana.corelib.web.utils.UrlBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @deprecated since 2017-09-22
 */
@Deprecated
public class MicrosoftTranslatorServiceImpl extends JsonApiServiceImpl implements
		MicrosoftTranslatorService {

	private static final Logger LOG = LogManager.getLogger(MicrosoftTranslatorServiceImpl.class);

	@Resource
	private Configuration config;

	private static final String TRANSLATOR_URL = "http://api.microsofttranslator.com/V2/Ajax.svc/Translate";

	public static MicrosoftTranslatorService getBeanInstance() {
		return ApplicationContextContainer.getBean(MicrosoftTranslatorServiceImpl.class);
	}

	@Override
	public String translate(String text, String languageCode) {
		String url = buildTranslateUrl(text, languageCode);
		LOG.debug("Calling translate url: {} ", url);
		ApiResult result = getJsonResponse(url);
		return parseJson(result);
	}

	private String parseJson(ApiResult result) {
		Gson gson = new Gson();
		return gson.fromJson(result.getContent(), String.class);
	}

	private String buildTranslateUrl(String text, String languageCode) {
		UrlBuilder url = new UrlBuilder(TRANSLATOR_URL);
		url.addParam("appId", config.getBingTranslateId());
		url.addParam("text", text);
		url.addParam("to", languageCode);
		return url.toString();
	}
}
