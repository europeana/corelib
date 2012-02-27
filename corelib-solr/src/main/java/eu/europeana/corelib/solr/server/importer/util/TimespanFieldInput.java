package eu.europeana.corelib.solr.server.importer.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.common.SolrInputDocument;

import eu.europeana.corelib.definitions.jibx.AltLabel;
import eu.europeana.corelib.definitions.jibx.IsPartOf;
import eu.europeana.corelib.definitions.jibx.Note;
import eu.europeana.corelib.definitions.jibx.PrefLabel;
import eu.europeana.corelib.definitions.jibx.TimeSpanType;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.solr.entity.TimespanImpl;
import eu.europeana.corelib.solr.server.MongoDBServer;

public class TimespanFieldInput {
	public static SolrInputDocument createTimespanSolrFields(
			TimeSpanType timespan, SolrInputDocument solrInputDocument) {
		solrInputDocument.addField(EdmLabel.EDM_TIMESPAN.toString(),
				timespan.getAbout());
		if (timespan.getAltLabelList() != null) {
			for (AltLabel altLabel : timespan.getAltLabelList()) {
				solrInputDocument.addField(
						EdmLabel.TS_SKOS_ALT_LABEL.toString() + "."
								+ altLabel.getLang().getLang(),
						altLabel.getString());
			}
		}
		if (timespan.getPrefLabelList() != null) {
			for (PrefLabel prefLabel : timespan.getPrefLabelList()) {
				solrInputDocument.addField(
						EdmLabel.TS_SKOS_PREF_LABEL.toString() + "."
								+ prefLabel.getLang().getLang(),
						prefLabel.getString());
			}
		}
		if (timespan.getNoteList() != null) {
			for (Note note : timespan.getNoteList()) {
				solrInputDocument.addField(EdmLabel.TS_SKOS_NOTE.toString(),
						note.getString());
			}
		}
		if (timespan.getBegins() != null) {
			for (String begin : timespan.getBegins()) {
				solrInputDocument.addField(EdmLabel.TS_EDM_BEGIN.toString(),
						begin);
			}
		}
		if (timespan.getEnds() != null) {
			for (String end : timespan.getEnds()) {
				solrInputDocument.addField(EdmLabel.TS_EDM_END.toString(), end);
			}
		}
		if (timespan.getIsPartOfList() != null) {
			for (IsPartOf isPartOf : timespan.getIsPartOfList()) {
				solrInputDocument.addField(
						EdmLabel.TS_DCTERMS_ISPART_OF.toString(),
						isPartOf.getString());
			}
		}
		return solrInputDocument;
	}

	public static TimespanImpl createTimespanMongoField(TimeSpanType timeSpan,
			MongoDBServer mongoServer) {
		TimespanImpl mongoTimespan = new TimespanImpl();
		// If timespan exists in mongo
		try {
			mongoTimespan = (TimespanImpl) mongoServer.searchByAbout(timeSpan
					.getAbout());
			mongoTimespan.getAbout();
		}
		// if it does not exist
		catch (NullPointerException npe) {
			mongoTimespan = new TimespanImpl();
			mongoTimespan.setAbout(timeSpan.getAbout());
			if (timeSpan.getNoteList() != null) {
				List<String> noteList = new ArrayList<String>();
				for (Note note : timeSpan.getNoteList()) {
					noteList.add(note.getString());
				}
				mongoTimespan.setNote(noteList.toArray(new String[noteList
						.size()]));
			}
			if (timeSpan.getPrefLabelList() != null) {
				Map<String, String> prefLabelMongo = new HashMap<String, String>();
				for (PrefLabel prefLabelJibx : timeSpan.getPrefLabelList()) {
					prefLabelMongo.put(prefLabelJibx.getLang().getLang(),
							prefLabelJibx.getString());
				}
				mongoTimespan.setPrefLabel(prefLabelMongo);
			}
			if (timeSpan.getAltLabelList() != null) {
				Map<String, String> altLabelMongo = new HashMap<String, String>();
				for (AltLabel altLabelJibx : timeSpan.getAltLabelList()) {
					altLabelMongo.put(altLabelJibx.getLang().getLang(),
							altLabelJibx.getString());
				}
				mongoTimespan.setAltLabel(altLabelMongo);
			}
			if (timeSpan.getIsPartOfList() != null) {
				List<String> isPartOfList = new ArrayList<String>();
				for (IsPartOf isPartOf : timeSpan.getIsPartOfList()) {
					isPartOfList.add(isPartOf.getString());
				}
				mongoTimespan.setIsPartOf(isPartOfList
						.toArray(new String[isPartOfList.size()]));
			}

			
			if (timeSpan.getBegins() != null) {
				mongoTimespan.setBegin(timeSpan.getBegins().get(0));
			}
			if (timeSpan.getEnds() != null) {
				mongoTimespan.setEnd(timeSpan.getEnds().get(0));
			}
			mongoServer.getDatastore().save(mongoTimespan);
		}
		return mongoTimespan;
	}

}
