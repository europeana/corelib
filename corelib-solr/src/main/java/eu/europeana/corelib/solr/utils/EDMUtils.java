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
package eu.europeana.corelib.solr.utils;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.JiBXException;

import eu.europeana.corelib.definitions.jibx.*;
import eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource;
import eu.europeana.corelib.definitions.solr.entity.EuropeanaAggregation;
import eu.europeana.corelib.definitions.solr.entity.Place;
import eu.europeana.corelib.definitions.solr.entity.Timespan;
import eu.europeana.corelib.definitions.solr.entity.WebResource;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import eu.europeana.corelib.solr.entity.AgentImpl;
import eu.europeana.corelib.solr.entity.AggregationImpl;
import eu.europeana.corelib.solr.entity.ConceptImpl;
import eu.europeana.corelib.solr.entity.PlaceImpl;
import eu.europeana.corelib.solr.entity.ProvidedCHOImpl;
import eu.europeana.corelib.solr.entity.ProxyImpl;
import eu.europeana.corelib.solr.entity.TimespanImpl;

/**
 * Convert a FullBean to EDM
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public class EDMUtils {

	private static IBindingFactory bfact;

	/**
	 * Convert a FullBean to an EDM String
	 * 
	 * @param fullBean
	 *            The FullBean to convert
	 * @return The resulting EDM string in RDF-XML
	 */
	public static synchronized String toEDM(FullBeanImpl fullBean) {
		RDF rdf = new RDF();
		appendCHO(rdf, fullBean.getProvidedCHOs());
		appendAggregation(rdf, fullBean.getAggregations());
		appendProxy(rdf, fullBean.getProxies());
		appendEuropeanaAggregation(rdf, fullBean);
		appendAgents(rdf, fullBean.getAgents());
		appendConcepts(rdf, fullBean.getConcepts());
		appendPlaces(rdf, fullBean.getPlaces());
		appendTimespans(rdf, fullBean.getTimespans());
		IMarshallingContext marshallingContext;
		try {
			if (bfact == null) {
				bfact = BindingDirectory.getFactory(RDF.class);
			}
			marshallingContext = bfact.createMarshallingContext();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			marshallingContext.setOutput(out, null);
			marshallingContext.marshalDocument(rdf);
			return out.toString("UTF-8");
		} catch (JiBXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	private static void appendTimespans(RDF rdf, List<TimespanImpl> timespans) {
		if (timespans != null) {
			List<TimeSpanType> timespanList = new ArrayList<TimeSpanType>();
			for (Timespan ts : timespans) {
				TimeSpanType timeSpan = new TimeSpanType();
				timeSpan.setAbout(ts.getAbout());
				List<AltLabel> altLabelList = convertListFromMap(
						AltLabel.class, ts.getAltLabel());
				if (altLabelList != null) {
					timeSpan.setAltLabelList(altLabelList);
				}
				Begin begin = convertMapToObj(Begin.class, ts.getBegin());
				if (begin != null) {
					timeSpan.setBegin(begin);
				}
				End end = convertMapToObj(End.class, ts.getEnd());
				if (end != null) {
					timeSpan.setEnd(end);
				}
				List<HasPart> hasPartList = convertListFromMap(HasPart.class,
						ts.getDctermsHasPart());
				if (hasPartList != null) {
					timeSpan.setHasPartList(hasPartList);
				}
				List<IsPartOf> isPartOfList = convertListFromMap(
						IsPartOf.class, ts.getIsPartOf());
				if (isPartOfList != null) {
					timeSpan.setIsPartOfList(isPartOfList);
				}
				List<Note> noteList = convertListFromMap(Note.class,
						ts.getNote());
				if (noteList != null) {
					timeSpan.setNoteList(noteList);
				}
				List<SameAs> sameAsList = convertListFromArray(SameAs.class,
						ts.getOwlSameAs());
				if (sameAsList != null) {
					timeSpan.setSameAList(sameAsList);
				}
				List<PrefLabel> prefLabelList = convertListFromMap(
						PrefLabel.class, ts.getPrefLabel());
				if (prefLabelList != null) {
					timeSpan.setPrefLabelList(prefLabelList);
				}
				timespanList.add(timeSpan);
			}
			rdf.setTimeSpanList(timespanList);
		}
	}

	private static void appendPlaces(RDF rdf, List<PlaceImpl> places) {
		if (places != null) {
			List<PlaceType> placeList = new ArrayList<PlaceType>();
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
				List<AltLabel> altLabelList = convertListFromMap(
						AltLabel.class, place.getAltLabel());
				if (altLabelList != null) {
					pType.setAltLabelList(altLabelList);
				}
				List<HasPart> hasPartList = convertListFromMap(HasPart.class,
						place.getDcTermsHasPart());
				if (hasPartList != null) {
					pType.setHasPartList(hasPartList);
				}
				List<IsPartOf> isPartOfList = convertListFromMap(
						IsPartOf.class, place.getIsPartOf());
				if (isPartOfList != null) {
					pType.setIsPartOfList(isPartOfList);
				}
				List<Note> noteList = convertListFromMap(Note.class,
						place.getNote());
				if (noteList != null) {
					pType.setNoteList(noteList);
				}
				List<PrefLabel> prefLabelList = convertListFromMap(
						PrefLabel.class, place.getPrefLabel());
				if (prefLabelList != null) {
					pType.setPrefLabelList(prefLabelList);
				}
				List<SameAs> sameAsList = convertListFromArray(SameAs.class,
						place.getOwlSameAs());
				if (sameAsList != null) {
					pType.setSameAList(sameAsList);
				}
				placeList.add(pType);
			}
			rdf.setPlaceList(placeList);
		}
	}

	private static void appendConcepts(RDF rdf, List<ConceptImpl> concepts) {
		if (concepts != null) {
			List<Concept> conceptList = new ArrayList<Concept>();
			for (ConceptImpl concept : concepts) {
				Concept con = new Concept();
				con.setAbout(concept.getAbout());
				List<Concept.Choice> choices = new ArrayList<Concept.Choice>();
				if (concept.getAltLabel() != null) {
					for (Entry<String, List<String>> entry : concept
							.getAltLabel().entrySet()) {
						LiteralType.Lang lang = null;
						if (StringUtils.isNotEmpty(entry.getKey())
								&& !StringUtils.equals("def", entry.getKey())) {
							lang = new LiteralType.Lang();
							lang.setLang(entry.getKey());
						}
						for (String str : entry.getValue()) {
							AltLabel altLabel = new AltLabel();
							altLabel.setString(str);
							if (lang == null) {
								lang = new LiteralType.Lang();
								lang.setLang("");
							}
							altLabel.setLang(lang);
							Concept.Choice ch = new Concept.Choice();
							ch.setAltLabel(altLabel);
							choices.add(ch);
						}
					}
				}
				if (concept.getPrefLabel() != null) {
					for (Entry<String, List<String>> entry : concept
							.getPrefLabel().entrySet()) {
						LiteralType.Lang lang = null;
						if (StringUtils.isNotEmpty(entry.getKey())
								&& !StringUtils.equals("def", entry.getKey())) {
							lang = new LiteralType.Lang();
							lang.setLang(entry.getKey());
						}
						for (String str : entry.getValue()) {
							PrefLabel prefLabel = new PrefLabel();
							prefLabel.setString(str);
							if (lang == null) {
								lang = new LiteralType.Lang();
								lang.setLang("");
							}
							prefLabel.setLang(lang);
							Concept.Choice ch = new Concept.Choice();
							ch.setPrefLabel(prefLabel);
							choices.add(ch);
						}
					}
				}
				if (concept.getNotation() != null) {
					for (Entry<String, List<String>> entry : concept
							.getNotation().entrySet()) {
						LiteralType.Lang lang = null;
						if (StringUtils.isNotEmpty(entry.getKey())
								&& !StringUtils.equals("def", entry.getKey())) {
							lang = new LiteralType.Lang();
							lang.setLang(entry.getKey());
						}
						for (String str : entry.getValue()) {
							Notation notation = new Notation();
							notation.setString(str);
							if (lang == null) {
								lang = new LiteralType.Lang();
								lang.setLang("");
							}
							notation.setLang(lang);
							Concept.Choice ch = new Concept.Choice();
							ch.setNotation(notation);
							choices.add(ch);
						}
					}
				}
				if (concept.getNote() != null) {
					for (Entry<String, List<String>> entry : concept.getNote()
							.entrySet()) {
						LiteralType.Lang lang = null;
						if (StringUtils.isNotEmpty(entry.getKey())
								&& !StringUtils.equals("def", entry.getKey())) {
							lang = new LiteralType.Lang();
							lang.setLang(entry.getKey());
						}
						for (String str : entry.getValue()) {
							Note note = new Note();
							note.setString(str);
							if (lang == null) {
								lang = new LiteralType.Lang();
								lang.setLang("");
							}
							note.setLang(lang);
							Concept.Choice ch = new Concept.Choice();
							ch.setNote(note);
							choices.add(ch);
						}
					}
				}
				if (concept.getBroader() != null) {
					for (String broaderString : concept.getBroader()) {
						Broader broader = new Broader();
						broader.setResource(broaderString);
						Concept.Choice ch = new Concept.Choice();
						ch.setBroader(broader);
						choices.add(ch);
					}
				}
				if (concept.getBroadMatch() != null) {
					for (String str : concept.getBroadMatch()) {
						BroadMatch broadMatch = new BroadMatch();
						broadMatch.setResource(str);
						Concept.Choice ch = new Concept.Choice();
						ch.setBroadMatch(broadMatch);
						choices.add(ch);
					}
				}
				if (concept.getCloseMatch() != null) {
					for (String str : concept.getCloseMatch()) {
						CloseMatch closeMatch = new CloseMatch();
						closeMatch.setResource(str);
						Concept.Choice ch = new Concept.Choice();
						ch.setCloseMatch(closeMatch);
						choices.add(ch);
					}
				}
				if (concept.getExactMatch() != null) {
					for (String str : concept.getExactMatch()) {
						ExactMatch exactMatch = new ExactMatch();
						exactMatch.setResource(str);
						Concept.Choice ch = new Concept.Choice();
						ch.setExactMatch(exactMatch);
						choices.add(ch);
					}
				}
				if (concept.getInScheme() != null) {
					for (String str : concept.getInScheme()) {
						InScheme inScheme = new InScheme();
						inScheme.setResource(str);
						Concept.Choice ch = new Concept.Choice();
						ch.setInScheme(inScheme);
						choices.add(ch);
					}
				}
				if (concept.getNarrower() != null) {
					for (String str : concept.getNarrower()) {
						Narrower narrower = new Narrower();
						narrower.setResource(str);
						Concept.Choice ch = new Concept.Choice();
						ch.setNarrower(narrower);
						choices.add(ch);
					}
				}
				if (concept.getNarrowMatch() != null) {
					for (String str : concept.getNarrowMatch()) {
						NarrowMatch narrowMatch = new NarrowMatch();
						narrowMatch.setResource(str);
						Concept.Choice ch = new Concept.Choice();
						ch.setNarrowMatch(narrowMatch);
						choices.add(ch);
					}
				}
				if (concept.getRelatedMatch() != null) {
					for (String str : concept.getRelatedMatch()) {
						RelatedMatch relatedMatch = new RelatedMatch();
						relatedMatch.setResource(str);
						Concept.Choice ch = new Concept.Choice();
						ch.setRelatedMatch(relatedMatch);
						choices.add(ch);
					}
				}
				if (concept.getRelated() != null) {
					for (String str : concept.getRelated()) {
						Related related = new Related();
						related.setResource(str);
						Concept.Choice ch = new Concept.Choice();
						ch.setRelated(related);
						choices.add(ch);
					}
				}
				con.setChoiceList(choices);
				conceptList.add(con);
			}
			rdf.setConceptList(conceptList);
		}
	}

	private static void appendAgents(RDF rdf, List<AgentImpl> agents) {
		if (agents != null) {
			List<AgentType> agentList = new ArrayList<AgentType>();

			for (AgentImpl ag : agents) {
				AgentType agent = new AgentType();
				agent.setAbout(ag.getAbout());
				List<AltLabel> altLabelList = convertListFromMap(
						AltLabel.class, ag.getAltLabel());
				if (altLabelList != null) {
					agent.setAltLabelList(altLabelList);
				}
				Begin begin = convertMapToObj(Begin.class, ag.getBegin());
				if (begin != null) {
					agent.setBegin(begin);
				}
				End end = convertMapToObj(End.class, ag.getEnd());
				if (end != null) {
					agent.setEnd(end);
				}
				BiographicalInformation bio = convertMapToObj(
						BiographicalInformation.class,
						ag.getRdaGr2BiographicalInformation());
				if (bio != null) {
					agent.setBiographicalInformation(bio);
				}
				List<Date> dateList = convertListFromMap(Date.class,
						ag.getDcDate());
				if (dateList != null) {
					agent.setDateList(dateList);
				}
				DateOfBirth dob = convertMapToObj(DateOfBirth.class,
						ag.getRdaGr2DateOfBirth());
				if (dob != null) {
					agent.setDateOfBirth(dob);
				}
				DateOfDeath dod = convertMapToObj(DateOfDeath.class,
						ag.getRdaGr2DateOfDeath());
				if (dod != null) {
					agent.setDateOfDeath(dod);
				}
				DateOfEstablishment doe = convertMapToObj(
						DateOfEstablishment.class,
						ag.getRdaGr2DateOfEstablishment());
				if (doe != null) {
					agent.setDateOfEstablishment(doe);
				}
				DateOfTermination dot = convertMapToObj(
						DateOfTermination.class,
						ag.getRdaGr2DateOfTermination());
				if (dot != null) {
					agent.setDateOfTermination(dot);
				}
				Gender gender = convertMapToObj(Gender.class,
						ag.getRdaGr2Gender());
				if (gender != null) {
					agent.setGender(gender);
				}
				List<HasMet> hasMetList = convertListFromMap(HasMet.class,
						ag.getEdmHasMet());
				if (hasMetList != null) {
					agent.setHasMetList(hasMetList);
				}
				List<Identifier> identifierList = convertListFromMap(
						Identifier.class, ag.getDcIdentifier());
				if (identifierList != null) {
					agent.setIdentifierList(identifierList);
				}
				List<IsRelatedTo> isRelatedToList = convertListFromMap(
						IsRelatedTo.class, ag.getEdmIsRelatedTo());
				if (isRelatedToList != null) {
					agent.setIsRelatedToList(isRelatedToList);
				}
				List<Name> nameList = convertListFromMap(Name.class,
						ag.getFoafName());
				if (nameList != null) {
					agent.setNameList(nameList);
				}
				List<Note> noteList = convertListFromMap(Note.class,
						ag.getNote());
				if (noteList != null) {
					agent.setNoteList(noteList);
				}
				List<PrefLabel> prefLabelList = convertListFromMap(
						PrefLabel.class, ag.getPrefLabel());
				if (prefLabelList != null) {
					agent.setPrefLabelList(prefLabelList);
				}
				ProfessionOrOccupation profOrOcc = convertMapToObj(
						ProfessionOrOccupation.class,
						ag.getRdaGr2ProfessionOrOccupation());
				if (profOrOcc != null) {
					agent.setProfessionOrOccupation(profOrOcc);
				}
				List<SameAs> sameAsList = convertListFromArray(SameAs.class,
						ag.getOwlSameAs());
				if (sameAsList != null) {
					agent.setSameAList(sameAsList);
				}
				agentList.add(agent);
			}
			rdf.setAgentList(agentList);
		}
	}

	private static void appendEuropeanaAggregation(RDF rdf, FullBeanImpl fBean) {
		EuropeanaAggregationType aggregation = new EuropeanaAggregationType();
		EuropeanaAggregation europeanaAggregation = fBean
				.getEuropeanaAggregation();
		aggregation.setAbout(europeanaAggregation.getAbout());

		AggregatedCHO cho = convertStringToObj(AggregatedCHO.class,
				europeanaAggregation.getAggregatedCHO());
		if (cho != null) {
			aggregation.setAggregatedCHO(cho);
		} else {
			AggregatedCHO agCHO = new AggregatedCHO();
			agCHO.setResource(fBean.getProvidedCHOs().get(0).getAbout());
			aggregation.setAggregatedCHO(agCHO);
		}
		List<Aggregates> aggregatesList = convertListFromArray(
				Aggregates.class, europeanaAggregation.getAggregates());
		if (aggregatesList != null) {
			aggregation.setAggregateList(aggregatesList);
		}
		CollectionName collectionName = new CollectionName();
		collectionName.setString(fBean.getEuropeanaCollectionName()[0]);
		aggregation.setCollectionName(collectionName);
		Country country = convertMapToCountry(europeanaAggregation
				.getEdmCountry());
		if (country != null) {
			aggregation.setCountry(country);
		}
		Creator creator = convertMapToObj(Creator.class,
				europeanaAggregation.getDcCreator());
		if (creator != null) {
			aggregation.setCreator(creator);
		}
		List<HasView> hasViewList = convertListFromArray(HasView.class,
				europeanaAggregation.getEdmHasView());
		if (hasViewList != null) {
			aggregation.setHasViewList(hasViewList);
		}
		IsShownBy isShownBy = convertStringToObj(IsShownBy.class,
				europeanaAggregation.getEdmIsShownBy());
		if (isShownBy != null) {
			aggregation.setIsShownBy(isShownBy);
		}
		LandingPage lp = convertStringToObj(LandingPage.class,
				europeanaAggregation.getEdmLandingPage());
		if (lp != null) {
			aggregation.setLandingPage(lp);
		}
		Language1 language = convertMapToLanguage(europeanaAggregation
				.getEdmLanguage());
		if (language != null) {
			aggregation.setLanguage(language);
		}
		Preview preview = convertStringToObj(Preview.class,
				europeanaAggregation.getEdmPreview());
		if (preview != null) {
			aggregation.setPreview(preview);
		}
		Rights1 rights = convertMapToObj(Rights1.class,
				europeanaAggregation.getEdmRights());
		if (rights != null) {
			aggregation.setRights(rights);
		} else {
			Rights1 rights1 = new Rights1();
			ResourceOrLiteralType.Lang lang = new ResourceOrLiteralType.Lang();
			lang.setLang("");
			rights1.setLang(lang);
			rights1.setString("");
			Resource res = new Resource();
			res.setResource("http://testrights/");
			rights1.setResource(res);
			aggregation.setRights(rights1);
		}
		List<EuropeanaAggregationType> lst = new ArrayList<EuropeanaAggregationType>();
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
			country.setCountry(CountryCodes.convert(edmCountry.entrySet()
					.iterator().next().getValue().get(0)));
		}
		return null;
	}

	private static void appendProxy(RDF rdf, List<ProxyImpl> proxies) {
		List<ProxyType> proxyList = new ArrayList<ProxyType>();
		for (ProxyImpl prx : proxies) {
			ProxyType proxy = new ProxyType();
			proxy.setAbout(prx.getAbout());
			CurrentLocation cl = convertStringToObj(CurrentLocation.class,
					prx.getEdmCurrentLocation());
			if (cl != null) {
				proxy.setCurrentLocation(cl);
			}
			EuropeanaProxy europeanaProxy = new EuropeanaProxy();
			europeanaProxy.setEuropeanaProxy(prx.isEuropeanaProxy());
			proxy.setEuropeanaProxy(europeanaProxy);
			List<HasMet> hasMetList = convertListFromMap(HasMet.class,
					prx.getEdmHasMet());
			if (hasMetList != null) {
				proxy.setHasMetList(hasMetList);
			}
			List<HasType> hasTypeList = convertListFromMap(HasType.class,
					prx.getEdmHasType());
			if (hasTypeList != null) {
				proxy.setHasTypeList(hasTypeList);
			}
			List<Incorporates> incorporates = convertListFromArray(
					Incorporates.class, prx.getEdmIncorporates());
			if (incorporates != null) {
				proxy.setIncorporateList(incorporates);
			}
			List<IsDerivativeOf> derivativeList = convertListFromArray(
					IsDerivativeOf.class, prx.getEdmIsDerivativeOf());
			if (derivativeList != null) {
				proxy.setIsDerivativeOfList(derivativeList);
			}

			List<IsNextInSequence> nis = null;

			String[] seqArray = prx.getEdmIsNextInSequence();

			if (seqArray != null) {
				nis = new ArrayList<IsNextInSequence>();

				for (int i = 0; i < seqArray.length; i++) {
					IsNextInSequence item = new IsNextInSequence();
					item.setResource(seqArray[i]);
					nis.add(item);
				}
			}

			if (nis != null) {
				proxy.setIsNextInSequenceList(nis);
			}

			List<IsRelatedTo> isRelatedToList = convertListFromMap(
					IsRelatedTo.class, prx.getEdmIsRelatedTo());
			if (isRelatedToList != null) {
				proxy.setIsRelatedToList(isRelatedToList);
			}
			IsRepresentationOf isRepOf = convertStringToObj(
					IsRepresentationOf.class, prx.getEdmIsRepresentationOf());
			if (isRepOf != null) {
				proxy.setIsRepresentationOf(isRepOf);
			}
			List<IsSimilarTo> isSimilarTo = convertListFromArray(
					IsSimilarTo.class, prx.getEdmIsSimilarTo());
			if (isSimilarTo != null) {
				proxy.setIsSimilarToList(isSimilarTo);
			}
			List<IsSuccessorOf> isSuccessorOf = convertListFromArray(
					IsSuccessorOf.class, prx.getEdmIsSuccessorOf());
			if (isSuccessorOf != null) {
				proxy.setIsSuccessorOfList(isSuccessorOf);
			}
			ProxyFor proxyFor = convertStringToObj(ProxyFor.class,
					prx.getProxyFor());
			if (proxyFor != null) {
				proxy.setProxyFor(proxyFor);
			}
			List<ProxyIn> proxyIn = convertListFromArray(ProxyIn.class,
					prx.getProxyIn());
			if (proxyIn != null) {
				proxy.setProxyInList(proxyIn);
			}
			if (!prx.isEuropeanaProxy()) {

				Type1 type = new Type1();

				type.setType(EdmType.valueOf(prx.getEdmType().toString()
						.replace("3D", "_3_D")));

				proxy.setType(type);

			} else {
				Type1 type = new Type1();
				type.setType(EdmType.IMAGE);
				proxy.setType(type);
			}
			List<Year> years = convertListFromMap(Year.class, prx.getYear());
			if (years != null) {
				proxy.setYearList(years);
			}
			List<EuropeanaType.Choice> dcChoices = new ArrayList<EuropeanaType.Choice>();
			if (prx.getDcContributor() != null) {
				for (Entry<String, List<String>> entry : prx.getDcContributor()
						.entrySet()) {
					ResourceOrLiteralType.Lang lang = null;
					if (StringUtils.isNotEmpty(entry.getKey())
							&& !StringUtils.equals("def", entry.getKey())) {
						lang = new ResourceOrLiteralType.Lang();
						lang.setLang(entry.getKey());
					}
					for (String str : entry.getValue()) {
						Contributor obj = new Contributor();
						createResourceOrLiteralFromString(obj, lang, str);
						EuropeanaType.Choice ch = new EuropeanaType.Choice();
						ch.setContributor(obj);
						dcChoices.add(ch);
					}
				}
			}
			if (prx.getDcCoverage() != null) {
				for (Entry<String, List<String>> entry : prx.getDcCoverage()
						.entrySet()) {
					ResourceOrLiteralType.Lang lang = null;
					if (StringUtils.isNotEmpty(entry.getKey())
							&& !StringUtils.equals("def", entry.getKey())) {
						lang = new ResourceOrLiteralType.Lang();
						lang.setLang(entry.getKey());
					}
					for (String str : entry.getValue()) {
						Coverage obj = new Coverage();
						createResourceOrLiteralFromString(obj, lang, str);
						EuropeanaType.Choice ch = new EuropeanaType.Choice();
						ch.setCoverage(obj);
						dcChoices.add(ch);
					}
				}
			}
			if (prx.getDcCreator() != null) {
				for (Entry<String, List<String>> entry : prx.getDcCreator()
						.entrySet()) {
					ResourceOrLiteralType.Lang lang = null;
					if (StringUtils.isNotEmpty(entry.getKey())
							&& !StringUtils.equals("def", entry.getKey())) {
						lang = new ResourceOrLiteralType.Lang();
						lang.setLang(entry.getKey());
					}
					for (String str : entry.getValue()) {
						Creator obj = new Creator();
						createResourceOrLiteralFromString(obj, lang, str);
						EuropeanaType.Choice ch = new EuropeanaType.Choice();
						ch.setCreator(obj);
						dcChoices.add(ch);
					}
				}
			}
			if (prx.getDcDate() != null) {
				for (Entry<String, List<String>> entry : prx.getDcDate()
						.entrySet()) {
					ResourceOrLiteralType.Lang lang = null;
					if (StringUtils.isNotEmpty(entry.getKey())
							&& !StringUtils.equals("def", entry.getKey())) {
						lang = new ResourceOrLiteralType.Lang();
						lang.setLang(entry.getKey());
					}
					for (String str : entry.getValue()) {
						Date obj = new Date();
						createResourceOrLiteralFromString(obj, lang, str);
						EuropeanaType.Choice ch = new EuropeanaType.Choice();
						ch.setDate(obj);
						dcChoices.add(ch);
					}
				}
			}
			if (prx.getDcDescription() != null) {
				for (Entry<String, List<String>> entry : prx.getDcDescription()
						.entrySet()) {
					ResourceOrLiteralType.Lang lang = null;
					if (StringUtils.isNotEmpty(entry.getKey())
							&& !StringUtils.equals("def", entry.getKey())) {
						lang = new ResourceOrLiteralType.Lang();
						lang.setLang(entry.getKey());
					}
					for (String str : entry.getValue()) {
						Description obj = new Description();
						createResourceOrLiteralFromString(obj, lang, str);
						EuropeanaType.Choice ch = new EuropeanaType.Choice();
						ch.setDescription(obj);
						dcChoices.add(ch);
					}
				}
			}
			if (prx.getDcFormat() != null) {
				for (Entry<String, List<String>> entry : prx.getDcFormat()
						.entrySet()) {
					ResourceOrLiteralType.Lang lang = null;
					if (StringUtils.isNotEmpty(entry.getKey())
							&& !StringUtils.equals("def", entry.getKey())) {
						lang = new ResourceOrLiteralType.Lang();
						lang.setLang(entry.getKey());
					}
					for (String str : entry.getValue()) {
						Format obj = new Format();
						createResourceOrLiteralFromString(obj, lang, str);
						EuropeanaType.Choice ch = new EuropeanaType.Choice();
						ch.setFormat(obj);
						dcChoices.add(ch);
					}
				}
			}
			if (prx.getDcIdentifier() != null) {
				for (Entry<String, List<String>> entry : prx.getDcIdentifier()
						.entrySet()) {
					LiteralType.Lang lang = null;
					if (StringUtils.isNotEmpty(entry.getKey())
							&& !StringUtils.equals("def", entry.getKey())) {
						lang = new LiteralType.Lang();
						lang.setLang(entry.getKey());
					}
					for (String str : entry.getValue()) {
						Identifier obj = new Identifier();
						obj.setString(str);
						if (lang == null) {
							lang = new LiteralType.Lang();
							lang.setLang("");
						}
						obj.setLang(lang);
						EuropeanaType.Choice ch = new EuropeanaType.Choice();
						ch.setIdentifier(obj);
						dcChoices.add(ch);
					}
				}
			}
			if (prx.getDcPublisher() != null) {
				for (Entry<String, List<String>> entry : prx.getDcPublisher()
						.entrySet()) {
					ResourceOrLiteralType.Lang lang = null;
					if (StringUtils.isNotEmpty(entry.getKey())
							&& !StringUtils.equals("def", entry.getKey())) {
						lang = new ResourceOrLiteralType.Lang();
						lang.setLang(entry.getKey());
					}
					for (String str : entry.getValue()) {
						Publisher obj = new Publisher();
						createResourceOrLiteralFromString(obj, lang, str);
						EuropeanaType.Choice ch = new EuropeanaType.Choice();
						ch.setPublisher(obj);
						dcChoices.add(ch);
					}
				}
			}
			if (prx.getDcRelation() != null) {
				for (Entry<String, List<String>> entry : prx.getDcRelation()
						.entrySet()) {
					ResourceOrLiteralType.Lang lang = null;
					if (StringUtils.isNotEmpty(entry.getKey())
							&& !StringUtils.equals("def", entry.getKey())) {
						lang = new ResourceOrLiteralType.Lang();
						lang.setLang(entry.getKey());
					}
					for (String str : entry.getValue()) {
						Relation obj = new Relation();
						createResourceOrLiteralFromString(obj, lang, str);
						EuropeanaType.Choice ch = new EuropeanaType.Choice();
						ch.setRelation(obj);
						dcChoices.add(ch);
					}
				}
			}
			if (prx.getDcRights() != null) {
				for (Entry<String, List<String>> entry : prx.getDcRights()
						.entrySet()) {
					ResourceOrLiteralType.Lang lang = null;
					if (StringUtils.isNotEmpty(entry.getKey())
							&& !StringUtils.equals("def", entry.getKey())) {
						lang = new ResourceOrLiteralType.Lang();
						lang.setLang(entry.getKey());
					}
					for (String str : entry.getValue()) {
						Rights obj = new Rights();
						createResourceOrLiteralFromString(obj, lang, str);
						EuropeanaType.Choice ch = new EuropeanaType.Choice();
						ch.setRights(obj);
						dcChoices.add(ch);
					}
				}
			}
			if (prx.getDcSource() != null) {
				for (Entry<String, List<String>> entry : prx.getDcSource()
						.entrySet()) {
					ResourceOrLiteralType.Lang lang = null;
					if (StringUtils.isNotEmpty(entry.getKey())
							&& !StringUtils.equals("def", entry.getKey())) {
						lang = new ResourceOrLiteralType.Lang();
						lang.setLang(entry.getKey());
					}
					for (String str : entry.getValue()) {
						Source obj = new Source();
						createResourceOrLiteralFromString(obj, lang, str);
						EuropeanaType.Choice ch = new EuropeanaType.Choice();
						ch.setSource(obj);
						dcChoices.add(ch);
					}
				}
			}
			if (prx.getDcSubject() != null) {
				for (Entry<String, List<String>> entry : prx.getDcSubject()
						.entrySet()) {
					ResourceOrLiteralType.Lang lang = null;
					if (StringUtils.isNotEmpty(entry.getKey())
							&& !StringUtils.equals("def", entry.getKey())) {
						lang = new ResourceOrLiteralType.Lang();
						lang.setLang(entry.getKey());
					}
					for (String str : entry.getValue()) {
						Subject obj = new Subject();
						createResourceOrLiteralFromString(obj, lang, str);
						EuropeanaType.Choice ch = new EuropeanaType.Choice();
						ch.setSubject(obj);
						dcChoices.add(ch);
					}
				}
			}
			if (prx.getDcTitle() != null) {
				for (Entry<String, List<String>> entry : prx.getDcTitle()
						.entrySet()) {
					LiteralType.Lang lang = null;
					if (StringUtils.isNotEmpty(entry.getKey())
							&& !StringUtils.equals("def", entry.getKey())) {
						lang = new LiteralType.Lang();
						lang.setLang(entry.getKey());
					}
					for (String str : entry.getValue()) {
						Title obj = new Title();
						obj.setString(str);
						if (lang == null) {
							lang = new LiteralType.Lang();
							lang.setLang("");
						}
						obj.setLang(lang);
						EuropeanaType.Choice ch = new EuropeanaType.Choice();
						ch.setTitle(obj);
						dcChoices.add(ch);
					}
				}
			}
			if (prx.getDcType() != null) {
				for (Entry<String, List<String>> entry : prx.getDcType()
						.entrySet()) {
					ResourceOrLiteralType.Lang lang = null;
					if (StringUtils.isNotEmpty(entry.getKey())
							&& !StringUtils.equals("def", entry.getKey())) {
						lang = new ResourceOrLiteralType.Lang();
						lang.setLang(entry.getKey());
					}
					for (String str : entry.getValue()) {
						Type obj = new Type();
						createResourceOrLiteralFromString(obj, lang, str);
						EuropeanaType.Choice ch = new EuropeanaType.Choice();
						ch.setType(obj);
						dcChoices.add(ch);
					}
				}
			}
			if (prx.getDctermsAlternative() != null) {
				for (Entry<String, List<String>> entry : prx
						.getDctermsAlternative().entrySet()) {
					LiteralType.Lang lang = null;
					if (StringUtils.isNotEmpty(entry.getKey())
							&& !StringUtils.equals("def", entry.getKey())) {
						lang = new LiteralType.Lang();
						lang.setLang(entry.getKey());
					}
					for (String str : entry.getValue()) {
						Alternative obj = new Alternative();
						obj.setString(str);
						if (lang == null) {
							lang = new LiteralType.Lang();
							lang.setLang("");
						}
						obj.setLang(lang);
						EuropeanaType.Choice ch = new EuropeanaType.Choice();
						ch.setAlternative(obj);
						dcChoices.add(ch);
					}
				}
			}
			if (prx.getDctermsConformsTo() != null) {
				for (Entry<String, List<String>> entry : prx
						.getDctermsConformsTo().entrySet()) {
					ResourceOrLiteralType.Lang lang = null;
					if (StringUtils.isNotEmpty(entry.getKey())
							&& !StringUtils.equals("def", entry.getKey())) {
						lang = new ResourceOrLiteralType.Lang();
						lang.setLang(entry.getKey());
					}
					for (String str : entry.getValue()) {
						ConformsTo obj = new ConformsTo();
						createResourceOrLiteralFromString(obj, lang, str);
						EuropeanaType.Choice ch = new EuropeanaType.Choice();
						ch.setConformsTo(obj);
						dcChoices.add(ch);
					}
				}
			}
			if (prx.getDctermsCreated() != null) {
				for (Entry<String, List<String>> entry : prx
						.getDctermsCreated().entrySet()) {
					ResourceOrLiteralType.Lang lang = null;
					if (StringUtils.isNotEmpty(entry.getKey())
							&& !StringUtils.equals("def", entry.getKey())) {
						lang = new ResourceOrLiteralType.Lang();
						lang.setLang(entry.getKey());
					}
					for (String str : entry.getValue()) {
						Created obj = new Created();
						createResourceOrLiteralFromString(obj, lang, str);
						EuropeanaType.Choice ch = new EuropeanaType.Choice();
						ch.setCreated(obj);
						dcChoices.add(ch);
					}
				}
			}
			if (prx.getDctermsExtent() != null) {
				for (Entry<String, List<String>> entry : prx.getDctermsExtent()
						.entrySet()) {
					ResourceOrLiteralType.Lang lang = null;
					if (StringUtils.isNotEmpty(entry.getKey())
							&& !StringUtils.equals("def", entry.getKey())) {
						lang = new ResourceOrLiteralType.Lang();
						lang.setLang(entry.getKey());
					}
					for (String str : entry.getValue()) {
						Extent obj = new Extent();
						createResourceOrLiteralFromString(obj, lang, str);
						EuropeanaType.Choice ch = new EuropeanaType.Choice();
						ch.setExtent(obj);
						dcChoices.add(ch);
					}
				}
			}
			if (prx.getDctermsHasFormat() != null) {
				for (Entry<String, List<String>> entry : prx
						.getDctermsHasFormat().entrySet()) {
					ResourceOrLiteralType.Lang lang = null;
					if (StringUtils.isNotEmpty(entry.getKey())
							&& !StringUtils.equals("def", entry.getKey())) {
						lang = new ResourceOrLiteralType.Lang();
						lang.setLang(entry.getKey());
					}
					for (String str : entry.getValue()) {
						HasFormat obj = new HasFormat();
						createResourceOrLiteralFromString(obj, lang, str);
						EuropeanaType.Choice ch = new EuropeanaType.Choice();
						ch.setHasFormat(obj);
						dcChoices.add(ch);
					}
				}
			}
			if (prx.getDctermsHasPart() != null) {
				for (Entry<String, List<String>> entry : prx
						.getDctermsHasPart().entrySet()) {
					ResourceOrLiteralType.Lang lang = null;
					if (StringUtils.isNotEmpty(entry.getKey())
							&& !StringUtils.equals("def", entry.getKey())) {
						lang = new ResourceOrLiteralType.Lang();
						lang.setLang(entry.getKey());
					}
					for (String str : entry.getValue()) {
						HasPart obj = new HasPart();
						createResourceOrLiteralFromString(obj, lang, str);
						EuropeanaType.Choice ch = new EuropeanaType.Choice();
						ch.setHasPart(obj);
						dcChoices.add(ch);
					}
				}
			}
			if (prx.getDctermsHasVersion() != null) {
				for (Entry<String, List<String>> entry : prx
						.getDctermsHasVersion().entrySet()) {
					ResourceOrLiteralType.Lang lang = null;
					if (StringUtils.isNotEmpty(entry.getKey())
							&& !StringUtils.equals("def", entry.getKey())) {
						lang = new ResourceOrLiteralType.Lang();
						lang.setLang(entry.getKey());
					}
					for (String str : entry.getValue()) {
						HasVersion obj = new HasVersion();
						createResourceOrLiteralFromString(obj, lang, str);
						EuropeanaType.Choice ch = new EuropeanaType.Choice();
						ch.setHasVersion(obj);
						dcChoices.add(ch);
					}
				}
			}
			if (prx.getDctermsIsFormatOf() != null) {
				for (Entry<String, List<String>> entry : prx
						.getDctermsIsFormatOf().entrySet()) {
					ResourceOrLiteralType.Lang lang = null;
					if (StringUtils.isNotEmpty(entry.getKey())
							&& !StringUtils.equals("def", entry.getKey())) {
						lang = new ResourceOrLiteralType.Lang();
						lang.setLang(entry.getKey());
					}
					for (String str : entry.getValue()) {
						IsFormatOf obj = new IsFormatOf();
						createResourceOrLiteralFromString(obj, lang, str);
						EuropeanaType.Choice ch = new EuropeanaType.Choice();
						ch.setIsFormatOf(obj);
						dcChoices.add(ch);
					}
				}
			}
			if (prx.getDctermsIsPartOf() != null) {
				for (Entry<String, List<String>> entry : prx
						.getDctermsIsPartOf().entrySet()) {
					ResourceOrLiteralType.Lang lang = null;
					if (StringUtils.isNotEmpty(entry.getKey())
							&& !StringUtils.equals("def", entry.getKey())) {
						lang = new ResourceOrLiteralType.Lang();
						lang.setLang(entry.getKey());
					}
					for (String str : entry.getValue()) {
						IsPartOf obj = new IsPartOf();
						createResourceOrLiteralFromString(obj, lang, str);
						EuropeanaType.Choice ch = new EuropeanaType.Choice();
						ch.setIsPartOf(obj);
						dcChoices.add(ch);
					}
				}
			}
			if (prx.getDctermsIsReferencedBy() != null) {
				for (Entry<String, List<String>> entry : prx
						.getDctermsIsReferencedBy().entrySet()) {
					ResourceOrLiteralType.Lang lang = null;
					if (StringUtils.isNotEmpty(entry.getKey())
							&& !StringUtils.equals("def", entry.getKey())) {
						lang = new ResourceOrLiteralType.Lang();
						lang.setLang(entry.getKey());
					}
					for (String str : entry.getValue()) {
						IsReferencedBy obj = new IsReferencedBy();
						createResourceOrLiteralFromString(obj, lang, str);
						EuropeanaType.Choice ch = new EuropeanaType.Choice();
						ch.setIsReferencedBy(obj);
						dcChoices.add(ch);
					}
				}
			}
			if (prx.getDctermsIsReplacedBy() != null) {
				for (Entry<String, List<String>> entry : prx
						.getDctermsIsReplacedBy().entrySet()) {
					ResourceOrLiteralType.Lang lang = null;
					if (StringUtils.isNotEmpty(entry.getKey())
							&& !StringUtils.equals("def", entry.getKey())) {
						lang = new ResourceOrLiteralType.Lang();
						lang.setLang(entry.getKey());
					}
					for (String str : entry.getValue()) {
						IsReplacedBy obj = new IsReplacedBy();
						createResourceOrLiteralFromString(obj, lang, str);
						EuropeanaType.Choice ch = new EuropeanaType.Choice();
						ch.setIsReplacedBy(obj);
						dcChoices.add(ch);
					}
				}
			}
			if (prx.getDctermsIssued() != null) {
				for (Entry<String, List<String>> entry : prx.getDctermsIssued()
						.entrySet()) {
					ResourceOrLiteralType.Lang lang = null;
					if (StringUtils.isNotEmpty(entry.getKey())
							&& !StringUtils.equals("def", entry.getKey())) {
						lang = new ResourceOrLiteralType.Lang();
						lang.setLang(entry.getKey());
					}
					for (String str : entry.getValue()) {
						Issued obj = new Issued();
						createResourceOrLiteralFromString(obj, lang, str);
						EuropeanaType.Choice ch = new EuropeanaType.Choice();
						ch.setIssued(obj);
						dcChoices.add(ch);
					}
				}
			}
			if (prx.getDctermsIsRequiredBy() != null) {
				for (Entry<String, List<String>> entry : prx
						.getDctermsIsRequiredBy().entrySet()) {
					ResourceOrLiteralType.Lang lang = null;
					if (StringUtils.isNotEmpty(entry.getKey())
							&& !StringUtils.equals("def", entry.getKey())) {
						lang = new ResourceOrLiteralType.Lang();
						lang.setLang(entry.getKey());
					}
					for (String str : entry.getValue()) {
						IsRequiredBy obj = new IsRequiredBy();
						createResourceOrLiteralFromString(obj, lang, str);
						EuropeanaType.Choice ch = new EuropeanaType.Choice();
						ch.setIsRequiredBy(obj);
						dcChoices.add(ch);
					}
				}
			}
			if (prx.getDctermsIsVersionOf() != null) {
				for (Entry<String, List<String>> entry : prx
						.getDctermsIsVersionOf().entrySet()) {
					ResourceOrLiteralType.Lang lang = null;
					if (StringUtils.isNotEmpty(entry.getKey())
							&& !StringUtils.equals("def", entry.getKey())) {
						lang = new ResourceOrLiteralType.Lang();
						lang.setLang(entry.getKey());
					}
					for (String str : entry.getValue()) {
						IsVersionOf obj = new IsVersionOf();
						createResourceOrLiteralFromString(obj, lang, str);
						EuropeanaType.Choice ch = new EuropeanaType.Choice();
						ch.setIsVersionOf(obj);
						dcChoices.add(ch);
					}
				}
			}
			if (prx.getDctermsMedium() != null) {
				for (Entry<String, List<String>> entry : prx.getDctermsMedium()
						.entrySet()) {
					ResourceOrLiteralType.Lang lang = null;
					if (StringUtils.isNotEmpty(entry.getKey())
							&& !StringUtils.equals("def", entry.getKey())) {
						lang = new ResourceOrLiteralType.Lang();
						lang.setLang(entry.getKey());
					}
					for (String str : entry.getValue()) {
						Medium obj = new Medium();
						createResourceOrLiteralFromString(obj, lang, str);
						EuropeanaType.Choice ch = new EuropeanaType.Choice();
						ch.setMedium(obj);
						dcChoices.add(ch);
					}
				}
			}
			if (prx.getDctermsProvenance() != null) {
				for (Entry<String, List<String>> entry : prx
						.getDctermsProvenance().entrySet()) {
					ResourceOrLiteralType.Lang lang = null;
					if (StringUtils.isNotEmpty(entry.getKey())
							&& !StringUtils.equals("def", entry.getKey())) {
						lang = new ResourceOrLiteralType.Lang();
						lang.setLang(entry.getKey());
					}
					for (String str : entry.getValue()) {
						Provenance obj = new Provenance();
						createResourceOrLiteralFromString(obj, lang, str);
						EuropeanaType.Choice ch = new EuropeanaType.Choice();
						ch.setProvenance(obj);
						dcChoices.add(ch);
					}
				}
			}
			if (prx.getDctermsReferences() != null) {
				for (Entry<String, List<String>> entry : prx
						.getDctermsReferences().entrySet()) {
					ResourceOrLiteralType.Lang lang = null;
					if (StringUtils.isNotEmpty(entry.getKey())
							&& !StringUtils.equals("def", entry.getKey())) {
						lang = new ResourceOrLiteralType.Lang();
						lang.setLang(entry.getKey());
					}
					for (String str : entry.getValue()) {
						References obj = new References();
						createResourceOrLiteralFromString(obj, lang, str);
						EuropeanaType.Choice ch = new EuropeanaType.Choice();
						ch.setReferences(obj);
						dcChoices.add(ch);
					}
				}
			}
			if (prx.getDctermsReplaces() != null) {
				for (Entry<String, List<String>> entry : prx
						.getDctermsReplaces().entrySet()) {
					ResourceOrLiteralType.Lang lang = null;
					if (StringUtils.isNotEmpty(entry.getKey())
							&& !StringUtils.equals("def", entry.getKey())) {
						lang = new ResourceOrLiteralType.Lang();
						lang.setLang(entry.getKey());
					}
					for (String str : entry.getValue()) {
						Replaces obj = new Replaces();
						createResourceOrLiteralFromString(obj, lang, str);
						EuropeanaType.Choice ch = new EuropeanaType.Choice();
						ch.setReplaces(obj);
						dcChoices.add(ch);
					}
				}
			}
			if (prx.getDctermsRequires() != null) {
				for (Entry<String, List<String>> entry : prx
						.getDctermsRequires().entrySet()) {
					ResourceOrLiteralType.Lang lang = null;
					if (StringUtils.isNotEmpty(entry.getKey())
							&& !StringUtils.equals("def", entry.getKey())) {
						lang = new ResourceOrLiteralType.Lang();
						lang.setLang(entry.getKey());
					}
					for (String str : entry.getValue()) {
						Requires obj = new Requires();
						createResourceOrLiteralFromString(obj, lang, str);
						EuropeanaType.Choice ch = new EuropeanaType.Choice();
						ch.setRequires(obj);
						dcChoices.add(ch);
					}
				}
			}
			if (prx.getDctermsSpatial() != null) {
				for (Entry<String, List<String>> entry : prx
						.getDctermsSpatial().entrySet()) {
					ResourceOrLiteralType.Lang lang = null;
					if (StringUtils.isNotEmpty(entry.getKey())
							&& !StringUtils.equals("def", entry.getKey())) {
						lang = new ResourceOrLiteralType.Lang();
						lang.setLang(entry.getKey());
					}
					for (String str : entry.getValue()) {
						Spatial obj = new Spatial();
						createResourceOrLiteralFromString(obj, lang, str);
						EuropeanaType.Choice ch = new EuropeanaType.Choice();
						ch.setSpatial(obj);
						dcChoices.add(ch);
					}
				}
			}
			if (prx.getDctermsTemporal() != null) {
				for (Entry<String, List<String>> entry : prx
						.getDctermsTemporal().entrySet()) {
					ResourceOrLiteralType.Lang lang = null;
					if (StringUtils.isNotEmpty(entry.getKey())
							&& !StringUtils.equals("def", entry.getKey())) {
						lang = new ResourceOrLiteralType.Lang();
						lang.setLang(entry.getKey());
					}
					for (String str : entry.getValue()) {
						Temporal obj = new Temporal();
						createResourceOrLiteralFromString(obj, lang, str);
						EuropeanaType.Choice ch = new EuropeanaType.Choice();
						ch.setTemporal(obj);
						dcChoices.add(ch);
					}
				}
			}
			if (prx.getDctermsTOC() != null) {
				for (Entry<String, List<String>> entry : prx.getDctermsTOC()
						.entrySet()) {
					ResourceOrLiteralType.Lang lang = null;
					if (StringUtils.isNotEmpty(entry.getKey())
							&& !StringUtils.equals("def", entry.getKey())) {
						lang = new ResourceOrLiteralType.Lang();
						lang.setLang(entry.getKey());
					}
					for (String str : entry.getValue()) {
						TableOfContents obj = new TableOfContents();
						createResourceOrLiteralFromString(obj, lang, str);
						EuropeanaType.Choice ch = new EuropeanaType.Choice();
						ch.setTableOfContents(obj);
						dcChoices.add(ch);
					}
				}
			}
			proxy.setChoiceList(dcChoices);
			proxyList.add(proxy);
		}

		rdf.setProxyList(proxyList);
	}

	private static void appendAggregation(RDF rdf,
			List<AggregationImpl> aggregations) {
		List<Aggregation> aggregationList = new ArrayList<Aggregation>();
		for (AggregationImpl aggr : aggregations) {
			Aggregation aggregation = new Aggregation();
			aggregation.setAbout(aggr.getAbout());
			AggregatedCHO agCho = convertStringToObj(AggregatedCHO.class,
					aggr.getAggregatedCHO());
			if (agCho != null) {
				aggregation.setAggregatedCHO(agCho);
			} else {
				AggregatedCHO cho = new AggregatedCHO();
				cho.setResource(rdf.getProvidedCHOList().get(0).getAbout());
				aggregation.setAggregatedCHO(cho);
			}
			DataProvider dProvider = convertMapToObj(DataProvider.class,
					aggr.getEdmDataProvider());
			if (dProvider == null) {
				dProvider = convertMapToObj(DataProvider.class,
						aggr.getEdmProvider());
			}
			if (dProvider != null) {
				aggregation.setDataProvider(dProvider);
			}
			IsShownAt isShownAt = convertStringToObj(IsShownAt.class,
					aggr.getEdmIsShownAt());
			if (isShownAt != null) {
				aggregation.setIsShownAt(isShownAt);
			}
			IsShownBy isShownBy = convertStringToObj(IsShownBy.class,
					aggr.getEdmIsShownBy());
			if (isShownBy != null) {
				aggregation.setIsShownBy(isShownBy);
			}
			_Object object = convertStringToObj(_Object.class,
					aggr.getEdmObject());
			if (object != null) {
				aggregation.setObject(object);
			}
			Provider provider = convertMapToObj(Provider.class,
					aggr.getEdmProvider());
			if (provider != null) {
				aggregation.setProvider(provider);
			}
			Rights1 rights1 = convertMapToObj(Rights1.class,
					aggr.getEdmRights());
			if (rights1 == null) {
				rights1 = new Rights1();
				ResourceOrLiteralType.Lang lang = new ResourceOrLiteralType.Lang();
				lang.setLang("");
				rights1.setString("");
				Resource testResource = new Resource();
				testResource.setResource("http://testedmrights/");
				rights1.setResource(testResource);
			}
			aggregation.setRights(rights1);
			if (aggr.getEdmUgc() != null) {
				Ugc ugc = new Ugc();

				ugc.setUgc(UGCType.valueOf(StringUtils.upperCase(aggr
						.getEdmUgc())));
				aggregation.setUgc(ugc);
			}
			List<Rights> rightsList = convertListFromMap(Rights.class,
					aggr.getDcRights());
			if (rightsList != null) {
				aggregation.setRightList(rightsList);
			}
			List<HasView> hasViewList = convertListFromArray(HasView.class,
					aggr.getHasView());
			if (hasViewList != null) {
				aggregation.setHasViewList(hasViewList);
			}
			createWebResources(rdf, aggr);
			aggregationList.add(aggregation);
		}
		rdf.setAggregationList(aggregationList);
	}

	private static void createWebResources(RDF rdf, AggregationImpl aggr) {
		List<WebResourceType> webResources = new ArrayList<WebResourceType>();
		for (WebResource wr : aggr.getWebResources()) {
			WebResourceType wResource = new WebResourceType();
			wResource.setAbout(wr.getAbout());
			List<ConformsTo> conformsToList = convertListFromMap(
					ConformsTo.class, wr.getDctermsConformsTo());
			if (conformsToList != null) {
				wResource.setConformsToList(conformsToList);
			}
			List<Created> createdList = convertListFromMap(Created.class,
					wr.getDctermsCreated());
			if (createdList != null) {
				wResource.setCreatedList(createdList);
			}
			List<Description> descriptionList = convertListFromMap(
					Description.class, wr.getDcDescription());
			if (descriptionList != null) {
				wResource.setDescriptionList(descriptionList);
			}
			List<Extent> extentList = convertListFromMap(Extent.class,
					wr.getDctermsExtent());
			if (extentList != null) {
				wResource.setExtentList(extentList);
			}
			List<Format> formatList = convertListFromMap(Format.class,
					wr.getDcFormat());
			if (formatList != null) {
				wResource.setFormatList(formatList);
			}
			List<HasPart> hasPartList = convertListFromMap(HasPart.class,
					wr.getDctermsHasPart());
			if (hasPartList != null) {
				wResource.setHasPartList(hasPartList);
			}
			List<IsFormatOf> isFormatOfList = convertListFromMap(
					IsFormatOf.class, wr.getDctermsIsFormatOf());
			if (isFormatOfList != null) {
				wResource.setIsFormatOfList(isFormatOfList);
			}
			IsNextInSequence isNextInSequence = convertStringToObj(
					IsNextInSequence.class, wr.getIsNextInSequence());
			if (isNextInSequence != null) {
				wResource.setIsNextInSequence(isNextInSequence);
			}
			List<Issued> issuedList = convertListFromMap(Issued.class,
					wr.getDctermsIssued());
			if (issuedList != null) {
				wResource.setIssuedList(issuedList);
			}
			List<Rights> rightsList = convertListFromMap(Rights.class,
					wr.getWebResourceDcRights());
			if (rightsList != null) {
				wResource.setRightList(rightsList);
			}
			Rights1 rights = convertMapToObj(Rights1.class,
					wr.getWebResourceEdmRights());
			if (rights != null) {
				wResource.setRights(rights);
			}
			List<Source> sourceList = convertListFromMap(Source.class,
					wr.getDcSource());
			if (sourceList != null) {
				wResource.setSourceList(sourceList);
			}
			webResources.add(wResource);
		}

		rdf.setWebResourceList(webResources);
	}

	private static void appendCHO(RDF rdf, List<ProvidedCHOImpl> chos) {
		List<ProvidedCHOType> pChoList = new ArrayList<ProvidedCHOType>();
		for (ProvidedCHOImpl pCho : chos) {
			ProvidedCHOType pChoJibx = new ProvidedCHOType();
			pChoJibx.setAbout(pCho.getAbout());
			List<SameAs> sList = convertListFromArray(SameAs.class,
					pCho.getOwlSameAs());
			if (sList != null) {
				pChoJibx.setSameAList(sList);
			}
			pChoList.add(pChoJibx);
		}
		rdf.setProvidedCHOList(pChoList);
	}

	private static <T> T convertStringToObj(Class<T> clazz, String str) {
		try {
			if (str != null) {
				T obj = clazz.newInstance();
				((ResourceType) obj).setResource(str);
				return obj;
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static <T> List<T> convertListFromArray(Class<T> clazz,
			String[] vals) {
		List<T> tList = new ArrayList<T>();
		try {
			if (vals != null) {
				for (String str : vals) {
					T obj = clazz.newInstance();
					((ResourceType) obj).setResource(str);
					tList.add(obj);
				}
				return tList;
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	private static <T> List<T> convertListFromMap(Class<T> clazz,
			Map<String, List<String>> map) {
		if (map != null) {
			List<T> list = new ArrayList<T>();
			for (Entry<String, List<String>> entry : map.entrySet()) {
				try {

					if (clazz.getSuperclass().isAssignableFrom(
							ResourceType.class)) {
						for (String str : entry.getValue()) {
							ResourceType t = (ResourceType) clazz.newInstance();

							t.setResource(str);
							list.add((T) t);
						}

					} else if (clazz.getSuperclass().isAssignableFrom(
							LiteralType.class)) {
						LiteralType.Lang lang = null;
						if (StringUtils.isNotEmpty(entry.getKey())
								&& !StringUtils.equals(entry.getKey(), "def")) {
							lang = new LiteralType.Lang();
							lang.setLang(entry.getKey());
						}
						for (String str : entry.getValue()) {
							LiteralType t = (LiteralType) clazz.newInstance();

							t.setString(str);
							if (lang == null) {
								lang = new LiteralType.Lang();
								lang.setLang("");

							}
							t.setLang(lang);
							list.add((T) t);
						}
					} else if (clazz.getSuperclass().isAssignableFrom(
							ResourceOrLiteralType.class)) {
						ResourceOrLiteralType.Lang lang = null;
						if (StringUtils.isNotEmpty(entry.getKey())
								&& !StringUtils.equals(entry.getKey(), "def")) {
							lang = new ResourceOrLiteralType.Lang();
							lang.setLang(entry.getKey());
						}
						for (String str : entry.getValue()) {
							ResourceOrLiteralType t = (ResourceOrLiteralType) clazz
									.newInstance();
							Resource resource = new Resource();
							resource.setResource("");
							t.setResource(resource);

							t.setString("");
							if (isUri(str)) {
								t.setResource(resource);
							} else {
								t.setString(str);
							}
							if (lang == null) {

								lang = new ResourceOrLiteralType.Lang();
								lang.setLang("");

							}
							t.setLang(lang);
							list.add((T) t);
						}
					}
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			return list;
		}
		return null;
	}

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
						resource.setResource("");

						obj.setResource(resource);
						obj.setString("");
						for (String str : entry.getValue()) {
							if (isUri(str)) {
								obj.setResource(resource);
							} else {
								obj.setString(str);
							}
						}
						if (lang == null) {
							lang = new ResourceOrLiteralType.Lang();
							lang.setLang("");
						}
						obj.setLang(lang);
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
						if (lang == null) {
							lang = new LiteralType.Lang();
							lang.setLang("");
						}
						obj.setLang(lang);
						return (T) obj;
					}
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		return null;
	}

	private static void createResourceOrLiteralFromString(
			ResourceOrLiteralType obj, ResourceOrLiteralType.Lang lang,
			String value) {
		Resource resource = new Resource();
		resource.setResource("");

		obj.setResource(resource);
		obj.setString("");
		if (isUri(value)) {
			obj.setResource(resource);
		} else {
			obj.setString(value);
		}
		if (lang == null) {
			lang = new ResourceOrLiteralType.Lang();
			lang.setLang("");
		}

		obj.setLang(lang);
	}

	private static boolean isUri(String str) {
		if (StringUtils.startsWith(str, "http://")) {
			return true;
		}
		return false;
	}
}
