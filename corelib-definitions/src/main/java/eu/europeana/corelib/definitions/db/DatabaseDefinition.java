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

package eu.europeana.corelib.definitions.db;

/**
 * This interface contains all the database definitions, reusable by front-end to validate fields.
 * 
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public interface DatabaseDefinition {
	
	// apikey
	static final String TABLENAME_APIKEY = "apikey";
	static final int FIELDSIZE_APIKEY = 30;
	
	// authentication
	static final String TABLENAME_AUTHENTICATION = "authentication";

	// user
	static final String TABLENAME_USER = "users";
	static final int FIELDSIZE_PERSONAL = 100;
	static final int FIELDSIZE_PASSWORD = 64;
	static final int FIELDSIZE_USERNAME = 60;
	static final int FIELDSIZE_ROLE = 25;

	// token
	static final String TABLENAME_TOKEN = "token";
	static final int FIELDSIZE_TOKEN = 32;

	// saved search
	static final String TABLENAME_SAVEDSEARCH = "SavedSearch";
	static final int FIELDSIZE_QUERY = 200;
	static final int FIELDSIZE_QUERY_STRING = 200;

	// saved item
	static final String TABLENAME_SAVEDITEM = "SavedItem";
	static final int FIELDSIZE_TITLE = 120;
	static final int FIELDSIZE_AUTHOR = 80;
	static final int FIELDSIZE_DOCTYPE = 10;
	static final int FIELDSIZE_EUROPEANA_URI = 256;
	static final int FIELDSIZE_EUROPEANA_OBJECT = 256;

	// social tags
	static final String TABLENAME_SOCIALTAGS = "SocialTag";
	static final int FIELDSIZE_TAG = 60;
}