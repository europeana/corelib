
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="search_by_module">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:string" name="user_name"/>
 *     &lt;xs:element type="xs:string" name="password"/>
 *     &lt;xs:element type="xs:string" name="search_string"/>
 *     &lt;xs:element type="ns:select_fields" name="modules"/>
 *     &lt;xs:element type="xs:int" name="offset"/>
 *     &lt;xs:element type="xs:int" name="max_results"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class SearchByModule
{
    private String userName;
    private String password;
    private String searchString;
    private SelectFields modules;
    private int offset;
    private int maxResults;

    /** 
     * Get the 'user_name' element value.
     * 
     * @return value
     */
    public String getUserName() {
        return userName;
    }

    /** 
     * Set the 'user_name' element value.
     * 
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** 
     * Get the 'password' element value.
     * 
     * @return value
     */
    public String getPassword() {
        return password;
    }

    /** 
     * Set the 'password' element value.
     * 
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /** 
     * Get the 'search_string' element value.
     * 
     * @return value
     */
    public String getSearchString() {
        return searchString;
    }

    /** 
     * Set the 'search_string' element value.
     * 
     * @param searchString
     */
    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

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
     * Get the 'offset' element value.
     * 
     * @return value
     */
    public int getOffset() {
        return offset;
    }

    /** 
     * Set the 'offset' element value.
     * 
     * @param offset
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    /** 
     * Get the 'max_results' element value.
     * 
     * @return value
     */
    public int getMaxResults() {
        return maxResults;
    }

    /** 
     * Set the 'max_results' element value.
     * 
     * @param maxResults
     */
    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }
}
