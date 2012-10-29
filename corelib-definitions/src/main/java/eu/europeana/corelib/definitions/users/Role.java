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

package eu.europeana.corelib.definitions.users;

/**
 * Enumerates the roles
 * 
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public enum Role {
	ROLE_USER, // general portal user, no dashboard access
	ROLE_CONTENT_TESTER, // a user of the special sandbox version of the dashboard
	ROLE_TRANSLATOR, // translation tab only
	ROLE_EDITOR, // carousel + proposed search terms
	ROLE_PACTA, // only pacta editor
	ROLE_CARROUSEL, // only carrousel editory
	ROLE_ADMINISTRATOR, // all rights, except record editor
	ROLE_GOD // all rights
}