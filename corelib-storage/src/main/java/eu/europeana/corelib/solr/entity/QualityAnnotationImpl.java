package eu.europeana.corelib.solr.entity;

import eu.europeana.corelib.definitions.edm.entity.QualityAnnotation;
import org.mongodb.morphia.annotations.Entity;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since 2019-06-13
 */
@Entity("QualityAnnotation")
public class QualityAnnotationImpl extends AbstractEdmEntityImpl implements QualityAnnotation {

  private String[] dctermsCreated;
  private String[] oaHasTarget;
  private String[] oaHasBody;

  @Override
  public String[] getDctermsCreated() {
    return dctermsCreated;
  }

  @Override
  public void setDcTermsCreated(String[] dcTermsCreated) {
    this.dctermsCreated = dcTermsCreated;
  }

  @Override
  public String[] getOaHasTarget() {
    return oaHasTarget;
  }

  @Override
  public void setOaHasTarget(String[] oaHasTarget) {
    this.oaHasTarget = oaHasTarget;
  }

  @Override
  public String[] getOaHasBody() {
    return oaHasBody;
  }

  @Override
  public void setOaHasBody(String[] oaHasBody) {
    this.oaHasBody = oaHasBody;
  }
}
