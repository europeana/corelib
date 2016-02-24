package eu.europeana.corelib.service.impl.swift;

import com.typesafe.config.Config;

import java.net.UnknownHostException;

/**
 * Created by salexandru on 03.06.2015.
 */
public class SwiftConfiguration {
    private final String authUrl;
    private final String userName;
    private final String password;
    private final String containerName;
    private final String regionName;
    private final String tenantName;

    public SwiftConfiguration(String authUrl, String tenantName, String userName, String password, String containerName,
                              String regionName) {
        this.authUrl = authUrl;
        this.userName = userName;
        this.password = password;
        this.containerName = containerName;
        this.regionName = regionName;
        this.tenantName = tenantName;
    }

    public static SwiftConfiguration valueOf(final Config config) throws UnknownHostException {

        final String AUTH_URL = "authUrl";
        final String USERNAME = "userName";
        final String PASSWORD = "password";
        final String CONTAINER_NAME = "containerName";
        final String REGION_NAME = "regionName";
        final String TENANT_NAME = "tenantName";


        if (!config.hasPath(AUTH_URL)) throw new IllegalArgumentException("The swift configuration needs a " + AUTH_URL);
        final String authUrl = config.getString(AUTH_URL);

        if (!config.hasPath(USERNAME)) throw new IllegalArgumentException("The swift configuration needs a " + USERNAME);
        final String userName = config.getString(USERNAME);

        if (!config.hasPath(PASSWORD)) throw new IllegalArgumentException("The swift configuration needs a " + PASSWORD);
        final String password = config.getString(PASSWORD);

        if (!config.hasPath(CONTAINER_NAME))
            throw new IllegalArgumentException("The swift configuration needs a " + CONTAINER_NAME);
        final String containerName = config.getString(CONTAINER_NAME);

        if (!config.hasPath(REGION_NAME))
            throw new IllegalArgumentException("The swift configuration needs a " + REGION_NAME);
        final String regionName = config.getString(REGION_NAME);

        if (!config.hasPath(TENANT_NAME))
            throw new IllegalArgumentException("The swift configuration needs a " + TENANT_NAME);
        final String tenantName = config.getString(TENANT_NAME);

        return new SwiftConfiguration(authUrl,
                tenantName,
                userName,
                password,
                containerName, regionName);

    }


    public String getAuthUrl() {
        return authUrl;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getContainerName() {
        return containerName;
    }

    public String getRegionName() {
        return regionName;
    }

    public String getTenantName() {
        return tenantName;
    }

    public String getIdentity() {
        return tenantName + ":" + userName;
    }
}
