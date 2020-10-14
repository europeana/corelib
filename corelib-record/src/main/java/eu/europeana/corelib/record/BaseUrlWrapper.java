package eu.europeana.corelib.record;

import java.util.Objects;

/**
 * Wrapper class for baseURL config properties
 */
public class BaseUrlWrapper {

    private final String api2BaseUrl;
    private final String apiGatewayBaseUrl;
    private final String portalBaseUrl;

    public BaseUrlWrapper(String api2BaseUrl, String apiGatewayBaseUrl, String portalBaseUrl) {
        this.api2BaseUrl = api2BaseUrl;
        this.apiGatewayBaseUrl = apiGatewayBaseUrl;
        this.portalBaseUrl = portalBaseUrl;
    }


    public String getApi2BaseUrl() {
        return api2BaseUrl;
    }

    public String getApiGatewayBaseUrl() {
        return apiGatewayBaseUrl;
    }

    public String getPortalBaseUrl() {
        return portalBaseUrl;
    }


    @Override
    public String toString() {
        return "{" +
                "api2BaseUrl='" + api2BaseUrl + '\'' +
                ", apiGatewayBaseUrl='" + apiGatewayBaseUrl + '\'' +
                ", portalBaseUrl='" + portalBaseUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseUrlWrapper that = (BaseUrlWrapper) o;
        return Objects.equals(api2BaseUrl, that.api2BaseUrl) &&
                Objects.equals(apiGatewayBaseUrl, that.apiGatewayBaseUrl) &&
                Objects.equals(portalBaseUrl, that.portalBaseUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(api2BaseUrl, apiGatewayBaseUrl, portalBaseUrl);
    }
}
