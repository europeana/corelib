package eu.europeana.corelib.tools.lookuptable;

/**
 * General states for record identifiers 
 * 
 * @author Georgios Markakis <gwarkx@hotmail.com>
 * @since 3 Oct 2012
 */
public enum LookupState {

	
	/**
	 * Status that indicates a newly crated object
	 */
	ID_REGISTERED,
	
	/**
	 * Status that indicates an update on an existing record
	 */
	UPDATE,
	
	/**
	 * Status that indicates that an unchanged record has been reimported
	 */
	IDENTICAL,
	
	/**
	 * Status that indicates that 2 records with the same id are contained twice in the same collection
	 */
	DUPLICATE_INCOLLECTION,
	
	/**
	 * Status that indicates that 2 records obtained the same identifier during the import process
	 */
	DERIVED_DUPLICATE_INCOLLECTION,
	
	/**
	 * Indicates that the same identifier is present in more than one collections
	 */
	DUPLICATE_IDENTIFIER_ACROSS_COLLECTIONS,
	
	/**
	 * Indicates that the same record (identifier & content) is present in more than one collections
	 */
	DUPLICATE_RECORD_ACROSS_COLLECTIONS,
	
	/**
	 * Indicates that the collection for this record has changed
	 */
	COLLECTION_CHANGED,
	
	//System Specific
	
	/**
	 * Indicates that the record has been discarded because its XML structure does
	 * not conform to the structural rules supported 
	 */
	INCOMPATIBLE_XML_CONTENT,
	
	/**
	 * Indicates a generic system error has occured
	 */
	SYSTEM_ERROR
	
	
	
}
