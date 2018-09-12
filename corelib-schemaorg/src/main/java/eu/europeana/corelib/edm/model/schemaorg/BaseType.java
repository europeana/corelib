package eu.europeana.corelib.edm.model.schemaorg;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface BaseType {
    @JsonIgnore
    String getTypeName();
}
