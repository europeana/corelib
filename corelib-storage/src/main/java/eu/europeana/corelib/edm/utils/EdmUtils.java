package eu.europeana.corelib.edm.utils;

import eu.europeana.corelib.definitions.edm.entity.EuropeanaAggregation;
import eu.europeana.corelib.definitions.edm.entity.Place;
import eu.europeana.corelib.definitions.edm.entity.Timespan;
import eu.europeana.metis.schema.jibx.*;
import eu.europeana.metis.schema.jibx.Date;
import eu.europeana.metis.schema.jibx.ResourceOrLiteralType.Resource;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import eu.europeana.corelib.solr.entity.*;
import eu.europeana.corelib.utils.DateUtils;
import eu.europeana.corelib.utils.EuropeanaUriUtils;
import eu.europeana.corelib.utils.StringArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.JiBXException;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;

/**
 * Convert a FullBean to EDM
 *
 * @author Yorgos.Mamakis@ kb.nl
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public final class EdmUtils {

    public static final String DEFAULT_LANGUAGE = "def";

    private static final Logger LOG = LogManager.getLogger(EdmUtils.class);

    private static final String BASE_URL = "http://data.europeana.eu";

    private static IBindingFactory bfact;

    private EdmUtils() {
        // empty constructor to prevent initialization
    }

    static {
        try {
            bfact = BindingDirectory.getFactory(RDF.class);
        } catch (JiBXException e) {
            LOG.error("Error initializing EdmUtils binding with JBIX classes", e);
        }
    }


    /**
     * Convert a FullBean to an EDM String
     *
     * @param fullBean The FullBean to convert
     * @return The resulting EDM string in RDF-XML
     */
    public static synchronized String toEDM(FullBeanImpl fullBean) {
        RDF rdf = toRDF(fullBean);
        return marshallToEDM(rdf);
    }

    /**
     * Convert an RDF object to an EDM String
     *
     * @param rdf The RDF to convert
     * @return The resulting EDM string in RDF-XML
     */
    public static synchronized String toEDM(RDF rdf) {
        if (rdf == null) {
            return null;
        }
        return marshallToEDM(rdf);
    }

    private static String marshallToEDM(RDF rdf) {
        IMarshallingContext marshallingContext;
        try (StringWriter out = new StringWriter()){
            marshallingContext = bfact.createMarshallingContext();
            marshallingContext.setOutput(out, EuropeanaUTF8Escaper.s_instance);
            marshallingContext.marshalDocument(rdf, "UTF-8", true);
            return out.toString();
        } catch (JiBXException | IOException e) {
            String id = null;
            if (rdf != null && rdf.getProvidedCHOList() != null && !rdf.getProvidedCHOList().isEmpty()) {
                id = rdf.getProvidedCHOList().get(0).getAbout();
            }
            LOG.error("Error marshalling RDF of record {}", id, e);
        }
        return null;
    }

    /**
     * Convert a FullBean to an RDF object
     * @param fullBean the fullbean to convert
     * @return RDF object
     */
    public static synchronized RDF toRDF(FullBeanImpl fullBean) {
      return toRDF(fullBean, false);
    }

    /**
     * Convert a FullBean to an RDF object
     * @param fullBean the fullbean to convert
     * @param preserveIdentifiers if true does not change the identifiers of entities, if false it
     *
     *  NOTE : for re-indexing the preserveIdentifiers is set to true. We dO NOT chnage the record value during re-indexing
     *
     * will add the {@link #BASE_URL} as prefix if it's not already an uri}.
     * @return RDF object
     */
    public static synchronized RDF toRDF(FullBeanImpl fullBean, boolean preserveIdentifiers) {
        RDF rdf = new RDF();
        String type = getType(fullBean);
        appendCHO(rdf, fullBean.getProvidedCHOs(), preserveIdentifiers);
       // appendQualityAnnotations(rdf, fullBean.getQualityAnnotations(), preserveIdentifiers);
        appendAggregation(rdf, fullBean.getAggregations(), fullBean.getQualityAnnotations(), preserveIdentifiers);
        appendProxy(rdf, fullBean.getProxies(), type, preserveIdentifiers);
        appendEuropeanaAggregation(rdf, fullBean, preserveIdentifiers);
        appendOrganisations(rdf, fullBean.getOrganizations());
        appendAgents(rdf, fullBean.getAgents());
        appendConcepts(rdf, fullBean.getConcepts());
        appendPlaces(rdf, fullBean.getPlaces());
        appendTimespans(rdf, fullBean.getTimespans());
        appendLicenses(rdf, fullBean.getLicenses(), preserveIdentifiers);
        appendServices(rdf, fullBean.getServices());
        return rdf;
    }

    private static void appendServices(RDF rdf, List<ServiceImpl> services) {
        if (services != null) {
            List<Service> serviceList = new ArrayList<>();
            for (ServiceImpl serv : services) {
                Service service = new Service();
                service.setAbout(serv.getAbout());
                //addAsList(service, ConformsTo.class, serv.getDctermsConformsTo());

                if(serv.getDctermsConformsTo() !=null && serv.getDctermsConformsTo().length>0){
                    List<ConformsTo> conformsToList = new ArrayList<>();

                    for(String conformsTo : serv.getDctermsConformsTo()){
                        if(StringUtils.isNotEmpty(conformsTo)) {
                            ConformsTo cTo = new ConformsTo();
                            ResourceOrLiteralType.Resource res = new Resource();
                            res.setResource(conformsTo);
                            cTo.setString("");
                            cTo.setLang(null);
                            cTo.setResource(res);
                            conformsToList.add(cTo);
                        }
                    }
                    if(!conformsToList.isEmpty()){
                        service.setConformsToList(conformsToList);
                    }
                }

                if (serv.getDoapImplements() != null && serv.getDoapImplements().length > 0){
                    List<Implements> implementsList = new ArrayList<>();

                    for(String doapImplements : serv.getDoapImplements()){
                        if(StringUtils.isNotEmpty(doapImplements)) {
                            Implements anImplements = new Implements();
                            anImplements.setResource(doapImplements);
                            implementsList.add(anImplements);
                        }
                    }
                    if(!implementsList.isEmpty()){
                        service.setImplementList(implementsList);
                    }
                }

                serviceList.add(service);
            }
            rdf.setServiceList(serviceList);
        }
    }

    private static void appendLicenses(RDF rdf, List<LicenseImpl> licenses,
        boolean preserveIdentifiers) {
        if (licenses != null) {
            List<License> licenseList = new ArrayList<>();
            for (LicenseImpl lic : licenses) {
                License license = new License();
                license.setAbout(lic.getAbout());
                addAsObject(license, InheritFrom.class, lic.getOdrlInheritFrom(), preserveIdentifiers);
                if (lic.getCcDeprecatedOn() != null) {
                    DateType dateType = new DateType();
                    dateType.setDate(new java.sql.Date(lic.getCcDeprecatedOn().getTime()));
                    license.setDeprecatedOn(dateType);
                }
                licenseList.add(license);
            }
            rdf.setLicenseList(licenseList);
        }

    }

    // get the type from the main proxy
    private static String getType(FullBeanImpl fullBean) {
        for (ProxyImpl prx : fullBean.getProxies()) {
            if (!prx.isEuropeanaProxy() && prx.getEdmType() != null) {
                return prx.getEdmType();
            }
        }
        return null;
    }

    private static void appendTimespans(RDF rdf, List<TimespanImpl> timespans) {
        if (timespans != null) {
            List<TimeSpanType> timespanList = new ArrayList<>();
            for (Timespan ts : timespans) {
                TimeSpanType timeSpan = new TimeSpanType();
                timeSpan.setAbout(ts.getAbout());
                addAsList(timeSpan, AltLabel.class, ts.getAltLabel());
                addAsObject(timeSpan, Begin.class, ts.getBegin());
                addAsObject(timeSpan, End.class, ts.getEnd());
                addAsList(timeSpan, HasPart.class, ts.getDctermsHasPart());
                addAsList(timeSpan, IsPartOf.class, ts.getIsPartOf());
                addAsList(timeSpan, Note.class, ts.getNote());
                addAsList(timeSpan, SameAs.class, ts.getOwlSameAs());
                addAsList(timeSpan, PrefLabel.class, ts.getPrefLabel());
                timespanList.add(timeSpan);
            }
            rdf.setTimeSpanList(timespanList);
        }
    }

    private static void appendPlaces(RDF rdf, List<PlaceImpl> places) {
        if (places != null) {
            List<PlaceType> placeList = new ArrayList<>();
            for (Place place : places) {
                PlaceType pType = new PlaceType();
                pType.setAbout(place.getAbout());

                if (place.getAltitude() != null && place.getAltitude() != 0) {
                    Alt alt = new Alt();
                    alt.setAlt(place.getAltitude());
                    pType.setAlt(alt);
                }

                if (place.getLatitude() != null && place.getLongitude() != null
                        && (place.getLatitude() != 0 && place.getLongitude() != 0)) {
                    Lat lat = new Lat();
                    lat.setLat(place.getLatitude());
                    pType.setLat(lat);

                    _Long l = new _Long();
                    l.setLong(place.getLongitude());
                    pType.setLong(l);
                }
                addAsList(pType, AltLabel.class, place.getAltLabel());
                addAsList(pType, HasPart.class, place.getDcTermsHasPart());
                addAsList(pType, IsPartOf.class, place.getIsPartOf());
                addAsList(pType, Note.class, place.getNote());
                addAsList(pType, PrefLabel.class, place.getPrefLabel());
                addAsList(pType, SameAs.class, place.getOwlSameAs());
                placeList.add(pType);
            }
            rdf.setPlaceList(placeList);
        }
    }

    private static void appendConcepts(RDF rdf, List<ConceptImpl> concepts) {
        if (concepts != null) {
            List<Concept> conceptList = new ArrayList<>();
            for (ConceptImpl concept : concepts) {
                Concept con = new Concept();
                con.setAbout(concept.getAbout());
                List<Concept.Choice> choices = new ArrayList<>();

                addConceptChoice(choices, AltLabel.class, concept.getAltLabel());
                addConceptChoice(choices, PrefLabel.class, concept.getPrefLabel());
                addConceptChoice(choices, Notation.class, concept.getNotation());
                addConceptChoice(choices, Note.class, concept.getNote());
                addConceptChoice(choices, Broader.class, concept.getBroader());
                addConceptChoice(choices, BroadMatch.class, concept.getBroadMatch());
                addConceptChoice(choices, CloseMatch.class, concept.getCloseMatch());
                addConceptChoice(choices, ExactMatch.class, concept.getExactMatch());
                addConceptChoice(choices, InScheme.class, concept.getInScheme());
                addConceptChoice(choices, Narrower.class, concept.getNarrower());
                addConceptChoice(choices, NarrowMatch.class, concept.getNarrowMatch());
                addConceptChoice(choices, RelatedMatch.class, concept.getRelatedMatch());
                addConceptChoice(choices, Related.class, concept.getRelated());

                con.setChoiceList(choices);
                conceptList.add(con);
            }
            rdf.setConceptList(conceptList);
        }
    }

    private static void appendEuropeanaAggregation(RDF rdf, FullBeanImpl fBean, boolean preserveIdentifiers) {
        EuropeanaAggregationType aggregation = new EuropeanaAggregationType();
        EuropeanaAggregation europeanaAggregation = fBean.getEuropeanaAggregation();
        if (preserveIdentifiers) {
            aggregation.setAbout(europeanaAggregation.getAbout());
        } else {
            aggregation.setAbout(getBaseUrl(europeanaAggregation.getAbout()));
        }

        if (!addAsObject(aggregation, AggregatedCHO.class, europeanaAggregation.getAggregatedCHO(), preserveIdentifiers)) {
            AggregatedCHO agCHO = new AggregatedCHO();
            if (preserveIdentifiers) {
                agCHO.setResource(fBean.getProvidedCHOs().get(0).getAbout());
            } else {
                agCHO.setResource(getBaseUrl(fBean.getProvidedCHOs().get(0).getAbout()));
            }
            aggregation.setAggregatedCHO(agCHO);
        }
        addAsList(aggregation, Aggregates.class, europeanaAggregation.getAggregates());
        DatasetName datasetName = new DatasetName();
        datasetName.setString(fBean.getEuropeanaCollectionName()[0]);
        aggregation.setDatasetName(datasetName);
        // TODO country will be removed once Organizations are fully implemented
        Country country = convertMapToCountry(europeanaAggregation.getEdmCountry());
        if (country != null) {
            aggregation.setCountry(country);
        }
        addAsObject(aggregation, Creator.class, europeanaAggregation.getDcCreator());
        addAsList(aggregation, HasView.class, europeanaAggregation.getEdmHasView());
        addAsObject(aggregation, IsShownBy.class, europeanaAggregation.getEdmIsShownBy(), preserveIdentifiers);
        addAsObject(aggregation, LandingPage.class, europeanaAggregation.getEdmLandingPage(), preserveIdentifiers);
        Language1 language = convertMapToLanguage(europeanaAggregation.getEdmLanguage());
        if (language != null) {
            aggregation.setLanguage(language);
        }
        addAsObject(aggregation, Preview.class, europeanaAggregation.getEdmPreview(), preserveIdentifiers);
        addAsObject(aggregation, Rights1.class, europeanaAggregation.getEdmRights());
        Completeness completeness = new Completeness();
        completeness.setString(Integer.toString(fBean.getEuropeanaCompleteness()));
        aggregation.setCompleteness(completeness);

        // EA-3809 and MET 5556 add QA in europeana aggregation
        appendQualityAnnotationsToEuropeanaAggregation(aggregation, europeanaAggregation, fBean, preserveIdentifiers);

        Created created = new Created();
        created.setString(DateUtils.format(fBean.getTimestampCreated()));
        aggregation.setCreated(created);

        Modified modified = new Modified();
        modified.setString(DateUtils.format(fBean.getTimestampUpdated()));
        aggregation.setModified(modified);

        List<EuropeanaAggregationType> lst = new ArrayList<>();
        lst.add(aggregation);
        rdf.setEuropeanaAggregationList(lst);
    }

    /**
     * Add QA in europeana Aggregation if DqvHasQualityAnnotation is present
     *
     * DqvHasQualityAnnotation contains the references of the QA present in the bean
     * match the dqvHasQualityAnnotation value with qualityAnnotation#about field
     *
     * @param aggregation Aggregation object for the response
     * @param europeanaAggregation Europeana Aggreagation fetched from DB
     * @param fBean fullbean
     * @param preserveIdentifiers
     */
    private static void appendQualityAnnotationsToEuropeanaAggregation(EuropeanaAggregationType aggregation,
                                                                       EuropeanaAggregation europeanaAggregation,
                                                                       FullBeanImpl fBean,
                                                                       boolean preserveIdentifiers) {
        // if Europeana Aggregation doesn't have DqvHasQualityAnnotation no need to add QA
        // OR if there are non QA present in the record
        if (europeanaAggregation.getDqvHasQualityAnnotation() == null || fBean.getQualityAnnotations() == null) {
            return;
        }

        List<? extends eu.europeana.corelib.definitions.edm.entity.QualityAnnotation> fBeanQA = fBean.getQualityAnnotations();
        if (europeanaAggregation.getDqvHasQualityAnnotation() != null) {
            List<HasQualityAnnotation> qualityAnnotations = new ArrayList<>();

            for (String anno : europeanaAggregation.getDqvHasQualityAnnotation()) {
                for (eu.europeana.corelib.definitions.edm.entity.QualityAnnotation qa : fBeanQA) {
                    if (StringUtils.equals(anno, qa.getAbout())) {
                        QualityAnnotation qualityAnnotation = new QualityAnnotation();

                        Created created = new Created();
                        created.setString(qa.getCreated());
                        qualityAnnotation.setCreated(created);

                        HasBody hasBody = new HasBody();
                        hasBody.setResource(qa.getBody());
                        qualityAnnotation.setHasBody(hasBody);

                        // value of target - will the 'about' field of europeana aggregation ('http://data.europeana.eu/aggregation/europeana/RECORD_ID')
                        String[] about = new String[] {europeanaAggregation.getAbout()};
                        // this for cases we do not want to append or change the values with a base urls. Mostly used in re-indexing
                        if (preserveIdentifiers) {
                            addAsList(qualityAnnotation, HasTarget.class, about );
                        } else {
                            addAsList(qualityAnnotation, HasTarget.class, about, null, true);
                        }
                        HasQualityAnnotation hasQualityAnnotation = new HasQualityAnnotation();
                        hasQualityAnnotation.setQualityAnnotation(qualityAnnotation);
                        qualityAnnotations.add(hasQualityAnnotation);
                    }
                }
            }
            aggregation.setHasQualityAnnotationList(qualityAnnotations);
        }
    }

    private static Language1 convertMapToLanguage(Map<String, List<String>> edmLanguage) {
        if (edmLanguage != null && edmLanguage.size() > 0) {
            Language1 lang = new Language1();
            lang.setLanguage(LanguageCodes.convert(edmLanguage.entrySet().iterator().next().getValue().get(0)));
            return lang;
        }
        return null;
    }

    private static Country convertMapToCountry(Map<String, List<String>> edmCountry) {
        // there should be only 1 country at most
        if (edmCountry != null && edmCountry.size() > 0) {
            return CountryUtils.convertToJibxCountry(edmCountry.entrySet().iterator().next().getValue().get(0));
        }
        return null;
    }



    private static void appendProxy(RDF rdf, List<ProxyImpl> proxies, String typeStr,
        boolean preserveIdentifiers) {
        List<ProxyType> proxyList = new ArrayList<>();
        for (ProxyImpl prx : proxies) {
            ProxyType proxy = new ProxyType();
            if (preserveIdentifiers) {
                proxy.setAbout(prx.getAbout());
            } else {
                proxy.setAbout(getBaseUrl(prx.getAbout()));
            }
            EuropeanaProxy europeanaProxy = new EuropeanaProxy();
            europeanaProxy.setEuropeanaProxy(prx.isEuropeanaProxy());
            proxy.setEuropeanaProxy(europeanaProxy);
            List<IsNextInSequence> nis = null;

            String[] seqArray = prx.getEdmIsNextInSequence();

            if (seqArray != null) {
                nis = new ArrayList<>();
    
                for (String s : seqArray) {
                    IsNextInSequence item = new IsNextInSequence();
                    item.setResource(s);
                    nis.add(item);
                }
            }

            if (nis != null) {
                proxy.setIsNextInSequenceList(nis);
            }

            String[] pIn = prx.getProxyIn();
            List<ProxyIn> pInList = null;
            if (pIn != null) {
                pInList = new ArrayList<>();
                for (String s : pIn) {
                    ProxyIn proxyIn = new ProxyIn();
                    if (preserveIdentifiers) {
                        proxyIn.setResource(s);
                    } else {
                        proxyIn.setResource(getBaseUrl(s));
                    }
                    pInList.add(proxyIn);
                }
            }
            if (pInList != null) {
                proxy.setProxyInList(pInList);
            }
            Type2 type = new Type2();
            type.setType(EdmType.valueOf(typeStr.replace("3D", "_3_D")));
            proxy.setType(type);

            addAsObject(proxy, CurrentLocation.class, prx.getEdmCurrentLocation());
            // this for cases we don not want to append or change the values with a base urls. Mostly used in re-indexing
            if (preserveIdentifiers) {
                addAsList(proxy, Lineage.class, prx.getLineage());
            } else {
                addAsList(proxy, Lineage.class, prx.getLineage(), null, true);
            }

            addAsList(proxy, HasMet.class, prx.getEdmHasMet());
            addAsList(proxy, HasType.class, prx.getEdmHasType());
            addAsList(proxy, Incorporates.class, prx.getEdmIncorporates());
            addAsList(proxy, IsDerivativeOf.class, prx.getEdmIsDerivativeOf());
            addAsList(proxy, IsRelatedTo.class, prx.getEdmIsRelatedTo());
            addAsObject(proxy, IsRepresentationOf.class, prx.getEdmIsRepresentationOf(), preserveIdentifiers);
            addAsList(proxy, IsSimilarTo.class, prx.getEdmIsSimilarTo());
            addAsList(proxy, IsSuccessorOf.class, prx.getEdmIsSuccessorOf());
            addAsList(proxy, Realizes.class, prx.getEdmRealizes());
            addAsObject(proxy, ProxyFor.class, preserveIdentifiers?prx.getProxyFor():getBaseUrl(prx.getProxyFor()), preserveIdentifiers);
            addAsList(proxy, Year.class, prx.getYear());

            List<EuropeanaType.Choice> dcChoices = new ArrayList<>();
            addEuropeanaTypeChoice(dcChoices, Contributor.class, prx.getDcContributor());
            addEuropeanaTypeChoice(dcChoices, Coverage.class, prx.getDcCoverage());
            addEuropeanaTypeChoice(dcChoices, Creator.class, prx.getDcCreator());
            addEuropeanaTypeChoice(dcChoices, Date.class, prx.getDcDate());
            addEuropeanaTypeChoice(dcChoices, Description.class, prx.getDcDescription());
            addEuropeanaTypeChoice(dcChoices, Format.class, prx.getDcFormat());
            addEuropeanaTypeChoiceLiteral(dcChoices, Identifier.class, prx.getDcIdentifier());
            addEuropeanaTypeChoice(dcChoices, Publisher.class, prx.getDcPublisher());
            addEuropeanaTypeChoiceLiteral(dcChoices, Language.class, prx.getDcLanguage());
            addEuropeanaTypeChoice(dcChoices, Relation.class, prx.getDcRelation());
            addEuropeanaTypeChoice(dcChoices, Rights.class, prx.getDcRights());
            addEuropeanaTypeChoice(dcChoices, Source.class, prx.getDcSource());
            addEuropeanaTypeChoice(dcChoices, Subject.class, prx.getDcSubject());
            addEuropeanaTypeChoiceLiteral(dcChoices, Title.class, prx.getDcTitle());
            addEuropeanaTypeChoice(dcChoices, Type.class, prx.getDcType());
            addEuropeanaTypeChoiceLiteral(dcChoices, Alternative.class, prx.getDctermsAlternative());
            addEuropeanaTypeChoice(dcChoices, ConformsTo.class, prx.getDctermsConformsTo());
            addEuropeanaTypeChoice(dcChoices, Created.class, prx.getDctermsCreated());
            addEuropeanaTypeChoice(dcChoices, Extent.class, prx.getDctermsExtent());
            addEuropeanaTypeChoice(dcChoices, HasFormat.class, prx.getDctermsHasFormat());
            addEuropeanaTypeChoice(dcChoices, HasPart.class, prx.getDctermsHasPart());
            addEuropeanaTypeChoice(dcChoices, HasVersion.class, prx.getDctermsHasVersion());
            addEuropeanaTypeChoice(dcChoices, IsFormatOf.class, prx.getDctermsIsFormatOf());
            addEuropeanaTypeChoice(dcChoices, IsPartOf.class, prx.getDctermsIsPartOf());
            addEuropeanaTypeChoice(dcChoices, IsReferencedBy.class, prx.getDctermsIsReferencedBy());
            addEuropeanaTypeChoice(dcChoices, IsReplacedBy.class, prx.getDctermsIsReplacedBy());
            addEuropeanaTypeChoice(dcChoices, Issued.class, prx.getDctermsIssued());
            addEuropeanaTypeChoice(dcChoices, IsRequiredBy.class, prx.getDctermsIsRequiredBy());
            addEuropeanaTypeChoice(dcChoices, IsVersionOf.class, prx.getDctermsIsVersionOf());
            addEuropeanaTypeChoice(dcChoices, Medium.class, prx.getDctermsMedium());
            addEuropeanaTypeChoice(dcChoices, Provenance.class, prx.getDctermsProvenance());
            addEuropeanaTypeChoice(dcChoices, References.class, prx.getDctermsReferences());
            addEuropeanaTypeChoice(dcChoices, Replaces.class, prx.getDctermsReplaces());
            addEuropeanaTypeChoice(dcChoices, Requires.class, prx.getDctermsRequires());
            addEuropeanaTypeChoice(dcChoices, Spatial.class, prx.getDctermsSpatial());
            addEuropeanaTypeChoice(dcChoices, Temporal.class, prx.getDctermsTemporal());
            addEuropeanaTypeChoice(dcChoices, TableOfContents.class, prx.getDctermsTOC());

            proxy.setChoiceList(dcChoices);
            proxyList.add(proxy);
        }

        rdf.setProxyList(proxyList);
    }

    private static void appendAggregation(RDF rdf, List<AggregationImpl> aggregations,
                                          List<? extends eu.europeana.corelib.definitions.edm.entity.QualityAnnotation> qualityAnnotations,
        boolean preserveIdentifiers) {
        List<Aggregation> aggregationList = new ArrayList<>();
        for (AggregationImpl aggr : aggregations) {
            Aggregation aggregation = new Aggregation();
            if (preserveIdentifiers) {
                aggregation.setAbout(aggr.getAbout());
            } else {
                aggregation.setAbout(getBaseUrl(aggr.getAbout()));
            }
            if (!addAsObject(aggregation, AggregatedCHO.class, aggr.getAggregatedCHO(), preserveIdentifiers)) {
                AggregatedCHO cho = new AggregatedCHO();
                if (preserveIdentifiers) {
                    cho.setResource(rdf.getProvidedCHOList().get(0).getAbout());
                } else {
                    cho.setResource(getBaseUrl(rdf.getProvidedCHOList().get(0).getAbout()));
                }
                aggregation.setAggregatedCHO(cho);
            }
            if (!addAsObject(aggregation, DataProvider.class, aggr.getEdmDataProvider())) {
                addAsObject(aggregation, DataProvider.class, aggr.getEdmProvider());
            }
            addAsObject(aggregation, IsShownAt.class, aggr.getEdmIsShownAt(), preserveIdentifiers);
            addAsObject(aggregation, IsShownBy.class, aggr.getEdmIsShownBy(), preserveIdentifiers);
            addAsObject(aggregation, _Object.class, aggr.getEdmObject(), preserveIdentifiers);
            addAsObject(aggregation, Provider.class, aggr.getEdmProvider());
            addAsObject(aggregation, Rights1.class, aggr.getEdmRights());
            addAsList(aggregation, IntermediateProvider.class, aggr.getEdmIntermediateProvider());

            if (aggr.getEdmUgc() != null && !"false".equalsIgnoreCase(aggr.getEdmUgc())) {
                Ugc ugc = new Ugc();
                ugc.setUgc(UGCType.valueOf(StringUtils.upperCase(aggr.getEdmUgc())));
                aggregation.setUgc(ugc);
            }
            addAsList(aggregation, Rights.class, aggr.getDcRights());
            addAsList(aggregation, HasView.class, aggr.getHasView());

            // EA-3652 add quality annotations in aggregation.
            appendQualityAnnotationsToAggregation(aggregation, aggr.getAbout(), qualityAnnotations, preserveIdentifiers);

            aggregationList.add(aggregation);
            if (aggr.getWebResources() != null && !aggr.getWebResources().isEmpty()) {
                EdmWebResourceUtils.createWebResources(rdf, aggr, preserveIdentifiers);
            }
        }
        rdf.setAggregationList(aggregationList);
    }

    /**
     * Append quality annotation to Aggregation
     * See - EA-3652 and MET-5632
     * Previously Aggregation object did not contain tier calculation information.
     * The change is to insert the quality annotations here
     * NOTE : the target field corresponds with about field of the Aggregation.
     *
     * @param aggregation
     * @param qualityAnnotations
     * @param preserveIdentifiers
     */
    private static void appendQualityAnnotationsToAggregation(Aggregation aggregation, String about,
                                                 List<? extends eu.europeana.corelib.definitions.edm.entity.QualityAnnotation> qualityAnnotations,
                                                 boolean preserveIdentifiers) {
        if (qualityAnnotations != null) {
            List<HasQualityAnnotation> resultList = new ArrayList<>();

            for (eu.europeana.corelib.definitions.edm.entity.QualityAnnotation anno : qualityAnnotations) {
                // aggregation.getAbout() might have a BASE_URL appended depending on the preserveIdentifiers value
                // hence we have about field to match the target values
                if (StringUtils.equals(about, anno.getTarget()[0])) {
                    QualityAnnotation qualityAnnotation = new QualityAnnotation();

                    Created created = new Created();
                    created.setString(anno.getCreated());
                    qualityAnnotation.setCreated(created);

                    HasBody hasBody = new HasBody();
                    hasBody.setResource(anno.getBody());
                    qualityAnnotation.setHasBody(hasBody);

                    // this for cases we don not want to append or change the values with a base urls. Mostly used in re-indexing
                    if (preserveIdentifiers) {
                        addAsList(qualityAnnotation, HasTarget.class, anno.getTarget());
                    } else {
                        addAsList(qualityAnnotation, HasTarget.class, anno.getTarget(), null, true);
                    }

                    HasQualityAnnotation hasQualityAnnotation = new HasQualityAnnotation();
                    hasQualityAnnotation.setQualityAnnotation(qualityAnnotation);
                    resultList.add(hasQualityAnnotation);
                }
            }
            aggregation.setHasQualityAnnotationList(resultList);
        }
    }


    private static void appendCHO(RDF rdf, List<ProvidedCHOImpl> chos, boolean preserveIdentifiers) {
        List<ProvidedCHOType> pChoList = new ArrayList<>();
        for (ProvidedCHOImpl pCho : chos) {
            ProvidedCHOType pChoJibx = new ProvidedCHOType();
            if (preserveIdentifiers) {
                pChoJibx.setAbout(pCho.getAbout());
            } else {
                pChoJibx.setAbout(getBaseUrl(pCho.getAbout()));
            }

            addAsList(pChoJibx, SameAs.class, pCho.getOwlSameAs());
            pChoList.add(pChoJibx);
        }
        rdf.setProvidedCHOList(pChoList);
    }

    private static void appendOrganisations(RDF rdf, List<OrganizationImpl> organisations) {
        if (organisations != null) {
            List<Organization> organizationList = new ArrayList<>();
            for (OrganizationImpl source : organisations) {
                Organization target = new Organization();
                target.setAbout(source.getAbout());
                addAsList(target, PrefLabel.class, source.getPrefLabel());
                // TODO for now we only add about and preflabel fields since nothing else is stored in Mongo
                organizationList.add(target);
            }
            rdf.setOrganizationList(organizationList);
        }
    }

    private static void appendAgents(RDF rdf, List<AgentImpl> agents) {
        if (agents != null) {
            List<AgentType> agentList = new ArrayList<>();

            for (AgentImpl ag : agents) {
                AgentType agent = new AgentType();
                agent.setAbout(ag.getAbout());
                addAsList(agent, AltLabel.class, ag.getAltLabel());
                addAsObject(agent, Begin.class, ag.getBegin());
                addAsObject(agent, End.class, ag.getEnd());
                addAsList(agent, BiographicalInformation.class, ag.getRdaGr2BiographicalInformation());
                addAsList(agent, Date.class, ag.getDcDate());
                addAsObject(agent, DateOfBirth.class, ag.getRdaGr2DateOfBirth());
                addAsObject(agent, DateOfDeath.class, ag.getRdaGr2DateOfDeath());
                addAsList(agent, PlaceOfBirth.class, ag.getRdaGr2PlaceOfBirth());
                addAsList(agent, PlaceOfDeath.class, ag.getRdaGr2PlaceOfDeath());
                addAsObject(agent, DateOfEstablishment.class, ag.getRdaGr2DateOfEstablishment());
                addAsObject(agent, DateOfTermination.class, ag.getRdaGr2DateOfTermination());
                addAsObject(agent, Gender.class, ag.getRdaGr2Gender());
                addAsList(agent, HasMet.class, ag.getEdmHasMet());
                addAsList(agent, Identifier.class, ag.getDcIdentifier());
                addAsList(agent, IsRelatedTo.class, ag.getEdmIsRelatedTo());
                addAsList(agent, Name.class, ag.getFoafName());
                addAsList(agent, Note.class, ag.getNote());
                addAsList(agent, PrefLabel.class, ag.getPrefLabel());
                addAsList(agent, ProfessionOrOccupation.class, ag.getRdaGr2ProfessionOrOccupation());
                addAsList(agent, SameAs.class, ag.getOwlSameAs());
                agentList.add(agent);
            }
            rdf.setAgentList(agentList);
        }
    }

    private static void addConceptChoice(List<Concept.Choice> choices,
             Class<? extends LiteralType> clazz, Map<String, List<String>> map) {
        if ((map != null) && !map.isEmpty()) {
            try {
                for (Entry<String, List<String>> entry : map.entrySet()) {
                    Method method = Concept.Choice.class.getMethod(getSetterMethodName(clazz, false), clazz);
                    LiteralType.Lang lang = null;
                    if (StringUtils.isNotEmpty(entry.getKey())
                            && !StringUtils.equals(DEFAULT_LANGUAGE, entry.getKey())) {
                        lang = new LiteralType.Lang();
                        lang.setLang(entry.getKey());
                    }
                    for (String str : entry.getValue()) {
                        if (StringUtils.isNotBlank(str)) {
                            LiteralType obj = clazz.newInstance();
                            obj.setString(str);
                            obj.setLang(lang);
                            Concept.Choice ch = new Concept.Choice();
                            method.invoke(ch, obj);
                            choices.add(ch);
                        }
                    }
                }
            } catch (SecurityException | IllegalAccessException | NoSuchMethodException | IllegalArgumentException
                    | InvocationTargetException | InstantiationException e) {
                LOG.error(e.getClass().getSimpleName() + "  " + e.getMessage(), e);
            }
        }
    }

    private static void addConceptChoice(List<Concept.Choice> choices,
                                         Class<? extends ResourceType> clazz, String[] array) {
        if (StringArrayUtils.isNotBlank(array)) {
            try {
                Method method = Concept.Choice.class.getMethod(getSetterMethodName(clazz, false), clazz);
                for (String str : array) {
                    if (StringUtils.isNotBlank(str)) {
                        ResourceType obj = clazz.newInstance();
                        obj.setResource(str);
                        Concept.Choice ch = new Concept.Choice();
                        method.invoke(ch, obj);
                        choices.add(ch);
                    }
                }
            } catch (SecurityException | IllegalAccessException | NoSuchMethodException | IllegalArgumentException
                    | InvocationTargetException | InstantiationException e) {
                LOG.error(e.getClass().getSimpleName() + "  " + e.getMessage(), e);
            }
        }
    }

    private static void addEuropeanaTypeChoice(List<EuropeanaType.Choice> dcChoices,
            Class<? extends ResourceOrLiteralType> clazz, Map<String, List<String>> entries) {
        if ((entries != null) && !entries.isEmpty()) {
            try {
                Method method = EuropeanaType.Choice.class.getMethod(getSetterMethodName(clazz, false), clazz);
                for (Entry<String, List<String>> entry : entries.entrySet()) {
                    ResourceOrLiteralType.Lang lang = null;
                    if (StringUtils.isNotEmpty(entry.getKey())
                            && !StringUtils.equals(DEFAULT_LANGUAGE, entry.getKey())) {
                        lang = new ResourceOrLiteralType.Lang();
                        lang.setLang(entry.getKey());
                    }
                    for (String str : entry.getValue()) {
                        if (StringUtils.isNotBlank(str)) {
                            ResourceOrLiteralType obj = clazz.newInstance();
                            if (EuropeanaUriUtils.isUri(str)) {
                                Resource resource = new Resource();
                                resource.setResource(str);
                                obj.setResource(resource);
                                obj.setString("");
                            } else {
                                obj.setString(str);
                            }
                            obj.setLang(lang);
                            EuropeanaType.Choice ch = new EuropeanaType.Choice();
                            method.invoke(ch, obj);
                            dcChoices.add(ch);
                        }
                    }
                }
            } catch (SecurityException | IllegalAccessException | NoSuchMethodException | IllegalArgumentException
                    | InvocationTargetException | InstantiationException e) {
                LOG.error(e.getClass().getSimpleName() + "  " + e.getMessage(), e);
            }
        }
    }

    private static void addEuropeanaTypeChoiceLiteral(List<EuropeanaType.Choice> dcChoices,
             Class<? extends LiteralType> clazz, Map<String, List<String>> entries) {
        if ((entries != null) && !entries.isEmpty()) {
            try {
                Method method = EuropeanaType.Choice.class.getMethod(
                        getSetterMethodName(clazz, false), clazz);
                for (Entry<String, List<String>> entry : entries.entrySet()) {
                    LiteralType.Lang lang = null;
                    if (StringUtils.isNotBlank(entry.getKey())
                            && !StringUtils.equals(DEFAULT_LANGUAGE, entry.getKey())) {
                        lang = new LiteralType.Lang();
                        lang.setLang(entry.getKey());
                    }
                    for (String str : entry.getValue()) {
                        if (StringUtils.isNotBlank(str)) {
                            LiteralType obj = clazz.newInstance();
                            obj.setString(str);
                            obj.setLang(lang);
                            EuropeanaType.Choice ch = new EuropeanaType.Choice();
                            method.invoke(ch, obj);
                            dcChoices.add(ch);
                        }
                    }
                }
            } catch (SecurityException | IllegalAccessException | NoSuchMethodException | IllegalArgumentException
                    | InvocationTargetException | InstantiationException e) {
                LOG.error(e.getClass().getSimpleName() + "  " + e.getMessage(), e);
            }
        }
    }

    public static boolean addAsObject(Object dest, Class<? extends ResourceType> clazz, String str, boolean preserveIdentifiers) {
        try {
            if (StringUtils.isNotBlank(str)) {
                Method method = dest.getClass().getMethod(getSetterMethodName(clazz, false), clazz);
                Object obj = clazz.newInstance();
                // when adding as object, the str value maybe an absolute url (starting with http or https), for fields like
                // edmIsShownBy, edmIsShownAt etc. And, we don't want to add the Base Url to those values.
                // That's why the check for absoluteUri is added here.
                if (EuropeanaUriUtils.isAbsoluteUri(str) || preserveIdentifiers) {
                    ((ResourceType) obj).setResource(str);
                } else {
                    ((ResourceType) obj).setResource(getBaseUrl(str));
                }
                method.invoke(dest, obj);
                return true;
            }
        } catch (SecurityException | IllegalAccessException | NoSuchMethodException | IllegalArgumentException
                | InvocationTargetException | InstantiationException e) {
            LOG.error(e.getClass().getSimpleName() + "  " + e.getMessage(), e);
        }
        return false;
    }

    public static <T> boolean addAsObject(Object dest, Class<T> clazz, Map<String, List<String>> map) {
        try {
            if ((map != null) && (!map.isEmpty())) {
                T obj = convertMapToObj(clazz, map);
                if (obj != null) {
                    Method method = dest.getClass().getMethod(getSetterMethodName(clazz, false), clazz);
                    method.invoke(dest, obj);
                    return true;
                }
            }
        } catch (SecurityException | IllegalAccessException | NoSuchMethodException | IllegalArgumentException
                | InvocationTargetException e) {
            LOG.error(e.getClass().getSimpleName() + "  " + e.getMessage(), e);
        }
        return false;
    }

    public static <T> boolean addAsList(Object dest, Class<T> clazz, Map<String, List<String>> map) {
        try {
            if ((map != null) && !map.isEmpty()) {
                Method method = dest.getClass().getMethod(getSetterMethodName(clazz, true), List.class);
                method.invoke(dest, convertListFromMap(clazz, map));
                return true;
            }
        } catch (SecurityException | IllegalAccessException | NoSuchMethodException | IllegalArgumentException
                | InvocationTargetException e) {
            LOG.error(e.getClass().getSimpleName() + "  " + e.getMessage(), e);
        }
        return false;
    }

    /**
     * Adds the values as list.
     * Prefix is always null and addBaseUrl is always set to false.
     *
     * @param dest
     * @param clazz
     * @param vals
     * @param <T>
     * @return
     */
    public static <T> boolean addAsList(Object dest, Class<T> clazz, String[] vals) {
        return addAsList(dest, clazz, vals, null, false);
    }

    /**
     * Adds values as List
     * if prefix present adds the prefix in the values
     * OR if addBaseUrl true, will check if the value is an absolute url or not.
     *                        if not will add the base Url to the value.
     * @param dest
     * @param clazz
     * @param vals
     * @param prefix
     * @param addBaseUrl
     * @param <T>
     * @return
     */
    public static <T> boolean addAsList(Object dest, Class<T> clazz, String[] vals, String prefix, boolean addBaseUrl) {
        try {
            if (StringArrayUtils.isNotBlank(vals)) {
                Method method = dest.getClass().getMethod(getSetterMethodName(clazz, true), List.class);
                String[] newValues = getModifiedValues(vals, prefix, addBaseUrl);
                method.invoke(dest, convertListFromArray(clazz, newValues));
                return true;
            }
        } catch (SecurityException | IllegalAccessException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException e) {
            LOG.error(e.getClass().getSimpleName() + "  " + e.getMessage(), e);
        }
        return false;
    }

    /**
     * Returns the String array with either prefix added to all the values
     * OR base url added to the relative uri's
     * OR else returns the original array
     * @param values
     * @param prefix
     * @param addBaseUrl
     * @return
     */
    private static String[] getModifiedValues(String[] values, String prefix, boolean addBaseUrl) {
        String[] newValues = new String[values.length];
        if (StringUtils.isNotBlank(prefix)) {
            int i = 0;
            for (String val : values) {
                newValues[i] = prefix + val;
                i++;
            }
        }
        else if (addBaseUrl) {
            int i = 0;
            for (String value : values) {
                newValues[i] = EuropeanaUriUtils.isRelativeUri(value) ? getBaseUrl(value) : value;
                i++;
            }
        } else {
            return values;
        }
        return newValues;
    }

    private static String getSetterMethodName(Class<?> clazz, boolean list) {
        StringBuilder sb = new StringBuilder("set");
        String clazzName = clazz.getSimpleName();
        if (StringUtils.equals("Rights", clazzName) && list) {
            clazzName = "Right";
        }
        if (StringUtils.equals("SameAs", clazzName) && list) {
            clazzName = "SameA";
        }
        if (StringUtils.equals("Aggregates", clazzName) && list) {
            clazzName = "Aggregate";
        }
        if (StringUtils.equals("Incorporates", clazzName) && list) {
            clazzName = "Incorporate";
        }
        if (StringUtils.equals("Realizes", clazzName) && list) {
            clazzName = "Realize";
        }
        clazzName = StringUtils.strip(clazzName, "_1");
        clazzName = StringUtils.strip(clazzName, "1");

        sb.append(clazzName);
        if (list) {
            sb.append("List");
        }
        return sb.toString();
    }

    private static <T> List<T> convertListFromArray(Class<T> clazz, String[] vals) {
        List<T> tList = new ArrayList<>();
        try {
            if (vals != null) {
                for (String str : vals) {
                    T obj = clazz.newInstance();
                    if (ResourceType.class.isAssignableFrom(obj.getClass())) {
                        ((ResourceType) obj).setResource(str);
                    }
                    tList.add(obj);
                }
                return tList;
            }
        } catch (SecurityException | InstantiationException | IllegalAccessException e) {
            LOG.error(e.getClass().getSimpleName() + " " + e.getMessage(), e);
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    private static <T> List<T> convertListFromMap(Class<T> clazz, Map<String, List<String>> map) {
        if (map != null) {
            List<T> list = new ArrayList<>();
            for (Entry<String, List<String>> entry : map.entrySet()) {
                try {
                    if (entry.getValue() != null) {
                        if (clazz.getSuperclass().isAssignableFrom(ResourceType.class)) {
                            for (String value : entry.getValue()) {
                                // null check shouldn't be necessary, but sometimes there is incorrect data
                                if (value != null) {
                                    ResourceType t = (ResourceType) clazz.newInstance();
                                    t.setResource(value);
                                    list.add((T) t);
                                }
                            }

                        } else if (clazz.getSuperclass().isAssignableFrom(LiteralType.class)) {
                            list.addAll(createLiteralLangList(clazz, entry));
                        } else if (clazz.getSuperclass().isAssignableFrom(ResourceOrLiteralType.class)) {
                            list.addAll(createResourceOrLiteralLangList(clazz, entry));
                        }
                    }
                } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InstantiationException e) {
                    LOG.error("Error converting map to list", e);
                }
            }
            return list;
        }
        return null;
    }

    private static ResourceOrLiteralType setResourceOrLiteralLangValue(ResourceOrLiteralType rlt, Resource r, ResourceOrLiteralType.Lang lang, String value) {
        if (EuropeanaUriUtils.isUri(value)) {
            r.setResource(value);
            rlt.setResource(r);
        } else {
            rlt.setString(value);
            rlt.setLang(lang);
        }
        return rlt;
    }

    private static <T> List<T> createLiteralLangList(Class<T> clazz, Entry<String, List<String>> entry)
            throws InstantiationException, IllegalAccessException {
        List<T> result = new ArrayList<>();
        LiteralType.Lang lang = null;
        if (StringUtils.isNotEmpty(entry.getKey()) && !StringUtils.equals(entry.getKey(),DEFAULT_LANGUAGE)) {
            lang = new LiteralType.Lang();
            lang.setLang(entry.getKey());
        }
        for (String value : entry.getValue()) {
            // null check shouldn't be necessary, but sometimes there is incorrect data
            if (value != null) {
                LiteralType t = (LiteralType) clazz.newInstance();
                t.setString(value);
                t.setLang(lang);
                result.add((T) t);
            }
        }
        return result;
    }

    private static <T> List<T> createResourceOrLiteralLangList(Class<T> clazz, Entry<String, List<String>> entry)
            throws InstantiationException, IllegalAccessException {
        List<T> result = new ArrayList<>();
        ResourceOrLiteralType.Lang lang = null;
        // 2018-08-30 PE: not sure why the check for "eur". Looks like this is a very old definition
        // and not used anymore, as it's the only reference to "eur" in the entire application
        if (StringUtils.isNotEmpty(entry.getKey())
                && !StringUtils.equals(entry.getKey(), DEFAULT_LANGUAGE)
                && !StringUtils.equals(entry.getKey(), "eur")) {
            lang = new ResourceOrLiteralType.Lang();
            lang.setLang(entry.getKey());
        }
        for (String value : entry.getValue()) {
            // null check shouldn't be necessary, but sometimes there is incorrect data
            if (value != null) {
                ResourceOrLiteralType t = (ResourceOrLiteralType) clazz.newInstance();
                Resource resource = new Resource();
                t.setString("");
                setResourceOrLiteralLangValue(t, resource, lang, value);
                result.add((T) t);
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private static <T> T convertMapToObj(Class<T> clazz, Map<String, List<String>> map) {
        if (map != null) {
            for (Entry<String, List<String>> entry : map.entrySet()) {
                try {
                    T t = clazz.newInstance();
                    if (clazz.getSuperclass().isAssignableFrom(ResourceType.class)) {
                        ((ResourceType) t).setResource(entry.getValue().get(0));
                        return t;

                    } else if (clazz.getSuperclass().isAssignableFrom(ResourceOrLiteralType.class)) {
                        ResourceOrLiteralType.Lang lang = null;
                        if (StringUtils.isNotEmpty(entry.getKey())
                                && !StringUtils.equals(entry.getKey(), DEFAULT_LANGUAGE)) {
                            lang = new ResourceOrLiteralType.Lang();
                            lang.setLang(entry.getKey());
                        }

                        ResourceOrLiteralType obj = ((ResourceOrLiteralType) t);
                        Resource resource = new Resource();
                        obj.setString("");
                        for (String value : entry.getValue()) {
                            setResourceOrLiteralLangValue(obj, resource, lang, value);
                        }

                        return (T) obj;
                    } else if (clazz.getSuperclass().isAssignableFrom(LiteralType.class)) {
                        LiteralType.Lang lang = null;
                        if (StringUtils.isNotEmpty(entry.getKey())
                                && !StringUtils.equals(entry.getKey(), DEFAULT_LANGUAGE)) {
                            lang = new LiteralType.Lang();
                            lang.setLang(entry.getKey());
                        }
                        LiteralType obj = ((LiteralType) t);
                        obj.setString("");
                        for (String value : entry.getValue()) {
                            obj.setString(value);
                        }
                        obj.setLang(lang);
                        return (T) obj;
                    }
                } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InstantiationException e) {
                    LOG.error(e.getClass().getSimpleName() + "  "
                            + e.getMessage(), e);
                }
            }
        }
        return null;
    }

    /**
     * @deprecated use {@linkplain EuropeanaUriUtils#isUri(String)} instead
     * @param str
     * @return
     */
    @Deprecated
    public static boolean isUri(String str) {
        return EuropeanaUriUtils.isUri(str);
    }

    /**
     * Make a clone of a list. Note that the provided list can actually contain a single String or a Map<String, String>
     * @param list
     * @return
     */
    public static List<Map<String, String>> cloneList(List<Map<String, String>> list) {
        if (list == null) {
            return null;
        }
        List<Map<String, String>> result = new ArrayList<>();
        for (Object label : list) {
            if (label instanceof String) {
                Map<String, String> map = new HashMap<>();
                map.put(DEFAULT_LANGUAGE, (String) label);
                result.add(map);
            } else {
                result.add((Map<String, String>) label);
            }
        }
        return result;
    }

    /**
     * Make a clone of a map. If the key contains a separator "." , get the value of the key after that.
     * This is to overcome solr fields like proxy_dc_creator.en. To get the language tagged value.
     * NOTE : if there is no seperator then the substringAfter method returns empty string.
     * @param map
     * @return
     */
    public static Map<String, List<String>> cloneMap(Map<String, List<String>> map) {
        if (map == null) {
            return null;
        }
        Map<String, List<String>> result = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            if (StringUtils.contains(entry.getKey(), ".")) {
                result.put(StringUtils.substringAfter(entry.getKey(), "."), entry.getValue());
            } else {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }

    private static String getBaseUrl(String url) {
        // Urls supplied by API2 always start with "/item" (see ItemFix.class) and the ones from OAI-PMH do not so
        // that's why we need to check
        if (url == null)  return null;
        String u = url.toLowerCase(Locale.GERMAN);
        if (u.startsWith("/item") || u.startsWith("/aggregation") || u.startsWith("/proxy")) {
            return BASE_URL + url;
        }
        return BASE_URL +"/item" + url;
    }

}
