package eu.europeana.corelib.record.config;

import eu.europeana.corelib.mongo.server.impl.EdmMongoServerImpl;
import eu.europeana.corelib.record.impl.RecordServiceImpl;
import eu.europeana.corelib.storage.impl.MongoProviderImpl;
import eu.europeana.metis.mongo.RecordRedirectDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class RecordServerConfig {
    @Value("#{europeanaProperties['mongodb.connectionUrl']}")
    private String mongoConnectionUrl;

    @Value("#{europeanaProperties['mongodb.max.connection.idle.time']}")
    private String mongoMaxConnectionIdleTime;

    @Value("#{europeanaProperties['mongodb.record.dbname']}")
    private String recordDbName;

    @Value("#{europeanaProperties['mongodb.redirect.dbname']}")
    private String redirectDbName;


    @Bean(name = "corelib_db_mongoProvider", destroyMethod = "close")
    public MongoProviderImpl mongoProvider(){
        return new MongoProviderImpl(mongoConnectionUrl, mongoMaxConnectionIdleTime);
    }

    @Bean
    public EdmMongoServerImpl edmMongoServer() {
        return new EdmMongoServerImpl(mongoProvider().getMongoClient(), recordDbName, false);
    }

    @Bean
    public RecordRedirectDao redirectDao() {
        return new RecordRedirectDao(mongoProvider().getMongoClient(), redirectDbName, false);
    }

    @Bean
    public RecordServiceImpl recordService() {
        return new RecordServiceImpl();
    }
}
