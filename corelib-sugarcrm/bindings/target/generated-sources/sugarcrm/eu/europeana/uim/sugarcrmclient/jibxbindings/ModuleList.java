
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="module_list">
 *   &lt;xs:all>
 *     &lt;xs:element type="ns:select_fields" name="modules"/>
 *     &lt;xs:element type="ns:error_value" name="error"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class ModuleList
{
    private SelectFields modules;
    private ErrorValue error;

    /** 
     * Get the 'modules' element value.
     * 
     * @return value
     */
    public SelectFields getModules() {
        return modules;
    }

    /** 
     * Set the 'modules' element value.
     * 
     * @param modules
     */
    public void setModules(SelectFields modules) {
        this.modules = modules;
    }

    /** 
     * Get the 'error' element value.
     * 
     * @return value
     */
    public ErrorValue getError() {
        return error;
    }

    /** 
     * Set the 'error' element value.
     * 
     * @param error
     */
    public void setError(ErrorValue error) {
        this.error = error;
    }
}
