package eu.europeana.corelib.definitions.edm.model.metainfo;

public interface WebResourceMetaInfo {
    public String getId();
    public ImageMetaInfo getImageMetaInfo();
    public AudioMetaInfo getAudioMetaInfo();
    public VideoMetaInfo getVideoMetaInfo();
    public TextMetaInfo getTextMetaInfo();
}