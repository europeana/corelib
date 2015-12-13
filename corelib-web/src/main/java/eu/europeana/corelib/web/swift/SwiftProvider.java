package eu.europeana.corelib.web.swift;

import org.jclouds.ContextBuilder;
import org.jclouds.openstack.swift.v1.SwiftApi;
import org.jclouds.openstack.swift.v1.features.ContainerApi;
import org.jclouds.openstack.swift.v1.features.ObjectApi;

/**
 * Created by ymamakis on 11/17/15.
 */
public class SwiftProvider {

    private ObjectApi objectApi;
    public SwiftProvider(String authUrl, String userName, String password, String containerName, String regionName, String tenantName){

        final SwiftApi swiftApi = ContextBuilder.newBuilder("openstack-swift")
                .credentials(tenantName + ":" + userName, password)
                .endpoint(authUrl)
                .buildApi(SwiftApi.class);

        final ContainerApi containerApi = swiftApi.getContainerApi(regionName);

        if (containerApi.get(containerName) == null) {
            if (!containerApi.create(containerName)) {
                throw new RuntimeException("swift cannot create container: " + containerName);
            }
        }

        objectApi = swiftApi.getObjectApi(regionName, containerName);
    }

    public ObjectApi getObjectApi(){
        return this.objectApi;
    }
}
