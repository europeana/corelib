package eu.europeana.corelib.definitions.edm.model.metainfo;

public interface AudioMetaInfo {
    public Integer getSampleRate();
    public Integer getBitRate();
    public Long getDuration();
    public String getMimeType();
    public String getFileFormat();
    public Long getFileSize();
    public Integer getChannels();
    public Integer getBitDepth() ;
}
