package eu.europeana.corelib.definitions.model.facets.logic;

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
        return soundHQ ? 1 : 0;
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

        if("very_short".equals(duration)) {
            return 1;
        }
        if("short".equals(duration)) {
            return 2;
        }
        if("medium".equals(duration)) {
            return 3;
        }
        if("long".equals(duration)) {
            return 4;
        }

        return 0;
    }
}
