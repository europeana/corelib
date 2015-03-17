package eu.europeana.corelib.search.service.inverseLogic;

public class SoundPropertyExtractor {

    public static String getQuality(Integer tag) {
        final Integer qualityCode = (tag<<7)>>20;

        switch (qualityCode) {
            case 1: return "HQ";

            default: return "";
        }
    }

    public static String getDuration(Integer tag) {
        final Integer durationCode = (tag<<7)>>17;

        switch (durationCode) {
            case 1: return "very_short";
            case 2: return "short";
            case 3: return "medium";
            case 4: return "long";

            default: return "";
        }
    }
}
