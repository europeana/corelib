package eu.europeana.corelib.definitions.edm.entity;

import java.util.List;
import java.util.Map;

/**
 * Persistent Identifier
 * @author srishti singh
 * @since 11 june 2025
 */
public interface PersistentIdentifier {

    String getAbout();

    String getValue();

    Map<String, String> getCreator();

    String getCreated();

    String getHasPolicy();

    List<String> getNotation();

    List<String> getHasURL();

    List<String> getEquivalentPID();

    List<String> getReplacesPID();

    String getInScheme();

}
