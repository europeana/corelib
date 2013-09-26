
package eu.europeana.uim.sugarcrmclient.jibxbindings;

import java.util.ArrayList;
import java.util.List;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:group xmlns:ns="http://schemas.xmlsoap.org/wsdl/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="request-response-or-one-way-operation">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="ns:tParam" name="input"/>
 *     &lt;xs:sequence minOccurs="0">
 *       &lt;!-- Reference to inner class Sequence -->
 *     &lt;/xs:sequence>
 *   &lt;/xs:sequence>
 * &lt;/xs:group>
 * </pre>
 */
public class RequestResponseOrOneWayOperation
{
    private TParam input;
    private Sequence sequence;

    /** 
     * Get the 'input' element value.
     * 
     * @return value
     */
    public TParam getInput() {
        return input;
    }

    /** 
     * Set the 'input' element value.
     * 
     * @param input
     */
    public void setInput(TParam input) {
        this.input = input;
    }

    /** 
     * Get the sequence value.
     * 
     * @return value
     */
    public Sequence getSequence() {
        return sequence;
    }

    /** 
     * Set the sequence value.
     * 
     * @param sequence
     */
    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }
    /** 
     * Schema fragment(s) for this class:
     * <pre>
     * &lt;xs:sequence xmlns:ns="http://schemas.xmlsoap.org/wsdl/" xmlns:xs="http://www.w3.org/2001/XMLSchema" minOccurs="0">
     *   &lt;xs:element type="ns:tParam" name="output"/>
     *   &lt;xs:element type="ns:tFault" name="fault" minOccurs="0" maxOccurs="unbounded"/>
     * &lt;/xs:sequence>
     * </pre>
     */
    public static class Sequence
    {
        private TParam output;
        private List<TFault> faultList = new ArrayList<TFault>();

        /** 
         * Get the 'output' element value.
         * 
         * @return value
         */
        public TParam getOutput() {
            return output;
        }

        /** 
         * Set the 'output' element value.
         * 
         * @param output
         */
        public void setOutput(TParam output) {
            this.output = output;
        }

        /** 
         * Get the list of 'fault' element items.
         * 
         * @return list
         */
        public List<TFault> getFaultList() {
            return faultList;
        }

        /** 
         * Set the list of 'fault' element items.
         * 
         * @param list
         */
        public void setFaultList(List<TFault> list) {
            faultList = list;
        }
    }
}
