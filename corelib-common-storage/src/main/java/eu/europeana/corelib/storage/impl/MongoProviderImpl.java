package eu.europeana.corelib.storage.impl;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import eu.europeana.corelib.storage.MongoProvider;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class MongoProviderImpl implements MongoProvider {

    private static final Logger logger = Logger.getLogger(MongoProviderImpl.class);

    private Mongo mongo;

    public MongoProviderImpl(String hosts, String ports) {
        logger.error("MongoProvider got " + hosts + ":" + ports);
        String[] hostList = StringUtils.split(hosts, ",");
        String[] portList = StringUtils.split(ports,",");
        List<ServerAddress> serverAddresses = new ArrayList<>();
        int i = 0;
        for (String host : hostList) {
            if (host.length() > 0) {
                try {
                    ServerAddress address = new ServerAddress(host,
                            Integer.parseInt(portList[i]));
                    serverAddresses.add(address);
                } catch (NumberFormatException | UnknownHostException e) {
                    logger.error(e);
                }
            }
            i++;
        }


        MongoClientOptions.Builder builder = MongoClientOptions.builder();
        mongo = new MongoClient(serverAddresses, builder.build());

        for (ServerAddress s : mongo.getAllAddress()) {
            logger.error("Client: " + s.getHost());
        }
    }

    @Override public Mongo getMongo() {
        return mongo;
    }
}
