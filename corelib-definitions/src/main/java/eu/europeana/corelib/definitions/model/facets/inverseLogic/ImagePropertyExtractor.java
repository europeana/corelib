package eu.europeana.corelib.definitions.model.facets.inverseLogic;

import eu.europeana.crf_faketags.extractor.ImageTagExtractor;
import eu.europeana.crf_faketags.extractor.TagEncoding;
import org.apache.commons.lang.StringUtils;

public class ImagePropertyExtractor {

    public static String getSize(int tag) {
        final Integer sizeCode = TagEncoding.IMAGE_SIZE.extractValue(tag);

        switch (sizeCode) {
            case 1:
                return "small";
            case 2:
                return "medium";
            case 3:
                return "large";
            case 4:
                return "extra_large";

            default:
                return "";
        }
    }

    public static String getColorSpace(int tag) {
        final Integer colorSpaceCode = TagEncoding.IMAGE_COLOURSPACE.extractValue(tag);

        if (2 == colorSpaceCode) {
            return "false";
        } else if (1 == colorSpaceCode || 3 == colorSpaceCode) {
            return "true";
        }

        return "";
    }

    public static String getAspectRatio(int tag) {
        final Integer aspectRatioCode = TagEncoding.IMAGE_ASPECTRATIO.extractValue(tag);

        switch (aspectRatioCode) {
            case 1:
                return "Landscape";
            case 2:
                return "Portrait";

            default:
                return "";
        }
    }

    public static String getColor(int tag) {
        final Integer colorCode = TagEncoding.IMAGE_COLOUR.extractValue(tag);
        return StringUtils.defaultIfEmpty(ImageTagExtractor.hexColors.inverse().get(colorCode), "");
    }
}
