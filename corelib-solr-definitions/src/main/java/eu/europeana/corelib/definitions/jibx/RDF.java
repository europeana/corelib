
package eu.europeana.corelib.definitions.jibx;

import java.util.ArrayList;
import java.util.List;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="RDF">
 *   &lt;xs:sequence>
 *     &lt;xs:choice minOccurs="0" maxOccurs="unbounded">
 *       &lt;!-- Reference to inner class Choice -->
 *     &lt;/xs:choice>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 */
public class RDF
{
    private List<Choice> choiceList = new ArrayList<Choice>();

    /** 
     * Get the list of 'RDF' complexType items.
     * 
     * @return list
     */
    public List<Choice> getChoiceList() {
        return choiceList;
    }

    /** 
     * Set the list of 'RDF' complexType items.
     * 
     * @param list
     */
    public void setChoiceList(List<Choice> list) {
        choiceList = list;
    }
    /** 
     * Schema fragment(s) for this class:
     * <pre>
     * &lt;xs:choice xmlns:ns="http://www.w3.org/2004/02/skos/core#" xmlns:ns1="http://www.europeana.eu/schemas/edm/" xmlns:ns2="http://www.openarchives.org/ore/terms/" xmlns:xs="http://www.w3.org/2001/XMLSchema" minOccurs="0" maxOccurs="unbounded">
     *   &lt;xs:element type="ns1:ProvidedCHOType" name="ProvidedCHO"/>
     *   &lt;xs:element type="ns1:WebResourceType" name="WebResource"/>
     *   &lt;xs:element type="ns1:AgentType" name="Agent"/>
     *   &lt;xs:element type="ns1:PlaceType" name="Place"/>
     *   &lt;xs:element type="ns1:TimeSpanType" name="TimeSpan"/>
     *   &lt;xs:element ref="ns:Concept"/>
     *   &lt;xs:element ref="ns2:Aggregation"/>
     * &lt;/xs:choice>
     * </pre>
     */
    public static class Choice
    {
        private int choiceListSelect = -1;
        private static final int PROVIDED_CHO_CHOICE = 0;
        private static final int WEB_RESOURCE_CHOICE = 1;
        private static final int AGENT_CHOICE = 2;
        private static final int PLACE_CHOICE = 3;
        private static final int TIME_SPAN_CHOICE = 4;
        private static final int CONCEPT_CHOICE = 5;
        private static final int AGGREGATION_CHOICE = 6;
        private ProvidedCHOType providedCHO;
        private WebResourceType webResource;
        private AgentType agent;
        private PlaceType place;
        private TimeSpanType timeSpan;
        private Concept concept;
        private Aggregation aggregation;

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
         * Check if ProvidedCHO is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifProvidedCHO() {
            return choiceListSelect == PROVIDED_CHO_CHOICE;
        }

        /** 
         * Get the 'ProvidedCHO' element value.
         * 
         * @return value
         */
        public ProvidedCHOType getProvidedCHO() {
            return providedCHO;
        }

        /** 
         * Set the 'ProvidedCHO' element value.
         * 
         * @param providedCHO
         */
        public void setProvidedCHO(ProvidedCHOType providedCHO) {
            setChoiceListSelect(PROVIDED_CHO_CHOICE);
            this.providedCHO = providedCHO;
        }

        /** 
         * Check if WebResource is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifWebResource() {
            return choiceListSelect == WEB_RESOURCE_CHOICE;
        }

        /** 
         * Get the 'WebResource' element value.
         * 
         * @return value
         */
        public WebResourceType getWebResource() {
            return webResource;
        }

        /** 
         * Set the 'WebResource' element value.
         * 
         * @param webResource
         */
        public void setWebResource(WebResourceType webResource) {
            setChoiceListSelect(WEB_RESOURCE_CHOICE);
            this.webResource = webResource;
        }

        /** 
         * Check if Agent is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifAgent() {
            return choiceListSelect == AGENT_CHOICE;
        }

        /** 
         * Get the 'Agent' element value.
         * 
         * @return value
         */
        public AgentType getAgent() {
            return agent;
        }

        /** 
         * Set the 'Agent' element value.
         * 
         * @param agent
         */
        public void setAgent(AgentType agent) {
            setChoiceListSelect(AGENT_CHOICE);
            this.agent = agent;
        }

        /** 
         * Check if Place is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifPlace() {
            return choiceListSelect == PLACE_CHOICE;
        }

        /** 
         * Get the 'Place' element value.
         * 
         * @return value
         */
        public PlaceType getPlace() {
            return place;
        }

        /** 
         * Set the 'Place' element value.
         * 
         * @param place
         */
        public void setPlace(PlaceType place) {
            setChoiceListSelect(PLACE_CHOICE);
            this.place = place;
        }

        /** 
         * Check if TimeSpan is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifTimeSpan() {
            return choiceListSelect == TIME_SPAN_CHOICE;
        }

        /** 
         * Get the 'TimeSpan' element value.
         * 
         * @return value
         */
        public TimeSpanType getTimeSpan() {
            return timeSpan;
        }

        /** 
         * Set the 'TimeSpan' element value.
         * 
         * @param timeSpan
         */
        public void setTimeSpan(TimeSpanType timeSpan) {
            setChoiceListSelect(TIME_SPAN_CHOICE);
            this.timeSpan = timeSpan;
        }

        /** 
         * Check if Concept is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifConcept() {
            return choiceListSelect == CONCEPT_CHOICE;
        }

        /** 
         * Get the 'Concept' element value.
         * 
         * @return value
         */
        public Concept getConcept() {
            return concept;
        }

        /** 
         * Set the 'Concept' element value.
         * 
         * @param concept
         */
        public void setConcept(Concept concept) {
            setChoiceListSelect(CONCEPT_CHOICE);
            this.concept = concept;
        }

        /** 
         * Check if Aggregation is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifAggregation() {
            return choiceListSelect == AGGREGATION_CHOICE;
        }

        /** 
         * Get the 'Aggregation' element value.
         * 
         * @return value
         */
        public Aggregation getAggregation() {
            return aggregation;
        }

        /** 
         * Set the 'Aggregation' element value.
         * 
         * @param aggregation
         */
        public void setAggregation(Aggregation aggregation) {
            setChoiceListSelect(AGGREGATION_CHOICE);
            this.aggregation = aggregation;
        }
    }
}
