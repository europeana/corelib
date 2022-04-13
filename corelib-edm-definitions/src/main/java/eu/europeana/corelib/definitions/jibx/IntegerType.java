
package eu.europeana.corelib.definitions.jibx;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="IntegerType">
 *   &lt;xs:simpleContent>
 *     &lt;xs:extension base="xs:long">
 *       &lt;xs:attribute use="required" fixed="http://www.w3.org/2001/XMLSchema#integer" ref="ns:datatype"/>
 *     &lt;/xs:extension>
 *   &lt;/xs:simpleContent>
 * &lt;/xs:complexType>
 * 
 * &lt;xs:attribute xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="xs:string" name="datatype"/>
 * </pre>
 */
public class IntegerType
{
    private long _long;
    private String datatype;

    /** 
     * Get the extension value.
     * 
     * @return value
     */
    public long getLong() {
        return _long;
    }

    /** 
     * Set the extension value.
     * 
     * @param _long
     */
    public void setLong(long _long) {
        this._long = _long;
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
