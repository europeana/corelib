
package eu.europeana.corelib.definitions.jibx;

import java.util.ArrayList;
import java.util.List;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://purl.org/dc/terms/" xmlns:ns1="http://purl.org/dc/elements/1.1/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="DCType">
 *   &lt;xs:complexContent>
 *     &lt;xs:extension base="ns:DCTermsType">
 *       &lt;xs:sequence>
 *         &lt;xs:choice minOccurs="0" maxOccurs="unbounded">
 *           &lt;!-- Reference to inner class Choice -->
 *         &lt;/xs:choice>
 *       &lt;/xs:sequence>
 *     &lt;/xs:extension>
 *   &lt;/xs:complexContent>
 * &lt;/xs:complexType>
 * </pre>
 */
public class DCType extends DCTermsType
{
    private List<Choice> choiceList1s = new ArrayList<Choice>();

    /** 
     * Get the list of choice items.
     * 
     * @return list
     */
    public List<Choice> getChoiceList1s() {
        return choiceList1s;
    }

    /** 
     * Set the list of choice items.
     * 
     * @param list
     */
    public void setChoiceList1s(List<Choice> list) {
        choiceList1s = list;
    }
    /** 
     * Schema fragment(s) for this class:
     * <pre>
     * &lt;xs:choice xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:ns1="http://purl.org/dc/elements/1.1/" xmlns:xs="http://www.w3.org/2001/XMLSchema" minOccurs="0" maxOccurs="unbounded">
     *   &lt;xs:element type="ns:ResourceOrLiteralType" name="contributor"/>
     *   &lt;xs:element type="ns:ResourceOrLiteralType" name="coverage"/>
     *   &lt;xs:element type="ns:ResourceOrLiteralType" name="creator"/>
     *   &lt;xs:element type="ns:ResourceOrLiteralType" name="date"/>
     *   &lt;xs:element type="ns:ResourceOrLiteralType" name="description"/>
     *   &lt;xs:element type="ns:LiteralType" name="identifier"/>
     *   &lt;xs:element type="ns:ResourceOrLiteralType" name="format"/>
     *   &lt;xs:element type="ns:LiteralType" name="language"/>
     *   &lt;xs:element type="ns:ResourceOrLiteralType" name="publisher"/>
     *   &lt;xs:element type="ns:ResourceOrLiteralType" name="relation"/>
     *   &lt;xs:element ref="ns1:rights"/>
     *   &lt;xs:element type="ns:ResourceOrLiteralType" name="source"/>
     *   &lt;xs:element type="ns:ResourceOrLiteralType" name="subject"/>
     *   &lt;xs:element type="ns:LiteralType" name="title"/>
     *   &lt;xs:element type="ns:ResourceOrLiteralType" name="type"/>
     * &lt;/xs:choice>
     * </pre>
     */
    public static class Choice
    {
        private int choiceList1Select = -1;
        private static final int CONTRIBUTOR_CHOICE = 0;
        private static final int COVERAGE_CHOICE = 1;
        private static final int CREATOR_CHOICE = 2;
        private static final int DATE_CHOICE = 3;
        private static final int DESCRIPTION_CHOICE = 4;
        private static final int IDENTIFIER_CHOICE = 5;
        private static final int FORMAT_CHOICE = 6;
        private static final int LANGUAGE_CHOICE = 7;
        private static final int PUBLISHER_CHOICE = 8;
        private static final int RELATION_CHOICE = 9;
        private static final int RIGHTS_CHOICE = 10;
        private static final int SOURCE_CHOICE = 11;
        private static final int SUBJECT_CHOICE = 12;
        private static final int TITLE_CHOICE = 13;
        private static final int TYPE_CHOICE = 14;
        private ResourceOrLiteralType contributor;
        private ResourceOrLiteralType coverage;
        private ResourceOrLiteralType creator;
        private ResourceOrLiteralType date;
        private ResourceOrLiteralType description;
        private LiteralType identifier;
        private ResourceOrLiteralType format;
        private LiteralType language;
        private ResourceOrLiteralType publisher;
        private ResourceOrLiteralType relation;
        private Rights1 rights;
        private ResourceOrLiteralType source;
        private ResourceOrLiteralType subject;
        private LiteralType title;
        private ResourceOrLiteralType type;

        private void setChoiceList1Select(int choice) {
            if (choiceList1Select == -1) {
                choiceList1Select = choice;
            } else if (choiceList1Select != choice) {
                throw new IllegalStateException(
                        "Need to call clearChoiceList1Select() before changing existing choice");
            }
        }

        /** 
         * Clear the choice selection.
         */
        public void clearChoiceList1Select() {
            choiceList1Select = -1;
        }

        /** 
         * Check if Contributor is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifContributor() {
            return choiceList1Select == CONTRIBUTOR_CHOICE;
        }

        /** 
         * Get the 'contributor' element value. 
        								An entity responsible for making contributions to the resource.
        								Example:
        								<contributor xmlns="http://www.w3.org/2001/XMLSchema">Maria Callas</contributor>
        								Type: String
        							
         * 
         * @return value
         */
        public ResourceOrLiteralType getContributor() {
            return contributor;
        }

        /** 
         * Set the 'contributor' element value. 
        								An entity responsible for making contributions to the resource.
        								Example:
        								<contributor xmlns="http://www.w3.org/2001/XMLSchema">Maria Callas</contributor>
        								Type: String
        							
         * 
         * @param contributor
         */
        public void setContributor(ResourceOrLiteralType contributor) {
            setChoiceList1Select(CONTRIBUTOR_CHOICE);
            this.contributor = contributor;
        }

        /** 
         * Check if Coverage is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifCoverage() {
            return choiceList1Select == COVERAGE_CHOICE;
        }

        /** 
         * Get the 'coverage' element value. 
        								The spatial or temporal topic of the resource, the spatial
        								applicability of the resource, or the jurisdiction under which
        								the resource is relevant. This may be a named place, a
        								location, a spatial coordinate, a period, date, date range or a
        								named administrative entity. Example:
        								<coverage xmlns="http://www.w3.org/2001/XMLSchema">1995-1996</coverage>
        								<coverage xmlns="http://www.w3.org/2001/XMLSchema">Boston, MA</coverage>
        								Type: String
        							
         * 
         * @return value
         */
        public ResourceOrLiteralType getCoverage() {
            return coverage;
        }

        /** 
         * Set the 'coverage' element value. 
        								The spatial or temporal topic of the resource, the spatial
        								applicability of the resource, or the jurisdiction under which
        								the resource is relevant. This may be a named place, a
        								location, a spatial coordinate, a period, date, date range or a
        								named administrative entity. Example:
        								<coverage xmlns="http://www.w3.org/2001/XMLSchema">1995-1996</coverage>
        								<coverage xmlns="http://www.w3.org/2001/XMLSchema">Boston, MA</coverage>
        								Type: String
        							
         * 
         * @param coverage
         */
        public void setCoverage(ResourceOrLiteralType coverage) {
            setChoiceList1Select(COVERAGE_CHOICE);
            this.coverage = coverage;
        }

        /** 
         * Check if Creator is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifCreator() {
            return choiceList1Select == CREATOR_CHOICE;
        }

        /** 
         * Get the 'creator' element value. 
        								An entity primarily responsible for making the resource. This
        								may be a person, organisation or a service. Example:
        								<creator xmlns="http://www.w3.org/2001/XMLSchema">Shakespeare, William</creator>
        								Type: String
        							
         * 
         * @return value
         */
        public ResourceOrLiteralType getCreator() {
            return creator;
        }

        /** 
         * Set the 'creator' element value. 
        								An entity primarily responsible for making the resource. This
        								may be a person, organisation or a service. Example:
        								<creator xmlns="http://www.w3.org/2001/XMLSchema">Shakespeare, William</creator>
        								Type: String
        							
         * 
         * @param creator
         */
        public void setCreator(ResourceOrLiteralType creator) {
            setChoiceList1Select(CREATOR_CHOICE);
            this.creator = creator;
        }

        /** 
         * Check if Date is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifDate() {
            return choiceList1Select == DATE_CHOICE;
        }

        /** 
         * Get the 'date' element value. 
        								A point or period of time associated with an event in the
        								lifecycle of the resource. Example:
        								<date xmlns="http://www.w3.org/2001/XMLSchema">17th century</date>
        								(For example the date when an object was repaired) Type: String
        							
         * 
         * @return value
         */
        public ResourceOrLiteralType getDate() {
            return date;
        }

        /** 
         * Set the 'date' element value. 
        								A point or period of time associated with an event in the
        								lifecycle of the resource. Example:
        								<date xmlns="http://www.w3.org/2001/XMLSchema">17th century</date>
        								(For example the date when an object was repaired) Type: String
        							
         * 
         * @param date
         */
        public void setDate(ResourceOrLiteralType date) {
            setChoiceList1Select(DATE_CHOICE);
            this.date = date;
        }

        /** 
         * Check if Description is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifDescription() {
            return choiceList1Select == DESCRIPTION_CHOICE;
        }

        /** 
         * Get the 'description' element value. 
        								An account of the resource. Example:
        								<description xmlns="http://www.w3.org/2001/XMLSchema">
        									Illustrated guide to airport markings and lighting signals, with
        									particular reference to SMGCS (Surface Movement Guidance and
        									Control System) for airports with low
        									visibility
        									conditions.
        								</description>
        								Type: String
        							
         * 
         * @return value
         */
        public ResourceOrLiteralType getDescription() {
            return description;
        }

        /** 
         * Set the 'description' element value. 
        								An account of the resource. Example:
        								<description xmlns="http://www.w3.org/2001/XMLSchema">
        									Illustrated guide to airport markings and lighting signals, with
        									particular reference to SMGCS (Surface Movement Guidance and
        									Control System) for airports with low
        									visibility
        									conditions.
        								</description>
        								Type: String
        							
         * 
         * @param description
         */
        public void setDescription(ResourceOrLiteralType description) {
            setChoiceList1Select(DESCRIPTION_CHOICE);
            this.description = description;
        }

        /** 
         * Check if Identifier is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifIdentifier() {
            return choiceList1Select == IDENTIFIER_CHOICE;
        }

        /** 
         * Get the 'identifier' element value. 
        								identifier
        							
         * 
         * @return value
         */
        public LiteralType getIdentifier() {
            return identifier;
        }

        /** 
         * Set the 'identifier' element value. 
        								identifier
        							
         * 
         * @param identifier
         */
        public void setIdentifier(LiteralType identifier) {
            setChoiceList1Select(IDENTIFIER_CHOICE);
            this.identifier = identifier;
        }

        /** 
         * Check if Format is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifFormat() {
            return choiceList1Select == FORMAT_CHOICE;
        }

        /** 
         * Get the 'format' element value. 
        								The file format, physical medium or dimensions of the resource.
        								Example:
        								<format xmlns="http://www.w3.org/2001/XMLSchema">image/jpeg</format>
        								Type: String
        							
         * 
         * @return value
         */
        public ResourceOrLiteralType getFormat() {
            return format;
        }

        /** 
         * Set the 'format' element value. 
        								The file format, physical medium or dimensions of the resource.
        								Example:
        								<format xmlns="http://www.w3.org/2001/XMLSchema">image/jpeg</format>
        								Type: String
        							
         * 
         * @param format
         */
        public void setFormat(ResourceOrLiteralType format) {
            setChoiceList1Select(FORMAT_CHOICE);
            this.format = format;
        }

        /** 
         * Check if Language is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifLanguage() {
            return choiceList1Select == LANGUAGE_CHOICE;
        }

        /** 
         * Get the 'language' element value. 
        								A language of the resource. Example:
        								<language xmlns="http://www.w3.org/2001/XMLSchema">it</language>
        								Type: String
        							
         * 
         * @return value
         */
        public LiteralType getLanguage() {
            return language;
        }

        /** 
         * Set the 'language' element value. 
        								A language of the resource. Example:
        								<language xmlns="http://www.w3.org/2001/XMLSchema">it</language>
        								Type: String
        							
         * 
         * @param language
         */
        public void setLanguage(LiteralType language) {
            setChoiceList1Select(LANGUAGE_CHOICE);
            this.language = language;
        }

        /** 
         * Check if Publisher is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifPublisher() {
            return choiceList1Select == PUBLISHER_CHOICE;
        }

        /** 
         * Get the 'publisher' element value. 
        								An entity responsible for making the resource available.
        								Examples of a publisher include a person, an organisation and a
        								service. Example:
        								<publisher xmlns="http://www.w3.org/2001/XMLSchema">Oxford University Press</publisher>
        								Type: String
        								
         * 
         * @return value
         */
        public ResourceOrLiteralType getPublisher() {
            return publisher;
        }

        /** 
         * Set the 'publisher' element value. 
        								An entity responsible for making the resource available.
        								Examples of a publisher include a person, an organisation and a
        								service. Example:
        								<publisher xmlns="http://www.w3.org/2001/XMLSchema">Oxford University Press</publisher>
        								Type: String
        								
         * 
         * @param publisher
         */
        public void setPublisher(ResourceOrLiteralType publisher) {
            setChoiceList1Select(PUBLISHER_CHOICE);
            this.publisher = publisher;
        }

        /** 
         * Check if Relation is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifRelation() {
            return choiceList1Select == RELATION_CHOICE;
        }

        /** 
         * Get the 'relation' element value. 
        								A related resource. The recommended best practice is to
        								identify the resource using a formal identification scheme.
        								Example:
        								<relation xmlns="http://www.w3.org/2001/XMLSchema">maps.crace.1/33</relation>
        								(This is the shelf mark for a map held in the British Library's
        								Crace Collection). Type: String
        							
         * 
         * @return value
         */
        public ResourceOrLiteralType getRelation() {
            return relation;
        }

        /** 
         * Set the 'relation' element value. 
        								A related resource. The recommended best practice is to
        								identify the resource using a formal identification scheme.
        								Example:
        								<relation xmlns="http://www.w3.org/2001/XMLSchema">maps.crace.1/33</relation>
        								(This is the shelf mark for a map held in the British Library's
        								Crace Collection). Type: String
        							
         * 
         * @param relation
         */
        public void setRelation(ResourceOrLiteralType relation) {
            setChoiceList1Select(RELATION_CHOICE);
            this.relation = relation;
        }

        /** 
         * Check if Rights is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifRights() {
            return choiceList1Select == RIGHTS_CHOICE;
        }

        /** 
         * Get the 'rights' element value.
         * 
         * @return value
         */
        public Rights1 getRights() {
            return rights;
        }

        /** 
         * Set the 'rights' element value.
         * 
         * @param rights
         */
        public void setRights(Rights1 rights) {
            setChoiceList1Select(RIGHTS_CHOICE);
            this.rights = rights;
        }

        /** 
         * Check if Source is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifSource() {
            return choiceList1Select == SOURCE_CHOICE;
        }

        /** 
         * Get the 'source' element value. 
        								A related resource from which the described resource is derived
        								in whole or in part. Example:
        								<source xmlns="http://www.w3.org/2001/XMLSchema">Security Magazine pp 3-12</source>
        								<source xmlns="http://www.w3.org/2001/XMLSchema">BAM portal</source>
        								Type: String
        							
         * 
         * @return value
         */
        public ResourceOrLiteralType getSource() {
            return source;
        }

        /** 
         * Set the 'source' element value. 
        								A related resource from which the described resource is derived
        								in whole or in part. Example:
        								<source xmlns="http://www.w3.org/2001/XMLSchema">Security Magazine pp 3-12</source>
        								<source xmlns="http://www.w3.org/2001/XMLSchema">BAM portal</source>
        								Type: String
        							
         * 
         * @param source
         */
        public void setSource(ResourceOrLiteralType source) {
            setChoiceList1Select(SOURCE_CHOICE);
            this.source = source;
        }

        /** 
         * Check if Subject is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifSubject() {
            return choiceList1Select == SUBJECT_CHOICE;
        }

        /** 
         * Get the 'subject' element value. 
        								The topic of the resource. Example:
        								<subject xmlns="http://www.w3.org/2001/XMLSchema">submarine</subject>
        								Type: String
        							
         * 
         * @return value
         */
        public ResourceOrLiteralType getSubject() {
            return subject;
        }

        /** 
         * Set the 'subject' element value. 
        								The topic of the resource. Example:
        								<subject xmlns="http://www.w3.org/2001/XMLSchema">submarine</subject>
        								Type: String
        							
         * 
         * @param subject
         */
        public void setSubject(ResourceOrLiteralType subject) {
            setChoiceList1Select(SUBJECT_CHOICE);
            this.subject = subject;
        }

        /** 
         * Check if Title is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifTitle() {
            return choiceList1Select == TITLE_CHOICE;
        }

        /** 
         * Get the 'title' element value. 
        								A name given to the resource. Typically, a Title will be a name
        								by which the resource is formally known. Example:
        								<title xmlns="http://www.w3.org/2001/XMLSchema">Taal vitaal</title>
        								Type: String
        							
         * 
         * @return value
         */
        public LiteralType getTitle() {
            return title;
        }

        /** 
         * Set the 'title' element value. 
        								A name given to the resource. Typically, a Title will be a name
        								by which the resource is formally known. Example:
        								<title xmlns="http://www.w3.org/2001/XMLSchema">Taal vitaal</title>
        								Type: String
        							
         * 
         * @param title
         */
        public void setTitle(LiteralType title) {
            setChoiceList1Select(TITLE_CHOICE);
            this.title = title;
        }

        /** 
         * Check if Type is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifType() {
            return choiceList1Select == TYPE_CHOICE;
        }

        /** 
         * Get the 'type' element value. 
        								The nature or genre of the resource. Type includes terms
        								describing general categories, functions, genres, or
        								aggregation levels for content. Example:
        								<type xmlns="http://www.w3.org/2001/XMLSchema">painting</type>
        								<type xmlns="http://www.w3.org/2001/XMLSchema">photograph</type>
        								<type xmlns="http://www.w3.org/2001/XMLSchema">coin</type>
        								Type: String
        							
         * 
         * @return value
         */
        public ResourceOrLiteralType getType() {
            return type;
        }

        /** 
         * Set the 'type' element value. 
        								The nature or genre of the resource. Type includes terms
        								describing general categories, functions, genres, or
        								aggregation levels for content. Example:
        								<type xmlns="http://www.w3.org/2001/XMLSchema">painting</type>
        								<type xmlns="http://www.w3.org/2001/XMLSchema">photograph</type>
        								<type xmlns="http://www.w3.org/2001/XMLSchema">coin</type>
        								Type: String
        							
         * 
         * @param type
         */
        public void setType(ResourceOrLiteralType type) {
            setChoiceList1Select(TYPE_CHOICE);
            this.type = type;
        }
    }
}
