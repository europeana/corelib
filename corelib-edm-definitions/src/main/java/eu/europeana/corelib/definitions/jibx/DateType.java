
package eu.europeana.corelib.definitions.jibx;

import java.sql.Date;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="DateType">
 *   &lt;xs:simpleContent>
 *     &lt;xs:extension base="xs:date">
 *       &lt;xs:attribute use="required" fixed="http://www.w3.org/2001/XMLSchema#date" ref="ns:datatype"/>
 *     &lt;/xs:extension>
 *   &lt;/xs:simpleContent>
 * &lt;/xs:complexType>
 * 
 * &lt;xs:attribute xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="xs:string" name="datatype"/>
 * </pre>
 */
public class DateType
{
    private Date date;
    private String datatype;

    /** 
     * Get the extension value.
     * 
     * @return value
     */
    public Date getDate() {
        return date;
    }

    /** 
     * Set the extension value.
     * 
     * @param date
     */
    public void setDate(Date date) {
        this.date = date;
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
