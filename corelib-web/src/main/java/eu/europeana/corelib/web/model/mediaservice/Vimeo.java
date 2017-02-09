package eu.europeana.corelib.web.model.mediaservice;

import eu.europeana.corelib.definitions.solr.DocType;

public class Vimeo extends MediaService {

//	protected String urlPattern = "http://vimeo.com/%s";
//	protected String embeddedUrlPattern = "http://player.vimeo.com/video/%s?title=0&byline=0&portrait=0";
//	protected String embeddedHtmlPattern = "<iframe src=\"http://player.vimeo.com/video/%s?title=0&amp;byline=0&amp;portrait=0\" width=\"WIDTH\" height=\"HEIGHT\" frameborder=\"0\" webkitallowfullscreen mozallowfullscreen allowfullscreen></iframe> <p><a href=\"http://vimeo.com/%s\">%s</a> on <a href=\"http://vimeo.com\">Vimeo</a>.</p>";
//
//	public Vimeo(String id) {
//		super(id);
//	}
//
//	@Override
//	public String getEmbeddedHtml() {
//		return String.format(getEmbeddedHtmlPattern(), id, id, id);
//	}
//
//	@Override
//	public DocType getDataType() {
//		return DocType.VIDEO;
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
//		Vimeo other = (Vimeo) obj;
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
//		if (urlPattern == null) {
//			if (other.urlPattern != null)
//				return false;
//		} else if (!urlPattern.equals(other.urlPattern))
//			return false;
//		return true;
//	}
}
