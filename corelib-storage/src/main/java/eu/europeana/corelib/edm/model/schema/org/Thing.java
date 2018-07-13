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

    @JsonldProperty("name")
    private List<MultilingualString> name;

    @JsonldProperty("description")
    private List<MultilingualString> description;

    @JsonldProperty("inLanguage")
    private List<String> inLanguage;

    @JsonldProperty("alternateName")
    private List<MultilingualString> alternateName;

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

    public List<MultilingualString> getDescription() {
        return description;
    }

    public void setDescription(List<MultilingualString> description) {
        if (this.description == null) {
            this.description = description;
        } else {
            this.description.addAll(description);
        }
    }

    public List<String> getInLanguage() {
        return inLanguage;
    }

    public void setInLanguage(List<String> inLanguage) {
        if (this.inLanguage == null) {
            this.inLanguage = inLanguage;
        } else {
            this.inLanguage.addAll(inLanguage);
        }
    }

    public List<MultilingualString> getAlternateName() {
        return alternateName;
    }

    public void setAlternateName(List<MultilingualString> alternateName) {
        if (this.alternateName == null) {
            this.alternateName = alternateName;
        } else {
            this.alternateName.addAll(alternateName);
        }
    }
}
