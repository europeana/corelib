/*
 * Copyright 2007-2012 The Europeana Foundation
 *
 *  Licenced under the EUPL, Version 1.1 (the "Licence") and subsequent versions as approved
 *  by the European Commission;
 *  You may not use this work except in compliance with the Licence.
 * 
 *  You may obtain a copy of the Licence at:
 *  http://joinup.ec.europa.eu/software/page/eupl
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under
 *  the Licence is distributed on an "AS IS" basis, without warranties or conditions of
 *  any kind, either express or implied.
 *  See the Licence for the specific language governing permissions and limitations under
 *  the Licence.
 */
package eu.europeana.corelib.definitions.edm.entity;
/**
 * Interface for the ConceptScheme - Not Currently used
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
public interface ConceptScheme extends AbstractEdmEntity {
	/**
	 * 
	 * @return the dc:title for the conceptscheme
	 */
	String[] getDcTitle();
	
	/**
	 * sets the dc:title for the conceptscheme
	 * @param dcTitle
	 */
	void setDcTitle(String[] dcTitle);
	
	/**
	 * 
	 * @return the dc:creator for the conceptscheme
	 */
	String[] getDcCreator();
	
	/**
	 * sets the dc:creator for the conceptscheme
	 * @param dcCreator
	 */
	void setDcCreator(String[] dcCreator);
	
	/**
	 * 
	 * @return the skos:hasTopConceptOf for the conceptscheme
	 */
	String[] getHasTopConceptOf();
	
	/**
	 * sets the skos:hasTopConceptOf for the conceptscheme
	 * @param hasTopConceptOf
	 */
	void setHasTopConceptOf(String[] hasTopConceptOf);
	
	/**
	 * 
	 * @return the skos:note for the conceptscheme
	 */
	String[] getNote();
	
	/**
	 * sets the skos:note for the conceptscheme
	 * @param note
	 */
	void setNote(String[] note);
}
