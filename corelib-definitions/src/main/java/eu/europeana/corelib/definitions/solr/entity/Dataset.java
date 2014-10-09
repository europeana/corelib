package eu.europeana.corelib.definitions.solr.entity;

//TODO: NOT TO BE USED
public interface Dataset extends AbstractEdmEntity {
	
	String getEdmDatasetName();
	
	void setEdmDatasetName(String edmDatasetName);
	
	String getEdmProvider();
	
	void setEdmProvider(String edmProvider);
	
	String getDcIdentifier();
	
	void setDcIdentifier(String dcIdentifier);
	
	String getDctermsCreated();
	
	void setDctermsCreated(String dctermsCreated);
	
	String getDctermsExtent();
	
	void setDctermsExtent(String dctermsExtent);
	
	String getAdmsStatus();
	
	void setAdmsStatus(String admsStatus);
}
