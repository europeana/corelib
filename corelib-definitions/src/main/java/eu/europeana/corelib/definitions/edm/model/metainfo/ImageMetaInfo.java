package eu.europeana.corelib.definitions.edm.model.metainfo;

public interface ImageMetaInfo {
    public Integer getWidth();

    public Integer getHeight();

    public String getMimeType();

    public String getFileFormat();

    public String getColorSpace();

    public Long getFileSize();

    public String[] getColorPalette();

    public ImageOrientation getOrientation();
}