package eu.europeana.corelib.solr.entity;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.google.code.morphia.annotations.Entity;

import eu.europeana.corelib.definitions.edm.entity.Dataset;
@JsonSerialize(include = Inclusion.NON_EMPTY)
@Entity("Dataset")
//TODO: NOT TO BE USED
public class DatasetImpl extends AbstractEdmEntityImpl implements Dataset {

	private String edmDatasetName;
	private String edmProvider;
	private String dcIdentifier;
	private String dctermsCreated;
	private String dctermsExtent;
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
	public String getDcIdentifier() {
		return this.dcIdentifier;
	}

	@Override
	public void setDcIdentifier(String dcIdentifier) {
		this.dcIdentifier = dcIdentifier;
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
	public String getAdmsStatus() {
		return this.admsStatus;
	}

	@Override
	public void setAdmsStatus(String admsStatus) {
		this.admsStatus=admsStatus;
	}

}
