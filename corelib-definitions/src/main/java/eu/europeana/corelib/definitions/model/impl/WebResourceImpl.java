/*
 * Copyright 2007 EDL FOUNDATION
 *
 * Licensed under the EUPL, Version 1.0 or? as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * you may not use this work except in compliance with the
 * Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://ec.europa.eu/idabc/eupl
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 */
package eu.europeana.corelib.definitions.model.impl;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.*;

import eu.europeana.corelib.definitions.model.WebResource;
/**
 * @see eu.europeana.corelid.definitions.model.WebResource
 * @author yorgos.mamakis@kb.nl
 *
 */
@Entity("Webresource")
public class WebResourceImpl implements WebResource {

	@Id ObjectId webResourceId;
	private String[] webResource;
	private String[] webResourceDcRights;
	private String webResourceEdmRights;
	
	@Override
	public String[] getEdmWebResource() {
		return this.webResource;
	}

	@Override
	public String[] getEdmWebResourceDcRights() {
		return this.webResourceDcRights;
	}

	@Override
	public String getEdmWebResourceEdmRights() {
		return this.webResourceEdmRights;
	}

}
