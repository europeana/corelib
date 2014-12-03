package eu.europeana.corelib.edm.exceptions;

public class EdmValueNotFoundException extends ArrayIndexOutOfBoundsException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String field;
	String recordId;
	
	public EdmValueNotFoundException(String field, String recordId) {
		this.field = field;
		this.recordId = recordId;
	}
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "Value for field: "+ field +" of record: "+ recordId +" does not exist";
	}

}
