package eu.europeana.corelib.definitions.model.facets.inverseLogic;

import eu.europeana.crf_faketags.extractor.TagEncoding;

public class VideoPropertyExtractor {

    public static String getQuality(Integer tag) {
        final Integer qualityCode = TagEncoding.VIDEO_QUALITY.extractValue(tag);

        if (1 == qualityCode) {
            return "true";
        }

        return "";
    }

    public static String getDuration(Integer tag) {
        final Integer durationCode = TagEncoding.VIDEO_DURATION.extractValue(tag);

        switch (durationCode) {
            case 1:
                return "short";
            case 2:
                return "medium";
            case 3:
                return "long";

            default:
                return "";
        }
    }
}
