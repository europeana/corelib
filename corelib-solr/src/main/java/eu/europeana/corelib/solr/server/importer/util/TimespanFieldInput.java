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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.common.SolrInputDocument;

import eu.europeana.corelib.definitions.jibx.AltLabel;
import eu.europeana.corelib.definitions.jibx.IsPartOf;
import eu.europeana.corelib.definitions.jibx.Note;
import eu.europeana.corelib.definitions.jibx.PrefLabel;
import eu.europeana.corelib.definitions.jibx.TimeSpanType;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.solr.entity.TimespanImpl;
import eu.europeana.corelib.solr.server.MongoDBServer;
import eu.europeana.corelib.solr.utils.MongoUtil;

/**
 * Constructor for a Timespan
 *
 * @author Yorgos.Mamakis@ kb.nl
 */
public class TimespanFieldInput {

	private TimespanFieldInput(){
		
	}
    /**
     * Create a SolrInputDocument with the Timespan fileds filled in
     *
     * @param timespan The JiBX timespan entity
     * @param solrInputDocument The SolrInputDocument to alter
     * @return The altered SolrInputDocument with the Timespan fields filled in
     */
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

    /**
     * Create a MongoDB Timespan Entity
     * 
     * @param timeSpan
     *              The JiBX Timespan Entity
     * @param mongoServer
     *              The MongoDB Server to save the Timespan Entity
     * @return The MongoDB Entity
     */
    public static TimespanImpl createTimespanMongoField(TimeSpanType timeSpan,
            MongoDBServer mongoServer) {
        TimespanImpl mongoTimespan = (TimespanImpl) mongoServer.searchByAbout(TimespanImpl.class, timeSpan.getAbout());
            
      if(mongoTimespan==null) {
           	mongoTimespan = createNewTimespan(timeSpan);
            mongoServer.getDatastore().save(mongoTimespan);
        }
      else{
    	  mongoTimespan = updateTimespan(mongoTimespan,timeSpan,mongoServer);
      }
        return mongoTimespan;
    }

	private static TimespanImpl updateTimespan(TimespanImpl mongoTimespan,
			TimeSpanType timeSpan, MongoDBServer mongoServer) {
		if (mongoTimespan.getBegin() != null
  				&& !StringUtils.equals(timeSpan.getBegins().get(0),
  						mongoTimespan.getBegin())) {
			MongoUtil.update(TimespanImpl.class, mongoTimespan.getAbout(), mongoServer,
					"begin", timeSpan.getBegins().get(0));
  			

  		}
  		if (mongoTimespan.getEnd() != null
  				&& !StringUtils.equals(timeSpan.getEnds().get(0),
  						mongoTimespan.getEnd())) {
  			MongoUtil.update(TimespanImpl.class, mongoTimespan.getAbout(), mongoServer,
					"end", timeSpan.getEnds().get(0));
  			
  		}

  		if (mongoTimespan.getNote() != null) {
  			List<String> newNoteList = new ArrayList<String>();
  			for (Note noteJibx : timeSpan.getNoteList()) {
  				if (!MongoUtil.contains(mongoTimespan.getNote(), noteJibx.getString())) {
  					newNoteList.add(noteJibx.getString());
  				}
  			}
  			for (String note : mongoTimespan.getNote()) {
  				newNoteList.add(note);
  			}

  			MongoUtil.update(TimespanImpl.class, mongoTimespan.getAbout(), mongoServer,
					"note", newNoteList);
  			
  		}

  		if (mongoTimespan.getAltLabel() != null) {
  			Map<String, String> newAltLabelMap = mongoTimespan.getAltLabel();
  			if (timeSpan.getAltLabelList() != null) {
  				for (AltLabel altLabel : timeSpan.getAltLabelList()) {
  					if (altLabel.getLang() != null) {
  						if (!MongoUtil.contains(newAltLabelMap, altLabel
  								.getLang().getLang(), altLabel.getString())) {
  							newAltLabelMap.put(altLabel.getLang().getLang(),
  									altLabel.getString());
  						}
  					} else {
  						newAltLabelMap.put("def", altLabel.getString());
  					}
  				}
  			}
  			MongoUtil.update(TimespanImpl.class, mongoTimespan.getAbout(), mongoServer,
					"altLabel", newAltLabelMap);

  		}

  		if (mongoTimespan.getPrefLabel() != null) {
  			Map<String, String> newPrefLabelMap = mongoTimespan.getPrefLabel();
  			if (timeSpan.getPrefLabelList() != null) {
  				for (PrefLabel prefLabel : timeSpan.getPrefLabelList()) {
  					if (prefLabel.getLang() != null) {
  						if (!MongoUtil.contains(newPrefLabelMap, prefLabel
  								.getLang().getLang(), prefLabel.getString())) {
  							newPrefLabelMap.put(prefLabel.getLang().getLang(),
  									prefLabel.getString());
  						}
  					} else {
  						newPrefLabelMap.put("def", prefLabel.getString());
  					}
  				}
  				MongoUtil.update(TimespanImpl.class, mongoTimespan.getAbout(), mongoServer,
  						"prefLabel", newPrefLabelMap);

  			}
  		}
  		return (TimespanImpl) mongoServer.searchByAbout(TimespanImpl.class,
  				timeSpan.getAbout());
		
	}

	private static TimespanImpl createNewTimespan(TimeSpanType timeSpan) {
		TimespanImpl mongoTimespan = new TimespanImpl();
        mongoTimespan.setAbout(timeSpan.getAbout());
        if (timeSpan.getNoteList() != null) {
            List<String> noteList = new ArrayList<String>();
            for (Note note : timeSpan.getNoteList()) {
                noteList.add(note.getString());
            }
            mongoTimespan.setNote(noteList.toArray(new String[noteList.size()]));
        }
        if (timeSpan.getPrefLabelList() != null) {
            Map<String, String> prefLabelMongo = new HashMap<String, String>();
            for (PrefLabel prefLabelJibx : timeSpan.getPrefLabelList()) {
                if(prefLabelJibx.getLang()!=null) {
                    prefLabelMongo.put(prefLabelJibx.getLang().getLang(),
                            prefLabelJibx.getString());
                } else {
                    prefLabelMongo.put("def",
                            prefLabelJibx.getString());
                }
            }
            mongoTimespan.setPrefLabel(prefLabelMongo);
        }
        if (timeSpan.getAltLabelList() != null) {
            Map<String, String> altLabelMongo = new HashMap<String, String>();
            for (AltLabel altLabelJibx : timeSpan.getAltLabelList()) {
                if(altLabelJibx.getLang()!=null) {
                    altLabelMongo.put(altLabelJibx.getLang().getLang(),
                            altLabelJibx.getString());
                } else {
                    altLabelMongo.put("def",
                            altLabelJibx.getString());
                }
            }
            mongoTimespan.setAltLabel(altLabelMongo);
        }
        if (timeSpan.getIsPartOfList() != null) {
            List<String> isPartOfList = new ArrayList<String>();
            for (IsPartOf isPartOf : timeSpan.getIsPartOfList()) {
                isPartOfList.add(isPartOf.getString());
            }
            mongoTimespan.setIsPartOf(isPartOfList.toArray(new String[isPartOfList.size()]));
        }


        if (timeSpan.getBegins() != null) {
            mongoTimespan.setBegin(timeSpan.getBegins().get(0));
        }
        if (timeSpan.getEnds() != null) {
            mongoTimespan.setEnd(timeSpan.getEnds().get(0));
        }
		return mongoTimespan;
	}
}
