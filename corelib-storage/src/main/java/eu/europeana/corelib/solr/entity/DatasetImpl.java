package eu.europeana.corelib.solr.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.mongodb.morphia.annotations.Entity;
import eu.europeana.corelib.definitions.edm.entity.Dataset;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@JsonInclude(Include.NON_EMPTY)
@Entity("Dataset")
//TODO: NOT TO BE USED
public class DatasetImpl extends AbstractEdmEntityImpl implements Dataset {

	private String edmDatasetName;
	private String edmProvider;
	private Map<String, List<String>> edmIntermediateProvider;
	private Map<String, List<String>> edmDataProvider;
	private String edmCountry;
	private String edmLanguage;
	private String dcIdentifier;
	private Map<String, List<String>> dcDescription;
	private String dctermsCreated;
	private String dctermsExtent;
	private String dctermsModified;
	private String admsStatus;
	
	@Override
	public String getEdmDatasetName() {
		
		return this.edmDatasetName;
	}

	@Override
	public void setEdmDatasetName(String edmDatasetName) {
		this.edmDatasetName = edmDatasetName;
	}

	@Override
	public String getEdmProvider() {
		return this.edmProvider;
	}

	@Override
	public void setEdmProvider(String edmProvider) {
		this.edmProvider = edmProvider;
	}

	@Override
	public Map<String, List<String>> getEdmIntermediateProvider() {
		return this.edmIntermediateProvider;
	}

	@Override
	public void setEdmIntermediateProvider(Map<String, List<String>> edmIntermediateProvider) {
		this.edmIntermediateProvider = edmIntermediateProvider;
	}

	@Override
	public Map<String, List<String>> getEdmDataProvider() {
		return this.edmDataProvider;
	}

	@Override
	public void setEdmDataProvider(Map<String, List<String>> edmDataProvider) {
		this.edmDataProvider = edmDataProvider;
	}

	@Override
	public String getEdmCountry() {
		return this.edmCountry;
	}

	@Override
	public void setEdmCountry(String edmCountry) {
		this.edmCountry = edmCountry;
	}

	@Override
	public String getEdmLanguage() {
		return this.edmLanguage;
	}

	@Override
	public void setEdmLanguage(String edmLanguage) {
		this.edmLanguage = edmLanguage;
	}

	@Override
	public String getDcIdentifier() {
		return this.dcIdentifier;
	}

	@Override
	public void setDcIdentifier(String dcIdentifier) {
		this.dcIdentifier = dcIdentifier;
	}

	@Override
	public Map<String, List<String>> getDcDescription() {
		return this.dcDescription;
	}

	@Override
	public void setDcDescription(Map<String, List<String>> dcDescription) {
		this.dcDescription = dcDescription;
	}

	@Override
	public String getDctermsCreated() {
		return this.dctermsCreated;
	}

	@Override
	public void setDctermsCreated(String dctermsCreated) {
		this.dctermsCreated = dctermsCreated;
	}

	@Override
	public String getDctermsExtent() {
		return this.dctermsExtent;
	}

	@Override
	public void setDctermsExtent(String dctermsExtent) {
		this.dctermsExtent = dctermsExtent;
	}

	@Override
	public String getDctermsModified() {
		return this.dctermsModified;
	}

	@Override
	public void setDctermsModified(String dctermsModified) {
		this.dctermsModified = dctermsModified;
	}

	@Override
	public String getAdmsStatus() {
		return this.admsStatus;
	}

	@Override
	public void setAdmsStatus(String admsStatus) {
		this.admsStatus=admsStatus;
	}

}
