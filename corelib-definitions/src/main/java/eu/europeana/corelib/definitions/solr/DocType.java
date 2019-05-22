package eu.europeana.corelib.definitions.solr;

import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.mongodb.morphia.converters.SimpleValueConverter;
import org.mongodb.morphia.converters.TypeConverter;
import org.mongodb.morphia.mapping.MappedField;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import eu.europeana.corelib.utils.StringArrayUtils;

/**
 * DocType defines the different type Object types Europeana supports.
 *
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public enum DocType {
  
	TEXT("TEXT", "doc", "pdf"),
	IMAGE("IMAGE", "jpeg", "jpg", "png", "tif"),
	SOUND("SOUND", "mp3"),
	VIDEO("VIDEO", "avi", "mpg"),
	_3D("3D");

	private String enumNameValue;
	private String[] extentions;

	DocType(String enumNameValue, String... extentions) {
		this.enumNameValue = enumNameValue;
		this.extentions = extentions;
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
	 * @param enumNameValue
	 *   The name of the DocType
	 * @return
	 *   The DocType object or null
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

	public static DocType getByExtention(String ext) {
		if (StringUtils.isNotBlank(ext)) {
			for (DocType t : values()) {
				if (StringArrayUtils.isNotBlank(t.extentions)) {
					for (String e : t.extentions) {
						e = StringArrayUtils.concat(".", e);
						if (StringUtils.endsWithIgnoreCase(ext, e)) {
							return t;
						}
					}
				}
			}
		}
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
	
    public static class DocTypeConverter extends TypeConverter implements SimpleValueConverter {
  
      public DocTypeConverter() {
          super(DocType.class);
      }

      @Override
      public DocType decode(Class<?> targetClass, Object fromDBObject, MappedField optionalExtraInfo) {
          return Optional.ofNullable(fromDBObject)
              .map(object -> (String) object)
              .map(DocType::safeValueOf)
              .orElse(null);
      }
  
      @Override
      public String encode(final Object value, final MappedField optionalExtraInfo) {
          return Optional.ofNullable(value)
              .map(object -> (DocType) object)
              .map(DocType::getEnumNameValue)
              .orElse(null);
      }
    }
}
