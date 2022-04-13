
package eu.europeana.corelib.definitions.jibx;

import java.util.ArrayList;
import java.util.List;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.w3.org/2002/07/owl#" xmlns:ns1="http://www.openarchives.org/ore/terms/" xmlns:ns2="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="ProxyType">
 *   &lt;xs:complexContent>
 *     &lt;xs:extension base="ns2:BaseProvidedCHOType">
 *       &lt;xs:sequence>
 *         &lt;xs:element ref="ns2:europeanaProxy" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns2:userTag" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns2:year" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns1:proxyFor" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns1:proxyIn" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns2:type" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns:sameAs" minOccurs="0" maxOccurs="unbounded"/>
 *       &lt;/xs:sequence>
 *     &lt;/xs:extension>
 *   &lt;/xs:complexContent>
 * &lt;/xs:complexType>
 * </pre>
 */
public class ProxyType extends BaseProvidedCHOType
{
    private EuropeanaProxy europeanaProxy;
    private List<UserTag> userTagList = new ArrayList<UserTag>();
    private List<Year> yearList = new ArrayList<Year>();
    private ProxyFor proxyFor;
    private List<ProxyIn> proxyInList = new ArrayList<ProxyIn>();
    private Type2 type;
    private List<SameAs> sameAList = new ArrayList<SameAs>();

    /** 
     * Get the 'europeanaProxy' element value.
     * 
     * @return value
     */
    public EuropeanaProxy getEuropeanaProxy() {
        return europeanaProxy;
    }

    /** 
     * Set the 'europeanaProxy' element value.
     * 
     * @param europeanaProxy
     */
    public void setEuropeanaProxy(EuropeanaProxy europeanaProxy) {
        this.europeanaProxy = europeanaProxy;
    }

    /** 
     * Get the list of 'userTag' element items.
     * 
     * @return list
     */
    public List<UserTag> getUserTagList() {
        return userTagList;
    }

    /** 
     * Set the list of 'userTag' element items.
     * 
     * @param list
     */
    public void setUserTagList(List<UserTag> list) {
        userTagList = list;
    }

    /** 
     * Get the list of 'year' element items.
     * 
     * @return list
     */
    public List<Year> getYearList() {
        return yearList;
    }

    /** 
     * Set the list of 'year' element items.
     * 
     * @param list
     */
    public void setYearList(List<Year> list) {
        yearList = list;
    }

    /** 
     * Get the 'proxyFor' element value.
     * 
     * @return value
     */
    public ProxyFor getProxyFor() {
        return proxyFor;
    }

    /** 
     * Set the 'proxyFor' element value.
     * 
     * @param proxyFor
     */
    public void setProxyFor(ProxyFor proxyFor) {
        this.proxyFor = proxyFor;
    }

    /** 
     * Get the list of 'proxyIn' element items.
     * 
     * @return list
     */
    public List<ProxyIn> getProxyInList() {
        return proxyInList;
    }

    /** 
     * Set the list of 'proxyIn' element items.
     * 
     * @param list
     */
    public void setProxyInList(List<ProxyIn> list) {
        proxyInList = list;
    }

    /** 
     * Get the 'type' element value.
     * 
     * @return value
     */
    public Type2 getType() {
        return type;
    }

    /** 
     * Set the 'type' element value.
     * 
     * @param type
     */
    public void setType(Type2 type) {
        this.type = type;
    }

    /** 
     * Get the list of 'sameAs' element items.
     * 
     * @return list
     */
    public List<SameAs> getSameAList() {
        return sameAList;
    }

    /** 
     * Set the list of 'sameAs' element items.
     * 
     * @param list
     */
    public void setSameAList(List<SameAs> list) {
        sameAList = list;
    }
}
