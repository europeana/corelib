package eu.europeana.corelib.definitions.edm.model.metainfo;

public interface WebResourceMetaInfo {

    String getId();

    ImageMetaInfo getImageMetaInfo();

    AudioMetaInfo getAudioMetaInfo();

    VideoMetaInfo getVideoMetaInfo();

    TextMetaInfo getTextMetaInfo();

}