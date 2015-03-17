package eu.europeana.corelib.search.service.inverseLogic;

public class VideoPropertyExtractor {

    public static String getQuality(Integer tag) {
        final Integer qualityCode = (tag<<7)>>20;

        switch (qualityCode) {
            case 1: return "HD";

            default: return "";
        }
    }

    public static String getDuration(Integer tag) {
        final Integer durationCode = (tag<<7)>>17;

        switch (durationCode) {
            case 1: return "short";
            case 2: return "medium";
            case 3: return "long";

            default: return "";
        }
    }
}
