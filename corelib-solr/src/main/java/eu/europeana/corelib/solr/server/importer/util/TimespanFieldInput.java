package eu.europeana.corelib.solr.server.importer.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.common.SolrInputDocument;

import com.google.code.morphia.converters.DateConverter;

import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.definitions.solr.entity.Timespan;
import eu.europeana.corelib.solr.entity.TimespanImpl;
import eu.europeana.corelib.solr.server.MongoDBServer;
import eu.europeana.corelib.definitions.jibx.AltLabel;
import eu.europeana.corelib.definitions.jibx.IsPartOf;
import eu.europeana.corelib.definitions.jibx.Note;
import eu.europeana.corelib.definitions.jibx.PrefLabel;
import eu.europeana.corelib.definitions.jibx.TimeSpanType;

public class TimespanFieldInput {
	public static SolrInputDocument createTimespanSolrFields(TimeSpanType timespan, SolrInputDocument solrInputDocument){
		solrInputDocument.addField(EdmLabel.EDM_TIMESPAN.toString(), timespan.getAbout());
		for(AltLabel altLabel: timespan.getAltLabelList()){
			solrInputDocument.addField(EdmLabel.TS_SKOS_ALT_LABEL.toString()+"."+altLabel.getLang().getLang(), altLabel.getString()); 
		}
		for(PrefLabel prefLabel: timespan.getPrefLabelList()){
			solrInputDocument.addField(EdmLabel.TS_SKOS_PREF_LABEL.toString()+"."+prefLabel.getLang().getLang(), prefLabel.getString());
		}
		for (Note note:timespan.getNoteList()){
			solrInputDocument.addField(EdmLabel.TS_SKOS_NOTE.toString(), note.getString());
		}
		for (String begin:timespan.getBegins()){
			solrInputDocument.addField(EdmLabel.TS_EDM_BEGIN.toString(),begin);
		}
		for (String end:timespan.getEnds()){
			solrInputDocument.addField(EdmLabel.TS_EDM_END.toString(),end);
		}
		for(IsPartOf isPartOf: timespan.getIsPartOfList()){
			solrInputDocument.addField(EdmLabel.TS_DCTERMS_ISPART_OF.toString(),isPartOf.getString());
		}
		return solrInputDocument;
	}

	public static List<? extends Timespan> createTimespanMongoField(
			TimeSpanType timeSpan, MongoDBServer mongoServer) {
		List<Timespan> timespanList = new ArrayList<Timespan>();
		//If timespan exists in mongo
		try {
			timespanList.add((TimespanImpl) mongoServer.searchByAbout(timeSpan.getAbout()));
		}
		// if it does not exist
		catch (NullPointerException npe) {
			Timespan mongoTimespan= new TimespanImpl();
			mongoTimespan.setAbout(timeSpan.getAbout());
			
			List<String> noteList = new ArrayList<String>();
			for(Note note: timeSpan.getNoteList()){
				noteList.add(note.getString());
			}
			mongoTimespan.setNote(noteList.toArray(new String[noteList.size()]));

			Map<String, String> prefLabelMongo = new HashMap<String, String>();
			for (PrefLabel prefLabelJibx : timeSpan.getPrefLabelList()) {
				prefLabelMongo.put(prefLabelJibx.getLang().getLang(),prefLabelJibx.getString());
			}
			mongoTimespan.setPrefLabel(prefLabelMongo);

			Map<String, String> altLabelMongo = new HashMap<String, String>();
			for (AltLabel altLabelJibx : timeSpan.getAltLabelList()) {
				altLabelMongo.put(altLabelJibx.getLang().getLang(),
						altLabelJibx.getString());
			}
			mongoTimespan.setAltLabel(altLabelMongo);
			
			List<String> isPartOfList = new ArrayList<String>();
			for(IsPartOf isPartOf: timeSpan.getIsPartOfList()){
				noteList.add(isPartOf.getString());
			}
			mongoTimespan.setIsPartOf(isPartOfList.toArray(new String[isPartOfList.size()]));
			

			DateConverter dc = new DateConverter();

			mongoTimespan.setBegin((Date) dc.encode(timeSpan.getBegins().get(0)));

			mongoTimespan.setEnd((Date) dc.encode(timeSpan.getEnds().get(0)));
			mongoServer.getDatastore().save(mongoTimespan);
		}
		return timespanList;
	}
	
}
