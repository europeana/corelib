package eu.europeana.corelib.solr.utils.updaters;

import java.sql.Date;

import org.apache.commons.lang.StringUtils;

import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

import eu.europeana.corelib.definitions.solr.entity.License;
import eu.europeana.corelib.solr.MongoServer;
import eu.europeana.corelib.solr.entity.LicenseImpl;

public class LicenseUpdater implements Updater<License, eu.europeana.corelib.definitions.jibx.License> {

	@Override
	public void update(License mongoEntity,
			eu.europeana.corelib.definitions.jibx.License jibxEntity,
			MongoServer mongoServer) {
		
		Query<LicenseImpl> updateQuery = mongoServer.getDatastore()
				.createQuery(LicenseImpl.class).field("about")
				.equal(jibxEntity.getAbout());
		UpdateOperations<LicenseImpl> ops = mongoServer.getDatastore()
				.createUpdateOperations(LicenseImpl.class);
		boolean update = false;
		
		if(jibxEntity.getDeprecatedOn()!=null){
			Date deprecatedJibx = jibxEntity.getDeprecatedOn().getDate();
			
			if(deprecatedJibx!= mongoEntity.getCcDeprecatedOn()){
				mongoEntity.setCcDeprecatedOn(deprecatedJibx);
				ops.set("ccDeprecatedOn", deprecatedJibx);
				update=true;
			}
		} else {
			ops.unset("ccDeprecatedOn");
			update=true;
		}
		
		if(jibxEntity.getInheritFrom()!=null){
			String inherited = jibxEntity.getInheritFrom().getResource();
			
			if(StringUtils.equals(inherited, mongoEntity.getOdrlInheritFrom())){
				mongoEntity.setOdrlInheritFrom(inherited);
				ops.set("odrlInheritFrom", inherited);
				update=true;
			}
		} else {
			ops.unset("odrlInheritFrom");
			update=true;
		}
		
		
		if(update){
			mongoServer.getDatastore().update(updateQuery, ops);
		}
	}

}
