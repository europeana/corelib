package eu.europeana.corelib.web.model.mediaservice;

import eu.europeana.corelib.definitions.solr.DocType;

public class Youtube extends MediaService {

	protected String urlPattern = "http://youtube.com/watch?v=%s";
	protected String embeddedUrlPattern = "http://youtube.com/embed/%s";
	protected String embeddedHtmlPattern = "<iframe width=\"420\" height=\"315\" src=\"http://www.youtube.com/embed/%s\" frameborder=\"0\" allowfullscreen></iframe>";

	public Youtube(String id) {
		super(id);
	}

	@Override
	public DocType getDataType() {
		return DocType.VIDEO;
	}

	protected String getUrlPattern() {
		return urlPattern;
	}

	protected String getEmbeddedUrlPattern() {
		return embeddedUrlPattern;
	}

	protected String getEmbeddedHtmlPattern() {
		return embeddedHtmlPattern;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((embeddedHtmlPattern == null) ? 0 : embeddedHtmlPattern
						.hashCode());
		result = prime
				* result
				+ ((embeddedUrlPattern == null) ? 0 : embeddedUrlPattern
						.hashCode());
		result = prime * result
				+ ((urlPattern == null) ? 0 : urlPattern.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Youtube other = (Youtube) obj;
		if (embeddedHtmlPattern == null) {
			if (other.embeddedHtmlPattern != null)
				return false;
		} else if (!embeddedHtmlPattern.equals(other.embeddedHtmlPattern))
			return false;
		if (embeddedUrlPattern == null) {
			if (other.embeddedUrlPattern != null)
				return false;
		} else if (!embeddedUrlPattern.equals(other.embeddedUrlPattern))
			return false;
		if (urlPattern == null) {
			if (other.urlPattern != null)
				return false;
		} else if (!urlPattern.equals(other.urlPattern))
			return false;
		return true;
	}
}
