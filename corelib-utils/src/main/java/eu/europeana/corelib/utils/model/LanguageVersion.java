package eu.europeana.corelib.utils.model;

/**
 * A simple class for holding information about a text and its language code.
 * 
 * @author Peter.Kiraly@europeana.eu
 */
public class LanguageVersion implements Comparable<LanguageVersion> {

	protected String text;
	protected String languageCode;

	public LanguageVersion(String text, String languageCode) {
		this.text = text;
		this.languageCode = languageCode;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((languageCode == null) ? 0 : languageCode.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		LanguageVersion other = (LanguageVersion) obj;
		if (languageCode == null) {
			if (other.languageCode != null)
				return false;
		} else if (!languageCode.equals(other.languageCode))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

	@Override
	public int compareTo(LanguageVersion other) {
		if (this.getLanguageCode().compareTo(other.getLanguageCode()) != 0) {
			return this.getLanguageCode().compareTo(other.getLanguageCode());
		} else {
			return this.getText().compareTo(other.getText());
		}
	}

	@Override
	public String toString() {
		return "LanguageVersion [text=" + text + ", languageCode="
				+ languageCode + "]";
	}
}
