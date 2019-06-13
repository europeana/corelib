package eu.europeana.corelib.definitions.edm.entity;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since 2019-06-13
 */
public interface QualityAnnotation extends AbstractEdmEntity {

  String[] getDctermsCreated();

  void setDcTermsCreated(String[] dcTermsCreated);

  String[] getOaHasTarget();

  void setOaHasTarget(String[] oaHasTarget);

  String[] getOaHasBody();

  void setOaHasBody(String[] oaHasBody);
}
