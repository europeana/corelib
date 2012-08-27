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

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Transient;

import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.definitions.solr.entity.Proxy;
import eu.europeana.corelib.utils.StringArrayUtils;

/**
 * @see eu.europeana.corelib.definitions.solr.entity.model.Proxy
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
@JsonSerialize(include = Inclusion.NON_EMPTY)
@Entity("Proxy")
public class ProxyImpl extends BasicProxyImpl implements Proxy {

	@Indexed(unique=false)
	private String about;

	private DocType edmType;

	@Transient
	private String[] edmUnstored;

	private boolean europeanaProxy;

	@Override
	public String[] getEdmUnstored() {
		return (StringArrayUtils.isNotBlank(edmUnstored) ? this.edmUnstored.clone() : null);
	}

	@Override
	public void setEdmUnstored(String[] edmUnstored) {
		this.edmUnstored = edmUnstored;
	}

	@Override
	public void setEdmType(DocType edmType) {
		this.edmType = edmType;
	}

	@Override
	public DocType getEdmType() {
		return this.edmType;
	}

	@Override
	public boolean isEuropeanaProxy() {
		return europeanaProxy;
	}

	@Override
	public void setEuropeanaProxy(boolean europeanaProxy) {
		this.europeanaProxy = europeanaProxy;
	}

	@Override
	public String getAbout() {
		return about;
	}

	@Override
	public void setAbout(String about) {
		this.about = about;
	}
}
