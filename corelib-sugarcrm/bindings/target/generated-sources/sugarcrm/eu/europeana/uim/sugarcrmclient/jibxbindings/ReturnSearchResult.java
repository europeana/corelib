
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="return_search_result">
 *   &lt;xs:all>
 *     &lt;xs:element type="ns:link_list" name="entry_list"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class ReturnSearchResult
{
    private LinkList entryList;

    /** 
     * Get the 'entry_list' element value.
     * 
     * @return value
     */
    public LinkList getEntryList() {
        return entryList;
    }

    /** 
     * Set the 'entry_list' element value.
     * 
     * @param entryList
     */
    public void setEntryList(LinkList entryList) {
        this.entryList = entryList;
    }
}
