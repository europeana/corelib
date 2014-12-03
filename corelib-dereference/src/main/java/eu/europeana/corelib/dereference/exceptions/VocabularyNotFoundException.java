package eu.europeana.corelib.dereference.exceptions;

/**
 * Exception thrown when a Vocabulary is not found
 * @author Yorgos.Mamakis@ europeana.eu
 *
 */
public class VocabularyNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String message;
	public VocabularyNotFoundException(String message){
		super(message);
		this.message = message;
	}
	
	public VocabularyNotFoundException(String uri, String name){
		super();
		this.message = String.format("Vocabulary with name: %s and uri %s does not exist",uri,name);
	}
	
	@Override
	public String getMessage(){
		return message;
	}
}
