package eu.europeana.corelib.web.model.mediaservice;

public class DailyMotion extends MediaService {

	protected String urlPattern = "http://www.dailymotion.com/video/%s";
	protected String embeddedUrlPattern = "http://www.dailymotion.com/embed/video/%s";
	protected String embeddedHtmlPattern = "<iframe frameborder=\"0\" width=\"480\" height=\"270\" src=\"http://www.dailymotion.com/embed/video/%s\" allowfullscreen></iframe><br /><a href=\"http://www.dailymotion.com/video/%s\" target=\"_blank\">%s</a>";

	public DailyMotion(String id) {
		super(id);
	}

	@Override
	public String getEmbeddedHtml() {
		return String.format(getEmbeddedHtmlPattern(), id, id, id);
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
		DailyMotion other = (DailyMotion) obj;
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
