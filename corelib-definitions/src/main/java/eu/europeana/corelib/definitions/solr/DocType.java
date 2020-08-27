package eu.europeana.corelib.definitions.solr;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import dev.morphia.mapping.codec.EnumCodec;
import eu.europeana.corelib.utils.StringArrayUtils;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

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

  public static class DocTypeConverter extends EnumCodec<DocType> {

    public DocTypeConverter() {
      super(DocType.class);
    }

    @Override
    public void encode(BsonWriter writer, DocType value, EncoderContext encoderContext) {
      writer.writeString(Optional.ofNullable(value)
          .map(DocType::getEnumNameValue)
          .orElse(null));
    }

    @Override
    public DocType decode(BsonReader reader, DecoderContext decoderContext) {
      return Optional.ofNullable(reader.readString())
          .map(DocType::safeValueOf)
          .orElse(null);
    }
  }
}
