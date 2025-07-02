package eu.europeana.corelib.solr.entity;

import dev.morphia.annotations.*;
import eu.europeana.corelib.definitions.edm.entity.PersistentIdentifier;

import java.util.List;
import java.util.Map;

/**
 * Contains Persistent Identifier information
 * @author srishti singh
 * @since 11 june 2025
 */
@Entity(useDiscriminator = false)
@Indexes(@Index(fields = {@Field("about")}))
public class PersistentIdentifierImpl implements PersistentIdentifier {

    private String about;
    private String value;
    private Map<String, String> creator;
    private String created;
    private String hasPolicy;
    private List<String> notation;
    private String hasURL;
    private List<String> equivalentPID;
    private List<String> replacesPID;
    private String inScheme;

    @Override
    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    @Override
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public Map<String, String> getCreator() {
        return creator;
    }

    public void setCreator(Map<String, String> creator) {
        this.creator = creator;
    }

    @Override
    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    @Override
    public String getHasPolicy() {
        return hasPolicy;
    }

    public void setHasPolicy(String hasPolicy) {
        this.hasPolicy = hasPolicy;
    }

    @Override
    public List<String> getNotation() {
        return notation;
    }

    public void setNotation(List<String> notation) {
        this.notation = notation;
    }

    @Override
    public String getHasURL() {
        return hasURL;
    }

    public void setHasURL(String hasURL) {
        this.hasURL = hasURL;
    }

    @Override
    public List<String> getEquivalentPID() {
        return equivalentPID;
    }

    public void setEquivalentPID(List<String> equivalentPID) {
        this.equivalentPID = equivalentPID;
    }

    @Override
    public List<String> getReplacesPID() {
        return replacesPID;
    }

    public void setReplacesPID(List<String> replacesPID) {
        this.replacesPID = replacesPID;
    }

    @Override
    public String getInScheme() {
        return inScheme;
    }

    public void setInScheme(String inScheme) {
        this.inScheme = inScheme;
    }
}
