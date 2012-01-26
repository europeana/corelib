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

package eu.europeana.corelib.solr.bean;


/**
 * Interface for the BriefBean. The BriefBean contains the fields exposed by the SOLR engine
 * for presenting each record in the result and search page.
 *    
 * NOTE: TBD what is going to be placed here
 * 
 * @author Yorgos Mamakis <yorgos.mamakis@kb.nl>
 */
public interface BriefBean extends IdBean {

	int getIndex();

	String getFullDocUrl();

	void setIndex(int index);

	void setFullDocUrl(String fullDocUrl);

}
