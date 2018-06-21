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
package eu.europeana.corelib.edm.server.importer.util;

import eu.europeana.corelib.definitions.jibx.TimeSpanType;
import eu.europeana.corelib.edm.utils.MongoUtils;
import eu.europeana.corelib.edm.utils.SolrUtils;
import eu.europeana.corelib.solr.entity.TimespanImpl;

/**
 * Constructor for a Timespan
 * 
 * @author Yorgos.Mamakis@ kb.nl
 */
public final class TimespanFieldInput {

	public TimespanFieldInput() {

	}
	
	public TimespanImpl createNewTimespan(TimeSpanType timeSpan) {
		TimespanImpl mongoTimespan = new TimespanImpl();
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
