package eu.europeana.corelib.definitions.edm.model.metainfo;

public interface ImageMetaInfo {

    Integer getWidth();

    Integer getHeight();

    String getMimeType();

    String getFileFormat();

    String getColorSpace();

    Long getFileSize();

    String[] getColorPalette();

    ImageOrientation getOrientation();
}