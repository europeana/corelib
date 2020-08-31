package eu.europeana.corelib.solr.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.morphia.annotations.Embedded;
import dev.morphia.annotations.Field;
import dev.morphia.annotations.Index;
import dev.morphia.annotations.Indexes;
import eu.europeana.corelib.definitions.edm.entity.AbstractEdmEntity;
import eu.europeana.corelib.definitions.edm.entity.QualityAnnotation;
import org.bson.types.ObjectId;

/**
 * Contains Quality annotation tier information.
 * <p>This class does not extend from {@link AbstractEdmEntityImpl} but rather implements the
 * {@link AbstractEdmEntity} to avoid the about field being indexed.<p/>
 *
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since 2019-06-13
 */
@Embedded
@Indexes(@Index(fields = {@Field("about")}))
public class QualityAnnotationImpl implements AbstractEdmEntity, QualityAnnotation {

  protected ObjectId id = new ObjectId();
  protected String about;

  private String created;
  private String[] target;
  private String body;

  @JsonIgnore
  @Override
  public ObjectId getId() {
    return this.id;
  }

  @Override
  public void setId(ObjectId id) {
    this.id = id;
  }

  @Override
  public String getAbout() {
    return this.about;
  }

  @Override
  public void setAbout(String about) {
    this.about = about;
  }

  @Override
  public String getCreated() {
    return created;
  }

  @Override
  public void setCreated(String dcTermsCreated) {
    this.created = dcTermsCreated;
  }

  @Override
  public String[] getTarget() {
    return target;
  }

  @Override
  public void setTarget(String[] target) {
    this.target = target;
  }

  @Override
  public String getBody() {
    return body;
  }

  @Override
  public void setBody(String body) {
    this.body = body;
  }
}
