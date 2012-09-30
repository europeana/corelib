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
package eu.europeana.corelib.solr.server.importer.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.common.SolrInputDocument;

import eu.europeana.corelib.definitions.jibx.AltLabel;
import eu.europeana.corelib.definitions.jibx.HasPart;
import eu.europeana.corelib.definitions.jibx.IsPartOf;
import eu.europeana.corelib.definitions.jibx.Note;
import eu.europeana.corelib.definitions.jibx.PrefLabel;
import eu.europeana.corelib.definitions.jibx.SameAs;
import eu.europeana.corelib.definitions.jibx.TimeSpanType;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.solr.MongoServer;
import eu.europeana.corelib.solr.entity.TimespanImpl;
import eu.europeana.corelib.solr.server.EdmMongoServer;
import eu.europeana.corelib.solr.utils.MongoUtils;
import eu.europeana.corelib.solr.utils.SolrUtils;
import eu.europeana.corelib.utils.StringArrayUtils;

/**
 * Constructor for a Timespan
 * 
 * @author Yorgos.Mamakis@ kb.nl
 */
public final class TimespanFieldInput {

	public TimespanFieldInput() {

	}

	/**
	 * Create a SolrInputDocument with the Timespan fileds filled in
	 * 
	 * @param timespan
	 *            The JiBX timespan entity
	 * @param solrInputDocument
	 *            The SolrInputDocument to alter
	 * @return The altered SolrInputDocument with the Timespan fields filled in
	 */
	public SolrInputDocument createTimespanSolrFields(TimeSpanType timespan,
			SolrInputDocument solrInputDocument) {
		solrInputDocument.addField(EdmLabel.EDM_TIMESPAN.toString(),
				timespan.getAbout());
		if (timespan.getAltLabelList() != null) {
			for (AltLabel altLabel : timespan.getAltLabelList()) {
				solrInputDocument = SolrUtils
						.addFieldFromLiteral(solrInputDocument, altLabel,
								EdmLabel.TS_SKOS_ALT_LABEL);
			}
		}
		if (timespan.getPrefLabelList() != null) {
			for (PrefLabel prefLabel : timespan.getPrefLabelList()) {
				solrInputDocument = SolrUtils.addFieldFromLiteral(
						solrInputDocument, prefLabel,
						EdmLabel.TS_SKOS_PREF_LABEL);
			}
		}
		if (timespan.getNoteList() != null) {
			for (Note note : timespan.getNoteList()) {
				solrInputDocument = SolrUtils.addFieldFromLiteral(
						solrInputDocument, note, EdmLabel.TS_SKOS_NOTE);
			}
		}
		if (timespan.getBegin() != null) {
			solrInputDocument = SolrUtils.addFieldFromLiteral(
					solrInputDocument, timespan.getBegin(),
					EdmLabel.TS_EDM_BEGIN);
		}
		if (timespan.getEnd() != null) {
			solrInputDocument = SolrUtils.addFieldFromLiteral(
					solrInputDocument, timespan.getEnd(), EdmLabel.TS_EDM_END);
		}
		if (timespan.getIsPartOfList() != null) {
			for (IsPartOf isPartOf : timespan.getIsPartOfList()) {
				solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
						solrInputDocument, isPartOf,
						EdmLabel.TS_DCTERMS_ISPART_OF);
			}
		}
		if (timespan.getHasPartList() != null) {
			for (HasPart hasPart : timespan.getHasPartList()) {
				solrInputDocument = SolrUtils
						.addFieldFromResourceOrLiteral(solrInputDocument,
								hasPart, EdmLabel.PL_DCTERMS_HASPART);
			}
		}
		return solrInputDocument;
	}

	/**
	 * Create a MongoDB Timespan Entity
	 * 
	 * @param timeSpan
	 *            The JiBX Timespan Entity
	 * @param mongoServer
	 *            The MongoDB Server to save the Timespan Entity
	 * @return The MongoDB Entity
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public TimespanImpl createTimespanMongoField(TimeSpanType timeSpan,
			MongoServer mongoServer) {
		TimespanImpl mongoTimespan = ((EdmMongoServer) mongoServer).getDatastore()
				.find(TimespanImpl.class)
				.filter("about", timeSpan.getAbout()).get();
		if (mongoTimespan == null) {
			mongoTimespan = createNewTimespan(timeSpan);
			mongoServer.getDatastore().save(mongoTimespan);
		} else {
			mongoTimespan = updateTimespan(mongoTimespan, timeSpan, mongoServer);
		}
		return mongoTimespan;
	}

	private TimespanImpl updateTimespan(TimespanImpl mongoTimespan,
			TimeSpanType timeSpan, MongoServer mongoServer) {
		if (mongoTimespan.getBegin() != null) {
			MongoUtils.update(TimespanImpl.class, mongoTimespan.getAbout(),
					mongoServer, "begin",
					MongoUtils.createLiteralMapFromString(timeSpan.getBegin()));

		}
		if (mongoTimespan.getEnd() != null) {
			MongoUtils.update(TimespanImpl.class, mongoTimespan.getAbout(),
					mongoServer, "end",
					MongoUtils.createLiteralMapFromString(timeSpan.getEnd()));

		}

		if (mongoTimespan.getNote() != null) {

			MongoUtils
					.update(TimespanImpl.class, mongoTimespan.getAbout(),
							mongoServer, "note", MongoUtils
									.createLiteralMapFromList(timeSpan
											.getNoteList()));

		}

		if (mongoTimespan.getAltLabel() != null) {

			MongoUtils.update(TimespanImpl.class, mongoTimespan.getAbout(),
					mongoServer, "altLabel", MongoUtils
							.createLiteralMapFromList(timeSpan
									.getAltLabelList()));

		}

		if (mongoTimespan.getPrefLabel() != null) {

			MongoUtils.update(TimespanImpl.class, mongoTimespan.getAbout(),
					mongoServer, "prefLabel", MongoUtils
							.createLiteralMapFromList(timeSpan
									.getPrefLabelList()));

		}

		if (mongoTimespan.getDctermsHasPart() != null) {

			MongoUtils.update(TimespanImpl.class, mongoTimespan.getAbout(),
					mongoServer, "dctermsHasPart", MongoUtils
							.createResourceOrLiteralMapFromList(timeSpan
									.getHasPartList()));
		}

		if (mongoTimespan.getOwlSameAs() != null) {
			List<String> owlSameAs = new ArrayList<String>();
			if (timeSpan.getSameAList() != null) {
				for (SameAs sameAsJibx : timeSpan.getSameAList()) {
					if (!MongoUtils.contains(mongoTimespan.getOwlSameAs(),
							sameAsJibx.getResource())) {
						owlSameAs.add(sameAsJibx.getResource());
					}
				}
			}
			for (String owlSameAsItem : mongoTimespan.getOwlSameAs()) {
				owlSameAs.add(owlSameAsItem);
			}
			MongoUtils.update(TimespanImpl.class, mongoTimespan.getAbout(),
					mongoServer, "owlSameAs",
					StringArrayUtils.toArray(owlSameAs));
		}

		return ((EdmMongoServer) mongoServer).getDatastore()
				.find(TimespanImpl.class)
				.filter("about", timeSpan.getAbout()).get();

	}

	private TimespanImpl createNewTimespan(TimeSpanType timeSpan) {
		TimespanImpl mongoTimespan = new TimespanImpl();
		// mongoTimespan.setId(new ObjectId());
		mongoTimespan.setAbout(timeSpan.getAbout());
		mongoTimespan.setNote(MongoUtils.createLiteralMapFromList(timeSpan
				.getNoteList()));
		mongoTimespan.setPrefLabel(MongoUtils.createLiteralMapFromList(timeSpan
				.getPrefLabelList()));
		mongoTimespan.setAltLabel(MongoUtils.createLiteralMapFromList(timeSpan
				.getAltLabelList()));
		mongoTimespan
				.setIsPartOf(MongoUtils
						.createResourceOrLiteralMapFromList(timeSpan
								.getIsPartOfList()));
		mongoTimespan.setDctermsHasPart(MongoUtils
				.createResourceOrLiteralMapFromList(timeSpan.getHasPartList()));
		mongoTimespan.setOwlSameAs(SolrUtils.resourceListToArray(timeSpan
				.getSameAList()));
		mongoTimespan.setBegin(MongoUtils.createLiteralMapFromString(timeSpan
				.getBegin()));
		mongoTimespan.setEnd(MongoUtils.createLiteralMapFromString(timeSpan
				.getEnd()));
		return mongoTimespan;
	}
}
