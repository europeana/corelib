package eu.europeana.corelib.solr.utils.construct;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

import eu.europeana.corelib.solr.MongoServer;
import eu.europeana.corelib.solr.entity.ProxyImpl;
import eu.europeana.corelib.solr.utils.MongoUtils;

public class ProxyUpdater implements Updater<ProxyImpl> {
	UpdateOperations<ProxyImpl> ops;

	private boolean update(ProxyImpl retProxy,Map<String, List<String>> orig,
			Map<String, List<String>> ret, String field) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Method setter = retProxy.getClass().getMethod(getSetterMethod(field),Map.class);
		if (orig != null) {
			if (ret == null || !MongoUtils.mapEquals(ret, orig)) {
				ops.set(field, orig);
				
				setter.invoke(retProxy, orig);
				return true;
			}
		} else {
			if (ret != null) {
				ops.unset(field);
				setter.invoke(retProxy, new Object[]{null});
				return true;
			}
		}
		return false;
	}

	private String getSetterMethod(String field) {
		return "set"+StringUtils.capitalize(field);
	}

	private boolean updateArray(ProxyImpl retProxy, String[] orig, String[] ret, String field) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Method setter = retProxy.getClass().getMethod(getSetterMethod(field),String[].class);
		if (orig != null) {
			if (ret == null || !MongoUtils.arrayEquals(ret, orig)) {
				ops.set(field, orig);
				setter.invoke(retProxy, orig);
				return true;
			}
		} else {
			if (ret != null) {
				ops.unset(field);
				setter.invoke(retProxy, new Object[]{null});
				return true;
			}
		}
		return false;
	}

	public ProxyImpl update(ProxyImpl retProxy, ProxyImpl proxy,
			MongoServer mongoServer)  {
		Query<ProxyImpl> updateQuery = mongoServer.getDatastore()
				.createQuery(ProxyImpl.class).field("about")
				.equal(proxy.getAbout());
		ops = mongoServer.getDatastore()
				.createUpdateOperations(ProxyImpl.class);
		boolean update = false;
		try {
			update = update(retProxy,
					proxy.getDcContributor() != null ? proxy.getDcContributor()
							: null,
					retProxy.getDcContributor() != null ? retProxy
							.getDcContributor() : null, "dcContributor")
					|| update;
		
		update = update(retProxy,proxy.getDcCoverage() != null ? proxy.getDcCoverage()
				: null,
				retProxy.getDcCoverage() != null ? retProxy.getDcCoverage()
						: null, "dcCoverage")
				|| update;
		update = update(retProxy,proxy.getDcCreator() != null ? proxy.getDcCreator()
				: null,
				retProxy.getDcCreator() != null ? retProxy.getDcCreator()
						: null, "dcCreator")
				|| update;
		update = update(retProxy,proxy.getDcDate() != null ? proxy.getDcDate() : null,
				retProxy.getDcDate() != null ? retProxy.getDcDate() : null,
				"dcDate")
				|| update;
		update = update(retProxy,
				proxy.getDcDescription() != null ? proxy.getDcDescription()
						: null,
				retProxy.getDcDescription() != null ? retProxy
						.getDcDescription() : null, "dcDescription")
				|| update;
		update = update(retProxy,proxy.getDcFormat() != null ? proxy.getDcFormat()
				: null, retProxy.getDcFormat() != null ? retProxy.getDcFormat()
				: null, "dcFormat")
				|| update;
		update = update(retProxy,
				proxy.getDcIdentifier() != null ? proxy.getDcIdentifier()
						: null,
				retProxy.getDcIdentifier() != null ? retProxy.getDcIdentifier()
						: null, "dcIdentifier")
				|| update;
		update = update(retProxy,proxy.getDcLanguage() != null ? proxy.getDcLanguage()
				: null,
				retProxy.getDcLanguage() != null ? retProxy.getDcLanguage()
						: null, "dcLanguage")
				|| update;
		update = update(retProxy,proxy.getDcPublisher() != null ? proxy.getDcPublisher()
				: null,
				retProxy.getDcPublisher() != null ? retProxy.getDcPublisher()
						: null, "dcPublisher")
				|| update;
		update = update(retProxy,proxy.getDcRelation() != null ? proxy.getDcRelation()
				: null,
				retProxy.getDcRelation() != null ? retProxy.getDcRelation()
						: null, "dcRelation")
				|| update;
		update = update(retProxy,proxy.getDcRights() != null ? proxy.getDcRights()
				: null, retProxy.getDcRights() != null ? retProxy.getDcRights()
				: null, "dcRights")
				|| update;
		update = update(retProxy,proxy.getDcSource() != null ? proxy.getDcSource()
				: null, retProxy.getDcSource() != null ? retProxy.getDcSource()
				: null, "dcSource")
				|| update;
		update = update(retProxy,proxy.getDcSubject() != null ? proxy.getDcSubject()
				: null,
				retProxy.getDcSubject() != null ? retProxy.getDcSubject()
						: null, "dcSubject")
				|| update;
		update = update(retProxy,proxy.getDcTitle() != null ? proxy.getDcTitle() : null,
				retProxy.getDcTitle() != null ? retProxy.getDcTitle() : null,
				"dcTitle")
				|| update;
		update = update(retProxy,proxy.getDcType() != null ? proxy.getDcType() : null,
				retProxy.getDcType() != null ? retProxy.getDcType() : null,
				"dcType")
				|| update;
		update = update(retProxy,
				proxy.getDctermsAlternative() != null ? proxy.getDctermsAlternative()
						: null,
				retProxy.getDctermsAlternative() != null ? retProxy
						.getDctermsAlternative() : null, "dctermsAlternative")
				|| update;
		update = update(retProxy,
				proxy.getDctermsConformsTo() != null ? proxy.getDctermsConformsTo()
						: null,
				retProxy.getDctermsConformsTo() != null ? retProxy
						.getDctermsConformsTo() : null, "dctermsConformsTo")
				|| update;
		update = update(retProxy,
				proxy.getDctermsCreated() != null ? proxy.getDctermsCreated()
						: null,
				retProxy.getDctermsCreated() != null ? retProxy
						.getDctermsCreated() : null, "dctermsCreated")
				|| update;
		update = update(retProxy,
				proxy.getDctermsExtent() != null ? proxy.getDctermsExtent()
						: null,
				retProxy.getDctermsExtent() != null ? retProxy
						.getDctermsExtent() : null, "dctermsExtent")
				|| update;
		update = update(retProxy,
				proxy.getDctermsHasFormat() != null ? proxy.getDctermsHasFormat()
						: null,
				retProxy.getDctermsHasFormat() != null ? retProxy
						.getDctermsHasFormat() : null, "dctermsHasFormat")
				|| update;
		update = update(retProxy,
				proxy.getDctermsHasPart() != null ? proxy.getDctermsHasPart()
						: null,
				retProxy.getDctermsHasPart() != null ? retProxy
						.getDctermsHasPart() : null, "dctermsHasPart")
				|| update;
		update = update(retProxy,
				proxy.getDctermsHasVersion() != null ? proxy.getDctermsHasVersion()
						: null,
				retProxy.getDctermsHasVersion() != null ? retProxy
						.getDctermsHasVersion() : null, "dctermsHasVersion")
				|| update;
		update = update(retProxy,
				proxy.getDctermsIsFormatOf() != null ? proxy.getDctermsIsFormatOf()
						: null,
				retProxy.getDctermsIsFormatOf() != null ? retProxy
						.getDctermsIsFormatOf() : null, "dctermsIsFormatOf")
				|| update;
		update = update(retProxy,
				proxy.getDctermsIsPartOf() != null ? proxy.getDctermsIsPartOf()
						: null,
				retProxy.getDctermsIsPartOf() != null ? retProxy
						.getDctermsIsPartOf() : null, "dctermsIsPartOf")
				|| update;
		update = update(retProxy,
				proxy.getDctermsIsReferencedBy() != null ? proxy.getDctermsIsReferencedBy()
						: null,
				retProxy.getDctermsIsReferencedBy() != null ? retProxy
						.getDctermsIsReferencedBy() : null,
				"dctermsIsReferencedBy")
				|| update;
		update = update(retProxy,
				proxy.getDctermsIsReplacedBy() != null ? proxy.getDctermsIsReplacedBy()
						: null,
				retProxy.getDctermsIsReplacedBy() != null ? retProxy
						.getDctermsIsReplacedBy() : null, "dctermsIsReplacedBy")
				|| update;
		update = update(retProxy,
				proxy.getDctermsIsRequiredBy() != null ? proxy.getDctermsIsRequiredBy()
						: null,
				retProxy.getDctermsIsRequiredBy() != null ? retProxy
						.getDctermsIsRequiredBy() : null, "dctermsIsRequiredBy")
				|| update;
		update = update(retProxy,
				proxy.getDctermsIssued() != null ? proxy.getDctermsIssued()
						: null,
				retProxy.getDctermsIssued() != null ? retProxy
						.getDctermsIssued() : null, "dctermsIssued")
				|| update;
		update = update(retProxy,
				proxy.getDctermsIsVersionOf() != null ? proxy.getDctermsIsVersionOf()
						: null,
				retProxy.getDctermsIsVersionOf() != null ? retProxy
						.getDctermsIsVersionOf() : null, "dctermsIsVersionOf")
				|| update;
		update = update(retProxy,
				proxy.getDctermsMedium() != null ? proxy.getDctermsMedium()
						: null,
				retProxy.getDctermsMedium() != null ? retProxy
						.getDctermsMedium() : null, "dctermsMedium")
				|| update;
		update = update(retProxy,
				proxy.getDctermsProvenance() != null ? proxy.getDctermsProvenance()
						: null,
				retProxy.getDctermsProvenance() != null ? retProxy
						.getDctermsProvenance() : null, "dctermsProvenance")
				|| update;
		update = update(retProxy,
				proxy.getDctermsReferences() != null ? proxy.getDctermsReferences()
						: null,
				retProxy.getDctermsReferences() != null ? retProxy
						.getDctermsReferences() : null, "dctermsReferences")
				|| update;
		update = update(retProxy,
				proxy.getDctermsReplaces() != null ? proxy.getDctermsReplaces()
						: null,
				retProxy.getDctermsReplaces() != null ? retProxy
						.getDctermsReplaces() : null, "dctermsReplaces")
				|| update;
		update = update(retProxy,
				proxy.getDctermsRequires() != null ? proxy.getDctermsRequires()
						: null,
				retProxy.getDctermsRequires() != null ? retProxy
						.getDctermsRequires() : null, "dctermsRequires")
				|| update;
		update = update(retProxy,
				proxy.getDctermsSpatial() != null ? proxy.getDctermsSpatial()
						: null,
				retProxy.getDctermsSpatial() != null ? retProxy
						.getDctermsSpatial() : null, "dctermsSpatial")
				|| update;
		update = update(retProxy,proxy.getDctermsTOC() != null ? proxy.getDctermsTOC()
				: null,
				retProxy.getDctermsTOC() != null ? retProxy.getDctermsTOC()
						: null, "dctermsTOC")
				|| update;
		update = update(retProxy,
				proxy.getDctermsTemporal() != null ? proxy.getDctermsTemporal()
						: null,
				retProxy.getDctermsTemporal() != null ? retProxy
						.getDctermsTemporal() : null, "dctermsTemporal")
				|| update;
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
		if (proxy.getEdmCurrentLocation() != null) {
			if (retProxy.getEdmCurrentLocation() == null
					|| !retProxy.getEdmCurrentLocation().equals(
							proxy.getEdmCurrentLocation())) {
				ops.set("edmCurrentLocation", proxy.getEdmCurrentLocation());
				retProxy.setEdmCurrentLocation(proxy.getEdmCurrentLocation());
				update = true;
			}
		} else {
			if (retProxy.getEdmCurrentLocation() != null) {
				ops.unset("edmCurrentLocation");
				retProxy.setEdmCurrentLocation(null);
				update = true;
			}
		}
		update = update(retProxy,proxy.getEdmRights() != null ? proxy.getEdmRights()
				: null,
				retProxy.getEdmRights() != null ? retProxy.getEdmRights()
						: null, "edmRights")
				|| update;
		update = update(retProxy,proxy.getEdmHasMet() != null ? proxy.getEdmHasMet()
				: null,
				retProxy.getEdmHasMet() != null ? retProxy.getEdmHasMet()
						: null, "edmHasMet")
				|| update;
		update = update(retProxy,proxy.getEdmHasType() != null ? proxy.getEdmHasType()
				: null,
				retProxy.getEdmHasType() != null ? retProxy.getEdmHasType()
						: null, "edmHasType")
				|| update;
		update = update(retProxy,
				proxy.getEdmIsRelatedTo() != null ? proxy.getEdmIsRelatedTo()
						: null,
				retProxy.getEdmIsRelatedTo() != null ? retProxy
						.getEdmIsRelatedTo() : null, "edmIsRelatedTo")
				|| update;
		update = updateArray(retProxy,
				proxy.getEdmIsDerivativeOf() != null ? proxy.getEdmIsDerivativeOf()
						: null,
				retProxy.getEdmIsDerivativeOf() != null ? retProxy
						.getEdmIsDerivativeOf() : null, "edmIsDerivativeOf")
				|| update;
		update = updateArray(retProxy,
				proxy.getEdmIsNextInSequence() != null ? proxy.getEdmIsNextInSequence()
						: null,
				retProxy.getEdmIsNextInSequence() != null ? retProxy
						.getEdmIsNextInSequence() : null, "edmIsNextInSequence")
				|| update;
		update = updateArray(retProxy,
				proxy.getEdmIsSimilarTo() != null ? proxy.getEdmIsSimilarTo()
						: null,
				retProxy.getEdmIsSimilarTo() != null ? retProxy
						.getEdmIsSimilarTo() : null, "edmIsSimilarTo")
				|| update;
		update = updateArray(retProxy,
				proxy.getEdmIsSuccessorOf() != null ? proxy.getEdmIsSuccessorOf()
						: null,
				retProxy.getEdmIsSuccessorOf() != null ? retProxy
						.getEdmIsSuccessorOf() : null, "edmIsSuccessorOf")
				|| update;
		update = updateArray(retProxy,
				proxy.getEdmWasPresentAt() != null ? proxy.getEdmWasPresentAt()
						: null,
				retProxy.getEdmWasPresentAt() != null ? retProxy
						.getEdmWasPresentAt() : null, "edmWasPresentAt")
				|| update;
		update = updateArray(retProxy,proxy.getProxyIn() != null ? proxy.getProxyIn()
				: null, retProxy.getProxyIn() != null ? retProxy.getProxyIn()
				: null, "proxyIn")
				|| update;
		if (proxy.getProxyFor() != null) {
			if (retProxy.getProxyFor() == null
					|| !retProxy.getProxyFor().equals(proxy.getProxyFor())) {
				ops.set("proxyFor", proxy.getProxyFor());
				retProxy.setProxyFor(proxy.getProxyFor());
				update = true;
			}
		} else {
			if (retProxy.getProxyFor() != null) {
				ops.unset("proxyFor");
				retProxy.setProxyFor(null);
				update = true;
			}
		}
		if (proxy.getEdmIsRepresentationOf() != null) {
			if (retProxy.getEdmIsRepresentationOf() == null
					|| !retProxy.getEdmIsRepresentationOf().equals(
							proxy.getEdmIsRepresentationOf())) {
				ops.set("edmIsRepresentationOf",
						proxy.getEdmIsRepresentationOf());
				retProxy.setEdmIsRepresentationOf(proxy.getEdmIsRepresentationOf());
				update = true;
			}
		} else {
			if (retProxy.getEdmIsRepresentationOf() != null) {
				ops.unset("edmIsRepresentationOf");
				retProxy.setEdmIsRepresentationOf(null);
				update = true;
			}
		}
		if (update) {
			mongoServer.getDatastore().update(updateQuery, ops);
		}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return retProxy;
	}
}
