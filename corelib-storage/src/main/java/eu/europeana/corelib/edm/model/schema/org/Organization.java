package eu.europeana.corelib.edm.model.schema.org;

import com.fasterxml.jackson.annotation.JsonProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

@JsonldType(SchemaOrgConstants.TYPE_ORGANIZATION)
public class Organization extends Thing {

    @JsonProperty("@type")
    @Override
    public String getTypeName() {
        return SchemaOrgConstants.TYPE_ORGANIZATION;
    }
}
