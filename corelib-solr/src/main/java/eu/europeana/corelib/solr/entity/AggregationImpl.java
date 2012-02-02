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

package eu.europeana.corelib.solr.entity;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.*;

import eu.europeana.corelib.definitions.solr.entity.Aggregation;
/**
 * @see eu.europeana.corelib.definitions.solr.entity.model.definitions.Aggregation
 * @author yorgos.mamakis@kb.nl
 *
 */
@Entity("Aggregation")
public class AggregationImpl implements Aggregation {
@Id ObjectId aggregationId;

private String edmDataProvider;
private String[] edmHasView;
private String edmIsShownBy;
private String edmIsShownAt;
private String edmObject;
private String edmProvider;
private String edmRights;
private String[] dcRights;
	@Override
	public String getEdmDataProvider() {
		return this.edmDataProvider;
	}

	@Override
	public String[] getEdmHasView() {
		return this.edmHasView;
	}

	@Override
	public String getEdmIsShownBy() {
		return this.edmIsShownBy;
	}

	@Override
	public String getEdmIsShownAt() {
		return this.edmIsShownAt;
	}

	@Override
	public String getEdmObject() {
		return this.edmObject;
	}

	@Override
	public String getEdmProvider() {
		return this.edmProvider;
	}

	@Override
	public String[] getDcRights() {
		return this.dcRights;
	}

	@Override
	public String getEdmRights() {
		return this.edmRights;
	}

	@Override
	public ObjectId getAggregationId() {
		return this.aggregationId;
	}

}
