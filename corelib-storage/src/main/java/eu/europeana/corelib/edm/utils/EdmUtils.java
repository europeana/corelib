package eu.europeana.corelib.edm.utils;

import eu.europeana.corelib.definitions.edm.entity.EuropeanaAggregation;
import eu.europeana.corelib.definitions.edm.entity.Place;
import eu.europeana.corelib.definitions.edm.entity.Timespan;
import eu.europeana.corelib.definitions.jibx.*;
import eu.europeana.corelib.definitions.jibx.Date;
import eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import eu.europeana.corelib.solr.entity.*;
import eu.europeana.corelib.utils.DateUtils;
import eu.europeana.corelib.utils.EuropeanaUriUtils;
import eu.europeana.corelib.utils.StringArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.JiBXException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
public class EdmUtils {

    public static final String DEFAULT_LANGUAGE = "def";

    private static final Logger LOG = LogManager.getLogger(EdmUtils.class);

    private static final String BASE_URL = "http://data.europeana.eu";

    private static IBindingFactory bfact;

    private EdmUtils() {
        // empty constructor to prevent initialization
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
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()){
            if (bfact == null) {
                bfact = BindingDirectory.getFactory(RDF.class);
            }
            marshallingContext = bfact.createMarshallingContext();
            marshallingContext.setOutput(out, null);
            marshallingContext.marshalDocument(rdf, "UTF-8", true);
            return out.toString("UTF-8");
        } catch (JiBXException | IOException e) {
            LOG.error("Error marshalling RDF", e);
        }
        return null;
    }

    /**
     * Convert a FullBean to an RDF object
     * @param fullBean the fullbean to convert
     * @return RDF object
     */
    public static synchronized RDF toRDF(FullBeanImpl fullBean) {
        RDF rdf = new RDF();
        String type = getType(fullBean);
        appendCHO(rdf, fullBean.getProvidedCHOs());
        appendAggregation(rdf, fullBean.getAggregations());
        appendProxy(rdf, fullBean.getProxies(), type);
        appendEuropeanaAggregation(rdf, fullBean);
        appendAgents(rdf, fullBean.getAgents());
        appendConcepts(rdf, fullBean.getConcepts());
        appendPlaces(rdf, fullBean.getPlaces());
        appendTimespans(rdf, fullBean.getTimespans());
        appendLicenses(rdf, fullBean.getLicenses());
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

    private static void appendLicenses(RDF rdf, List<LicenseImpl> licenses) {
        if (licenses != null) {
            List<License> licenseList = new ArrayList<>();
            for (LicenseImpl lic : licenses) {
                License license = new License();
                license.setAbout(lic.getAbout());
                addAsObject(license, InheritFrom.class,
                        lic.getOdrlInheritFrom());
                DateType date = new DateType();
                date.setDate(new java.sql.Date(lic.getCcDeprecatedOn()
                        .getTime()));
                license.setDeprecatedOn(date);
                licenseList.add(license);
            }
            rdf.setLicenseList(licenseList);
        }

    }

    private static String getType(FullBeanImpl fullBean) {
        for (ProxyImpl prx : fullBean.getProxies()) {
            if (!prx.isEuropeanaProxy()) {
                return prx.getEdmType().getEnumNameValue();
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

    private static void appendEuropeanaAggregation(RDF rdf, FullBeanImpl fBean) {
        EuropeanaAggregationType aggregation = new EuropeanaAggregationType();
        EuropeanaAggregation europeanaAggregation = fBean.getEuropeanaAggregation();
        if (isUri(europeanaAggregation.getAbout())) {
            aggregation.setAbout(europeanaAggregation.getAbout());
        } else {
            aggregation.setAbout(getBaseUrl(europeanaAggregation.getAbout()));
        }

        if (!addAsObject(aggregation, AggregatedCHO.class, europeanaAggregation.getAggregatedCHO())) {
            AggregatedCHO agCHO = new AggregatedCHO();
            if (isUri(fBean.getProvidedCHOs().get(0).getAbout())) {
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
        addAsObject(aggregation, IsShownBy.class, europeanaAggregation.getEdmIsShownBy());
        addAsObject(aggregation, LandingPage.class, europeanaAggregation.getEdmLandingPage());
        Language1 language = convertMapToLanguage(europeanaAggregation.getEdmLanguage());
        if (language != null) {
            aggregation.setLanguage(language);
        }
        addAsObject(aggregation, Preview.class, europeanaAggregation.getEdmPreview());
        addAsObject(aggregation, Rights1.class, europeanaAggregation.getEdmRights());
        Completeness completeness = new Completeness();
        completeness.setString(Integer.toString(fBean.getEuropeanaCompleteness()));
        aggregation.setCompleteness(completeness);

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



    private static void appendProxy(RDF rdf, List<ProxyImpl> proxies, String typeStr) {
        List<ProxyType> proxyList = new ArrayList<>();
        for (ProxyImpl prx : proxies) {
            ProxyType proxy = new ProxyType();
            if (isUri(prx.getAbout())) {
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

                for (int i = 0; i < seqArray.length; i++) {
                    IsNextInSequence item = new IsNextInSequence();
                    item.setResource(seqArray[i]);
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
                for (int i = 0; i < pIn.length; i++) {
                    ProxyIn proxyIn = new ProxyIn();
                    if (isUri(pIn[i])) {
                        proxyIn.setResource(pIn[i]);
                    } else {
                        proxyIn.setResource(getBaseUrl(pIn[i]));
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
            addAsList(proxy, HasMet.class, prx.getEdmHasMet());
            addAsList(proxy, HasType.class, prx.getEdmHasType());
            addAsList(proxy, Incorporates.class, prx.getEdmIncorporates());
            addAsList(proxy, IsDerivativeOf.class, prx.getEdmIsDerivativeOf());
            addAsList(proxy, IsRelatedTo.class, prx.getEdmIsRelatedTo());
            addAsObject(proxy, IsRepresentationOf.class, prx.getEdmIsRepresentationOf());
            addAsList(proxy, IsSimilarTo.class, prx.getEdmIsSimilarTo());
            addAsList(proxy, IsSuccessorOf.class, prx.getEdmIsSuccessorOf());
            addAsList(proxy, Realizes.class, prx.getEdmRealizes());
            addAsObject(proxy, ProxyFor.class, getBaseUrl(prx.getProxyFor()));
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

    private static void appendAggregation(RDF rdf, List<AggregationImpl> aggregations) {
        List<Aggregation> aggregationList = new ArrayList<>();
        for (AggregationImpl aggr : aggregations) {
            Aggregation aggregation = new Aggregation();
            if (isUri(aggr.getAbout())) {
                aggregation.setAbout(aggr.getAbout());
            } else {
                aggregation.setAbout(getBaseUrl(aggr.getAbout()));
            }
            if (!addAsObject(aggregation, AggregatedCHO.class, aggr.getAggregatedCHO())) {
                AggregatedCHO cho = new AggregatedCHO();
                if (isUri(rdf.getProvidedCHOList().get(0).getAbout())) {
                    cho.setResource(rdf.getProvidedCHOList().get(0).getAbout());
                } else {
                    cho.setResource(getBaseUrl(rdf.getProvidedCHOList().get(0).getAbout()));
                }
                aggregation.setAggregatedCHO(cho);
            }
            if (!addAsObject(aggregation, DataProvider.class, aggr.getEdmDataProvider())) {
                addAsObject(aggregation, DataProvider.class, aggr.getEdmProvider());
            }
            addAsObject(aggregation, IsShownAt.class, aggr.getEdmIsShownAt());
            addAsObject(aggregation, IsShownBy.class, aggr.getEdmIsShownBy());
            addAsObject(aggregation, _Object.class, aggr.getEdmObject());
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
            EdmWebResourceUtils.createWebResources(rdf, aggr);
            aggregationList.add(aggregation);
        }
        rdf.setAggregationList(aggregationList);
    }

    private static void appendCHO(RDF rdf, List<ProvidedCHOImpl> chos) {
        List<ProvidedCHOType> pChoList = new ArrayList<>();
        for (ProvidedCHOImpl pCho : chos) {
            ProvidedCHOType pChoJibx = new ProvidedCHOType();
            if (isUri(pCho.getAbout())) {
                pChoJibx.setAbout(pCho.getAbout());
            } else {
                pChoJibx.setAbout(getBaseUrl(pCho.getAbout()));
            }

            addAsList(pChoJibx, SameAs.class, pCho.getOwlSameAs());
            pChoList.add(pChoJibx);
        }
        rdf.setProvidedCHOList(pChoList);
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
                            if (isUri(str)) {
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

    public static boolean addAsObject(Object dest, Class<? extends ResourceType> clazz, String str) {
        try {
            if (StringUtils.isNotBlank(str)) {
                Method method = dest.getClass().getMethod(getSetterMethodName(clazz, false), clazz);
                Object obj = clazz.newInstance();
                if (isUri(str)) {
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

    public static <T> boolean addAsList(Object dest, Class<T> clazz, String[] vals, String... prefix) {
        try {
            if (StringArrayUtils.isNotBlank(vals)) {
                Method method = dest.getClass().getMethod(getSetterMethodName(clazz, true), List.class);
                if (prefix.length == 1) {
                    String[] valNew = new String[vals.length];
                    int i = 0;
                    for (String val : vals) {
                        valNew[i] = prefix + val;
                        i++;
                    }
                    method.invoke(dest, convertListFromArray(clazz, valNew));
                } else {
                    method.invoke(dest, convertListFromArray(clazz, vals));
                }
                return true;
            }
        } catch (SecurityException | IllegalAccessException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException e) {
            LOG.error(e.getClass().getSimpleName() + "  " + e.getMessage(), e);
        }
        return false;
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
        if (isUri(value)) {
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
     * Make a clone of a map
     * @param map
     * @return
     */
    public static Map<String, List<String>> cloneMap(Map<String, List<String>> map) {
        if (map == null) {
            return null;
        }
        Map<String, List<String>> result = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            result.put(StringUtils.substringAfter(entry.getKey(), "."), entry.getValue());
        }
        return result;
    }

    private static String getBaseUrl(String url) {
        // Urls supplied by API2 always start with "/item" (see ItemFix.class) and the ones from OAI-PMH do not so
        // that's why we need to check
        String u = url.toLowerCase(Locale.GERMAN);
        if (u.startsWith("/item") || u.startsWith("/aggregation") || u.startsWith("/proxy")) {
            return BASE_URL + url;
        }
        return BASE_URL +"/item" + url;
    }

}
