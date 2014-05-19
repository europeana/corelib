package eu.europeana.corelib.web.service.impl;

import javax.annotation.Resource;

import com.google.gson.Gson;

import eu.europeana.corelib.definitions.ApplicationContextContainer;
import eu.europeana.corelib.logging.Log;
import eu.europeana.corelib.logging.Logger;
import eu.europeana.corelib.web.model.ApiResult;
import eu.europeana.corelib.web.service.MicrosoftTranslatorService;
import eu.europeana.corelib.web.support.Configuration;
import eu.europeana.corelib.web.utils.UrlBuilder;

public class MicrosoftTranslatorServiceImpl extends JsonApiServiceImpl implements
		MicrosoftTranslatorService {

	@Log
	Logger log;

	@Resource
	private Configuration config;

	private static final String TRANSLATOR_URL = "http://api.microsofttranslator.com/V2/Ajax.svc/Translate";

	public static MicrosoftTranslatorService getBeanInstance() {
		return ApplicationContextContainer.getBean(MicrosoftTranslatorServiceImpl.class);
	}

	@Override
	public String translate(String text, String languageCode) {
		String url = buildTranslateUrl(text, languageCode);
		log.info("url: " + url);
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
