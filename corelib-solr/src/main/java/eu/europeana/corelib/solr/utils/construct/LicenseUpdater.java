package eu.europeana.corelib.solr.utils.construct;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang.StringUtils;

import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

import eu.europeana.corelib.solr.MongoServer;
import eu.europeana.corelib.solr.entity.LicenseImpl;

public class LicenseUpdater implements Updater<LicenseImpl> {

	@Override
	public LicenseImpl update(LicenseImpl mongoEntity, LicenseImpl newEntity,
			MongoServer mongoServer) throws NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		Query<LicenseImpl> updateQuery = mongoServer.getDatastore()
				.createQuery(LicenseImpl.class).field("about")
				.equal(mongoEntity.getAbout());
		UpdateOperations<LicenseImpl> ops = mongoServer.getDatastore()
				.createUpdateOperations(LicenseImpl.class);
		boolean update = false;
		if(mongoEntity.getCcDeprecatedOn()== newEntity.getCcDeprecatedOn()){
			if(mongoEntity.getCcDeprecatedOn()==null){
				newEntity.setCcDeprecatedOn(null);
				ops.unset("ccDeprecatedOn");
				update=true;
			} else {
				newEntity.setCcDeprecatedOn(mongoEntity.getCcDeprecatedOn());
				ops.set("ccDeprecatedOn", mongoEntity.getCcDeprecatedOn());
				update = true;
			}
		}
		if(!StringUtils.equals(mongoEntity.getOdrlInheritFrom(), newEntity.getOdrlInheritFrom())){
			if(mongoEntity.getOdrlInheritFrom()==null){
				newEntity.setOdrlInheritFrom(null);
				ops.unset("odrlInheritedFrom");
				update=true;
			} else {
				newEntity.setOdrlInheritFrom(mongoEntity.getOdrlInheritFrom());
				ops.set("odrlInheritedFrom", mongoEntity.getOdrlInheritFrom());
				update = true;
			}
		}
		if(update){
			mongoServer.getDatastore().update(updateQuery, ops);
		}
		return mongoEntity;
	}

}
