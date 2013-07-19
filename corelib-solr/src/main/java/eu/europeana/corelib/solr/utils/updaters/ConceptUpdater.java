package eu.europeana.corelib.solr.utils.updaters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

import eu.europeana.corelib.definitions.jibx.AltLabel;
import eu.europeana.corelib.definitions.jibx.Concept;
import eu.europeana.corelib.definitions.jibx.LiteralType;
import eu.europeana.corelib.definitions.jibx.Notation;
import eu.europeana.corelib.definitions.jibx.Note;
import eu.europeana.corelib.definitions.jibx.PrefLabel;
import eu.europeana.corelib.definitions.jibx.ResourceType;
import eu.europeana.corelib.solr.MongoServer;
import eu.europeana.corelib.solr.entity.ConceptImpl;
import eu.europeana.corelib.solr.utils.MongoUtils;

public class ConceptUpdater implements Updater<ConceptImpl,Concept>{

	public void update(ConceptImpl conceptMongo, Concept concept,
			MongoServer mongoServer) {
		Query<ConceptImpl> updateQuery = mongoServer.getDatastore()
				.createQuery(ConceptImpl.class).field("about")
				.equal(conceptMongo.getAbout());
		UpdateOperations<ConceptImpl> ops = mongoServer.getDatastore()
				.createUpdateOperations(ConceptImpl.class);
		boolean update = false;
		for (Concept.Choice choice : concept.getChoiceList()) {
			if (choice.ifNote()) {
				Map<String, List<String>> newNoteMap;
				if (conceptMongo.getNote() != null) {

					newNoteMap = conceptMongo.getNote();
				} else {
					newNoteMap = new HashMap<String, List<String>>();
				}
				Note note = choice.getNote();
				if (note.getLang() != null
						&& StringUtils.isNotBlank(note.getLang().getLang())) {

					if (!MongoUtils.contains(newNoteMap, note.getLang()
							.getLang(), note.getString())) {
						List<String> val = newNoteMap.get(note.getLang()
								.getLang()) != null ? newNoteMap.get(note
								.getLang().getLang()) : new ArrayList<String>();
						if (!val.contains(note.getString())) {
							val.add(note.getString());
						}
						newNoteMap.put(note.getLang().getLang(), val);
					}
				} else {
					List<String> val = newNoteMap.get("def") != null ? newNoteMap
							.get("def") : new ArrayList<String>();
					if (!val.contains(note.getString())) {
						val.add(note.getString());
					}
					newNoteMap.put("def", val);
				}

				ops.set("note", newNoteMap);
				update = true;
			}
			if (choice.ifAltLabel()) {
				Map<String, List<String>> newAltLabelMap;
				if (conceptMongo.getAltLabel() != null) {
					newAltLabelMap = conceptMongo.getAltLabel();
				} else {
					newAltLabelMap = new HashMap<String, List<String>>();
				}
				AltLabel altLabel = choice.getAltLabel();
				if (altLabel.getLang() != null
						&& StringUtils.isNotBlank(altLabel.getLang().getLang())) {
					if (!MongoUtils.contains(newAltLabelMap, altLabel.getLang()
							.getLang(), altLabel.getString())) {
						List<String> val = newAltLabelMap.get(altLabel
								.getLang().getLang()) != null ? newAltLabelMap
								.get(altLabel.getLang().getLang())
								: new ArrayList<String>();
						if (!val.contains(altLabel.getString())) {
							val.add(altLabel.getString());
						}
						newAltLabelMap.put(altLabel.getLang().getLang(), val);
					}
				} else {
					List<String> val = newAltLabelMap.get("def") != null ? newAltLabelMap
							.get("def") : new ArrayList<String>();
					if (!val.contains(altLabel.getString())) {
						val.add(altLabel.getString());
					}
					newAltLabelMap.put("def", val);
				}

				ops.set("altLabel", newAltLabelMap);
				update = true;
			}

			if (choice.ifPrefLabel()) {
				Map<String, List<String>> newPrefLabelMap;
				if (conceptMongo.getPrefLabel() != null) {
					newPrefLabelMap = conceptMongo.getPrefLabel();

				} else {
					newPrefLabelMap = new HashMap<String, List<String>>();
				}
				PrefLabel prefLabel = choice.getPrefLabel();
				if (prefLabel.getLang() != null
						&& StringUtils
								.isNotBlank(prefLabel.getLang().getLang())) {
					if (!MongoUtils.contains(newPrefLabelMap, prefLabel
							.getLang().getLang(), prefLabel.getString())) {
						List<String> val = newPrefLabelMap.get(prefLabel
								.getLang().getLang()) != null ? newPrefLabelMap
								.get(prefLabel.getLang().getLang())
								: new ArrayList<String>();

						if (!val.contains(prefLabel.getString())) {
							val.add(prefLabel.getString());
						}
						newPrefLabelMap.put(prefLabel.getLang().getLang(), val);
					}
				} else {
					List<String> val = newPrefLabelMap.get("def") != null ? newPrefLabelMap
							.get("def") : new ArrayList<String>();
					if (!val.contains(prefLabel.getString())) {
						val.add(prefLabel.getString());
					}
					newPrefLabelMap.put("def", val);
				}

				ops.set("prefLabel", newPrefLabelMap);
				update = true;
			}
			if (choice.ifBroader()) {
				if (conceptMongo.getBroader() != null) {
					List<String> broaderList = createObjectList(
							conceptMongo.getBroader(), choice.getBroader());
					ops.set("broader", broaderList);
					update = true;
				}

			}
			if (choice.ifBroadMatch()) {
				if (conceptMongo.getBroadMatch() != null) {
					List<String> broadMatchList = createObjectList(
							conceptMongo.getBroadMatch(),
							choice.getBroadMatch());
					ops.set("broadMatch", broadMatchList);
					update = true;
				}
			}
			if (choice.ifCloseMatch()) {
				if (conceptMongo.getCloseMatch() != null) {
					List<String> closeMatchList = createObjectList(
							conceptMongo.getCloseMatch(),
							choice.getCloseMatch());
					ops.set("closeMatch", closeMatchList);

					update = true;
				}
			}
			if (choice.ifExactMatch()) {
				if (conceptMongo.getExactMatch() != null) {
					List<String> exactMatchList = createObjectList(
							conceptMongo.getExactMatch(),
							choice.getExactMatch());
					ops.set("exactMatch", exactMatchList);
					update = true;
				}
			}

			if (choice.ifNarrowMatch()) {
				if (conceptMongo.getNarrowMatch() != null) {
					List<String> narrowMatchList = createObjectList(
							conceptMongo.getNarrowMatch(),
							choice.getNarrowMatch());
					ops.set("narrowMatch", narrowMatchList);
					update = true;
				}
			}

			if (choice.ifInScheme()) {
				if (conceptMongo.getInScheme() != null) {
					List<String> inSchemeList = createObjectList(
							conceptMongo.getInScheme(), choice.getInScheme());
					ops.set("inScheme", inSchemeList);
					update = true;
				}
			}

			if (choice.ifNarrower()) {
				if (conceptMongo.getNarrower() != null) {
					List<String> narrowerList = createObjectList(
							conceptMongo.getNarrower(), choice.getNarrower());
					ops.set("narrower", narrowerList);
					update = true;
				}
			}

			if (choice.ifNotation()) {
				Map<String, List<String>> newNotationMap;
				if (conceptMongo.getNotation() != null) {
					newNotationMap = conceptMongo.getNotation();
				} else {
					newNotationMap = new HashMap<String, List<String>>();
				}

				Notation notation = choice.getNotation();
				if (notation.getLang() != null
						&& StringUtils.isNotBlank(notation.getLang().getLang())) {
					if (!MongoUtils.contains(newNotationMap, notation.getLang()
							.getLang(), notation.getString())) {
						List<String> val = newNotationMap.get(notation
								.getLang().getLang()) != null ? newNotationMap
								.get(notation.getLang().getLang())
								: new ArrayList<String>();
						if (!val.contains(notation.getString())) {
							val.add(notation.getString());
						}
						newNotationMap.put(notation.getLang().getLang(), val);
					}
				} else {
					List<String> val = newNotationMap.get("def") != null ? newNotationMap
							.get("def") : new ArrayList<String>();
					if (!val.contains(notation.getString())) {
						val.add(notation.getString());
					}
					newNotationMap.put("def", val);
				}
				update = true;
				ops.set("notation", newNotationMap);

			}
			if (choice.ifRelated()) {
				if (conceptMongo.getRelated() != null) {
					List<String> relatedList = createObjectList(
							conceptMongo.getRelated(), choice.getRelated());
					ops.set("related", relatedList);
					update = true;
				}
			}
			if (choice.ifRelatedMatch()) {
				if (conceptMongo.getRelated() != null) {
					List<String> relatedMatchList = createObjectList(
							conceptMongo.getRelatedMatch(),
							choice.getRelatedMatch());
					ops.set("relatedMatch", relatedMatchList);
					update = true;
				}
			}
		}
		if (update) {
			mongoServer.getDatastore().update(updateQuery, ops);
		}
	}

	private static List<String> createObjectList(String[] originalValues, Object obj) {

		List<String> newList = new ArrayList<String>();
		String str = "";
		if (obj instanceof ResourceType) {
			str = ((ResourceType) obj).getResource();
		} else {
			str = ((LiteralType) obj).getString();
		}

		if (!MongoUtils.contains(originalValues, str)) {
			if (!newList.contains(str)) {
				newList.add(str);
			}
		}

		for (String arrStr : originalValues) {
			if (!newList.contains(arrStr)) {
				newList.add(arrStr);
			}
		}
		return newList;
	}

}
