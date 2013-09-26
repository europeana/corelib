
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="new_module_fields">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:string" name="module_name"/>
 *     &lt;xs:element type="ns:field_list" name="module_fields"/>
 *     &lt;xs:element type="ns:link_field_list" name="link_fields"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class NewModuleFields
{
    private String moduleName;
    private FieldList moduleFields;
    private LinkFieldList linkFields;

    /** 
     * Get the 'module_name' element value.
     * 
     * @return value
     */
    public String getModuleName() {
        return moduleName;
    }

    /** 
     * Set the 'module_name' element value.
     * 
     * @param moduleName
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    /** 
     * Get the 'module_fields' element value.
     * 
     * @return value
     */
    public FieldList getModuleFields() {
        return moduleFields;
    }

    /** 
     * Set the 'module_fields' element value.
     * 
     * @param moduleFields
     */
    public void setModuleFields(FieldList moduleFields) {
        this.moduleFields = moduleFields;
    }

    /** 
     * Get the 'link_fields' element value.
     * 
     * @return value
     */
    public LinkFieldList getLinkFields() {
        return linkFields;
    }

    /** 
     * Set the 'link_fields' element value.
     * 
     * @param linkFields
     */
    public void setLinkFields(LinkFieldList linkFields) {
        this.linkFields = linkFields;
    }
}
