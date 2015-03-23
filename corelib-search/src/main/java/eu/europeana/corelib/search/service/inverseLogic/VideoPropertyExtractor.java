package eu.europeana.corelib.search.service.inverseLogic;

public class VideoPropertyExtractor {
    public static final Integer mask = (1 << 25) - 1;

    public static String getQuality(Integer tag) {
        final Integer qualityCode = (tag & mask)>>13;

        switch (qualityCode) {
            case 1: return "HD";

            default: return "";
        }
    }

    public static String getDuration(Integer tag) {
        final Integer durationCode = (tag & mask)>>10;

        switch (durationCode) {
            case 1: return "short";
            case 2: return "medium";
            case 3: return "long";

            default: return "";
        }
    }
}
