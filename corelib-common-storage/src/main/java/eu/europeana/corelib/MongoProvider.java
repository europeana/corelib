package eu.europeana.corelib;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;

public class MongoProvider {

	private Mongo mongo;

	public MongoProvider(String hosts, String ports) {
		org.apache.log4j.Logger.getLogger(this.getClass()).error("MongoProvider got "+hosts + ":" +ports);
		String[] hostList = hosts.split(",");
		String[] portList = ports.split(",");
		List<ServerAddress> serverAddresses = new ArrayList<>();
		int i = 0;
		for (String host : hostList) {
			if (host.length() > 0) {
				try {
					ServerAddress address = new ServerAddress(host,
							Integer.parseInt(portList[i]));
					serverAddresses.add(address);
				} catch (NumberFormatException | UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			i++;
		}
		
		
                MongoClientOptions.Builder builder = MongoClientOptions.builder();
                MongoClient client =new MongoClient(serverAddresses, builder.build());
                
                mongo = client;
                for (ServerAddress s : client.getAllAddress())
                   org.apache.log4j.Logger.getLogger(this.getClass()).error("Client: " + s.getHost());
		//mongo = new Mongo(serverAddresses,settings);
                org.apache.log4j.Logger.getLogger(this.getClass()).error("MongoProvider says " +mongo==null+"");
	}

	public Mongo getMongo() {
		return mongo;
	}
}
