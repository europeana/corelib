
package eu.europeana.corelib.definitions.jibx;

import java.util.ArrayList;
import java.util.List;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:ns1="http://www.w3.org/2004/02/skos/core#" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Concept">
 *   &lt;xs:complexType>
 *     &lt;xs:complexContent>
 *       &lt;xs:extension base="ns:AboutType">
 *         &lt;xs:sequence>
 *           &lt;xs:choice minOccurs="0" maxOccurs="unbounded">
 *             &lt;!-- Reference to inner class Choice -->
 *           &lt;/xs:choice>
 *         &lt;/xs:sequence>
 *       &lt;/xs:extension>
 *     &lt;/xs:complexContent>
 *   &lt;/xs:complexType>
 * &lt;/xs:element>
 * </pre>
 */
public class Concept extends AboutType
{
    private List<Choice> choiceList = new ArrayList<Choice>();

    /** 
     * Get the list of choice items.
     * 
     * @return list
     */
    public List<Choice> getChoiceList() {
        return choiceList;
    }

    /** 
     * Set the list of choice items.
     * 
     * @param list
     */
    public void setChoiceList(List<Choice> list) {
        choiceList = list;
    }
    /** 
     * Schema fragment(s) for this class:
     * <pre>
     * &lt;xs:choice xmlns:ns="http://www.w3.org/2004/02/skos/core#" xmlns:xs="http://www.w3.org/2001/XMLSchema" minOccurs="0" maxOccurs="unbounded">
     *   &lt;xs:element ref="ns:prefLabel"/>
     *   &lt;xs:element ref="ns:altLabel"/>
     *   &lt;xs:element ref="ns:broader"/>
     *   &lt;xs:element ref="ns:narrower"/>
     *   &lt;xs:element ref="ns:related"/>
     *   &lt;xs:element ref="ns:broadMatch"/>
     *   &lt;xs:element ref="ns:narrowMatch"/>
     *   &lt;xs:element ref="ns:relatedMatch"/>
     *   &lt;xs:element ref="ns:exactMatch"/>
     *   &lt;xs:element ref="ns:closeMatch"/>
     *   &lt;xs:element ref="ns:note"/>
     *   &lt;xs:element ref="ns:notation"/>
     *   &lt;xs:element ref="ns:inScheme"/>
     * &lt;/xs:choice>
     * </pre>
     */
    public static class Choice
    {
        private int choiceListSelect = -1;
        private static final int PREF_LABEL_CHOICE = 0;
        private static final int ALT_LABEL_CHOICE = 1;
        private static final int BROADER_CHOICE = 2;
        private static final int NARROWER_CHOICE = 3;
        private static final int RELATED_CHOICE = 4;
        private static final int BROAD_MATCH_CHOICE = 5;
        private static final int NARROW_MATCH_CHOICE = 6;
        private static final int RELATED_MATCH_CHOICE = 7;
        private static final int EXACT_MATCH_CHOICE = 8;
        private static final int CLOSE_MATCH_CHOICE = 9;
        private static final int NOTE_CHOICE = 10;
        private static final int NOTATION_CHOICE = 11;
        private static final int IN_SCHEME_CHOICE = 12;
        private PrefLabel prefLabel;
        private AltLabel altLabel;
        private Broader broader;
        private Narrower narrower;
        private Related related;
        private BroadMatch broadMatch;
        private NarrowMatch narrowMatch;
        private RelatedMatch relatedMatch;
        private ExactMatch exactMatch;
        private CloseMatch closeMatch;
        private Note note;
        private Notation notation;
        private InScheme inScheme;

        private void setChoiceListSelect(int choice) {
            if (choiceListSelect == -1) {
                choiceListSelect = choice;
            } else if (choiceListSelect != choice) {
                throw new IllegalStateException(
                        "Need to call clearChoiceListSelect() before changing existing choice");
            }
        }

        /** 
         * Clear the choice selection.
         */
        public void clearChoiceListSelect() {
            choiceListSelect = -1;
        }

        /** 
         * Check if PrefLabel is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifPrefLabel() {
            return choiceListSelect == PREF_LABEL_CHOICE;
        }

        /** 
         * Get the 'prefLabel' element value.
         * 
         * @return value
         */
        public PrefLabel getPrefLabel() {
            return prefLabel;
        }

        /** 
         * Set the 'prefLabel' element value.
         * 
         * @param prefLabel
         */
        public void setPrefLabel(PrefLabel prefLabel) {
            setChoiceListSelect(PREF_LABEL_CHOICE);
            this.prefLabel = prefLabel;
        }

        /** 
         * Check if AltLabel is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifAltLabel() {
            return choiceListSelect == ALT_LABEL_CHOICE;
        }

        /** 
         * Get the 'altLabel' element value.
         * 
         * @return value
         */
        public AltLabel getAltLabel() {
            return altLabel;
        }

        /** 
         * Set the 'altLabel' element value.
         * 
         * @param altLabel
         */
        public void setAltLabel(AltLabel altLabel) {
            setChoiceListSelect(ALT_LABEL_CHOICE);
            this.altLabel = altLabel;
        }

        /** 
         * Check if Broader is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifBroader() {
            return choiceListSelect == BROADER_CHOICE;
        }

        /** 
         * Get the 'broader' element value.
         * 
         * @return value
         */
        public Broader getBroader() {
            return broader;
        }

        /** 
         * Set the 'broader' element value.
         * 
         * @param broader
         */
        public void setBroader(Broader broader) {
            setChoiceListSelect(BROADER_CHOICE);
            this.broader = broader;
        }

        /** 
         * Check if Narrower is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifNarrower() {
            return choiceListSelect == NARROWER_CHOICE;
        }

        /** 
         * Get the 'narrower' element value.
         * 
         * @return value
         */
        public Narrower getNarrower() {
            return narrower;
        }

        /** 
         * Set the 'narrower' element value.
         * 
         * @param narrower
         */
        public void setNarrower(Narrower narrower) {
            setChoiceListSelect(NARROWER_CHOICE);
            this.narrower = narrower;
        }

        /** 
         * Check if Related is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifRelated() {
            return choiceListSelect == RELATED_CHOICE;
        }

        /** 
         * Get the 'related' element value.
         * 
         * @return value
         */
        public Related getRelated() {
            return related;
        }

        /** 
         * Set the 'related' element value.
         * 
         * @param related
         */
        public void setRelated(Related related) {
            setChoiceListSelect(RELATED_CHOICE);
            this.related = related;
        }

        /** 
         * Check if BroadMatch is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifBroadMatch() {
            return choiceListSelect == BROAD_MATCH_CHOICE;
        }

        /** 
         * Get the 'broadMatch' element value.
         * 
         * @return value
         */
        public BroadMatch getBroadMatch() {
            return broadMatch;
        }

        /** 
         * Set the 'broadMatch' element value.
         * 
         * @param broadMatch
         */
        public void setBroadMatch(BroadMatch broadMatch) {
            setChoiceListSelect(BROAD_MATCH_CHOICE);
            this.broadMatch = broadMatch;
        }

        /** 
         * Check if NarrowMatch is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifNarrowMatch() {
            return choiceListSelect == NARROW_MATCH_CHOICE;
        }

        /** 
         * Get the 'narrowMatch' element value.
         * 
         * @return value
         */
        public NarrowMatch getNarrowMatch() {
            return narrowMatch;
        }

        /** 
         * Set the 'narrowMatch' element value.
         * 
         * @param narrowMatch
         */
        public void setNarrowMatch(NarrowMatch narrowMatch) {
            setChoiceListSelect(NARROW_MATCH_CHOICE);
            this.narrowMatch = narrowMatch;
        }

        /** 
         * Check if RelatedMatch is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifRelatedMatch() {
            return choiceListSelect == RELATED_MATCH_CHOICE;
        }

        /** 
         * Get the 'relatedMatch' element value.
         * 
         * @return value
         */
        public RelatedMatch getRelatedMatch() {
            return relatedMatch;
        }

        /** 
         * Set the 'relatedMatch' element value.
         * 
         * @param relatedMatch
         */
        public void setRelatedMatch(RelatedMatch relatedMatch) {
            setChoiceListSelect(RELATED_MATCH_CHOICE);
            this.relatedMatch = relatedMatch;
        }

        /** 
         * Check if ExactMatch is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifExactMatch() {
            return choiceListSelect == EXACT_MATCH_CHOICE;
        }

        /** 
         * Get the 'exactMatch' element value.
         * 
         * @return value
         */
        public ExactMatch getExactMatch() {
            return exactMatch;
        }

        /** 
         * Set the 'exactMatch' element value.
         * 
         * @param exactMatch
         */
        public void setExactMatch(ExactMatch exactMatch) {
            setChoiceListSelect(EXACT_MATCH_CHOICE);
            this.exactMatch = exactMatch;
        }

        /** 
         * Check if CloseMatch is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifCloseMatch() {
            return choiceListSelect == CLOSE_MATCH_CHOICE;
        }

        /** 
         * Get the 'closeMatch' element value.
         * 
         * @return value
         */
        public CloseMatch getCloseMatch() {
            return closeMatch;
        }

        /** 
         * Set the 'closeMatch' element value.
         * 
         * @param closeMatch
         */
        public void setCloseMatch(CloseMatch closeMatch) {
            setChoiceListSelect(CLOSE_MATCH_CHOICE);
            this.closeMatch = closeMatch;
        }

        /** 
         * Check if Note is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifNote() {
            return choiceListSelect == NOTE_CHOICE;
        }

        /** 
         * Get the 'note' element value.
         * 
         * @return value
         */
        public Note getNote() {
            return note;
        }

        /** 
         * Set the 'note' element value.
         * 
         * @param note
         */
        public void setNote(Note note) {
            setChoiceListSelect(NOTE_CHOICE);
            this.note = note;
        }

        /** 
         * Check if Notation is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifNotation() {
            return choiceListSelect == NOTATION_CHOICE;
        }

        /** 
         * Get the 'notation' element value.
         * 
         * @return value
         */
        public Notation getNotation() {
            return notation;
        }

        /** 
         * Set the 'notation' element value.
         * 
         * @param notation
         */
        public void setNotation(Notation notation) {
            setChoiceListSelect(NOTATION_CHOICE);
            this.notation = notation;
        }

        /** 
         * Check if InScheme is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifInScheme() {
            return choiceListSelect == IN_SCHEME_CHOICE;
        }

        /** 
         * Get the 'inScheme' element value.
         * 
         * @return value
         */
        public InScheme getInScheme() {
            return inScheme;
        }

        /** 
         * Set the 'inScheme' element value.
         * 
         * @param inScheme
         */
        public void setInScheme(InScheme inScheme) {
            setChoiceListSelect(IN_SCHEME_CHOICE);
            this.inScheme = inScheme;
        }
    }
}
