package eu.europeana.corelib.search.service.logic;

/**
 * Extracts the pure tags from an audio resource and generates the fake tags.
 */
public class SoundTagExtractor {

    public static Integer getQualityCode(final Integer bitDepth, final Integer sampleRate, final String fileFormat) {
        if(bitDepth != null && sampleRate != null && bitDepth >= 16 && sampleRate >= 44100) {
            return 1;
        }

        if(fileFormat != null && (fileFormat.equalsIgnoreCase("alac") || fileFormat.equalsIgnoreCase("flac") ||
                fileFormat.equalsIgnoreCase("ape") || fileFormat.equalsIgnoreCase("shn") ||
                fileFormat.equalsIgnoreCase("wav") || fileFormat.equalsIgnoreCase("wma") ||
                fileFormat.equalsIgnoreCase("aiff") || fileFormat.equalsIgnoreCase("dsd"))) {
            return 1;
        }

        return 0;
    }

    public static Integer getQualityCode(final Boolean soundHQ) {
        if(soundHQ == null) {
            return 0;
        }

        if(soundHQ) {
            return 1;
        } else {
            return 0;
        }
    }

    public static Integer getDurationCode(Long duration) {
        if(duration == null) {
            return 0;
        }
        final Long temp = duration/60000;
        if(temp <= 0.5) {
            return 1;
        }
        if(temp <= 3) {
            return 2;
        }
        if(temp <= 6) {
            return 3;
        }

        return 4;
    }

    public static Integer getDurationCode(String duration) {
        if(duration == null) {
            return 0;
        }

        if(duration.equals("very_short")) {
            return 1;
        }
        if(duration.equals("short")) {
            return 2;
        }
        if(duration.equals("medium")) {
            return 3;
        }
        if(duration.equals("long")) {
            return 4;
        }

        return 0;
    }
}
