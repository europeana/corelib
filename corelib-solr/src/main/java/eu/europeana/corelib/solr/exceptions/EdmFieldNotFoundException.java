package eu.europeana.corelib.solr.exceptions;

public class EdmFieldNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String field;
	String recordId;
	
	public EdmFieldNotFoundException(String field, String recordId) {
		this.field = field;
		this.recordId = recordId;
	}
	
	
	@Override
	public String getMessage() {
		return "EDM Field not found exception: " + field + " for recordId: "+ recordId + " has not been mapped to an EDM field";
	}
}
