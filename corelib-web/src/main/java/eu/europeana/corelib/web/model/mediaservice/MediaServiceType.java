package eu.europeana.corelib.web.model.mediaservice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MediaServiceType {

	SOUNDCLOUD("^urn:soundcloud:(.*?)$", SoundCloud.class),
	VIMEO("^urn:vimeo:(.*?)$", Vimeo.class),
	YOUTUBE("^urn:youtube:(.*?)$", Youtube.class),
	AUDIOBOO("^urn:audioboo:(.*?)$", AudioBoo.class),
	DAILYMOTION("^urn:dailymotion:(.*?)$", DailyMotion.class);

	private Pattern urnPattern;
	private String urnPatternString;
	private Class<? extends MediaService> clazz;

	private MediaServiceFactory factory = MediaServiceFactory.getFactory();

	private MediaServiceType(String urnPatternString, Class<? extends MediaService> clazz) {
		this.urnPattern = Pattern.compile(urnPatternString);
		this.clazz = clazz;
	}

	public Pattern getUrnPattern() {
		return urnPattern;
	}

	public String getUrnPatternString() {
		return urnPatternString;
	}

	public static MediaService findInstance(String urn) {
		for (MediaServiceType service : MediaServiceType.values()) {
			MediaService mediaService = service.getInstance(urn);
			if (mediaService != null) {
				return mediaService;
			}
		}
		return null;
	}

	public MediaService getInstance(String urn) {
		if (clazz != null) {
			Matcher matcher = urnPattern.matcher(urn);
			if (matcher.find()) {
				return factory.create(clazz, matcher.group(1));
			}
		}
		return null;
	}
}
