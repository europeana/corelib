package eu.europeana.corelib.solr.utils.updaters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

import eu.europeana.corelib.definitions.jibx.SameAs;
import eu.europeana.corelib.definitions.jibx.TimeSpanType;
import eu.europeana.corelib.solr.MongoServer;
import eu.europeana.corelib.solr.entity.TimespanImpl;
import eu.europeana.corelib.solr.utils.MongoUtils;
import eu.europeana.corelib.utils.StringArrayUtils;

public class TimespanUpdater {
	public static void update(TimespanImpl mongoTimespan, TimeSpanType timeSpan,
			MongoServer mongoServer) {
		Query<TimespanImpl> updateQuery = mongoServer.getDatastore()
				.createQuery(TimespanImpl.class).field("about")
				.equal(mongoTimespan.getAbout());
		UpdateOperations<TimespanImpl> ops = mongoServer.getDatastore()
				.createUpdateOperations(TimespanImpl.class);
		boolean update = false;
		if (timeSpan.getBegin() != null) {
			Map<String, List<String>> begin = MongoUtils
					.createLiteralMapFromString(timeSpan.getBegin());
			if (mongoTimespan.getBegin() == null
					|| !MongoUtils.mapEquals(begin, mongoTimespan.getBegin())) {
				ops.set("begin", begin);
				update = true;
			}

		}
		if (timeSpan.getEnd() != null) {
			Map<String, List<String>> end = MongoUtils
					.createLiteralMapFromString(timeSpan.getEnd());
			if (mongoTimespan.getEnd() == null
					|| !MongoUtils.mapEquals(end, mongoTimespan.getEnd())) {
				ops.set("end", end);
				update = true;
			}

		}

		if (timeSpan.getNoteList() != null) {
			Map<String, List<String>> note = MongoUtils
					.createLiteralMapFromList(timeSpan.getNoteList());
			if (mongoTimespan.getNote() == null
					|| MongoUtils.mapEquals(note, mongoTimespan.getNote())) {
				ops.set("note", note);
				update = true;
			}
		}

		if (timeSpan.getAltLabelList() != null) {
			Map<String, List<String>> altLabel = MongoUtils
					.createLiteralMapFromList(timeSpan.getAltLabelList());
			if (mongoTimespan.getAltLabel() == null
					|| MongoUtils.mapEquals(altLabel,
							mongoTimespan.getAltLabel())) {
				ops.set("altLabel", altLabel);
				update = true;
			}

		}

		if (timeSpan.getPrefLabelList() != null) {
			Map<String,List<String>> prefLabel = MongoUtils.createLiteralMapFromList(timeSpan
					.getPrefLabelList());
			if(mongoTimespan.getPrefLabel() == null || MongoUtils.mapEquals(prefLabel, mongoTimespan.getPrefLabel())){
				
			
			ops.set("prefLabel", prefLabel);
			update = true;
			}
		}

		if (timeSpan.getHasPartList() != null) {
			Map<String,List<String>> hasPart= MongoUtils
					.createResourceOrLiteralMapFromList(timeSpan
							.getHasPartList());
			if(mongoTimespan.getDctermsHasPart()==null|| MongoUtils.mapEquals(hasPart, mongoTimespan.getDctermsHasPart())){
			ops.set("dctermsHasPart", hasPart);
			update = true;
			}
		}

		if (timeSpan.getSameAList() != null) {
			List<String> owlSameAs = new ArrayList<String>();
			if (timeSpan.getSameAList() != null) {
				for (SameAs sameAsJibx : timeSpan.getSameAList()) {
					if (!MongoUtils.contains(mongoTimespan.getOwlSameAs(),
							sameAsJibx.getResource())) {
						owlSameAs.add(sameAsJibx.getResource());
					}
				}
			}
			if (mongoTimespan.getOwlSameAs() != null) {
				for (String owlSameAsItem : mongoTimespan.getOwlSameAs()) {
					owlSameAs.add(owlSameAsItem);
				}
			}
			ops.set("owlSameAs", StringArrayUtils.toArray(owlSameAs));
			update = true;
		}
		if (update) {
			mongoServer.getDatastore().update(updateQuery, ops);
		}
	}
}
