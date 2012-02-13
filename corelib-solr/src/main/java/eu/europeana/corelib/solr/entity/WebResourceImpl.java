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

import eu.europeana.corelib.definitions.solr.entity.WebResource;
/**
 * @see eu.europeana.corelib.definitions.solr.entity.corelid.definitions.model.WebResource
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
@Entity("Webresource")
public class WebResourceImpl implements WebResource {

	@Id ObjectId webResourceId;
	private String webResource;
	private String[] webResourceDcRights;
	private String webResourceEdmRights;
	
	@Override
	public String getWebResource() {
		return this.webResource;
	}

	@Override
	public void setWebResourceId(ObjectId webResourceId) {
		this.webResourceId = webResourceId;
	}

	@Override
	public void setWebResource(String webResource) {
		this.webResource = webResource;
	}

	@Override
	public void setWebResourceDcRights(String[] webResourceDcRights) {
		this.webResourceDcRights = webResourceDcRights.clone();
	}

	@Override
	public void setWebResourceEdmRights(String webResourceEdmRights) {
		this.webResourceEdmRights = webResourceEdmRights;
	}

	@Override
	public String[] getWebResourceDcRights() {
		return this.webResourceDcRights;
	}

	@Override
	public String getWebResourceEdmRights() {
		return this.webResourceEdmRights;
	}

	@Override
	public ObjectId getWebResourceId() {
		return this.webResourceId;
	}
	@Override
	public boolean equals(Object o){
		return this.getWebResourceId().equals(((WebResourceImpl)o).getWebResourceId());
	}
}
