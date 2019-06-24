package eu.europeana.corelib.definitions.edm.entity;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since 2019-06-13
 */
public interface QualityAnnotation extends AbstractEdmEntity {

  String getCreated();

  void setCreated(String dcTermsCreated);

  String[] getTarget();

  void setTarget(String[] target);

  String getBody();

  void setBody(String body);
}
