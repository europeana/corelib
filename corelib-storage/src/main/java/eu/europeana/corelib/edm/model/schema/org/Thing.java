package eu.europeana.corelib.edm.model.schema.org;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldId;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonldType("Thing")
@JsonPropertyOrder({"@id", "name"})
public class Thing implements BaseType {
    private String id;

    @JsonldId
    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    /**
     * Properties
     */
    @JsonIgnore
    private Map<String, List<BaseType>> properties = new HashMap<>();

    private List<MultilingualString> name;

    @JsonldProperty("name")
    public void setName(List<MultilingualString> name) {
        if (this.name == null) {
            this.name = name;
        } else {
            this.name.addAll(name);
        }
    }

    public List<MultilingualString> getName() {
        return name;
    }

    @Override
    public String getTypeName() {
        return "Thing";
    }
}
