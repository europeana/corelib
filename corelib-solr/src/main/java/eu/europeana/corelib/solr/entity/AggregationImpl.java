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

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.*;

import eu.europeana.corelib.definitions.solr.entity.Aggregation;
import eu.europeana.corelib.definitions.solr.entity.WebResource;

/**
 * @see eu.europeana.corelib.definitions.solr.entity.model.definitions.Aggregation
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
@Entity("Aggregation")
public class AggregationImpl implements Aggregation {
	@Id
	ObjectId aggregationId;

	private String edmDataProvider;
	private String edmIsShownBy;
	private String edmIsShownAt;
	private String edmObject;
	private String edmProvider;
	private String edmRights;
	private String[] dcRights;
	private String about;
	
	@Override
	public String getAbout() {
		return about;
	}

	@Override
	public void setAbout(String about) {
		this.about = about;
	}

	@Embedded
	private ArrayList<WebResource> webResources;

	@Override
	public void setAggregationId(ObjectId aggregationId) {
		this.aggregationId = aggregationId;
	}

	@Override
	public void setEdmDataProvider(String edmDataProvider) {
		this.edmDataProvider = edmDataProvider;
	}

	@Override
	public void setEdmIsShownBy(String edmIsShownBy) {
		this.edmIsShownBy = edmIsShownBy;
	}

	@Override
	public void setEdmIsShownAt(String edmIsShownAt) {
		this.edmIsShownAt = edmIsShownAt;
	}

	@Override
	public void setEdmObject(String edmObject) {
		this.edmObject = edmObject;
	}

	@Override
	public void setEdmProvider(String edmProvider) {
		this.edmProvider = edmProvider;
	}

	@Override
	public void setEdmRights(String edmRights) {
		this.edmRights = edmRights;
	}

	@Override
	public void setDcRights(String[] dcRights) {
		this.dcRights = dcRights.clone();
	}

	@Override
	public String getEdmDataProvider() {
		return this.edmDataProvider;
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

	@Override
	public ArrayList<WebResource> getWebResources() {
		return webResources;
	}

	@Override
	public void setWebResources(List<WebResource> webResources) {
		this.webResources = (ArrayList<WebResource>) webResources;
	}

	@Override
	public boolean equals(Object o) {
		return this.getAggregationId().equals(
				((AggregationImpl) o).getAggregationId());
	}

}
