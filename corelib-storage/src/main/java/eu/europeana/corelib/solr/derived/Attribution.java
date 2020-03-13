package eu.europeana.corelib.solr.derived;

import java.util.HashMap;
import java.util.Map;

public class Attribution {
    private String                  itemUri;
    private Map<String, String>     title;
    private Map<String, String>     creator;
    private Map<String, String>     date;
    private Map<String, String>     provider;
    private String                  providerUrl;
    private String                  country;
    private String                  rightsStatement;
    private String                  rightsLabel;
    private String []               rightsIcon;
    private String                  ccDeprecatedOn;
    private String                  landingPage;


    public Attribution() {
        title      = new HashMap<>();
        creator    = new HashMap<>();
        provider   = new HashMap<>();
        date       = new HashMap<>();
    }

    public String getItemUri() {
        return itemUri;
    }

    public void setItemUri(String itemUri) {
        this.itemUri = itemUri;
    }

    public Map<String, String> getTitle() {
        return title;
    }

    public void setTitle(Map<String, String> title) {
        this.title = title;
    }

    public Map<String, String> getCreator() {
        return creator;
    }

    public void setCreator(Map<String, String> creator) {
        this.creator = creator;
    }

    public Map<String, String> getDate() {
        return date;
    }

    public void setDate(Map<String, String> date) {
        this.date = date;
    }

    public Map<String, String> getProvider() {
        return provider;
    }

    public void setProvider(Map<String, String> provider) {
        this.provider = provider;
    }

    public String getProviderUrl() {
        return providerUrl;
    }

    public void setProviderUrl(String providerUrl) {
        this.providerUrl = providerUrl;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRightsStatement() {
        return rightsStatement;
    }

    public void setRightsStatement(String rightsStatement) {
        this.rightsStatement = rightsStatement;
    }

    public String getRightsLabel() {
        return rightsLabel;
    }

    public void setRightsLabel(String rightsLabel) {
        this.rightsLabel = rightsLabel;
    }

    public String[] getRightsIcon() {
        return rightsIcon;
    }

    public void setRightsIcon(String [] rightsIcon) {
        this.rightsIcon = rightsIcon;
    }

    public String getCcDeprecatedOn() {
        return ccDeprecatedOn;
    }

    public void setCcDeprecatedOn(String ccDeprecatedOn) {
        this.ccDeprecatedOn = ccDeprecatedOn;
    }

    public String getLandingPage() {
        return landingPage;
    }

    public void setLandingPage(String landingPage) {
        this.landingPage = landingPage;
    }
}
