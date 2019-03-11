package eu.europeana.corelib.edm.model.schemaorg;

import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

@JsonldType(SchemaOrgConstants.TYPE_ORGANIZATION)
public class Organization extends ContextualEntity {
    @Override
    public String getTypeName() {
        return SchemaOrgConstants.TYPE_ORGANIZATION;
    }
}
