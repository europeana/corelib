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

package eu.europeana.corelib.definitions.db.entity;

/**
 * This interface contains all the database definitions, reusable by front-end to validate fields.
 * 
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public interface RelationalDatabase {

	// apikey
	String TABLENAME_APIKEY = "apikey";
	int FIELDSIZE_APIKEY = 30;

	// user
	String TABLENAME_USER = "users";
	int FIELDSIZE_PERSONAL = 100;
	int FIELDSIZE_PASSWORD = 64;
	int FIELDSIZE_USERNAME = 60;
	int FIELDSIZE_ROLE = 25;
	int FIELDSIZE_NAME = 30;
	int FIELDSIZE_SURNAME = 50;
	int FIELDSIZE_COMPANY = 100;
	int FIELDSIZE_COUNTRY = 30;
	int FIELDSIZE_ADDRESS = 250;
	int FIELDSIZE_PHONE = 15;
	int FIELDSIZE_WEBSITE = 100;
	int FIELDSIZE_FIELDOFWORK = 50;
	int FIELDSIZE_LANGUAGEPORTAL = 20;
	int FIELDSIZE_LANGUAGEITEM = 20;
	int FIELDSIZE_LANGUAGESEARCH = 20;

	// token
	String TABLENAME_TOKEN = "token";
	int FIELDSIZE_TOKEN = 32;
	int FIELDSIZE_REDIRECT = 256;

	// saved search
	String TABLENAME_SAVEDSEARCH = "SavedSearch";
	int FIELDSIZE_QUERY = 200;
	int FIELDSIZE_QUERY_STRING = 200;

	// saved item
	String TABLENAME_SAVEDITEM = "SavedItem";
	int FIELDSIZE_TITLE = 120;
	int FIELDSIZE_AUTHOR = 80;
	int FIELDSIZE_DOCTYPE = 10;
	int FIELDSIZE_EUROPEANA_URI = 256;
	int FIELDSIZE_EUROPEANA_OBJECT = 256;

	// social tags
	String TABLENAME_SOCIALTAGS = "SocialTag";
	int FIELDSIZE_TAG = 60;

	String SEARCH_LANGUAGES_SEPARATOR = "|";
}