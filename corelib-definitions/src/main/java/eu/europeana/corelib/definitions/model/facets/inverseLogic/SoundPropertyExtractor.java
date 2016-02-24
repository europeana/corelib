package eu.europeana.corelib.definitions.model.facets.inverseLogic;

import eu.europeana.crf_faketags.extractor.TagEncoding;

public class SoundPropertyExtractor {

    public static String getQuality(Integer tag) {
        final Integer qualityCode = TagEncoding.SOUND_QUALITY.extractValue(tag);

        if (1 == qualityCode) {
            return "true";
        }

        return "";
    }

    public static String getDuration(Integer tag) {
        final Integer durationCode = TagEncoding.SOUND_DURATION.extractValue(tag);

        switch (durationCode) {
            case 1:
                return "very_short";
            case 2:
                return "short";
            case 3:
                return "medium";
            case 4:
                return "long";

            default:
                return "";
        }
    }
}
