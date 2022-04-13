
package eu.europeana.corelib.definitions.jibx;

import java.util.ArrayList;
import java.util.List;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.europeana.eu/schemas/edm/" xmlns:ns1="http://xmlns.com/foaf/0.1/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Organization">
 *   &lt;xs:complexType>
 *     &lt;xs:complexContent>
 *       &lt;xs:extension base="ns:AgentType">
 *         &lt;xs:sequence>
 *           &lt;xs:element ref="ns:acronym" minOccurs="0" maxOccurs="unbounded"/>
 *           &lt;xs:element ref="ns:organizationScope" minOccurs="1" maxOccurs="1"/>
 *           &lt;xs:element ref="ns:organizationDomain" minOccurs="1" maxOccurs="1"/>
 *           &lt;xs:element ref="ns:organizationSector" minOccurs="1" maxOccurs="1"/>
 *           &lt;xs:element ref="ns:geographicLevel" minOccurs="1" maxOccurs="1"/>
 *           &lt;xs:element ref="ns:country" minOccurs="1" maxOccurs="1"/>
 *           &lt;xs:element ref="ns:europeanaRole" minOccurs="1" maxOccurs="unbounded"/>
 *           &lt;xs:element ref="ns1:homepage" minOccurs="0" maxOccurs="1"/>
 *           &lt;xs:element ref="ns1:logo" minOccurs="0" maxOccurs="1"/>
 *         &lt;/xs:sequence>
 *       &lt;/xs:extension>
 *     &lt;/xs:complexContent>
 *   &lt;/xs:complexType>
 * &lt;/xs:element>
 * </pre>
 */
public class Organization extends AgentType
{
    private List<Acronym> acronymList = new ArrayList<Acronym>();
    private OrganizationScope organizationScope;
    private OrganizationDomain organizationDomain;
    private OrganizationSector organizationSector;
    private GeographicLevel geographicLevel;
    private Country country;
    private List<EuropeanaRole> europeanaRoleList = new ArrayList<EuropeanaRole>();
    private Homepage homepage;
    private Logo logo;

    /** 
     * Get the list of 'acronym' element items.
     * 
     * @return list
     */
    public List<Acronym> getAcronymList() {
        return acronymList;
    }

    /** 
     * Set the list of 'acronym' element items.
     * 
     * @param list
     */
    public void setAcronymList(List<Acronym> list) {
        acronymList = list;
    }

    /** 
     * Get the 'organizationScope' element value.
     * 
     * @return value
     */
    public OrganizationScope getOrganizationScope() {
        return organizationScope;
    }

    /** 
     * Set the 'organizationScope' element value.
     * 
     * @param organizationScope
     */
    public void setOrganizationScope(OrganizationScope organizationScope) {
        this.organizationScope = organizationScope;
    }

    /** 
     * Get the 'organizationDomain' element value.
     * 
     * @return value
     */
    public OrganizationDomain getOrganizationDomain() {
        return organizationDomain;
    }

    /** 
     * Set the 'organizationDomain' element value.
     * 
     * @param organizationDomain
     */
    public void setOrganizationDomain(OrganizationDomain organizationDomain) {
        this.organizationDomain = organizationDomain;
    }

    /** 
     * Get the 'organizationSector' element value.
     * 
     * @return value
     */
    public OrganizationSector getOrganizationSector() {
        return organizationSector;
    }

    /** 
     * Set the 'organizationSector' element value.
     * 
     * @param organizationSector
     */
    public void setOrganizationSector(OrganizationSector organizationSector) {
        this.organizationSector = organizationSector;
    }

    /** 
     * Get the 'geographicLevel' element value.
     * 
     * @return value
     */
    public GeographicLevel getGeographicLevel() {
        return geographicLevel;
    }

    /** 
     * Set the 'geographicLevel' element value.
     * 
     * @param geographicLevel
     */
    public void setGeographicLevel(GeographicLevel geographicLevel) {
        this.geographicLevel = geographicLevel;
    }

    /** 
     * Get the 'country' element value.
     * 
     * @return value
     */
    public Country getCountry() {
        return country;
    }

    /** 
     * Set the 'country' element value.
     * 
     * @param country
     */
    public void setCountry(Country country) {
        this.country = country;
    }

    /** 
     * Get the list of 'europeanaRole' element items.
     * 
     * @return list
     */
    public List<EuropeanaRole> getEuropeanaRoleList() {
        return europeanaRoleList;
    }

    /** 
     * Set the list of 'europeanaRole' element items.
     * 
     * @param list
     */
    public void setEuropeanaRoleList(List<EuropeanaRole> list) {
        europeanaRoleList = list;
    }

    /** 
     * Get the 'homepage' element value.
     * 
     * @return value
     */
    public Homepage getHomepage() {
        return homepage;
    }

    /** 
     * Set the 'homepage' element value.
     * 
     * @param homepage
     */
    public void setHomepage(Homepage homepage) {
        this.homepage = homepage;
    }

    /** 
     * Get the 'logo' element value.
     * 
     * @return value
     */
    public Logo getLogo() {
        return logo;
    }

    /** 
     * Set the 'logo' element value.
     * 
     * @param logo
     */
    public void setLogo(Logo logo) {
        this.logo = logo;
    }
}
