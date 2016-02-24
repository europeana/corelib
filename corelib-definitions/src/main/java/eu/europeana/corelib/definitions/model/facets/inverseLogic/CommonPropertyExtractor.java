package eu.europeana.corelib.definitions.model.facets.inverseLogic;


import eu.europeana.crf_faketags.extractor.CommonTagExtractor;
import eu.europeana.crf_faketags.extractor.MediaTypeEncoding;
import eu.europeana.crf_faketags.extractor.TagEncoding;

public class CommonPropertyExtractor {

    public static MediaTypeEncoding getType(Integer tag) {
        return MediaTypeEncoding.valueOf(TagEncoding.MEDIA_TYPE.extractValue(tag));
    }

    public static String getMimeType(Integer tag) {
        return CommonTagExtractor.mimeTypes.inverse().get(TagEncoding.MIME_TYPE.extractValue(tag));
    }
}
