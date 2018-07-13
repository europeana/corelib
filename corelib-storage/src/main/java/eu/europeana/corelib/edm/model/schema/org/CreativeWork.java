package eu.europeana.corelib.edm.model.schema.org;

import com.fasterxml.jackson.annotation.JsonInclude;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

import java.util.Date;
import java.util.List;

@JsonldType("CreativeWork")
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class CreativeWork extends Thing {
    @Override
    public String getTypeName() { return "CreativeWork";}

    @JsonldProperty("publisher")
    private List<BaseType> publisher;

    @JsonldProperty("contributor")
    private List<BaseType> contributor;

    @JsonldProperty("about")
    private List<BaseType> about;

    @JsonldProperty("creator")
    private List<BaseType> creator;

    @JsonldProperty("dateCreated")
    private List<Date> dateCreated;

    @JsonldProperty("hasPart")
    private List<BaseType> hasPart;

    @JsonldProperty("exampleOfWork")
    private List<BaseType> exampleOfWork;

    @JsonldProperty("isPartOf")
    private List<BaseType> isPartOf;

    @JsonldProperty("datePublished")
    private List<Date> datePublished;

    @JsonldProperty("mentions")
    private List<BaseType> mentions;

    @JsonldProperty("spatialCoverage")
    private List<BaseType> spatialCoverage;

    @JsonldProperty("temporalCoverage")
    private List<String> temporalCoverage;

    public void setPublisher(List<BaseType> publisher) {
        if (this.publisher == null) {
            this.publisher = publisher;
        } else {
            this.publisher.addAll(publisher);
        }
    }

    public List<BaseType> getPublisher() {
        return publisher;
    }

    public void setContributor(List<BaseType> contributor) {
        if (this.contributor == null) {
            this.contributor = contributor;
        } else {
            this.contributor.addAll(contributor);
        }
    }

    public List<BaseType> getContributor() {
        return contributor;
    }

    public List<BaseType> getAbout() {
        return about;
    }

    public void setAbout(List<BaseType> about) {
        if (this.about == null) {
            this.about = about;
        } else {
            this.about.addAll(about);
        }
    }

    public List<BaseType> getCreator() {
        return creator;
    }

    public void setCreator(List<BaseType> creator) {
        if (this.creator == null) {
            this.creator = creator;
        } else {
            this.creator.addAll(creator);
        }
    }

    public List<Date> getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(List<Date> dateCreated) {
        if (this.dateCreated == null) {
            this.dateCreated = dateCreated;
        } else {
            this.dateCreated.addAll(dateCreated);
        }
    }

    public List<BaseType> getHasPart() {
        return hasPart;
    }

    public void setHasPart(List<BaseType> hasPart) {
        if (this.hasPart == null) {
            this.hasPart = hasPart;
        } else {
            this.hasPart.addAll(hasPart);
        }
    }

    public List<BaseType> getExampleOfWork() {
        return exampleOfWork;
    }

    public void setExampleOfWork(List<BaseType> exampleOfWork) {
        if (this.exampleOfWork == null) {
            this.exampleOfWork = exampleOfWork;
        } else {
            this.exampleOfWork.addAll(exampleOfWork);
        }
    }

    public List<BaseType> getIsPartOf() {
        return isPartOf;
    }

    public void setIsPartOf(List<BaseType> isPartOf) {
        if (this.isPartOf == null) {
            this.isPartOf = isPartOf;
        } else {
            this.isPartOf.addAll(isPartOf);
        }
    }

    public List<Date> getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(List<Date> datePublished) {
        if (this.datePublished == null) {
            this.datePublished = datePublished;
        } else {
            this.datePublished.addAll(datePublished);
        }
    }

    public List<BaseType> getMentions() {
        return mentions;
    }

    public void setMentions(List<BaseType> mentions) {
        if (this.mentions == null) {
            this.mentions = mentions;
        } else {
            this.mentions.addAll(mentions);
        }
    }

    public List<BaseType> getSpatialCoverage() {
        return spatialCoverage;
    }

    public void setSpatialCoverage(List<BaseType> spatialCoverage) {
        if (this.spatialCoverage == null) {
            this.spatialCoverage = spatialCoverage;
        } else {
            this.spatialCoverage.addAll(spatialCoverage);
        }
    }

    public List<String> getTemporalCoverage() {
        return temporalCoverage;
    }

    public void setTemporalCoverage(List<String> temporalCoverage) {
        if (this.temporalCoverage == null) {
            this.temporalCoverage = temporalCoverage;
        } else {
            this.temporalCoverage.addAll(temporalCoverage);
        }
    }
}
