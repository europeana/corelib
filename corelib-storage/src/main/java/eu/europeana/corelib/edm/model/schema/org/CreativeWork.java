package eu.europeana.corelib.edm.model.schema.org;

import com.fasterxml.jackson.annotation.JsonInclude;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

import java.util.List;

@JsonldType("CreativeWork")
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class CreativeWork extends Thing {
    private List<MultilingualStringWithReference> publisher;

    @JsonldProperty("publisher")
    public void setPublisher(List<MultilingualStringWithReference> publisher) {
        if (this.publisher == null) {
            this.publisher = publisher;
        } else {
            this.publisher.addAll(publisher);
        }
    }

    public List<MultilingualStringWithReference> getPublisher() {
        return publisher;
    }

}
