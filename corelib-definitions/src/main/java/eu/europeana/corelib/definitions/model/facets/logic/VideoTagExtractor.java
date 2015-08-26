package eu.europeana.corelib.definitions.model.facets.logic;

/**
 * Extracts the pure tags from a video resource and generates the fake tags.
 */
public class VideoTagExtractor {

    public static Integer getQualityCode(Integer height) {
        if(height == null || height<576) {
            return 0;
        }

        return 1;
    }

    public static Integer getQualityCode(Boolean videoQuality) {
        if(videoQuality == null || !videoQuality) {
            return 0;
        }

        return 1;
    }

    public static Integer getDurationCode(Long duration) {
        if(duration == null) {
            return 0;
        }
        final Long temp = duration/60000;
        if(temp <= 4) {
            return 1;
        }
        if(temp <= 20) {
            return 2;
        }

        return 3;
    }

    public static Integer getDurationCode(String duration) {
        if(duration == null || duration.equals("")) {
            return 0;
        }

        if(duration.equals("short")) {
            return 1;
        }
        if(duration.equals("medium")) {
            return 2;
        }
        if(duration.equals("long")) {
            return 3;
        }

        return 0;
    }

}
