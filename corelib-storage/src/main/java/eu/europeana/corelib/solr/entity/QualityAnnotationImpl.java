package eu.europeana.corelib.solr.entity;

import eu.europeana.corelib.definitions.edm.entity.QualityAnnotation;
import org.mongodb.morphia.annotations.Entity;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since 2019-06-13
 */
@Entity("QualityAnnotation")
public class QualityAnnotationImpl extends AbstractEdmEntityImpl implements QualityAnnotation {

  private String created;
  private String[] target;
  private String body;

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
