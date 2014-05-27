package eu.europeana.corelib.web.model.mediaservice;

import eu.europeana.corelib.definitions.solr.DocType;

public abstract class MediaService {

	protected String id;
	protected String urlPattern;
	protected String embeddedUrlPattern;
	protected String embeddedHtmlPattern;

	public MediaService(String id) {
		this.id = id;
	}

	public String getUrl() {
		return String.format(getUrlPattern(), id);
	}

	public String getEmbeddedUrl() {
		return String.format(getEmbeddedUrlPattern(), id);
	}

	public String getEmbeddedHtml() {
		return String.format(getEmbeddedHtmlPattern(), id);
	}

	public abstract DocType getDataType();

	protected abstract String getUrlPattern();

	protected abstract String getEmbeddedUrlPattern();

	protected abstract String getEmbeddedHtmlPattern();
}
