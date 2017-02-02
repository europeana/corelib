package eu.europeana.corelib.web.service.impl;

import javax.annotation.Resource;

import com.google.gson.Gson;

import eu.europeana.corelib.web.model.ApiResult;
import eu.europeana.corelib.web.model.mediaservice.soundcloud.Track;
import eu.europeana.corelib.web.service.SoundCloudApiService;
import eu.europeana.corelib.web.support.Configuration;

public class SoundCloudApiServiceImpl extends JsonApiServiceImpl implements SoundCloudApiService {

//	@Resource
//	private Configuration config;
//
//	private static final String resolvePathUrl = "http://api.soundcloud.com/resolve.json?url=http://soundcloud.com/%s&client_id=%s";
//	private static final String trackInfoUrl = "http://api.soundcloud.com/tracks/%s.json?client_id=%s";
//
//	@Override
//	public Track resolvePath(String path) {
//		String apiUrl = buildResolverUrl(path);
//		ApiResult apiResponse = getJsonResponse(apiUrl);
//		return parseResolverJson(apiResponse);
//	}
//
//	@Override
//	public Track getTrackInfo(String id) {
//		String apiUrl = buildTrackInfoUrl(id);
//		ApiResult apiResponse = getJsonResponse(apiUrl);
//		return parseResolverJson(apiResponse);
//	}
//
//	private String buildResolverUrl(String path) {
//		return String.format(resolvePathUrl, path, config.getSoundcloudClientID());
//	}
//
//	private String buildTrackInfoUrl(String id) {
//		return String.format(trackInfoUrl, id, config.getSoundcloudClientID());
//	}
//
//	private Track parseResolverJson(ApiResult apiResponse) {
//		Gson gson = new Gson();
//		return gson.fromJson(apiResponse.getContent(), Track.class);
//	}
}
