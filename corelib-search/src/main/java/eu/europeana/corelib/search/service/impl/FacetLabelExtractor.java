package eu.europeana.corelib.search.service.impl;

import eu.europeana.corelib.search.service.inverseLogic.CommonPropertyExtractor;
import eu.europeana.corelib.search.service.inverseLogic.ImagePropertyExtractor;
import eu.europeana.corelib.search.service.inverseLogic.SoundPropertyExtractor;
import eu.europeana.corelib.search.service.inverseLogic.VideoPropertyExtractor;

public class FacetLabelExtractor {

    public static String getFacetLabel(Integer tag) {
        final Integer mediaType = CommonPropertyExtractor.getType(tag);

        String label;
        switch (mediaType) {
            case 1:
                label = ImagePropertyExtractor.getAspectRatio(tag);
                if(!label.equals("")) {
                    return label;
                }
                label = ImagePropertyExtractor.getColor(tag);
                if(!label.equals("")) {
                    return label;
                }
                label = ImagePropertyExtractor.getColorSpace(tag);
                if(!label.equals("")) {
                    return label;
                }
                label = ImagePropertyExtractor.getSize(tag);
                return label;
            case 2:
                label = SoundPropertyExtractor.getDuration(tag);
                if(!label.equals("")) {
                    return label;
                }
                label = SoundPropertyExtractor.getQuality(tag);
                return label;
            case 3:
                label = VideoPropertyExtractor.getDuration(tag);
                if(!label.equals("")) {
                    return label;
                }
                label = VideoPropertyExtractor.getQuality(tag);
                return label;

            default: return "";
        }
    }
}
