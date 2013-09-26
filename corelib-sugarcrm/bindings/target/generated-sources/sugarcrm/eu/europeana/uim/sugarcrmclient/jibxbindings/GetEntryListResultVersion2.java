
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="get_entry_list_result_version2">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:int" name="result_count"/>
 *     &lt;xs:element type="xs:int" name="next_offset" minOccurs="0"/>
 *     &lt;xs:element type="ns:entry_list" name="entry_list"/>
 *     &lt;xs:element type="ns:link_lists" name="relationship_list"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class GetEntryListResultVersion2
{
    private int resultCount;
    private Integer nextOffset;
    private EntryList entryList;
    private LinkLists relationshipList;

    /** 
     * Get the 'result_count' element value.
     * 
     * @return value
     */
    public int getResultCount() {
        return resultCount;
    }

    /** 
     * Set the 'result_count' element value.
     * 
     * @param resultCount
     */
    public void setResultCount(int resultCount) {
        this.resultCount = resultCount;
    }

    /** 
     * Get the 'next_offset' element value.
     * 
     * @return value
     */
    public Integer getNextOffset() {
        return nextOffset;
    }

    /** 
     * Set the 'next_offset' element value.
     * 
     * @param nextOffset
     */
    public void setNextOffset(Integer nextOffset) {
        this.nextOffset = nextOffset;
    }

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
