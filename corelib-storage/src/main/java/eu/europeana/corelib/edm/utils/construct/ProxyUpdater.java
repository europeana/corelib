package eu.europeana.corelib.edm.utils.construct;

import eu.europeana.corelib.edm.exceptions.MongoUpdateException;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import eu.europeana.corelib.storage.MongoServer;
import eu.europeana.corelib.edm.utils.MongoUtils;
import eu.europeana.corelib.solr.entity.ProxyImpl;

public class ProxyUpdater implements Updater<ProxyImpl> {
    UpdateOperations<ProxyImpl> ops;

      public ProxyImpl update(ProxyImpl retProxy, ProxyImpl proxy, MongoServer mongoServer) throws MongoUpdateException {
        Query<ProxyImpl> updateQuery = mongoServer.getDatastore()
                .createQuery(ProxyImpl.class).field("about")
                .equal(proxy.getAbout());
        ops = mongoServer.getDatastore()
                .createUpdateOperations(ProxyImpl.class);
        boolean update = false;
        update = MongoUtils.updateMap(retProxy, proxy, "dcContributor", ops) || update;
        update = MongoUtils.updateMap(retProxy, proxy, "dcCoverage", ops) || update;
        update = MongoUtils.updateMap(retProxy, proxy, "dcCreator", ops) || update;
        update = MongoUtils.updateMap(retProxy, proxy, "dcDate", ops) || update;
        update = MongoUtils.updateMap(retProxy, proxy, "dcDescription", ops) || update;
        update = MongoUtils.updateMap(retProxy, proxy, "dcFormat", ops) || update;
        update = MongoUtils.updateMap(retProxy, proxy, "dcIdentifier", ops) || update;
        update = MongoUtils.updateMap(retProxy, proxy, "dcLanguage", ops) || update;
        update = MongoUtils.updateMap(retProxy, proxy, "dcPublisher", ops) || update;
        update = MongoUtils.updateMap(retProxy, proxy, "dcRelation", ops) || update;
        update = MongoUtils.updateMap(retProxy, proxy, "dcRights", ops) || update;
        update = MongoUtils.updateMap(retProxy, proxy, "dcSource", ops) || update;
        update = MongoUtils.updateMap(retProxy, proxy, "dcSubject", ops) || update;
        update = MongoUtils.updateMap(retProxy, proxy, "dcTitle", ops) || update;
        update = MongoUtils.updateMap(retProxy, proxy, "dcType", ops) || update;
        update = MongoUtils.updateMap(retProxy, proxy, "dctermsAlternative", ops) || update;
        update = MongoUtils.updateMap(retProxy, proxy, "dctermsConformsTo", ops) || update;
        update = MongoUtils.updateMap(retProxy, proxy, "dctermsCreated", ops) || update;
        update = MongoUtils.updateMap(retProxy, proxy, "dctermsExtent", ops) || update;
        update = MongoUtils.updateMap(retProxy, proxy, "dctermsHasFormat", ops) || update;
        update = MongoUtils.updateMap(retProxy, proxy, "dctermsHasPart", ops) || update;
        update = MongoUtils.updateMap(retProxy, proxy, "dctermsHasVersion", ops) || update;
        update = MongoUtils.updateMap(retProxy, proxy, "dctermsIsFormatOf", ops) || update;
        update = MongoUtils.updateMap(retProxy, proxy, "dctermsIsPartOf", ops) || update;
        update = MongoUtils.updateMap(retProxy, proxy, "dctermsIsReferencedBy", ops) || update;
        update = MongoUtils.updateMap(retProxy, proxy, "dctermsIsReplacedBy", ops) || update;
        update = MongoUtils.updateMap(retProxy, proxy, "dctermsIsRequiredBy", ops) || update;
        update = MongoUtils.updateMap(retProxy, proxy, "dctermsIssued", ops) || update;
        update = MongoUtils.updateMap(retProxy, proxy, "dctermsIsVersionOf", ops) || update;
        update = MongoUtils.updateMap(retProxy, proxy, "dctermsMedium", ops) || update;
        update = MongoUtils.updateMap(retProxy, proxy, "dctermsProvenance", ops) || update;
        update = MongoUtils.updateMap(retProxy, proxy, "dctermsReferences", ops) || update;
        update = MongoUtils.updateMap(retProxy, proxy, "dctermsRequires", ops) || update;
        update = MongoUtils.updateMap(retProxy, proxy, "dctermsSpatial", ops) || update;
        update = MongoUtils.updateMap(retProxy, proxy, "dctermsTOC", ops) || update;
        update = MongoUtils.updateMap(retProxy, proxy, "dctermsTemporal", ops) || update;
        if (proxy.getEdmType() != null) {
            if (retProxy.getEdmType() == null
                    || !retProxy.getEdmType().equals(proxy.getEdmType())) {
                ops.set("edmType", proxy.getEdmType());
                retProxy.setEdmType(proxy.getEdmType());
                update = true;
            }
        } else {
            if (retProxy.getEdmType() != null) {
                ops.unset("edmType");
                update = true;
            }
        }
        update = MongoUtils.updateMap(retProxy, proxy, "edmCurrentLocation", ops) || update;
        update = MongoUtils.updateMap(retProxy, proxy, "edmRights", ops) || update;
        update = MongoUtils.updateMap(retProxy, proxy, "edmHasMet", ops) || update;
        update = MongoUtils.updateMap(retProxy, proxy, "edmHasType", ops) || update;
        update = MongoUtils.updateMap(retProxy, proxy, "year", ops) || update;
        update = MongoUtils.updateMap(retProxy, proxy, "edmIsRelatedTo", ops) || update;
        update = MongoUtils.updateArray(retProxy, proxy, "edmIsDerivativeOf", ops) || update;
        update = MongoUtils.updateArray(retProxy, proxy, "edmIsNextInSequence", ops) || update;
        update = MongoUtils.updateArray(retProxy, proxy, "edmIsSimilarTo", ops) || update;
        update = MongoUtils.updateArray(retProxy, proxy, "edmIsSuccessorOf", ops) || update;
        update = MongoUtils.updateArray(retProxy, proxy, "edmWasPresentAt", ops) || update;
        update = MongoUtils.updateArray(retProxy, proxy, "proxyIn", ops) || update;
        update = MongoUtils.updateString(retProxy, proxy, "proxyFor", ops) || update;
        update = MongoUtils.updateString(retProxy, proxy, "edmIsRepresentationOf", ops) || update;

        if (update) {
            mongoServer.getDatastore().update(updateQuery, ops);
        }

        return retProxy;
    }
}
