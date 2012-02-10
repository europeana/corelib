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
	final String TABLENAME_APIKEY = "apikey";
	final int FIELDSIZE_APIKEY = 30;
	
	// authentication
	final String TABLENAME_AUTHENTICATION = "authentication";

	// user
	final String TABLENAME_USER = "users";
	final int FIELDSIZE_PERSONAL = 100;
	final int FIELDSIZE_PASSWORD = 64;
	final int FIELDSIZE_USERNAME = 60;
	final int FIELDSIZE_ROLE = 25;

	// token
	final String TABLENAME_TOKEN = "token";
	final int FIELDSIZE_TOKEN = 32;

	// saved search
	final String TABLENAME_SAVEDSEARCH = "SavedSearch";
	final int FIELDSIZE_QUERY = 200;
	final int FIELDSIZE_QUERY_STRING = 200;

	// saved item
	final String TABLENAME_SAVEDITEM = "SavedItem";
	final int FIELDSIZE_TITLE = 120;
	final int FIELDSIZE_AUTHOR = 80;
	final int FIELDSIZE_DOCTYPE = 10;
	final int FIELDSIZE_EUROPEANA_URI = 256;
	final int FIELDSIZE_EUROPEANA_OBJECT = 256;

	// social tags
	final String TABLENAME_SOCIALTAGS = "SocialTag";
	final int FIELDSIZE_TAG = 60;
}