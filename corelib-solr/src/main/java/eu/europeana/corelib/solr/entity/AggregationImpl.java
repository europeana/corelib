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

import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Reference;

import eu.europeana.corelib.definitions.solr.entity.Aggregation;
import eu.europeana.corelib.definitions.solr.entity.WebResource;

/**
 * @see eu.europeana.corelib.definitions.solr.entity.model.definitions.Aggregation
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
@Embedded
public class AggregationImpl implements Aggregation {
	private ObjectId id;

	private String edmDataProvider;
	private String edmIsShownBy;
	private String edmIsShownAt;
	private String edmObject;
	private String edmProvider;
	private String edmRights;
	private String edmUgc;
	private String[] dcRights;
	private String[] hasView;
	private String aggregatedCHO;
	@Embedded
	private List<WebResourceImpl> webResources;
	
	@Indexed(unique=true, dropDups=true)
	private String about;
	private Boolean edmPreviewNoDistribute;
	
	
	@Override
	public String getAggregatedCHO() {
		return this.aggregatedCHO;
	}

	@Override
	public void setAggregatedCHO(String aggregatedCHO) {
		this.aggregatedCHO = aggregatedCHO;
	}

	@Override
	public Boolean getEdmPreviewNoDistribute() {
		return this.edmPreviewNoDistribute;
	}

	@Override
	public void setEdmPreviewNoDistribute(Boolean edmPreviewNoDistribute) {
		this.edmPreviewNoDistribute = edmPreviewNoDistribute;
	}

	@Override
	public String getEdmUgc() {
		return this.edmUgc;
	}
	
	@Override
	public void setEdmUgc(String edmUgc) {
		this.edmUgc = edmUgc;
	}
	
	@Override
	public String getAbout() {
		return this.about;
	}

	@Override
	public void setAbout(String about) {
		this.about = about;
	}

	

	@Override
	public void setId(ObjectId id) {
		this.id = id;
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
		return (this.dcRights!=null?this.dcRights.clone():null);
	}

	@Override
	public String getEdmRights() {
		return this.edmRights;
	}

	@Override
	public ObjectId getId() {
		return this.id;
	}

	@Override
	public List<WebResourceImpl> getWebResources() {
		return this.webResources;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setWebResources(List<? extends WebResource> webResources) {
		this.webResources = (List<WebResourceImpl>) webResources;
	}

	
	@Override
	public String[] getHasView() {
		return (this.hasView!=null?hasView.clone():null);
	}

	@Override
	public void setHasView(String[] hasView) {
		this.hasView = hasView.clone();
	}

	@Override
	public boolean equals(Object o) {
		if(o==null){
			return false;
		}
		if(o.getClass() == this.getClass()){
			return this.getAbout().equals(((AggregationImpl) o).getAbout());
		}
		return false;
	}

	@Override
	public int hashCode(){ 
		return this.about.hashCode();
	}
}
