
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="link_field">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:string" name="name"/>
 *     &lt;xs:element type="xs:string" name="type"/>
 *     &lt;xs:element type="xs:string" name="relationship"/>
 *     &lt;xs:element type="xs:string" name="module"/>
 *     &lt;xs:element type="xs:string" name="bean_name"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class LinkField
{
    private String name;
    private String type;
    private String relationship;
    private String module;
    private String beanName;

    /** 
     * Get the 'name' element value.
     * 
     * @return value
     */
    public String getName() {
        return name;
    }

    /** 
     * Set the 'name' element value.
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /** 
     * Get the 'type' element value.
     * 
     * @return value
     */
    public String getType() {
        return type;
    }

    /** 
     * Set the 'type' element value.
     * 
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /** 
     * Get the 'relationship' element value.
     * 
     * @return value
     */
    public String getRelationship() {
        return relationship;
    }

    /** 
     * Set the 'relationship' element value.
     * 
     * @param relationship
     */
    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    /** 
     * Get the 'module' element value.
     * 
     * @return value
     */
    public String getModule() {
        return module;
    }

    /** 
     * Set the 'module' element value.
     * 
     * @param module
     */
    public void setModule(String module) {
        this.module = module;
    }

    /** 
     * Get the 'bean_name' element value.
     * 
     * @return value
     */
    public String getBeanName() {
        return beanName;
    }

    /** 
     * Set the 'bean_name' element value.
     * 
     * @param beanName
     */
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
