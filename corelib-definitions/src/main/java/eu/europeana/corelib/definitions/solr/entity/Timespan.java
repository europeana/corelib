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
package eu.europeana.corelib.definitions.solr.entity;

/**
 * EDM Timespan fields representation
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public interface Timespan extends ContextualClass {

	/**
	 * Retrieve the edm:begin field of a Timespan
	 * 
	 * @return Stringrepresenting the edm:begin field of a timespan
	 */
	String[] getBegin();

	/**
	 * Retrieve the edm:end field of a Timespan
	 * 
	 * @return String representing the edm:end field of a timespan
	 */
	String[] getEnd();

	/**
	 * Retrieve the dcterms:isPartOf field of a Timespan
	 * 
	 * @return String array representing the dcterms:isPartOf fields of a
	 *         timespan
	 */
	String[] getIsPartOf();

	/**
	 * Set the edm:begin field for a Timespan. It expects to find a date.
	 * 
	 * @param begin
	 *            the edm:begin field for a Timespan
	 */
	void setBegin(String[] begin);

	/**
	 * Set the edm:end field for a Timespan. It expects to find a date.
	 * 
	 * @param end
	 *            the edm:end field for a Timespan
	 */
	void setEnd(String[] end);

	/**
	 * Set the isPartOf fields for a Timespan.
	 * 
	 * @param isPartOf
	 *            A String array representing the isPartOf fields for a Timespan
	 */
	void setIsPartOf(String[] isPartOf);
	
	void setDctermsHasPart(String[] hasPart);
	
	String[] getDctermsHasPart();
	
	void setOwlSameAs(String[] owlSameAs);
	
	String[] getOwlSameAs();
	

	
	
}
