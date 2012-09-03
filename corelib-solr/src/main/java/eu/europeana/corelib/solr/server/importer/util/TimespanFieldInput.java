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
import eu.europeana.corelib.definitions.jibx.HasPart;
import eu.europeana.corelib.definitions.jibx.IsPartOf;
import eu.europeana.corelib.definitions.jibx.Note;
import eu.europeana.corelib.definitions.jibx.PrefLabel;
import eu.europeana.corelib.definitions.jibx.SameAs;
import eu.europeana.corelib.definitions.jibx.TimeSpanType;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.solr.MongoServer;
import eu.europeana.corelib.solr.entity.PlaceImpl;
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
                        EdmLabel.TS_SKOS_ALT_LABEL.toString() + (altLabel.getLang()!=null? "."
                        + altLabel.getLang().getLang():""),
                        altLabel.getString());
            }
        }
        if (timespan.getPrefLabelList() != null) {
            for (PrefLabel prefLabel : timespan.getPrefLabelList()) {
                solrInputDocument.addField(
                        EdmLabel.TS_SKOS_PREF_LABEL.toString() + (prefLabel.getLang()!=null?"."
                        + prefLabel.getLang().getLang():""),
                        prefLabel.getString());
            }
        }
        if (timespan.getNoteList() != null) {
            for (Note note : timespan.getNoteList()) {
                solrInputDocument.addField(EdmLabel.TS_SKOS_NOTE.toString(),
                        note.getString());
            }
        }
        if (timespan.getBegin() != null) {
                solrInputDocument.addField(EdmLabel.TS_EDM_BEGIN.toString(),
                		timespan.getBegin().getString());
        }
        if (timespan.getEnd() != null) {
                solrInputDocument.addField(EdmLabel.TS_EDM_END.toString(), timespan.getEnd().getString());
        }
        if (timespan.getIsPartOfList() != null) {
            for (IsPartOf isPartOf : timespan.getIsPartOfList()) {
                solrInputDocument.addField(
                        EdmLabel.TS_DCTERMS_ISPART_OF.toString(),
                        isPartOf.getResource());
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
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    public static TimespanImpl createTimespanMongoField(TimeSpanType timeSpan,
            MongoServer mongoServer) {
        TimespanImpl mongoTimespan = (TimespanImpl) ((EdmMongoServer)mongoServer).searchByAbout(TimespanImpl.class, timeSpan.getAbout());
            
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
			TimeSpanType timeSpan, MongoServer mongoServer)  {
		if (mongoTimespan.getBegin() != null
  				&& !StringUtils.equals(timeSpan.getBegin().getString(),
  						mongoTimespan.getBegin()[0])) {
			MongoUtils.update(TimespanImpl.class, mongoTimespan.getAbout(), mongoServer,
					"begin", timeSpan.getBegin().getString());
  			

  		}
  		if (mongoTimespan.getEnd() != null
  				&& !StringUtils.equals(timeSpan.getEnd().getString(),
  						mongoTimespan.getEnd()[0])) {
  			MongoUtils.update(TimespanImpl.class, mongoTimespan.getAbout(), mongoServer,
					"end", timeSpan.getEnd().getString());
  			
  		}

  		if (mongoTimespan.getNote() != null) {
  			List<String> newNoteList = new ArrayList<String>();
  			for (Note noteJibx : timeSpan.getNoteList()) {
  				if (!MongoUtils.contains(mongoTimespan.getNote(), noteJibx.getString())) {
  					newNoteList.add(noteJibx.getString());
  				}
  			}
  			for (String note : mongoTimespan.getNote()) {
  				newNoteList.add(note);
  			}

  			MongoUtils.update(TimespanImpl.class, mongoTimespan.getAbout(), mongoServer,
					"note", newNoteList);
  			
  		}

  		if (mongoTimespan.getAltLabel() != null) {
  			Map<String, String> newAltLabelMap = mongoTimespan.getAltLabel();
  			if (timeSpan.getAltLabelList() != null) {
  				for (AltLabel altLabel : timeSpan.getAltLabelList()) {
  					if (altLabel.getLang() != null) {
  						if (!MongoUtils.contains(newAltLabelMap, altLabel
  								.getLang().getLang(), altLabel.getString())) {
  							newAltLabelMap.put(altLabel.getLang().getLang(),
  									altLabel.getString());
  						}
  					} else {
  						newAltLabelMap.put("def", altLabel.getString());
  					}
  				}
  			}
  			MongoUtils.update(TimespanImpl.class, mongoTimespan.getAbout(), mongoServer,
					"altLabel", newAltLabelMap);

  		}

  		if (mongoTimespan.getPrefLabel() != null) {
  			Map<String, String> newPrefLabelMap = mongoTimespan.getPrefLabel();
  			if (timeSpan.getPrefLabelList() != null) {
  				for (PrefLabel prefLabel : timeSpan.getPrefLabelList()) {
  					if (prefLabel.getLang() != null) {
  						if (!MongoUtils.contains(newPrefLabelMap, prefLabel
  								.getLang().getLang(), prefLabel.getString())) {
  							newPrefLabelMap.put(prefLabel.getLang().getLang(),
  									prefLabel.getString());
  						}
  					} else {
  						newPrefLabelMap.put("def", prefLabel.getString());
  					}
  				}
  				MongoUtils.update(TimespanImpl.class, mongoTimespan.getAbout(), mongoServer,
  						"prefLabel", newPrefLabelMap);

  			}
  		}
  		
		if (mongoTimespan.getDctermsHasPart() != null) {
			List<String> dcTermsHasPart = new ArrayList<String>();
			if (timeSpan.getHasPartList() != null) {
				for (HasPart hasPartJibx : timeSpan.getHasPartList()) {
					if (!MongoUtils.contains(mongoTimespan.getDctermsHasPart(),
							hasPartJibx.getResource())) {
						dcTermsHasPart.add(hasPartJibx.getResource());
					}
				}
			}
			for (String isPartOf : mongoTimespan.getIsPartOf()) {
				dcTermsHasPart.add(isPartOf);
			}
			MongoUtils.update(PlaceImpl.class, mongoTimespan.getAbout(), mongoServer,
					"dctermsHasPart", StringArrayUtils.toArray(dcTermsHasPart));
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
			for (String isPartOf : mongoTimespan.getIsPartOf()) {
				owlSameAs.add(isPartOf);
			}
			MongoUtils.update(PlaceImpl.class, mongoTimespan.getAbout(), mongoServer,
					"owlSameAs", StringArrayUtils.toArray(owlSameAs));
		}
		
  		
  		
  		return (TimespanImpl) ((EdmMongoServer)mongoServer).searchByAbout(TimespanImpl.class,
  				timeSpan.getAbout());
		
	}

	private static TimespanImpl createNewTimespan(TimeSpanType timeSpan) {
		TimespanImpl mongoTimespan = new TimespanImpl();
		//mongoTimespan.setId(new ObjectId());
        mongoTimespan.setAbout(timeSpan.getAbout());
        mongoTimespan.setNote(SolrUtils.literalListToArray(timeSpan.getNoteList()));
        if (timeSpan.getPrefLabelList() != null) {
            Map<String, String> prefLabelMongo = new HashMap<String, String>();
            for (PrefLabel prefLabelJibx : timeSpan.getPrefLabelList()) {
                if(prefLabelJibx.getLang()!=null) {
                    prefLabelMongo.put(prefLabelJibx.getLang().getLang(),
                            prefLabelJibx.getString());
                } else {
                    prefLabelMongo.put( prefLabelJibx.getString(),
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
                    altLabelMongo.put(altLabelJibx.getString(),
                            altLabelJibx.getString());
                }
            }
            mongoTimespan.setAltLabel(altLabelMongo);
        }
        mongoTimespan.setIsPartOf(SolrUtils.resourceOrLiteralListToArray(timeSpan.getIsPartOfList()));
        mongoTimespan.setDctermsHasPart(SolrUtils.resourceOrLiteralListToArray(timeSpan.getHasPartList()));
        mongoTimespan.setOwlSameAs(SolrUtils.resourceListToArray(timeSpan.getSameAList()));
        if (timeSpan.getBegin() != null) {
            mongoTimespan.setBegin(new String[]{timeSpan.getBegin().getString()});
        }
        if (timeSpan.getEnd() != null) {
            mongoTimespan.setEnd(new String[]{timeSpan.getEnd().getString()});
        }
		return mongoTimespan;
	}
}
