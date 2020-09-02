package eu.europeana.corelib.definitions.solr;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import eu.europeana.corelib.utils.StringArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;

/**
 * DocType defines the different type Object types Europeana supports.
 *
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public enum DocType {

  TEXT("TEXT"),
  IMAGE("IMAGE"),
  SOUND("SOUND"),
  VIDEO("VIDEO"),
  _3D("3D");

  private String enumNameValue;

  DocType(String enumNameValue) {
    this.enumNameValue = enumNameValue;
  }

  public static DocType safeValueOf(String[] strings) {
    if (StringArrayUtils.isNotBlank(strings)) {
      return safeValueOf(strings[0]);
    }
    LogManager.getLogger(DocType.class).debug("Illegal type value (empty array)");
    return null;
  }

  /**
   * Returns DocType object that corresponds to the provider enumNameValue.
   *
   * @param enumNameValue The name of the DocType
   * @return The DocType object or null
   */
  @JsonCreator
  public static DocType safeValueOf(String enumNameValue) {
    if (StringUtils.isNotBlank(enumNameValue)) {
      for (DocType t : values()) {
        if (t.getEnumNameValue().equalsIgnoreCase(enumNameValue)) {
          return t;
        }
      }
    }
    LogManager.getLogger(DocType.class).debug("Illegal type value '{}'", enumNameValue);
    return null;
  }

  @JsonValue
  public String getEnumNameValue() {
    return enumNameValue;
  }

  @Override
  public String toString() {
    return this.getEnumNameValue();
  }
}
