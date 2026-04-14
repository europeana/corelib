package eu.europeana.corelib.edm.model.metainfo;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.morphia.annotations.Embedded;

/**
 * The type Three d meta info.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Embedded(useDiscriminator = false)
public class ThreeDMetaInfoImpl implements eu.europeana.corelib.definitions.edm.model.metainfo.ThreeDMetaInfo{

    /**
     * An Internet media type is a standard identifier used on the
     * Internet to indicate the type of data that a file contains.
     */
    private String mimeType;

    /**
     * The size of the file in bytes.
     */
    private Long fileSize;

    /**
     * The point count
     */
    private Long pointCount;

    /**
     * The polygon count
     */
    private Long polygonCount;

    /**
     * The vertex count
     */
    private Long vertexCount;

    /**
     * The Gaussian count
     */
    private Long gaussianCount;

    /**
     * Instantiates a new Three d meta info.
     *
     * @param mimeType the mime type
     * @param fileSize the file size
     * @param pointCount the point count
     * @param polygonCount the polygon count
     * @param vertexCount the vertex count
     * @param gaussianCount the Gaussian count
     */
    public ThreeDMetaInfoImpl(String mimeType, Long fileSize, Long pointCount, Long polygonCount, Long vertexCount, Long gaussianCount) {
        this.mimeType = mimeType;
        this.fileSize = fileSize;
        this.pointCount = pointCount;
        this.polygonCount = polygonCount;
        this.vertexCount = vertexCount;
        this.gaussianCount = gaussianCount;
    }

    /**
     * Instantiates a new Three d meta info.
     */
    public ThreeDMetaInfoImpl() {
        this.mimeType = null;
        this.fileSize = null;
        this.pointCount = null;
        this.polygonCount = null;
        this.vertexCount = null;
        this.gaussianCount = null;
    }

    /**
     * Gets mime type.
     *
     * @return the mime type
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * Gets file size.
     *
     * @return the file size
     */
    public Long getFileSize() {
        return fileSize;
    }

    /**
     * Sets mime type.
     *
     * @param mimeType the mime type
     */
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * Sets file size.
     *
     * @param fileSize the file size
     */
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * Gets point count.
     *
     * @return the point count
     */
    public Long getPointCount() {
        return pointCount;
    }

    /**
     * Sets point count.
     *
     * @param pointCount the point count
     */
    public void setPointCount(Long pointCount) {
        this.pointCount = pointCount;
    }

    /**
     * Gets polygon count.
     *
     * @return the polygon count
     */
    public Long getPolygonCount() {
        return polygonCount;
    }

    /**
     * Sets polygon count.
     *
     * @param polygonCount the polygon count
     */
    public void setPolygonCount(Long polygonCount) {
        this.polygonCount = polygonCount;
    }

    /**
     * Gets vertex count.
     *
     * @return the vertex count
     */
    public Long getVertexCount() {
        return vertexCount;
    }

    /**
     * Sets vertex count.
     *
     * @param vertexCount the vertex count
     */
    public void setVertexCount(Long vertexCount) {
        this.vertexCount = vertexCount;
    }

    /**
     * Gets Gaussian count.
     *
     * @return the Gaussian count
     */
    public Long getGaussianCount() {
      return gaussianCount;
    }

    /**
     * Sets Gaussian count.
     *
     * @param gaussianCount the Gaussian count
     */
    public void setGaussianCount(Long gaussianCount) {
      this.gaussianCount = gaussianCount;
    }
}
