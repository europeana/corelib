package eu.europeana.corelib.solr.utils.updaters;

import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

import eu.europeana.corelib.solr.MongoServer;
import eu.europeana.corelib.solr.entity.ProxyImpl;
import eu.europeana.corelib.solr.utils.MongoUtils;

public class ProxyUpdater implements Updater<ProxyImpl,ProxyImpl> {
	public void update(ProxyImpl retProxy, ProxyImpl proxy,
			MongoServer mongoServer) {
		Query<ProxyImpl> updateQuery = mongoServer.getDatastore()
				.createQuery(ProxyImpl.class).field("about")
				.equal(proxy.getAbout());
		UpdateOperations<ProxyImpl> ops = mongoServer.getDatastore()
				.createUpdateOperations(ProxyImpl.class);
		boolean update = false;
		if (proxy.getDcContributor() != null) {
			if (retProxy.getDcContributor() == null
					|| !MongoUtils.mapEquals(retProxy.getDcContributor(),
							proxy.getDcContributor())) {
				ops.set("dcContributor", proxy.getDcContributor());
				update = true;
			}
		}
		if (proxy.getDcCoverage() != null) {
			if (retProxy.getDcCoverage() == null
					|| !MongoUtils.mapEquals(retProxy.getDcCoverage(),
							proxy.getDcCoverage())) {
				ops.set("dcCoverage", proxy.getDcCoverage());
				update = true;
			}
		}
		if (proxy.getDcCreator() != null) {
			if (retProxy.getDcCreator() == null
					|| !MongoUtils.mapEquals(retProxy.getDcCreator(),
							proxy.getDcCreator())) {
				ops.set("dcCreator", proxy.getDcCreator());
				update = true;
			}
		}
		if (proxy.getDcDate() != null) {
			if (retProxy.getDcDate() == null
					|| !MongoUtils.mapEquals(retProxy.getDcDate(),
							proxy.getDcDate())) {
				ops.set("dcDate", proxy.getDcDate());
				update = true;
			}
		}
		if (proxy.getDcDescription() != null) {
			if (retProxy.getDcDescription() == null
					|| !MongoUtils.mapEquals(retProxy.getDcDescription(),
							proxy.getDcDescription())) {
				ops.set("dcDescription", proxy.getDcDescription());
				update = true;
			}
		}
		if (proxy.getDcFormat() != null) {
			if (retProxy.getDcFormat() == null
					|| !MongoUtils.mapEquals(retProxy.getDcFormat(),
							proxy.getDcFormat())) {
				ops.set("dcFormat", proxy.getDcFormat());
				update = true;
			}
		}
		if (proxy.getDcIdentifier() != null) {
			if (retProxy.getDcIdentifier() == null
					|| !MongoUtils.mapEquals(retProxy.getDcIdentifier(),
							proxy.getDcIdentifier())) {
				ops.set("dcIdentifier", proxy.getDcIdentifier());
				update = true;
			}
		}
		if (proxy.getDcLanguage() != null) {
			if (retProxy.getDcLanguage() == null
					|| !MongoUtils.mapEquals(retProxy.getDcLanguage(),
							proxy.getDcLanguage())) {
				ops.set("dcLanguage", proxy.getDcLanguage());
				update = true;
			}
		}
		if (proxy.getDcPublisher() != null) {
			if (retProxy.getDcPublisher() == null
					|| !MongoUtils.mapEquals(retProxy.getDcPublisher(),
							proxy.getDcPublisher())) {
				ops.set("dcPublisher", proxy.getDcPublisher());
				update = true;
			}
		}
		if (proxy.getDcRelation() != null) {
			if (retProxy.getDcRelation() == null
					|| !MongoUtils.mapEquals(retProxy.getDcRelation(),
							proxy.getDcRelation())) {
				ops.set("dcRelation", proxy.getDcRelation());
				update = true;
			}
		}
		if (proxy.getDcRights() != null) {
			if (retProxy.getDcRights() == null
					|| !MongoUtils.mapEquals(retProxy.getDcRights(),
							proxy.getDcRights())) {
				ops.set("dcRights", proxy.getDcRights());
				update = true;
			}
		}
		if (proxy.getDcSource() != null) {
			if (retProxy.getDcSource() == null
					|| !MongoUtils.mapEquals(retProxy.getDcSource(),
							proxy.getDcSource())) {
				ops.set("dcSource", proxy.getDcSource());
				update = true;
			}
		}
		if (proxy.getDcSubject() != null) {
			if (retProxy.getDcSubject() == null
					|| !MongoUtils.mapEquals(retProxy.getDcSubject(),
							proxy.getDcSubject())) {
				ops.set("dcSubject", proxy.getDcSubject());
				update = true;
			}
		}
		if (proxy.getDcTitle() != null) {
			if (retProxy.getDcTitle() == null
					|| !MongoUtils.mapEquals(proxy.getDcTitle(),
							retProxy.getDcTitle())) {
				ops.set("dcTitle", proxy.getDcTitle());
				update = true;
			}
		}
		if (proxy.getDcType() != null) {
			if (retProxy.getDcType() == null
					|| !MongoUtils.mapEquals(retProxy.getDcType(),
							proxy.getDcType())) {
				ops.set("dcType", proxy.getDcType());
				update = true;
			}
		}
		if (proxy.getDctermsAlternative() != null) {
			if (retProxy.getDctermsAlternative() == null
					|| !MongoUtils.mapEquals(retProxy.getDctermsAlternative(),
							proxy.getDctermsAlternative())) {
				ops.set("dctermsAlternative", proxy.getDctermsAlternative());
				update = true;
			}
		}
		if (proxy.getDctermsConformsTo() != null) {
			if (retProxy.getDctermsConformsTo() == null
					|| !MongoUtils.mapEquals(retProxy.getDctermsConformsTo(),
							proxy.getDctermsConformsTo())) {
				ops.set("dctermsConformsTo", proxy.getDctermsConformsTo());
				update = true;
			}
		}
		if (proxy.getDctermsCreated() != null) {
			if (retProxy.getDctermsCreated() == null
					|| !MongoUtils.mapEquals(retProxy.getDctermsCreated(),
							proxy.getDctermsCreated())) {
				ops.set("dctermsCreated", proxy.getDctermsCreated());
				update = true;
			}
		}
		if (proxy.getDctermsExtent() != null) {
			if (retProxy.getDctermsExtent() == null
					|| !MongoUtils.mapEquals(retProxy.getDctermsExtent(),
							proxy.getDctermsExtent())) {
				ops.set("dctermsExtent", proxy.getDctermsExtent());
				update = true;
			}
		}
		if (proxy.getDctermsHasFormat() != null) {
			if (retProxy.getDctermsHasFormat() == null
					|| !MongoUtils.mapEquals(retProxy.getDctermsHasFormat(),
							proxy.getDctermsHasFormat())) {
				ops.set("dctermsHasFormat", proxy.getDctermsHasFormat());
				update = true;
			}
		}
		if (proxy.getDctermsHasPart() != null) {
			if (retProxy.getDctermsHasPart() == null
					|| !MongoUtils.mapEquals(retProxy.getDctermsHasPart(),
							proxy.getDctermsHasPart())) {
				ops.set("dctermsHasPart", proxy.getDctermsHasPart());
				update = true;
			}
		}
		if (proxy.getDctermsHasVersion() != null) {
			if (retProxy.getDctermsHasVersion() == null
					|| !MongoUtils.mapEquals(retProxy.getDctermsHasVersion(),
							proxy.getDctermsHasVersion())) {
				ops.set("dctermsHasVersion", proxy.getDctermsHasVersion());
				update = true;
			}

		}
		if (proxy.getDctermsIsFormatOf() != null) {
			if (retProxy.getDctermsIsFormatOf() == null
					|| !MongoUtils.mapEquals(retProxy.getDctermsIsFormatOf(),
							proxy.getDctermsIsFormatOf())) {
				ops.set("dctermsIsFormatOf", proxy.getDctermsIsFormatOf());
				update = true;
			}
		}
		if (proxy.getDctermsIsPartOf() != null) {
			if (retProxy.getDctermsIsPartOf() == null
					|| !MongoUtils.mapEquals(retProxy.getDctermsIsPartOf(),
							proxy.getDctermsIsPartOf())) {
				ops.set("dctermsIsPartOf", proxy.getDctermsIsPartOf());
				update = true;
			}
		}
		if (proxy.getDctermsIsReferencedBy() != null) {
			if (retProxy.getDctermsIsReferencedBy() == null
					|| !MongoUtils.mapEquals(
							retProxy.getDctermsIsReferencedBy(),
							proxy.getDctermsIsReferencedBy())) {
				ops.set("dctermsIsReferencedBy",
						proxy.getDctermsIsReferencedBy());
				update = true;
			}
		}
		if (proxy.getDctermsIsReplacedBy() != null) {
			if (retProxy.getDctermsIsReplacedBy() == null
					|| !MongoUtils.mapEquals(retProxy.getDctermsIsReplacedBy(),
							proxy.getDctermsIsReplacedBy())) {
				ops.set("dctermsIsReplacedBy", proxy.getDctermsIsReplacedBy());
				update = true;
			}
		}
		if (proxy.getDctermsIsRequiredBy() != null) {
			if (retProxy.getDctermsIsRequiredBy() == null
					|| !MongoUtils.mapEquals(retProxy.getDctermsIsRequiredBy(),
							proxy.getDctermsIsRequiredBy())) {
				ops.set("dctermsIsRequiredBy", proxy.getDctermsIsRequiredBy());
				update = true;
			}
		}
		if (proxy.getDctermsIssued() != null) {
			if (retProxy.getDctermsIssued() == null
					|| !MongoUtils.mapEquals(retProxy.getDctermsIssued(),
							proxy.getDctermsIssued())) {
				ops.set("dctermsIssued", proxy.getDctermsIssued());
				update = true;
			}
		}
		if (proxy.getDctermsIsVersionOf() != null) {
			if (retProxy.getDctermsIsVersionOf() == null
					|| !MongoUtils.mapEquals(retProxy.getDctermsIssued(),
							proxy.getDctermsIssued())) {
				ops.set("dctermsIsVersionOf", proxy.getDctermsIsVersionOf());
				update = true;
			}
		}
		if (proxy.getDctermsMedium() != null) {
			if (retProxy.getDctermsMedium() == null
					|| !MongoUtils.mapEquals(retProxy.getDctermsMedium(),
							proxy.getDctermsMedium())) {
				ops.set("dctermsMedium", proxy.getDctermsMedium());
				update = true;
			}
		}
		if (proxy.getDctermsProvenance() != null) {
			if (retProxy.getDctermsProvenance() == null
					|| !MongoUtils.mapEquals(retProxy.getDctermsProvenance(),
							proxy.getDctermsProvenance())) {
				ops.set("dctermsProvenance", proxy.getDctermsProvenance());
				update = true;
			}
		}
		if (proxy.getDctermsReferences() != null) {
			if (retProxy.getDctermsReferences() == null
					|| !MongoUtils.mapEquals(retProxy.getDctermsReferences(),
							proxy.getDctermsReferences())) {
				ops.set("dctermsReferences", proxy.getDctermsReferences());
				update = true;
			}
		}
		if (proxy.getDctermsReplaces() != null) {

			if (retProxy.getDctermsReplaces() == null
					|| !MongoUtils.mapEquals(retProxy.getDctermsReplaces(),
							proxy.getDctermsReplaces())) {
				ops.set("dctermsReplaces", proxy.getDctermsReplaces());
				update = true;
			}
		}
		if (proxy.getDctermsRequires() != null) {
			if (retProxy.getDctermsRequires() == null
					|| !MongoUtils.mapEquals(retProxy.getDctermsRequires(),
							proxy.getDctermsRequires())) {
				ops.set("dctermsRequires", proxy.getDctermsRequires());
				update = true;
			}
		}
		if (proxy.getDctermsSpatial() != null) {
			if (retProxy.getDctermsSpatial() == null
					|| !MongoUtils.mapEquals(retProxy.getDctermsSpatial(),
							proxy.getDctermsSpatial())) {
				ops.set("dctermsSpatial", proxy.getDctermsSpatial());
				update = true;
			}
		}
		if (proxy.getDctermsTOC() != null) {
			if (retProxy.getDctermsTOC() == null
					|| !MongoUtils.mapEquals(retProxy.getDctermsTOC(),
							proxy.getDctermsTOC())) {
				ops.set("dctermsTOC", proxy.getDctermsTOC());
				update = true;
			}
		}
		if (proxy.getDctermsTemporal() != null) {
			if (retProxy.getDctermsTemporal() == null
					|| !MongoUtils.mapEquals(retProxy.getDctermsTemporal(),
							proxy.getDctermsTemporal())) {
				ops.set("dctermsTemporal", proxy.getDctermsTemporal());
				update = true;
			}
		}
		if (proxy.getEdmType() != null) {
			if (retProxy.getEdmType() == null
					|| !retProxy.getEdmType().equals(proxy.getEdmType())) {
				ops.set("edmType", proxy.getEdmType());
				update = true;
			}
		}
		if (proxy.getEdmCurrentLocation() != null) {
			if (retProxy.getEdmCurrentLocation() == null
					|| !retProxy.getEdmCurrentLocation().equals(
							proxy.getEdmCurrentLocation())) {
				ops.set("edmCurrentLocation", proxy.getEdmCurrentLocation());
				update = true;
			}
		}
		if (proxy.getEdmRights() != null) {
			if (retProxy.getEdmRights() != null
					|| !MongoUtils.mapEquals(retProxy.getEdmRights(),
							proxy.getEdmRights())) {
				ops.set("edmRights", proxy.getEdmRights());
				update = true;
			}
		}
		if (proxy.getProxyIn() != null) {
			if (retProxy.getProxyIn() == null
					|| !MongoUtils.arrayEquals(retProxy.getProxyIn(),
							proxy.getProxyIn())) {
				ops.set("proxyIn", proxy.getProxyIn());
				update = true;
			}
		}
		if (proxy.getProxyFor() != null) {
			if (retProxy.getProxyFor() != null
					|| !retProxy.getProxyFor().equals(proxy.getProxyFor())) {
				ops.set("proxyFor", proxy.getProxyFor());
				update = true;
			}
		}
		if (update) {
			mongoServer.getDatastore().update(updateQuery, ops);
		}
	}
}
