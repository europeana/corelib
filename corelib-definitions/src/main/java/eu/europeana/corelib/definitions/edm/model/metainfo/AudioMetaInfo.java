package eu.europeana.corelib.definitions.edm.model.metainfo;

public interface AudioMetaInfo {

    Integer getSampleRate();

    Integer getBitRate();

    Long getDuration();

    String getMimeType();

    String getFileFormat();

    Long getFileSize();

    Integer getChannels();

    Integer getBitDepth();

}
