package eu.europeana.corelib.definitions.edm.model.metainfo;

public interface VideoMetaInfo {

    Integer getWidth();

    Integer getHeight();

    Long getDuration();

    String getMimeType();

    Double getFrameRate();

    Long getFileSize();

    String getCodec();

    String getResolution();

    Integer getBitRate();

}