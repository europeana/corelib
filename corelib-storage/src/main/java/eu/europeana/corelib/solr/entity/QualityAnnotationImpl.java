package eu.europeana.corelib.solr.entity;

import eu.europeana.corelib.definitions.edm.entity.AbstractEdmEntity;
import eu.europeana.corelib.definitions.edm.entity.QualityAnnotation;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

/**
 * Contains Quality annotation tier information.
 * <p>This class does not extend from {@link AbstractEdmEntityImpl} but rather implements the
 * {@link AbstractEdmEntity} to avoid the about field being indexed.<p/>
 *
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since 2019-06-13
 */
@Entity("QualityAnnotation")
public class QualityAnnotationImpl implements AbstractEdmEntity, QualityAnnotation {

  @Id
  protected ObjectId id = new ObjectId();
  protected String about;

  private String created;
  private String[] target;
  @Indexed
  private String body;

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
