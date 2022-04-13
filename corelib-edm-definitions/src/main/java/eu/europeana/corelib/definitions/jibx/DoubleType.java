
package eu.europeana.corelib.definitions.jibx;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="DoubleType">
 *   &lt;xs:simpleContent>
 *     &lt;xs:extension base="xs:double">
 *       &lt;xs:attribute use="required" fixed="http://www.w3.org/2001/XMLSchema#double" ref="ns:datatype"/>
 *     &lt;/xs:extension>
 *   &lt;/xs:simpleContent>
 * &lt;/xs:complexType>
 * 
 * &lt;xs:attribute xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="xs:string" name="datatype"/>
 * </pre>
 */
public class DoubleType
{
    private Double _double;
    private String datatype;

    /** 
     * Get the extension value.
     * 
     * @return value
     */
    public Double getDouble() {
        return _double;
    }

    /** 
     * Set the extension value.
     * 
     * @param _double
     */
    public void setDouble(Double _double) {
        this._double = _double;
    }

    /** 
     * Get the 'datatype' attribute value.
     * 
     * @return value
     */
    public String getDatatype() {
        return datatype;
    }

    /** 
     * Set the 'datatype' attribute value.
     * 
     * @param datatype
     */
    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }
}
