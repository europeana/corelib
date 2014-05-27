package eu.europeana.corelib.web.model.mediaservice;

public class AudioBoo extends MediaService {

	protected String urlPattern = "http://audioboo.fm/boos/%s";
	protected String embeddedUrlPattern = "https://audioboo.fm/boos/%s/embed/v2?eid=AQAAAGBQf1PyGgwA";
	protected String embeddedHtmlPattern = "<div class=\"ab-player\" data-boourl=\"https://audioboo.fm/boos/%s/embed/v2?eid=AQAAAGBQf1PyGgwA\" data-boowidth=\"100%%\" data-maxheight=\"150\" data-iframestyle=\"background-color:transparent; display:block; box-shadow:0 0 1px 1px rgba(0, 0, 0, 0.15); min-width:349px; max-width:700px;\" style=\"background-color:transparent;\"><a href=\"http://audioboo.fm/boos/\">%s on Audioboo</a></div><script type=\"text/javascript\">(function() { var po = document.createElement(\"script\"); po.type = \"text/javascript\"; po.async = true; po.src = \"https://d15mj6e6qmt1na.cloudfront.net/cdn/embed.js\"; var s = document.getElementsByTagName(\"script\")[0]; s.parentNode.insertBefore(po, s); })();</script>";

	public AudioBoo(String id) {
		super(id);
	}

	@Override
	public String getEmbededHtml() {
		return String.format(getEmbeddedHtmlPattern(), id, id);
	}

	protected String getUrlPattern() {;
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
		AudioBoo other = (AudioBoo) obj;
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
