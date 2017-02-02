package eu.europeana.corelib.web.model.mediaservice;

import javax.annotation.Resource;

import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.web.model.mediaservice.soundcloud.Track;
import eu.europeana.corelib.web.service.SoundCloudApiService;

public class SoundCloud extends MediaService {

//	@Resource
//	private SoundCloudApiService service;
//
//	private String trackId;
//	private String path;
//
//	protected String urlPattern = "http://soundcloud.com/%s";
//	protected String embeddedUrlPattern = "https://w.soundcloud.com/player/?url=https%%3A//api.soundcloud.com/tracks/%s&amp;auto_play=false&amp;hide_related=false&amp;visual=true";
//	protected String embeddedHtmlPattern = "<iframe width=\"100%%\" height=\"450\" scrolling=\"no\" frameborder=\"no\" src=\"https://w.soundcloud.com/player/?url=https%%3A//api.soundcloud.com/tracks/%s&amp;auto_play=false&amp;hide_related=false&amp;visual=true\"></iframe>";
//
//	public SoundCloud(String id) {
//		super(id);
//		resolve();
//	}
//
//	public SoundCloud(String id, SoundCloudApiService service) {
//		super(id);
//		this.service = service;
//		resolve();
//	}
//
//	private void resolve() {
//		Track response = null;
//		if (id.contains("/")) {
//			response = service.resolvePath(id);
//		} else {
//			response = service.getTrackInfo(id);
//		}
//		path = response.getPath();
//		trackId = response.getId();
//	}
//
//	@Override
//	public String getUrl() {
//		return String.format(getUrlPattern(), path);
//	}
//
//	@Override
//	public String getEmbeddedUrl() {
//		return String.format(getEmbeddedUrlPattern(), trackId);
//	}
//
//	@Override
//	public String getEmbeddedHtml() {
//		return String.format(getEmbeddedHtmlPattern(), trackId);
//	}
//
//	@Override
//	public DocType getDataType() {
//		return DocType.SOUND;
//	}
//
//	protected String getUrlPattern() {
//		return urlPattern;
//	}
//
//	protected String getEmbeddedUrlPattern() {
//		return embeddedUrlPattern;
//	}
//
//	protected String getEmbeddedHtmlPattern() {
//		return embeddedHtmlPattern;
//	}
//
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime
//				* result
//				+ ((embeddedHtmlPattern == null) ? 0 : embeddedHtmlPattern
//						.hashCode());
//		result = prime
//				* result
//				+ ((embeddedUrlPattern == null) ? 0 : embeddedUrlPattern
//						.hashCode());
//		result = prime * result + ((path == null) ? 0 : path.hashCode());
//		result = prime * result + ((service == null) ? 0 : service.hashCode());
//		result = prime * result + ((trackId == null) ? 0 : trackId.hashCode());
//		result = prime * result
//				+ ((urlPattern == null) ? 0 : urlPattern.hashCode());
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		SoundCloud other = (SoundCloud) obj;
//		if (embeddedHtmlPattern == null) {
//			if (other.embeddedHtmlPattern != null)
//				return false;
//		} else if (!embeddedHtmlPattern.equals(other.embeddedHtmlPattern))
//			return false;
//		if (embeddedUrlPattern == null) {
//			if (other.embeddedUrlPattern != null)
//				return false;
//		} else if (!embeddedUrlPattern.equals(other.embeddedUrlPattern))
//			return false;
//		if (path == null) {
//			if (other.path != null)
//				return false;
//		} else if (!path.equals(other.path))
//			return false;
//		if (service == null) {
//			if (other.service != null)
//				return false;
//		} else if (!service.equals(other.service))
//			return false;
//		if (trackId == null) {
//			if (other.trackId != null)
//				return false;
//		} else if (!trackId.equals(other.trackId))
//			return false;
//		if (urlPattern == null) {
//			if (other.urlPattern != null)
//				return false;
//		} else if (!urlPattern.equals(other.urlPattern))
//			return false;
//		return true;
//	}
}
