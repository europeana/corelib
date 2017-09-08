/*
 * Copyright 2007-2012 The Europeana Foundation
 *
 *  Licenced under the EUPL, Version 1.1 (the "Licence") and subsequent versions as approved
 *  by the European Commission;
 *  You may not use this work except in compliance with the Licence.
 * 
 *  You may obtain a copy of the Licence at:
 *  http://joinup.ec.europa.eu/software/page/eupl
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under
 *  the Licence is distributed on an "AS IS" basis, without warranties or conditions of
 *  any kind, either express or implied.
 *  See the Licence for the specific language governing permissions and limitations under
 *  the Licence.
 */
package eu.europeana.corelib.edm.utils;

import eu.europeana.corelib.definitions.edm.entity.EuropeanaAggregation;
import eu.europeana.corelib.definitions.edm.entity.Place;
import eu.europeana.corelib.definitions.edm.entity.Timespan;
import eu.europeana.corelib.definitions.edm.entity.WebResource;
import eu.europeana.corelib.definitions.jibx.*;
import eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource;
import eu.europeana.corelib.definitions.model.ColorSpace;
import eu.europeana.corelib.definitions.model.Orientation;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import eu.europeana.corelib.solr.entity.*;
import eu.europeana.corelib.utils.StringArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.JiBXException;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

/**
 * Convert a FullBean to EDM
 *
 * @author Yorgos.Mamakis@ kb.nl
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public class EdmUtils {

    private static final Logger log = Logger.getLogger(EdmUtils.class
            .getCanonicalName());

    private static IBindingFactory bfact;
    private static final  String SPACE = " ";
    private static final  String PREFIX = "http://data.europeana.eu";
    private static final  String LONGDATATYPE = "http://www.w3.org/2001/XMLSchema#long";
    private static final String HEXBINARYDATATYPE = "http://www.w3.org/2001/XMLSchema#hexBinary";
    private static final String STRINGDATATYPE = "http://www.w3.org/2001/XMLSchema#string";
    private static final String INTEGERDATATYPE = "http://www.w3.org/2001/XMLSchema#integer";
    private static final String NONNEGATIVEINTEGERDATATYPE = "http://www.w3.org/2001/XMLSchema#nonNegativeInteger";
    private static final String DOUBLEDATATYPE = "http://www.w3.org/2001/XMLSchema#double";

    /**
     * Convert a FullBean to an EDM String
     *
     * @param fullBean The FullBean to convert
     * @return The resulting EDM string in RDF-XML
     */
    public static synchronized String toEDM(FullBeanImpl fullBean, boolean isUim) {

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
        IMarshallingContext marshallingContext;
        try {
            if (bfact == null) {
                bfact = BindingDirectory.getFactory(RDF.class);
            }
            marshallingContext = bfact.createMarshallingContext();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            marshallingContext.setOutput(out, null);
            marshallingContext.marshalDocument(rdf, "UTF-8", true);
            return out.toString("UTF-8");
        } catch (JiBXException | UnsupportedEncodingException e) {
            log.severe(e.getClass().getSimpleName() + "  " + e.getMessage());
        }
        return null;
    }

    private static void appendServices(RDF rdf, List<ServiceImpl> services) {
        if (services != null) {
            List<Service> serviceList = new ArrayList<>();
            for (ServiceImpl serv : services) {
                Service service = new Service();
                service.setAbout(serv.getAbout());
                //addAsList(service, ConformsTo.class, serv.getDctermsConformsTo());
                if(serv.getDctermsConformsTo()!=null && serv.getDctermsConformsTo().length>0){
                    List<ConformsTo> conformsToList = new ArrayList<>();

                    for(String conformsTo:serv.getDctermsConformsTo()){
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
                    if(conformsToList.size()>0){
                        service.setConformsToList(conformsToList);
                    }
                }
                if (serv.getDoapImplements() != null && serv.getDoapImplements().length > 0){
                    List<Implements> implementsList = new ArrayList<>();
                    for(String doapImplements : serv.getDoapImplements()){
                        Implements anImplements = new Implements();
                        anImplements.setResource(doapImplements);
                    }
                    service.setImplementList(implementsList);
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
                return prx.getEdmType().toString();
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

                if (place.getLatitude() != null
                        && place.getLongitude() != null
                        && (place.getLatitude() != 0 && place.getLongitude() != 0)) {
                    Lat lat = new Lat();
                    lat.setLat(place.getLatitude());
                    pType.setLat(lat);

                    _Long _long = new _Long();
                    _long.setLong(place.getLongitude());
                    pType.setLong(_long);
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
                addConceptChoice(choices, PrefLabel.class,
                        concept.getPrefLabel());
                addConceptChoice(choices, Notation.class, concept.getNotation());
                addConceptChoice(choices, Note.class, concept.getNote());
                addConceptChoice(choices, Broader.class, concept.getBroader());
                addConceptChoice(choices, BroadMatch.class,
                        concept.getBroadMatch());
                addConceptChoice(choices, CloseMatch.class,
                        concept.getCloseMatch());
                addConceptChoice(choices, ExactMatch.class,
                        concept.getExactMatch());
                addConceptChoice(choices, InScheme.class, concept.getInScheme());
                addConceptChoice(choices, Narrower.class, concept.getNarrower());
                addConceptChoice(choices, NarrowMatch.class,
                        concept.getNarrowMatch());
                addConceptChoice(choices, RelatedMatch.class,
                        concept.getRelatedMatch());
                addConceptChoice(choices, Related.class, concept.getRelated());

                con.setChoiceList(choices);
                conceptList.add(con);
            }
            rdf.setConceptList(conceptList);
        }
    }

    private static void appendEuropeanaAggregation(RDF rdf, FullBeanImpl fBean) {
        EuropeanaAggregationType aggregation = new EuropeanaAggregationType();
        EuropeanaAggregation europeanaAggregation = fBean
                .getEuropeanaAggregation();
        if (isUri(europeanaAggregation.getAbout())) {
            aggregation.setAbout(europeanaAggregation.getAbout());
        } else {
            aggregation.setAbout(PREFIX + europeanaAggregation.getAbout());
        }

        if (!addAsObject(aggregation, AggregatedCHO.class,
                europeanaAggregation.getAggregatedCHO())) {
            AggregatedCHO agCHO = new AggregatedCHO();
            if (isUri(fBean.getProvidedCHOs().get(0).getAbout())) {
                agCHO.setResource(fBean.getProvidedCHOs().get(0).getAbout());
            } else {
                agCHO.setResource(PREFIX
                        + fBean.getProvidedCHOs().get(0).getAbout());
            }
            aggregation.setAggregatedCHO(agCHO);
        }
        addAsList(aggregation, Aggregates.class,
                europeanaAggregation.getAggregates());
        CollectionName collectionName = new CollectionName();
        collectionName.setString(fBean.getEuropeanaCollectionName()[0]);
        aggregation.setCollectionName(collectionName);
        Country country = convertMapToCountry(europeanaAggregation
                .getEdmCountry());
        if (country != null) {
            aggregation.setCountry(country);
        }
        addAsObject(aggregation, Creator.class,
                europeanaAggregation.getDcCreator());
        addAsList(aggregation, HasView.class,
                europeanaAggregation.getEdmHasView());
        addAsObject(aggregation, IsShownBy.class,
                europeanaAggregation.getEdmIsShownBy());
        addAsObject(aggregation, LandingPage.class,
                europeanaAggregation.getEdmLandingPage());
        Language1 language = convertMapToLanguage(europeanaAggregation
                .getEdmLanguage());
        if (language != null) {
            aggregation.setLanguage(language);
        }
        addAsObject(aggregation, Preview.class,
                europeanaAggregation.getEdmPreview());
        addAsObject(aggregation, Rights1.class,
                europeanaAggregation.getEdmRights());

        List<EuropeanaAggregationType> lst = new ArrayList<>();
        lst.add(aggregation);
        rdf.setEuropeanaAggregationList(lst);
    }

    private static Language1 convertMapToLanguage(
            Map<String, List<String>> edmLanguage) {
        if (edmLanguage != null && edmLanguage.size() > 0) {
            Language1 lang = new Language1();
            lang.setLanguage(LanguageCodes.convert(edmLanguage.entrySet()
                    .iterator().next().getValue().get(0)));
            return lang;
        }
        return null;
    }

    private static Country convertMapToCountry(
            Map<String, List<String>> edmCountry) {

        if (edmCountry != null && edmCountry.size() > 0) {
            Country country = new Country();
            StringBuilder sb = new StringBuilder();
            String[] splitCountry = edmCountry.entrySet().iterator().next()
                    .getValue().get(0).split(SPACE);
            for (String countryWord : splitCountry) {
                if (StringUtils.equals("and", countryWord)) {
                    sb.append(countryWord);
                } else {
                    sb.append(StringUtils.capitalize(countryWord));
                }
                sb.append(SPACE);
            }
            String countryFixed = sb.toString().replace(" Of ", " of ").trim();
            country.setCountry(CountryCodes.convert(countryFixed));

            return country;
        }
        return null;
    }

    private static void appendProxy(RDF rdf, List<ProxyImpl> proxies,
                                    String typeStr) {
        List<ProxyType> proxyList = new ArrayList<>();
        for (ProxyImpl prx : proxies) {
            ProxyType proxy = new ProxyType();
            if (isUri(prx.getAbout())) {
                proxy.setAbout(prx.getAbout());
            } else {
                proxy.setAbout(PREFIX + prx.getAbout());
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
                        proxyIn.setResource(PREFIX + pIn[i]);
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

            addAsObject(proxy, CurrentLocation.class,
                    prx.getEdmCurrentLocation());
            addAsList(proxy, HasMet.class, prx.getEdmHasMet());
            addAsList(proxy, HasType.class, prx.getEdmHasType());
            addAsList(proxy, Incorporates.class, prx.getEdmIncorporates());
            addAsList(proxy, IsDerivativeOf.class, prx.getEdmIsDerivativeOf());
            addAsList(proxy, IsRelatedTo.class, prx.getEdmIsRelatedTo());
            addAsObject(proxy, IsRepresentationOf.class,
                    prx.getEdmIsRepresentationOf());
            addAsList(proxy, IsSimilarTo.class, prx.getEdmIsSimilarTo());
            addAsList(proxy, IsSuccessorOf.class, prx.getEdmIsSuccessorOf());
            addAsObject(proxy, ProxyFor.class, PREFIX + prx.getProxyFor());
            addAsList(proxy, Year.class, prx.getYear());

            List<EuropeanaType.Choice> dcChoices = new ArrayList<>();
            addEuropeanaTypeChoice(dcChoices, Contributor.class,
                    prx.getDcContributor());
            addEuropeanaTypeChoice(dcChoices, Coverage.class,
                    prx.getDcCoverage());
            addEuropeanaTypeChoice(dcChoices, Creator.class, prx.getDcCreator());
            addEuropeanaTypeChoice(dcChoices, Date.class, prx.getDcDate());
            addEuropeanaTypeChoice(dcChoices, Description.class,
                    prx.getDcDescription());
            addEuropeanaTypeChoice(dcChoices, Format.class, prx.getDcFormat());
            addEuropeanaTypeChoiceLiteral(dcChoices, Identifier.class,
                    prx.getDcIdentifier());
            addEuropeanaTypeChoice(dcChoices, Publisher.class,
                    prx.getDcPublisher());
            addEuropeanaTypeChoiceLiteral(dcChoices, Language.class,
                    prx.getDcLanguage());
            addEuropeanaTypeChoice(dcChoices, Relation.class,
                    prx.getDcRelation());
            addEuropeanaTypeChoice(dcChoices, Rights.class, prx.getDcRights());
            addEuropeanaTypeChoice(dcChoices, Source.class, prx.getDcSource());
            addEuropeanaTypeChoice(dcChoices, Subject.class, prx.getDcSubject());
            addEuropeanaTypeChoiceLiteral(dcChoices, Title.class,
                    prx.getDcTitle());
            addEuropeanaTypeChoice(dcChoices, Type.class, prx.getDcType());
            addEuropeanaTypeChoiceLiteral(dcChoices, Alternative.class,
                    prx.getDctermsAlternative());
            addEuropeanaTypeChoice(dcChoices, ConformsTo.class,
                    prx.getDctermsConformsTo());
            addEuropeanaTypeChoice(dcChoices, Created.class,
                    prx.getDctermsCreated());
            addEuropeanaTypeChoice(dcChoices, Extent.class,
                    prx.getDctermsExtent());
            addEuropeanaTypeChoice(dcChoices, HasFormat.class,
                    prx.getDctermsHasFormat());
            addEuropeanaTypeChoice(dcChoices, HasPart.class,
                    prx.getDctermsHasPart());
            addEuropeanaTypeChoice(dcChoices, HasVersion.class,
                    prx.getDctermsHasVersion());
            addEuropeanaTypeChoice(dcChoices, IsFormatOf.class,
                    prx.getDctermsIsFormatOf());
            addEuropeanaTypeChoice(dcChoices, IsPartOf.class,
                    prx.getDctermsIsPartOf());
            addEuropeanaTypeChoice(dcChoices, IsReferencedBy.class,
                    prx.getDctermsIsReferencedBy());
            addEuropeanaTypeChoice(dcChoices, IsReplacedBy.class,
                    prx.getDctermsIsReplacedBy());
            addEuropeanaTypeChoice(dcChoices, Issued.class,
                    prx.getDctermsIssued());
            addEuropeanaTypeChoice(dcChoices, IsRequiredBy.class,
                    prx.getDctermsIsRequiredBy());
            addEuropeanaTypeChoice(dcChoices, IsVersionOf.class,
                    prx.getDctermsIsVersionOf());
            addEuropeanaTypeChoice(dcChoices, Medium.class,
                    prx.getDctermsMedium());
            addEuropeanaTypeChoice(dcChoices, Provenance.class,
                    prx.getDctermsProvenance());
            addEuropeanaTypeChoice(dcChoices, References.class,
                    prx.getDctermsReferences());
            addEuropeanaTypeChoice(dcChoices, Replaces.class,
                    prx.getDctermsReplaces());
            addEuropeanaTypeChoice(dcChoices, Requires.class,
                    prx.getDctermsRequires());
            addEuropeanaTypeChoice(dcChoices, Spatial.class,
                    prx.getDctermsSpatial());
            addEuropeanaTypeChoice(dcChoices, Temporal.class,
                    prx.getDctermsTemporal());
            addEuropeanaTypeChoice(dcChoices, TableOfContents.class,
                    prx.getDctermsTOC());

            proxy.setChoiceList(dcChoices);
            proxyList.add(proxy);
        }

        rdf.setProxyList(proxyList);
    }

    private static void appendAggregation(RDF rdf,
                                          List<AggregationImpl> aggregations) {
        List<Aggregation> aggregationList = new ArrayList<>();
        for (AggregationImpl aggr : aggregations) {
            Aggregation aggregation = new Aggregation();
            if (isUri(aggr.getAbout())) {
                aggregation.setAbout(aggr.getAbout());
            } else {
                aggregation.setAbout(PREFIX + aggr.getAbout());
            }
            if (!addAsObject(aggregation, AggregatedCHO.class,
                    aggr.getAggregatedCHO())) {
                AggregatedCHO cho = new AggregatedCHO();
                if (isUri(rdf.getProvidedCHOList().get(0).getAbout())) {
                    cho.setResource(rdf.getProvidedCHOList().get(0).getAbout());
                } else {
                    cho.setResource(PREFIX
                            + rdf.getProvidedCHOList().get(0).getAbout());
                }
                aggregation.setAggregatedCHO(cho);
            }
            if (!addAsObject(aggregation, DataProvider.class,
                    aggr.getEdmDataProvider())) {
                addAsObject(aggregation, DataProvider.class,
                        aggr.getEdmProvider());
            }
            addAsObject(aggregation, IsShownAt.class, aggr.getEdmIsShownAt());
            addAsObject(aggregation, IsShownBy.class, aggr.getEdmIsShownBy());
            addAsObject(aggregation, _Object.class, aggr.getEdmObject());
            addAsObject(aggregation, Provider.class, aggr.getEdmProvider());
            addAsObject(aggregation, Rights1.class, aggr.getEdmRights());
            addAsList(aggregation, IntermediateProvider.class, aggr.getEdmIntermediateProvider());

            if (aggr.getEdmUgc() != null && !aggr.getEdmUgc().equalsIgnoreCase("false")) {
                Ugc ugc = new Ugc();

                ugc.setUgc(UGCType.valueOf(StringUtils.upperCase(aggr
                        .getEdmUgc())));
                aggregation.setUgc(ugc);
            }
            addAsList(aggregation, Rights.class, aggr.getDcRights());
            addAsList(aggregation, HasView.class, aggr.getHasView());
            createWebResources(rdf, aggr);
            aggregationList.add(aggregation);
        }
        rdf.setAggregationList(aggregationList);
    }

    private static void createWebResources(RDF rdf, AggregationImpl aggr) {
        List<WebResourceType> webResources = new ArrayList<>();
        for (WebResource wr : aggr.getWebResources()) {
            WebResourceType wResource = new WebResourceType();
            wResource.setAbout(wr.getAbout());
            addAsList(wResource, ConformsTo.class, wr.getDctermsConformsTo());
            addAsList(wResource, Created.class, wr.getDctermsCreated());
            addAsList(wResource, Creator.class, wr.getDcCreator());
            addAsList(wResource, Description.class, wr.getDcDescription());
            addAsList(wResource, Extent.class, wr.getDctermsExtent());
            addAsList(wResource, Format.class, wr.getDcFormat());
            addAsList(wResource, HasPart.class, wr.getDctermsHasPart());
            addAsList(wResource, IsFormatOf.class, wr.getDctermsIsFormatOf());
            addAsObject(wResource, IsNextInSequence.class,
                    wr.getIsNextInSequence());
            addAsList(wResource, Issued.class, wr.getDctermsIssued());
            addAsList(wResource, Rights.class, wr.getWebResourceDcRights());
            addAsList(wResource, Type.class, wr.getDcType());
            addAsObject(wResource, Rights1.class, wr.getWebResourceEdmRights());
            addAsList(wResource, Source.class, wr.getDcSource());
            addAsList(wResource, SameAs.class, wr.getOwlSameAs());
            addAsObject(wResource, Type1.class, wr.getRdfType());

            if (wr.getEdmCodecName() != null) {
                CodecName codecName = new CodecName();
                codecName.setCodecName(wr.getEdmCodecName());
                wResource.setCodecName(codecName);
            }
            if (wr.getEbucoreHasMimeType() != null) {
                HasMimeType hasMimeType = new HasMimeType();
                hasMimeType.setHasMimeType(wr.getEbucoreHasMimeType());
                wResource.setHasMimeType(hasMimeType);
            }
            if (wr.getEbucoreFileByteSize() != null) {
                LongType fileByteSize = new LongType();
                fileByteSize.setLong(wr.getEbucoreFileByteSize());
                fileByteSize.setDatatype(LONGDATATYPE);
                wResource.setFileByteSize(fileByteSize);
            }
            if (wr.getEbucoreDuration() != null) {
                Duration duration = new Duration();
                duration.setDuration(wr.getEbucoreDuration());
                wResource.setDuration(duration);
            }

            if (wr.getEbucoreWidth() != null) {
                Width width = new Width();
                width.setLong(wr.getEbucoreWidth());
                width.setDatatype(INTEGERDATATYPE);
                wResource.setWidth(width);
            }

            if (wr.getEbucoreHeight() != null) {
                Height height = new Height();
                height.setLong(wr.getEbucoreHeight());
                height.setDatatype(INTEGERDATATYPE);
                wResource.setHeight(height);
            }

            if (wr.getEdmSpatialResolution() != null) {
                SpatialResolution resolution = new SpatialResolution();
                resolution.setDatatype(NONNEGATIVEINTEGERDATATYPE);
                resolution.setInteger(new BigInteger(Integer.toString(wr.getEdmSpatialResolution())));
                wResource.setSpatialResolution(resolution);
            }

            if (wr.getEbucoreSampleRate() != null) {
                SampleRate sampleRate = new SampleRate();
                sampleRate.setLong(wr.getEbucoreSampleRate());
                sampleRate.setDatatype(INTEGERDATATYPE);
                wResource.setSampleRate(sampleRate);
            }
            if (wr.getEbucoreSampleSize() != null) {
                SampleSize sampleSize = new SampleSize();
                sampleSize.setLong(wr.getEbucoreSampleSize());
                sampleSize.setDatatype(INTEGERDATATYPE);
                wResource.setSampleSize(sampleSize);
            }

            if (wr.getEbucoreBitRate() != null) {
                BitRate bitRate = new BitRate();
                bitRate.setInteger(new BigInteger(Integer.toString(wr.getEbucoreBitRate())));
                bitRate.setDatatype(NONNEGATIVEINTEGERDATATYPE);
                wResource.setBitRate(bitRate);
            }

            if (wr.getEbucoreFrameRate() != null) {
                DoubleType frameRate = new DoubleType();
                frameRate.setDouble(wr.getEbucoreFrameRate());
                frameRate.setDatatype(DOUBLEDATATYPE);
                wResource.setFrameRate(frameRate);
            }

            if (wr.getEdmHasColorSpace() != null) {
                HasColorSpace hasColorSpace = new HasColorSpace();
                ColorSpaceType type = null;
                if (StringUtils.equals(wr.getEdmHasColorSpace(), ColorSpace.getValue(ColorSpace.GRAYSCALE))) {
                    type = ColorSpaceType.GRAYSCALE;
                } else {
                    type = ColorSpaceType.S_RGB;
                }
                hasColorSpace.setHasColorSpace(type);
                wResource.setHasColorSpace(hasColorSpace);
            }

            if (wr.getEbucoreOrientation() != null) {
                OrientationType orientation = new OrientationType();

                if (StringUtils.equals(wr.getEbucoreOrientation(), Orientation.getValue(Orientation.LANDSCAPE))) {
                    orientation.setString("landscape");
                } else {
                    orientation.setString("portrait");
                }
                orientation.setDatatype(STRINGDATATYPE);
                wResource.setOrientation(orientation);
            }

            if (wr.getEdmComponentColor() != null && wr.getEdmComponentColor().size() > 0) {
                List<HexBinaryType> componentColors = new ArrayList<>();
                for (String componentColor : wr.getEdmComponentColor()) {
                    HexBinaryType type = new HexBinaryType();
                    type.setString(componentColor);
                    type.setDatatype(HEXBINARYDATATYPE);
                    componentColors.add(type);
                }
                wResource.setComponentColorList(componentColors);
            }
            if(wr.getSvcsHasService()!=null){
                List<HasService> hsList = new ArrayList<>();
                for(String hasService:wr.getSvcsHasService()){
                    HasService hs = new HasService();
                    hs.setResource(hasService);
                    hsList.add(hs);

                }
                wResource.setHasServiceList(hsList);
            }

            addAsObject(wResource,Preview.class,wr.getEdmPreview());
            //addAsList(wResource, IsReferencedBy.class, wr.getDctermsIsReferencedBy());
            if (wr.getDctermsIsReferencedBy() != null) {
                List<IsReferencedBy> hsList = new ArrayList<>();
                for (String isRef : wr.getDctermsIsReferencedBy()) {
                    IsReferencedBy hs = new IsReferencedBy();
                    ResourceOrLiteralType.Resource res= new ResourceOrLiteralType.Resource();
                    res.setResource(isRef);
                    hs.setResource(res);
                    hs.setString("");
                    hs.setLang(null);
                    hsList.add(hs);

                }
                wResource.setIsReferencedByList(hsList);
            }
            webResources.add(wResource);
        }

        rdf.setWebResourceList(webResources);
    }


    private static void appendCHO(RDF rdf, List<ProvidedCHOImpl> chos) {
        List<ProvidedCHOType> pChoList = new ArrayList<>();
        for (ProvidedCHOImpl pCho : chos) {
            ProvidedCHOType pChoJibx = new ProvidedCHOType();
            if (isUri(pCho.getAbout())) {
                pChoJibx.setAbout(pCho.getAbout());
            } else {
                pChoJibx.setAbout(PREFIX + pCho.getAbout());
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
                addAsObject(agent, BiographicalInformation.class,
                        ag.getRdaGr2BiographicalInformation());
                addAsList(agent, Date.class, ag.getDcDate());
                addAsObject(agent, DateOfBirth.class, ag.getRdaGr2DateOfBirth());
                addAsObject(agent, DateOfDeath.class, ag.getRdaGr2DateOfDeath());
                addAsObject(agent, PlaceOfBirth.class,
                        ag.getRdaGr2PlaceOfBirth());
                addAsObject(agent, PlaceOfDeath.class,
                        ag.getRdaGr2PlaceOfDeath());
                addAsObject(agent, DateOfEstablishment.class,
                        ag.getRdaGr2DateOfEstablishment());
                addAsObject(agent, DateOfTermination.class,
                        ag.getRdaGr2DateOfTermination());
                addAsObject(agent, Gender.class, ag.getRdaGr2Gender());
                addAsList(agent, HasMet.class, ag.getEdmHasMet());
                addAsList(agent, Identifier.class, ag.getDcIdentifier());
                addAsList(agent, IsRelatedTo.class, ag.getEdmIsRelatedTo());
                addAsList(agent, Name.class, ag.getFoafName());
                addAsList(agent, Note.class, ag.getNote());
                addAsList(agent, PrefLabel.class, ag.getPrefLabel());
                addAsList(agent, ProfessionOrOccupation.class,
                        ag.getRdaGr2ProfessionOrOccupation());
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
                    Method method = Concept.Choice.class.getMethod(
                            getSetterMethodName(clazz, false), clazz);
                    LiteralType.Lang lang = null;
                    if (StringUtils.isNotEmpty(entry.getKey())
                            && !StringUtils.equals("def", entry.getKey())) {
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
            } catch (SecurityException | IllegalAccessException
                    | NoSuchMethodException | IllegalArgumentException
                    | InvocationTargetException | InstantiationException e) {
                log.severe(e.getClass().getSimpleName() + "  " + e.getMessage());
            }
        }
    }

    private static void addConceptChoice(List<Concept.Choice> choices,
                                         Class<? extends ResourceType> clazz, String[] array) {
        if (StringArrayUtils.isNotBlank(array)) {
            try {
                Method method = Concept.Choice.class.getMethod(
                        getSetterMethodName(clazz, false), clazz);
                for (String str : array) {
                    if (StringUtils.isNotBlank(str)) {
                        ResourceType obj = clazz.newInstance();
                        obj.setResource(str);
                        Concept.Choice ch = new Concept.Choice();
                        method.invoke(ch, obj);
                        choices.add(ch);
                    }
                }
            } catch (SecurityException | IllegalAccessException
                    | NoSuchMethodException | IllegalArgumentException
                    | InvocationTargetException | InstantiationException e) {
                log.severe(e.getClass().getSimpleName() + "  " + e.getMessage());
            }
        }
    }

    private static void addEuropeanaTypeChoice(
            List<EuropeanaType.Choice> dcChoices,
            Class<? extends ResourceOrLiteralType> clazz,
            Map<String, List<String>> entries) {
        if ((entries != null) && !entries.isEmpty()) {
            try {
                Method method = EuropeanaType.Choice.class.getMethod(
                        getSetterMethodName(clazz, false), clazz);
                for (Entry<String, List<String>> entry : entries.entrySet()) {
                    ResourceOrLiteralType.Lang lang = null;
                    if (StringUtils.isNotEmpty(entry.getKey())
                            && !StringUtils.equals("def", entry.getKey())) {
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
            } catch (SecurityException | IllegalAccessException
                    | NoSuchMethodException | IllegalArgumentException
                    | InvocationTargetException | InstantiationException e) {
                log.severe(e.getClass().getSimpleName() + "  " + e.getMessage());
            }
        }
    }

    private static void addEuropeanaTypeChoiceLiteral(
            List<EuropeanaType.Choice> dcChoices,
            Class<? extends LiteralType> clazz,
            Map<String, List<String>> entries) {
        if ((entries != null) && !entries.isEmpty()) {
            try {
                Method method = EuropeanaType.Choice.class.getMethod(
                        getSetterMethodName(clazz, false), clazz);
                for (Entry<String, List<String>> entry : entries.entrySet()) {
                    LiteralType.Lang lang = null;
                    if (StringUtils.isNotBlank(entry.getKey())
                            && !StringUtils.equals("def", entry.getKey())) {
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
            } catch (SecurityException | IllegalAccessException
                    | NoSuchMethodException | IllegalArgumentException
                    | InvocationTargetException | InstantiationException e) {
                log.severe(e.getClass().getSimpleName() + "  " + e.getMessage());
            }
        }
    }

    private static boolean addAsObject(Object dest,
                                       Class<? extends ResourceType> clazz, String str) {
        try {
            if (StringUtils.isNotBlank(str)) {
                Method method = dest.getClass().getMethod(
                        getSetterMethodName(clazz, false), clazz);
                Object obj = clazz.newInstance();
                if (isUri(str)) {
                    ((ResourceType) obj).setResource(str);
                } else {
                    ((ResourceType) obj).setResource(PREFIX + str);
                }
                method.invoke(dest, obj);
                return true;
            }
        } catch (SecurityException | IllegalAccessException
                | NoSuchMethodException | IllegalArgumentException
                | InvocationTargetException | InstantiationException e) {
            log.severe(e.getClass().getSimpleName() + "  " + e.getMessage());
        }
        return false;
    }

    private static <T> boolean addAsObject(Object dest, Class<T> clazz,
                                           Map<String, List<String>> map) {
        try {
            if ((map != null) && (!map.isEmpty())) {
                T obj = convertMapToObj(clazz, map);
                if (obj != null) {
                    Method method = dest.getClass().getMethod(
                            getSetterMethodName(clazz, false), clazz);
                    method.invoke(dest, obj);
                    return true;
                }
            }
        } catch (SecurityException | IllegalAccessException
                | NoSuchMethodException | IllegalArgumentException
                | InvocationTargetException e) {
            log.severe(e.getClass().getSimpleName() + "  " + e.getMessage());
        }
        return false;
    }

    private static <T> boolean addAsList(Object dest, Class<T> clazz,
                                         Map<String, List<String>> map) {
        try {
            if ((map != null) && !map.isEmpty()) {
                Method method = dest.getClass().getMethod(
                        getSetterMethodName(clazz, true), List.class);
                method.invoke(dest, convertListFromMap(clazz, map));
                return true;
            }
        } catch (SecurityException | IllegalAccessException
                | NoSuchMethodException | IllegalArgumentException
                | InvocationTargetException e) {
            log.severe(e.getClass().getSimpleName() + "  " + e.getMessage());
        }
        return false;
    }

    private static <T> boolean addAsList(Object dest, Class<T> clazz, String[] vals, String... prefix) {
        try {
            if (StringArrayUtils.isNotBlank(vals)) {
                Method method = dest.getClass().getMethod(getSetterMethodName(clazz, true), List.class);
                if (prefix.length == 1) {
                    String[] valNew = new String[vals.length];
                    int      i      = 0;
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
            log.severe(e.getClass().getSimpleName() + "  " + e.getMessage());
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
        clazzName = StringUtils.strip(clazzName, "_1");
        clazzName = StringUtils.strip(clazzName, "1");

        sb.append(clazzName);
        if (list) {
            sb.append("List");
        }
        return sb.toString();
    }

    private static <T> List<T> convertListFromArray(Class<T> clazz,
                                                    String[] vals) {
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
        } catch (SecurityException | InstantiationException
                | IllegalAccessException e) {
            log.severe(e.getClass().getSimpleName() + " " + e.getMessage());
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    private static <T> List<T> convertListFromMap(Class<T> clazz,
                                                  Map<String, List<String>> map) {
        if (map != null) {
            List<T> list = new ArrayList<>();
            for (Entry<String, List<String>> entry : map.entrySet()) {
                try {
                    if (entry.getValue() != null) {
                        if (clazz.getSuperclass().isAssignableFrom(
                                ResourceType.class)) {

                            for (String str : entry.getValue()) {
                                ResourceType t = (ResourceType) clazz
                                        .newInstance();

                                t.setResource(str);
                                list.add((T) t);
                            }

                        } else if (clazz.getSuperclass().isAssignableFrom(
                                LiteralType.class)) {
                            LiteralType.Lang lang = null;
                            if (StringUtils.isNotEmpty(entry.getKey())
                                    && !StringUtils.equals(entry.getKey(),
                                    "def")) {
                                lang = new LiteralType.Lang();
                                lang.setLang(entry.getKey());
                            }
                            for (String str : entry.getValue()) {
                                LiteralType t = (LiteralType) clazz
                                        .newInstance();

                                t.setString(str);
                                t.setLang(lang);
                                list.add((T) t);
                            }
                        } else if (clazz.getSuperclass().isAssignableFrom(
                                ResourceOrLiteralType.class)) {
                            ResourceOrLiteralType.Lang lang = null;
                            if (StringUtils.isNotEmpty(entry.getKey())
                                    && !StringUtils.equals(entry.getKey(),
                                    "def")
                                    && !StringUtils.equals(entry.getKey(),
                                    "eur")) {
                                lang = new ResourceOrLiteralType.Lang();
                                lang.setLang(entry.getKey());
                            }
                            for (String str : entry.getValue()) {
                                ResourceOrLiteralType t = (ResourceOrLiteralType) clazz
                                        .newInstance();
                                Resource resource = new Resource();
                                t.setString("");
                                if (isUri(str)) {
                                    resource.setResource(str);
                                    t.setResource(resource);
                                } else {
                                    t.setString(str);
                                    t.setLang(lang);
                                }

                                list.add((T) t);
                            }
                        }
                    }
                } catch (SecurityException | IllegalAccessException
                        | IllegalArgumentException | InstantiationException e) {
                    log.severe(e.getClass().getSimpleName() + "  "
                            + e.getMessage());
                }
            }
            return list;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private static <T> T convertMapToObj(Class<T> clazz,
                                         Map<String, List<String>> map) {
        if (map != null) {
            for (Entry<String, List<String>> entry : map.entrySet()) {
                try {
                    T t = clazz.newInstance();
                    if (clazz.getSuperclass().isAssignableFrom(
                            ResourceType.class)) {
                        ((ResourceType) t).setResource(entry.getValue().get(0));
                        return t;

                    } else if (clazz.getSuperclass().isAssignableFrom(
                            ResourceOrLiteralType.class)) {
                        ResourceOrLiteralType.Lang lang = null;
                        if (StringUtils.isNotEmpty(entry.getKey())
                                && !StringUtils.equals(entry.getKey(), "def")) {
                            lang = new ResourceOrLiteralType.Lang();
                            lang.setLang(entry.getKey());
                        }

                        ResourceOrLiteralType obj = ((ResourceOrLiteralType) t);
                        Resource resource = new Resource();
                        // resource.setResource("");

                        // obj.setResource(resource);
                        obj.setString("");
                        for (String str : entry.getValue()) {
                            if (isUri(str)) {
                                resource.setResource(str);
                                obj.setResource(resource);
                            } else {
                                obj.setString(str);
                                obj.setLang(lang);
                            }
                        }

                        return (T) obj;
                    } else if (clazz.getSuperclass().isAssignableFrom(
                            LiteralType.class)) {
                        LiteralType.Lang lang = null;
                        if (StringUtils.isNotEmpty(entry.getKey())
                                && !StringUtils.equals(entry.getKey(), "def")) {
                            lang = new LiteralType.Lang();
                            lang.setLang(entry.getKey());
                        }
                        LiteralType obj = ((LiteralType) t);
                        obj.setString("");
                        for (String str : entry.getValue()) {
                            obj.setString(str);
                        }
                        obj.setLang(lang);
                        return (T) obj;
                    }
                } catch (SecurityException | IllegalAccessException
                        | IllegalArgumentException | InstantiationException e) {
                    log.severe(e.getClass().getSimpleName() + "  "
                            + e.getMessage());
                }
            }
        }
        return null;
    }

    private static boolean isUri(String str) {
        return StringUtils.startsWith(str, "http://")
                || StringUtils.startsWith(str, "https://")
                || StringUtils.startsWith(str, "urn:")
                || StringUtils.startsWith(str, "#");
    }

    /**
     * Make a clone of a list
     * @param list
     * @return
     */
    public static List<Map<String, String>> cloneList(List<Map<String, String>> list) {
        if (list == null) {
            return null;
        }
        List<Map<String, String>> result = new ArrayList<>();
        for (int i = 0, max = list.size(); i < max; i++) {
            Object label = list.get(i);
            if (label.getClass().getName() == "java.lang.String") {
                Map<String, String> map = new HashMap<>();
                map.put("def", (String) label);
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
        for (String key : map.keySet()) {
            result.put(StringUtils.substringAfter(key, "."), map.get(key));
        }
        return result;
    }

}
