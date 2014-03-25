package eu.europeana.corelib.solr.utils.construct;

import java.util.ArrayList;
import java.util.List;

import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

import eu.europeana.corelib.solr.MongoServer;
import eu.europeana.corelib.solr.entity.TimespanImpl;
import eu.europeana.corelib.solr.utils.MongoUtils;
import eu.europeana.corelib.utils.StringArrayUtils;

public class TimespanUpdater implements Updater<TimespanImpl> {
	public TimespanImpl update(TimespanImpl mongoTimespan, TimespanImpl timeSpan,
			MongoServer mongoServer) {
		Query<TimespanImpl> updateQuery = mongoServer.getDatastore()
				.createQuery(TimespanImpl.class).field("about")
				.equal(mongoTimespan.getAbout());
		UpdateOperations<TimespanImpl> ops = mongoServer.getDatastore()
				.createUpdateOperations(TimespanImpl.class);
		boolean update = false;
		if (timeSpan.getBegin() != null) {
			
				if (mongoTimespan.getBegin() == null
						|| !MongoUtils.mapEquals(timeSpan.getBegin(),
								mongoTimespan.getBegin())) {
					ops.set("begin", timeSpan.getBegin());
					mongoTimespan.setBegin(timeSpan.getBegin());
					update = true;
				
			}

		} else {
			if (mongoTimespan.getBegin()!=null){
				ops.unset("begin");
				mongoTimespan.setBegin(null);
				update = true;
				
			}
		}
		if (timeSpan.getEnd() != null) {
				if (mongoTimespan.getEnd() == null
						|| !MongoUtils.mapEquals(timeSpan.getEnd(), mongoTimespan.getEnd())) {
					ops.set("end", timeSpan.getEnd());
					mongoTimespan.setEnd(timeSpan.getEnd());
					update = true;
				}
		}else {
			if (mongoTimespan.getEnd()!=null){
				ops.unset("end");
				mongoTimespan.setEnd(null);
				update = true;
			}
		}

		if (timeSpan.getNote() != null) {
				if (mongoTimespan.getNote() == null
						|| MongoUtils.mapEquals(timeSpan.getNote(), mongoTimespan.getNote())) {
					ops.set("note", timeSpan.getNote());
					mongoTimespan.setNote(timeSpan.getNote());
					update = true;
				}
		} else {
			if (mongoTimespan.getNote()!=null){
				ops.unset("note");
				mongoTimespan.setNote(null);
				update = true;
			}
		}

		if (timeSpan.getAltLabel() != null) {
				if (mongoTimespan.getAltLabel() == null
						|| MongoUtils.mapEquals(timeSpan.getAltLabel(),
								mongoTimespan.getAltLabel())) {
					ops.set("altLabel", timeSpan.getAltLabel());
					mongoTimespan.setAltLabel(timeSpan.getAltLabel());
					update = true;
				}
		} else {
			if (mongoTimespan.getAltLabel()!=null){
				ops.unset("altLabel");
				mongoTimespan.setAltLabel(null);
				update = true;
			}
		}

		if (timeSpan.getPrefLabel() != null) {
				if (mongoTimespan.getPrefLabel() == null
						|| MongoUtils.mapEquals(timeSpan.getPrefLabel(),
								mongoTimespan.getPrefLabel())) {

					ops.set("prefLabel", timeSpan.getPrefLabel());
					mongoTimespan.setPrefLabel(timeSpan.getPrefLabel());
					update = true;
				}
		} else {
			if (mongoTimespan.getPrefLabel()!=null){
				ops.unset("prefLabel");
				mongoTimespan.setPrefLabel(null);
				update = true;
			}
		}

		if (timeSpan.getDctermsHasPart() != null) {
				if (mongoTimespan.getDctermsHasPart() == null
						|| MongoUtils.mapEquals(timeSpan.getDctermsHasPart(),
								mongoTimespan.getDctermsHasPart())) {
					ops.set("dctermsHasPart", timeSpan.getDctermsHasPart());
					mongoTimespan.setDctermsHasPart(timeSpan.getDctermsHasPart());
					update = true;
				}
		} else {
			if (mongoTimespan.getDctermsHasPart()!=null){
				ops.unset("dctermsHasPart");
				mongoTimespan.setDctermsHasPart(null);
				update = true;
			}
		}

		if (timeSpan.getOwlSameAs() != null) {
			List<String> owlSameAs = new ArrayList<String>();
			
				for (String sameAsJibx : timeSpan.getOwlSameAs()) {
					if (!MongoUtils.contains(mongoTimespan.getOwlSameAs(),
							sameAsJibx)) {
						owlSameAs.add(sameAsJibx);
					}
				}
			
			if (mongoTimespan.getOwlSameAs() != null) {
				for (String owlSameAsItem : mongoTimespan.getOwlSameAs()) {
					owlSameAs.add(owlSameAsItem);
				}
			}
			ops.set("owlSameAs", StringArrayUtils.toArray(owlSameAs));
			mongoTimespan.setOwlSameAs(timeSpan.getOwlSameAs());
			update = true;
		} else {
			if (mongoTimespan.getOwlSameAs()!=null){
				ops.unset("owlSameAs");
				mongoTimespan.setOwlSameAs(timeSpan.getOwlSameAs());
				update = true;
			}
		}
		if (update) {
			mongoServer.getDatastore().update(updateQuery, ops);
		}
		return mongoTimespan;
	}
}
