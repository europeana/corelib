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