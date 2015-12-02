package eu.europeana.corelib.definitions.db.entity.relational.enums;

/**
 * @author Willem-Jan Boogerd (www.eledge.net/contact).
 */
public enum ApiClientLevel {

    CLIENT("ROLE_CLIENT"), ADMIN("ROLE_CLIENT,ROLE_ADMIN_CLIENT");

    private final String roles;

    ApiClientLevel(String roles) {
        this.roles = roles;
    }

    public String getRoles() {
        return roles;
    }
}

