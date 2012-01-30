/*
 * Copyright 2007 EDL FOUNDATION
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they 
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "License");
 * you may not use this work except in compliance with the
 * License.
 * You may obtain a copy of the License at:
 *
 * http://ec.europa.eu/idabc/eupl
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the License is
 * distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 */

package eu.europeana.corelib.definitions.db.entity.abstracts;

import java.util.Date;

import eu.europeana.corelib.definitions.solr.DocType;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public interface EuropeanaUserObject extends UserConnected<Long> {

	public abstract Long getId();

	public abstract Date getDateSaved();

	public abstract void setDateSaved(Date dateSaved);

	public abstract String getTitle();

	public abstract void setTitle(String title);

	public abstract String getEuropeanaObject();

	public abstract void setEuropeanaObject(String europeanaObject);

	public abstract String getEuropeanaUri();

	public abstract void setEuropeanaUri(String europeanaUri);

	public abstract DocType getDocType();

	public abstract void setDocType(DocType docType);

}