package eu.europeana.corelib.definitions.edm.model.metainfo;

public interface VideoMetaInfo {
    public Integer getWidth();
    public Integer getHeight();
    public Long getDuration();
    public String getMimeType();
    public Double getFrameRate();
    public Long getFileSize();
    public String getCodec();
    public String getResolution();
    public Integer getBitRate();
}