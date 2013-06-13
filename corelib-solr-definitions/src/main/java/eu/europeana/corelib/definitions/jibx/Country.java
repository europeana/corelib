
package eu.europeana.corelib.definitions.jibx;

/** 
 * This is the name of the country in which the Provider is based or
 "Europe" in the case of Europe-wide projects. Example:
 &lt;edm:country&gt;AL&lt;/edm:country&gt; 
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="ns:CountryCodes" name="country"/>
 * </pre>
 */
public class Country
{
    private CountryCodes country;

    /** 
     * Get the 'country' element value.
     * 
     * @return value
     */
    public CountryCodes getCountry() {
        return country;
    }

    /** 
     * Set the 'country' element value.
     * 
     * @param country
     */
    public void setCountry(CountryCodes country) {
        this.country = country;
    }
}
