package eu.europeana.corelib.search.service.impl;

import eu.europeana.corelib.definitions.model.facets.inverseLogic.*;

public class FacetLabelExtractor {

    public static String getFacetLabel(Integer tag) {
        final MediaTypeEncoding mediaType = CommonPropertyExtractor.getType(tag);
        final String  mimeType  = CommonPropertyExtractor.getMimeType(tag);

        if (null != mimeType && !mimeType.trim().isEmpty()) {
            return mimeType;
        }

        String label;
        switch (mediaType) {
            case IMAGE:
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
            case SOUND:
                label = SoundPropertyExtractor.getDuration(tag);
                if(!label.equals("")) {
                    return label;
                }
                label = SoundPropertyExtractor.getQuality(tag);
                return label;
            case VIDEO:
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
