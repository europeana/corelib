
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="get_entry_result_version2">
 *   &lt;xs:all>
 *     &lt;xs:element type="ns:entry_list" name="entry_list"/>
 *     &lt;xs:element type="ns:link_lists" name="relationship_list"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class GetEntryResultVersion2
{
    private EntryList entryList;
    private LinkLists relationshipList;

    /** 
     * Get the 'entry_list' element value.
     * 
     * @return value
     */
    public EntryList getEntryList() {
        return entryList;
    }

    /** 
     * Set the 'entry_list' element value.
     * 
     * @param entryList
     */
    public void setEntryList(EntryList entryList) {
        this.entryList = entryList;
    }

    /** 
     * Get the 'relationship_list' element value.
     * 
     * @return value
     */
    public LinkLists getRelationshipList() {
        return relationshipList;
    }

    /** 
     * Set the 'relationship_list' element value.
     * 
     * @param relationshipList
     */
    public void setRelationshipList(LinkLists relationshipList) {
        this.relationshipList = relationshipList;
    }
}
