package eu.europeana.corelib.web.model.mediaservice;

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

	public String getEmbededUrl() {
		return String.format(getEmbeddedUrlPattern(), id);
	}

	public String getEmbededHtml() {
		return String.format(getEmbeddedHtmlPattern(), id);
	}

	protected abstract String getUrlPattern();

	protected abstract String getEmbeddedUrlPattern();

	protected abstract String getEmbeddedHtmlPattern();
}
