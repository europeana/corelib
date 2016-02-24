package eu.europeana.corelib.edm.utils.construct;

import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import eu.europeana.corelib.definitions.edm.entity.Service;
import eu.europeana.corelib.solr.entity.ServiceImpl;
import eu.europeana.corelib.storage.MongoServer;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by ymamakis on 1/12/16.
 */
public class ServiceUpdater implements Updater<ServiceImpl> {
    @Override
    public ServiceImpl update(ServiceImpl mongoEntity, ServiceImpl newEntity, MongoServer mongoServer) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Query<ServiceImpl> updateQuery = mongoServer.getDatastore()
                .createQuery(ServiceImpl.class).field("about")
                .equal(mongoEntity.getAbout());
        UpdateOperations<ServiceImpl> ops = mongoServer.getDatastore()
                .createUpdateOperations(ServiceImpl.class);
        boolean update = false;
        if(!StringUtils.equals(mongoEntity.getDctermsConformsTo(),newEntity.getDctermsConformsTo())){
            if(mongoEntity.getDctermsConformsTo()==null){
                newEntity.setDcTermsConformsTo(null);
                ops.unset("dctermsConformsTo");
            } else {
                newEntity.setDcTermsConformsTo(mongoEntity.getDctermsConformsTo());
                ops.set("odrlInheritedFrom", mongoEntity.getDctermsConformsTo());
            }
            update=true;
        }
        if(update){
            mongoServer.getDatastore().update(updateQuery, ops);
        }
        return mongoEntity;
    }
}
