package eu.europeana.corelib.edm.model.schema.org;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

@JsonldType(SchemaOrgConstants.TYPE_BOOK)
public class Book extends CreativeWork {
    @JsonIgnore
    @Override
    public String getTypeName() { return SchemaOrgConstants.TYPE_BOOK; }
}
