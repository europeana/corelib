package eu.europeana.corelib.solr.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Reference;
import dev.morphia.annotations.Transient;
import eu.europeana.corelib.definitions.edm.entity.ChangeLog;
import eu.europeana.corelib.definitions.edm.entity.EuropeanaAggregation;
import eu.europeana.corelib.definitions.edm.entity.QualityAnnotation;
import eu.europeana.corelib.definitions.edm.entity.WebResource;
import eu.europeana.corelib.edm.utils.ValidateUtils;
import eu.europeana.corelib.utils.StringArrayUtils;
import eu.europeana.corelib.web.service.impl.EuropeanaUrlBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;

@JsonInclude(Include.NON_EMPTY)
@Entity(value = "EuropeanaAggregation", useDiscriminator = false)
public class EuropeanaAggregationImpl extends AbstractEdmEntityImpl implements
    EuropeanaAggregation {

  @Reference
  private List<WebResourceImpl> webResources = new ArrayList<>();

  private String aggregatedCHO;
  private String[] aggregates;
  private Map<String, List<String>> dcCreator;
  private String edmIsShownBy;
  private String[] edmHasView;
  private Map<String, List<String>> edmCountry;
  private Map<String, List<String>> edmLanguage;
  private Map<String, List<String>> edmRights;
  private String edmPreview = "";
  private String edmLandingPage; // used to be loaded from UIM Mongo, but not anymore with Metis Mongo
  private List<ChangeLogImpl> changeLog = new ArrayList<>();

  /**
   *  dqvHasQualityAnnotation should be fetched from Mongo but not added like string[] in the json response
   *  See - EA-3809 for the new structure of quality annotations
   *  Also see below the field add List<QualityAnnotation> hasQualityAnnotation for the json response
   */
  @JsonIgnore
  private String[] dqvHasQualityAnnotation;

  /**
   * EA-3809 we need to add List<QualityAnnotation> now based on the values present in dqvHasQualityAnnotation
   * AS this value doesn't exist in the Mongo. Hence the Transient annotation. This field is only for json view purposes
   * @See eu.europeana.api2.v2.model.json.view.FullView#getEuropeanaAggregation
   */
  @Transient
  @JsonProperty("dqvHasQualityAnnotation")
  private List<QualityAnnotation> hasQualityAnnotation;

  @JsonIgnore
  private boolean edmLandingPageExternal = false; // does the edmLandingPage contain an alternative value set by an external source?

  /**
   * The edmLandingPage is not stored in Mongo but derived. Since Morphia sets all fields after
   * constructing the object we have no choice but to call this method in the getter, but we make it
   * so we only do this once.
   */
  private String generateEdmLandingPage() {
    String id = this.aggregatedCHO;
    // it's possible that /item is added via the ItemFix.class in API2, so always check first
    if (id != null && id.startsWith("/item")) {
      id = StringUtils.substringAfter(this.aggregatedCHO, "/item");
    }
    if (id == null) {
      // use aggregation about field as fallback
      id = StringUtils.substringAfter(this.about, "/aggregation/europeana");
    }
    // extra checks to see if we have a proper id and url
    if (ValidateUtils.validateRecordIdFormat(id)) {
      return EuropeanaUrlBuilder.getRecordPortalUrl(id).toString();
    } else {
      LogManager.getLogger(EuropeanaAggregationImpl.class)
          .error("Error generating edmLandingPage. Found incorrect id: {}", id);
    }
    return null;
  }

  @Override
  public String getAggregatedCHO() {
    return this.aggregatedCHO;
  }

  @Override
  public void setAggregatedCHO(String aggregatedCHO) {
    this.aggregatedCHO = aggregatedCHO;
  }

  @Override
  public String[] getAggregates() {
    return (StringArrayUtils.isNotBlank(this.aggregates) ? this.aggregates.clone() : null);
  }

  @Override
  public void setAggregates(String[] aggregates) {
    this.aggregates = aggregates != null ? aggregates.clone() : null;
  }

  @Override
  public Map<String, List<String>> getDcCreator() {
    return this.dcCreator;
  }

  @Override
  public void setDcCreator(Map<String, List<String>> dcCreator) {
    this.dcCreator = dcCreator;
  }

  /**
   * @see EuropeanaAggregation#getEdmLandingPage()
   */
  @Override
  public String getEdmLandingPage() {
    if (edmLandingPageExternal) {
      return this.edmLandingPage;
    } else {
      return generateEdmLandingPage();
    }
  }

  /**
   * @see EuropeanaAggregation#setEdmLandingPage(String)
   */
  @Override
  public void setEdmLandingPage(String edmLandingPage) {
    this.edmLandingPageExternal = true;
    this.edmLandingPage = edmLandingPage;
  }

  @Override
  public String getEdmIsShownBy() {
    return this.edmIsShownBy;
  }

  @Override
  public void setEdmIsShownBy(String edmIsShownBy) {
    this.edmIsShownBy = edmIsShownBy;
  }

  @Override
  public String[] getEdmHasView() {
    return this.edmHasView;
  }

  @Override
  public void setEdmHasView(String[] edmHasView) {
    this.edmHasView = edmHasView != null ? edmHasView.clone() : null;
  }

  @Override
  public Map<String, List<String>> getEdmCountry() {
    return this.edmCountry;
  }

  @Override
  public void setEdmCountry(Map<String, List<String>> edmCountry) {
    this.edmCountry = edmCountry;
  }

  @Override
  public Map<String, List<String>> getEdmLanguage() {
    return this.edmLanguage;
  }

  @Override
  public void setEdmLanguage(Map<String, List<String>> edmLanguage) {
    this.edmLanguage = edmLanguage;
  }

  @Override
  public Map<String, List<String>> getEdmRights() {
    return this.edmRights;
  }

  @Override
  public void setEdmRights(Map<String, List<String>> edmRights) {
    this.edmRights = edmRights;
  }

  @Override
  public List<? extends WebResource> getWebResources() {
    return webResources;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void setWebResources(List<? extends WebResource> webResources) {
    this.webResources = (List<WebResourceImpl>) webResources;
  }

  @Override
  public String getEdmPreview() {

    return this.edmPreview;
  }

  @Override
  public void setEdmPreview(String edmPreview) {
    this.edmPreview = edmPreview;
  }

  @Override
  public String[] getDqvHasQualityAnnotation() {
    return dqvHasQualityAnnotation;
  }

  @Override
  public void setDqvHasQualityAnnotation(String[] dqvHasQualityAnnotation) {
    this.dqvHasQualityAnnotation = dqvHasQualityAnnotation;
  }

  // EA-3809 add list of QualityAnnotation
  public List<QualityAnnotation> getHasQualityAnnotation() {
    return hasQualityAnnotation;
  }

  public void setHasQualityAnnotation(List<QualityAnnotation> hasQualityAnnotation) {
    this.hasQualityAnnotation = hasQualityAnnotation;
  }

  @Override
  public List<? extends ChangeLog> getChangeLog() {
    return changeLog;
  }

  @Override
  public void setChangeLog(List<? extends ChangeLog> changeLog) {
    this.changeLog = (List<ChangeLogImpl>) changeLog;
  }
}
