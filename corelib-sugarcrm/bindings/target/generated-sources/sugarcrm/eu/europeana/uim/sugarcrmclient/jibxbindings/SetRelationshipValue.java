
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="set_relationship_value">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:string" name="module1"/>
 *     &lt;xs:element type="xs:string" name="module1_id"/>
 *     &lt;xs:element type="xs:string" name="module2"/>
 *     &lt;xs:element type="xs:string" name="module2_id"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class SetRelationshipValue
{
    private String module1;
    private String module1Id;
    private String module2;
    private String module2Id;

    /** 
     * Get the 'module1' element value.
     * 
     * @return value
     */
    public String getModule1() {
        return module1;
    }

    /** 
     * Set the 'module1' element value.
     * 
     * @param module1
     */
    public void setModule1(String module1) {
        this.module1 = module1;
    }

    /** 
     * Get the 'module1_id' element value.
     * 
     * @return value
     */
    public String getModule1Id() {
        return module1Id;
    }

    /** 
     * Set the 'module1_id' element value.
     * 
     * @param module1Id
     */
    public void setModule1Id(String module1Id) {
        this.module1Id = module1Id;
    }

    /** 
     * Get the 'module2' element value.
     * 
     * @return value
     */
    public String getModule2() {
        return module2;
    }

    /** 
     * Set the 'module2' element value.
     * 
     * @param module2
     */
    public void setModule2(String module2) {
        this.module2 = module2;
    }

    /** 
     * Get the 'module2_id' element value.
     * 
     * @return value
     */
    public String getModule2Id() {
        return module2Id;
    }

    /** 
     * Set the 'module2_id' element value.
     * 
     * @param module2Id
     */
    public void setModule2Id(String module2Id) {
        this.module2Id = module2Id;
    }
}
