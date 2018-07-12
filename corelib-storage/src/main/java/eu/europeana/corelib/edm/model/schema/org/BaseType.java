package eu.europeana.corelib.edm.model.schema.org;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface BaseType {
    @JsonIgnore
    String getTypeName();
}
