package eu.europeana.corelib.definitions.model.facets.inverseLogic;

import eu.europeana.corelib.definitions.model.facets.logic.CommonTagExtractor;

import java.util.HashMap;
import java.util.Map;

public class CommonPropertyExtractor {

    public static MediaTypeEncoding getType(Integer tag) {
        return MediaTypeEncoding.valueOf(TagEncoding.MEDIA_TYPE.extractValue(tag));
    }

    public static String getMimeType(Integer tag) {
        return CommonTagExtractor.mimeTypes.inverse().get(TagEncoding.MIME_TYPE.extractValue(tag));
    }
}
