package eu.europeana.corelib.edm.model.metainfo;

import org.mongodb.morphia.annotations.Entity;
import eu.europeana.corelib.definitions.edm.model.metainfo.ImageOrientation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * A class which contains information about an IMAGE document
 */
@JsonInclude(Include.NON_EMPTY)
@Entity("ImageMetaInfo")
@JsonIgnoreProperties({"colorPalette"})
public class ImageMetaInfoImpl implements eu.europeana.corelib.definitions.edm.model.metainfo.ImageMetaInfo {

    /**
     * The width of image in pixels.
     */
    private Integer width;

    /**
     * The height of image in pixels.
     */
    private Integer height;

    /**
     * An Internet media type is a standard identifier used on the
     * Internet to indicate the type of data that a file contains.
     */
    private String mimeType;

    /**
     * A file format is a standard way that information is encoded for storage in a computer file.
     */
    private String fileFormat;

    /**
     * A color space is a specific organization of colors.
     */
    private String colorSpace;

    /**
     * The size of the file in bytes
     */
    private Long fileSize;

    /**
     * An array with up to 6 colors (in the HEX code used for web applications)
     */
    private String[] colorPalette;

    /**
     * The orientation of the image (LANDSCAPE or PORTRAIT)
     */
    private ImageOrientation orientation;

    public ImageMetaInfoImpl() {
        this.width = null;
        this.height = null;
        this.mimeType = null;
        this.fileFormat = null;
        this.colorSpace = null;
        this.fileSize = null;
        this.colorPalette = null;
        this.orientation = null;
    }

    public ImageMetaInfoImpl(final Integer width, final Integer height,
                             final String mimeType, final String fileFormat, final String colorSpace,
                             final Long fileSize, final String[] colorPalette, final ImageOrientation orientation) {
        this.width = width;
        this.height = height;
        this.mimeType = mimeType;
        this.fileFormat = fileFormat;
        this.colorSpace = colorSpace;
        this.fileSize = fileSize;
        this.colorPalette = colorPalette;
        this.orientation = orientation;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public String getColorSpace() {
        return colorSpace;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public String[] getColorPalette() {
        return colorPalette;
    }

    public ImageOrientation getOrientation() {
        return orientation;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public void setColorSpace(String colorSpace) {
        this.colorSpace = colorSpace;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public void setColorPalette(String[] colorPalette) {
        this.colorPalette = colorPalette;
    }

    public void setOrientation(ImageOrientation orientation) {
        this.orientation = orientation;
    }
}
