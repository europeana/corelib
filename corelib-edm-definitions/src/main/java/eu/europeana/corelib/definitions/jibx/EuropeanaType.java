
package eu.europeana.corelib.definitions.jibx;

import java.util.ArrayList;
import java.util.List;

/** 
 *  EuropeanaType contains the DC &amp; DCTERMS elements. 
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:ns1="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="EuropeanaType">
 *   &lt;xs:complexContent>
 *     &lt;xs:extension base="ns:AboutType">
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
public class EuropeanaType extends AboutType
{
    private List<Choice> choiceList = new ArrayList<Choice>();

    /** 
     * Get the list of choice items.
     * 
     * @return list
     */
    public List<Choice> getChoiceList() {
        return choiceList;
    }

    /** 
     * Set the list of choice items.
     * 
     * @param list
     */
    public void setChoiceList(List<Choice> list) {
        choiceList = list;
    }
    /** 
     * Schema fragment(s) for this class:
     * <pre>
     * &lt;xs:choice xmlns:ns="http://purl.org/dc/terms/" xmlns:ns1="http://purl.org/dc/elements/1.1/" xmlns:xs="http://www.w3.org/2001/XMLSchema" minOccurs="0" maxOccurs="unbounded">
     *   &lt;xs:element ref="ns1:contributor"/>
     *   &lt;xs:element ref="ns1:coverage"/>
     *   &lt;xs:element ref="ns1:creator"/>
     *   &lt;xs:element ref="ns1:date"/>
     *   &lt;xs:element ref="ns1:description"/>
     *   &lt;xs:element ref="ns1:format"/>
     *   &lt;xs:element ref="ns1:identifier"/>
     *   &lt;xs:element ref="ns1:language"/>
     *   &lt;xs:element ref="ns1:publisher"/>
     *   &lt;xs:element ref="ns1:relation"/>
     *   &lt;xs:element ref="ns1:rights"/>
     *   &lt;xs:element ref="ns1:source"/>
     *   &lt;xs:element ref="ns1:subject"/>
     *   &lt;xs:element ref="ns1:title"/>
     *   &lt;xs:element ref="ns1:type"/>
     *   &lt;xs:element ref="ns:alternative"/>
     *   &lt;xs:element ref="ns:conformsTo"/>
     *   &lt;xs:element ref="ns:created"/>
     *   &lt;xs:element ref="ns:extent"/>
     *   &lt;xs:element ref="ns:hasFormat"/>
     *   &lt;xs:element ref="ns:hasPart"/>
     *   &lt;xs:element ref="ns:hasVersion"/>
     *   &lt;xs:element ref="ns:isFormatOf"/>
     *   &lt;xs:element ref="ns:isPartOf"/>
     *   &lt;xs:element ref="ns:isReferencedBy"/>
     *   &lt;xs:element ref="ns:isReplacedBy"/>
     *   &lt;xs:element ref="ns:isRequiredBy"/>
     *   &lt;xs:element ref="ns:issued"/>
     *   &lt;xs:element ref="ns:isVersionOf"/>
     *   &lt;xs:element ref="ns:medium"/>
     *   &lt;xs:element ref="ns:provenance"/>
     *   &lt;xs:element ref="ns:references"/>
     *   &lt;xs:element ref="ns:replaces"/>
     *   &lt;xs:element ref="ns:requires"/>
     *   &lt;xs:element ref="ns:spatial"/>
     *   &lt;xs:element ref="ns:tableOfContents"/>
     *   &lt;xs:element ref="ns:temporal"/>
     * &lt;/xs:choice>
     * </pre>
     */
    public static class Choice
    {
        private int choiceListSelect = -1;
        private static final int CONTRIBUTOR_CHOICE = 0;
        private static final int COVERAGE_CHOICE = 1;
        private static final int CREATOR_CHOICE = 2;
        private static final int DATE_CHOICE = 3;
        private static final int DESCRIPTION_CHOICE = 4;
        private static final int FORMAT_CHOICE = 5;
        private static final int IDENTIFIER_CHOICE = 6;
        private static final int LANGUAGE_CHOICE = 7;
        private static final int PUBLISHER_CHOICE = 8;
        private static final int RELATION_CHOICE = 9;
        private static final int RIGHTS_CHOICE = 10;
        private static final int SOURCE_CHOICE = 11;
        private static final int SUBJECT_CHOICE = 12;
        private static final int TITLE_CHOICE = 13;
        private static final int TYPE_CHOICE = 14;
        private static final int ALTERNATIVE_CHOICE = 15;
        private static final int CONFORMS_TO_CHOICE = 16;
        private static final int CREATED_CHOICE = 17;
        private static final int EXTENT_CHOICE = 18;
        private static final int HAS_FORMAT_CHOICE = 19;
        private static final int HAS_PART_CHOICE = 20;
        private static final int HAS_VERSION_CHOICE = 21;
        private static final int IS_FORMAT_OF_CHOICE = 22;
        private static final int IS_PART_OF_CHOICE = 23;
        private static final int IS_REFERENCED_BY_CHOICE = 24;
        private static final int IS_REPLACED_BY_CHOICE = 25;
        private static final int IS_REQUIRED_BY_CHOICE = 26;
        private static final int ISSUED_CHOICE = 27;
        private static final int IS_VERSION_OF_CHOICE = 28;
        private static final int MEDIUM_CHOICE = 29;
        private static final int PROVENANCE_CHOICE = 30;
        private static final int REFERENCES_CHOICE = 31;
        private static final int REPLACES_CHOICE = 32;
        private static final int REQUIRES_CHOICE = 33;
        private static final int SPATIAL_CHOICE = 34;
        private static final int TABLE_OF_CONTENTS_CHOICE = 35;
        private static final int TEMPORAL_CHOICE = 36;
        private Contributor contributor;
        private Coverage coverage;
        private Creator creator;
        private Date date;
        private Description description;
        private Format format;
        private Identifier identifier;
        private Language language;
        private Publisher publisher;
        private Relation relation;
        private Rights rights;
        private Source source;
        private Subject subject;
        private Title title;
        private Type type;
        private Alternative alternative;
        private ConformsTo conformsTo;
        private Created created;
        private Extent extent;
        private HasFormat hasFormat;
        private HasPart hasPart;
        private HasVersion hasVersion;
        private IsFormatOf isFormatOf;
        private IsPartOf isPartOf;
        private IsReferencedBy isReferencedBy;
        private IsReplacedBy isReplacedBy;
        private IsRequiredBy isRequiredBy;
        private Issued issued;
        private IsVersionOf isVersionOf;
        private Medium medium;
        private Provenance provenance;
        private References references;
        private Replaces replaces;
        private Requires requires;
        private Spatial spatial;
        private TableOfContents tableOfContents;
        private Temporal temporal;

        private void setChoiceListSelect(int choice) {
            if (choiceListSelect == -1) {
                choiceListSelect = choice;
            } else if (choiceListSelect != choice) {
                throw new IllegalStateException(
                        "Need to call clearChoiceListSelect() before changing existing choice");
            }
        }

        /** 
         * Clear the choice selection.
         */
        public void clearChoiceListSelect() {
            choiceListSelect = -1;
        }

        /** 
         * Check if Contributor is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifContributor() {
            return choiceListSelect == CONTRIBUTOR_CHOICE;
        }

        /** 
         * Get the 'contributor' element value.
         * 
         * @return value
         */
        public Contributor getContributor() {
            return contributor;
        }

        /** 
         * Set the 'contributor' element value.
         * 
         * @param contributor
         */
        public void setContributor(Contributor contributor) {
            setChoiceListSelect(CONTRIBUTOR_CHOICE);
            this.contributor = contributor;
        }

        /** 
         * Check if Coverage is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifCoverage() {
            return choiceListSelect == COVERAGE_CHOICE;
        }

        /** 
         * Get the 'coverage' element value.
         * 
         * @return value
         */
        public Coverage getCoverage() {
            return coverage;
        }

        /** 
         * Set the 'coverage' element value.
         * 
         * @param coverage
         */
        public void setCoverage(Coverage coverage) {
            setChoiceListSelect(COVERAGE_CHOICE);
            this.coverage = coverage;
        }

        /** 
         * Check if Creator is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifCreator() {
            return choiceListSelect == CREATOR_CHOICE;
        }

        /** 
         * Get the 'creator' element value.
         * 
         * @return value
         */
        public Creator getCreator() {
            return creator;
        }

        /** 
         * Set the 'creator' element value.
         * 
         * @param creator
         */
        public void setCreator(Creator creator) {
            setChoiceListSelect(CREATOR_CHOICE);
            this.creator = creator;
        }

        /** 
         * Check if Date is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifDate() {
            return choiceListSelect == DATE_CHOICE;
        }

        /** 
         * Get the 'date' element value.
         * 
         * @return value
         */
        public Date getDate() {
            return date;
        }

        /** 
         * Set the 'date' element value.
         * 
         * @param date
         */
        public void setDate(Date date) {
            setChoiceListSelect(DATE_CHOICE);
            this.date = date;
        }

        /** 
         * Check if Description is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifDescription() {
            return choiceListSelect == DESCRIPTION_CHOICE;
        }

        /** 
         * Get the 'description' element value.
         * 
         * @return value
         */
        public Description getDescription() {
            return description;
        }

        /** 
         * Set the 'description' element value.
         * 
         * @param description
         */
        public void setDescription(Description description) {
            setChoiceListSelect(DESCRIPTION_CHOICE);
            this.description = description;
        }

        /** 
         * Check if Format is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifFormat() {
            return choiceListSelect == FORMAT_CHOICE;
        }

        /** 
         * Get the 'format' element value.
         * 
         * @return value
         */
        public Format getFormat() {
            return format;
        }

        /** 
         * Set the 'format' element value.
         * 
         * @param format
         */
        public void setFormat(Format format) {
            setChoiceListSelect(FORMAT_CHOICE);
            this.format = format;
        }

        /** 
         * Check if Identifier is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifIdentifier() {
            return choiceListSelect == IDENTIFIER_CHOICE;
        }

        /** 
         * Get the 'identifier' element value.
         * 
         * @return value
         */
        public Identifier getIdentifier() {
            return identifier;
        }

        /** 
         * Set the 'identifier' element value.
         * 
         * @param identifier
         */
        public void setIdentifier(Identifier identifier) {
            setChoiceListSelect(IDENTIFIER_CHOICE);
            this.identifier = identifier;
        }

        /** 
         * Check if Language is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifLanguage() {
            return choiceListSelect == LANGUAGE_CHOICE;
        }

        /** 
         * Get the 'language' element value.
         * 
         * @return value
         */
        public Language getLanguage() {
            return language;
        }

        /** 
         * Set the 'language' element value.
         * 
         * @param language
         */
        public void setLanguage(Language language) {
            setChoiceListSelect(LANGUAGE_CHOICE);
            this.language = language;
        }

        /** 
         * Check if Publisher is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifPublisher() {
            return choiceListSelect == PUBLISHER_CHOICE;
        }

        /** 
         * Get the 'publisher' element value.
         * 
         * @return value
         */
        public Publisher getPublisher() {
            return publisher;
        }

        /** 
         * Set the 'publisher' element value.
         * 
         * @param publisher
         */
        public void setPublisher(Publisher publisher) {
            setChoiceListSelect(PUBLISHER_CHOICE);
            this.publisher = publisher;
        }

        /** 
         * Check if Relation is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifRelation() {
            return choiceListSelect == RELATION_CHOICE;
        }

        /** 
         * Get the 'relation' element value.
         * 
         * @return value
         */
        public Relation getRelation() {
            return relation;
        }

        /** 
         * Set the 'relation' element value.
         * 
         * @param relation
         */
        public void setRelation(Relation relation) {
            setChoiceListSelect(RELATION_CHOICE);
            this.relation = relation;
        }

        /** 
         * Check if Rights is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifRights() {
            return choiceListSelect == RIGHTS_CHOICE;
        }

        /** 
         * Get the 'rights' element value.
         * 
         * @return value
         */
        public Rights getRights() {
            return rights;
        }

        /** 
         * Set the 'rights' element value.
         * 
         * @param rights
         */
        public void setRights(Rights rights) {
            setChoiceListSelect(RIGHTS_CHOICE);
            this.rights = rights;
        }

        /** 
         * Check if Source is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifSource() {
            return choiceListSelect == SOURCE_CHOICE;
        }

        /** 
         * Get the 'source' element value.
         * 
         * @return value
         */
        public Source getSource() {
            return source;
        }

        /** 
         * Set the 'source' element value.
         * 
         * @param source
         */
        public void setSource(Source source) {
            setChoiceListSelect(SOURCE_CHOICE);
            this.source = source;
        }

        /** 
         * Check if Subject is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifSubject() {
            return choiceListSelect == SUBJECT_CHOICE;
        }

        /** 
         * Get the 'subject' element value.
         * 
         * @return value
         */
        public Subject getSubject() {
            return subject;
        }

        /** 
         * Set the 'subject' element value.
         * 
         * @param subject
         */
        public void setSubject(Subject subject) {
            setChoiceListSelect(SUBJECT_CHOICE);
            this.subject = subject;
        }

        /** 
         * Check if Title is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifTitle() {
            return choiceListSelect == TITLE_CHOICE;
        }

        /** 
         * Get the 'title' element value.
         * 
         * @return value
         */
        public Title getTitle() {
            return title;
        }

        /** 
         * Set the 'title' element value.
         * 
         * @param title
         */
        public void setTitle(Title title) {
            setChoiceListSelect(TITLE_CHOICE);
            this.title = title;
        }

        /** 
         * Check if Type is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifType() {
            return choiceListSelect == TYPE_CHOICE;
        }

        /** 
         * Get the 'type' element value.
         * 
         * @return value
         */
        public Type getType() {
            return type;
        }

        /** 
         * Set the 'type' element value.
         * 
         * @param type
         */
        public void setType(Type type) {
            setChoiceListSelect(TYPE_CHOICE);
            this.type = type;
        }

        /** 
         * Check if Alternative is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifAlternative() {
            return choiceListSelect == ALTERNATIVE_CHOICE;
        }

        /** 
         * Get the 'alternative' element value.
         * 
         * @return value
         */
        public Alternative getAlternative() {
            return alternative;
        }

        /** 
         * Set the 'alternative' element value.
         * 
         * @param alternative
         */
        public void setAlternative(Alternative alternative) {
            setChoiceListSelect(ALTERNATIVE_CHOICE);
            this.alternative = alternative;
        }

        /** 
         * Check if ConformsTo is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifConformsTo() {
            return choiceListSelect == CONFORMS_TO_CHOICE;
        }

        /** 
         * Get the 'conformsTo' element value.
         * 
         * @return value
         */
        public ConformsTo getConformsTo() {
            return conformsTo;
        }

        /** 
         * Set the 'conformsTo' element value.
         * 
         * @param conformsTo
         */
        public void setConformsTo(ConformsTo conformsTo) {
            setChoiceListSelect(CONFORMS_TO_CHOICE);
            this.conformsTo = conformsTo;
        }

        /** 
         * Check if Created is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifCreated() {
            return choiceListSelect == CREATED_CHOICE;
        }

        /** 
         * Get the 'created' element value.
         * 
         * @return value
         */
        public Created getCreated() {
            return created;
        }

        /** 
         * Set the 'created' element value.
         * 
         * @param created
         */
        public void setCreated(Created created) {
            setChoiceListSelect(CREATED_CHOICE);
            this.created = created;
        }

        /** 
         * Check if Extent is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifExtent() {
            return choiceListSelect == EXTENT_CHOICE;
        }

        /** 
         * Get the 'extent' element value.
         * 
         * @return value
         */
        public Extent getExtent() {
            return extent;
        }

        /** 
         * Set the 'extent' element value.
         * 
         * @param extent
         */
        public void setExtent(Extent extent) {
            setChoiceListSelect(EXTENT_CHOICE);
            this.extent = extent;
        }

        /** 
         * Check if HasFormat is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifHasFormat() {
            return choiceListSelect == HAS_FORMAT_CHOICE;
        }

        /** 
         * Get the 'hasFormat' element value.
         * 
         * @return value
         */
        public HasFormat getHasFormat() {
            return hasFormat;
        }

        /** 
         * Set the 'hasFormat' element value.
         * 
         * @param hasFormat
         */
        public void setHasFormat(HasFormat hasFormat) {
            setChoiceListSelect(HAS_FORMAT_CHOICE);
            this.hasFormat = hasFormat;
        }

        /** 
         * Check if HasPart is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifHasPart() {
            return choiceListSelect == HAS_PART_CHOICE;
        }

        /** 
         * Get the 'hasPart' element value.
         * 
         * @return value
         */
        public HasPart getHasPart() {
            return hasPart;
        }

        /** 
         * Set the 'hasPart' element value.
         * 
         * @param hasPart
         */
        public void setHasPart(HasPart hasPart) {
            setChoiceListSelect(HAS_PART_CHOICE);
            this.hasPart = hasPart;
        }

        /** 
         * Check if HasVersion is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifHasVersion() {
            return choiceListSelect == HAS_VERSION_CHOICE;
        }

        /** 
         * Get the 'hasVersion' element value.
         * 
         * @return value
         */
        public HasVersion getHasVersion() {
            return hasVersion;
        }

        /** 
         * Set the 'hasVersion' element value.
         * 
         * @param hasVersion
         */
        public void setHasVersion(HasVersion hasVersion) {
            setChoiceListSelect(HAS_VERSION_CHOICE);
            this.hasVersion = hasVersion;
        }

        /** 
         * Check if IsFormatOf is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifIsFormatOf() {
            return choiceListSelect == IS_FORMAT_OF_CHOICE;
        }

        /** 
         * Get the 'isFormatOf' element value.
         * 
         * @return value
         */
        public IsFormatOf getIsFormatOf() {
            return isFormatOf;
        }

        /** 
         * Set the 'isFormatOf' element value.
         * 
         * @param isFormatOf
         */
        public void setIsFormatOf(IsFormatOf isFormatOf) {
            setChoiceListSelect(IS_FORMAT_OF_CHOICE);
            this.isFormatOf = isFormatOf;
        }

        /** 
         * Check if IsPartOf is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifIsPartOf() {
            return choiceListSelect == IS_PART_OF_CHOICE;
        }

        /** 
         * Get the 'isPartOf' element value.
         * 
         * @return value
         */
        public IsPartOf getIsPartOf() {
            return isPartOf;
        }

        /** 
         * Set the 'isPartOf' element value.
         * 
         * @param isPartOf
         */
        public void setIsPartOf(IsPartOf isPartOf) {
            setChoiceListSelect(IS_PART_OF_CHOICE);
            this.isPartOf = isPartOf;
        }

        /** 
         * Check if IsReferencedBy is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifIsReferencedBy() {
            return choiceListSelect == IS_REFERENCED_BY_CHOICE;
        }

        /** 
         * Get the 'isReferencedBy' element value.
         * 
         * @return value
         */
        public IsReferencedBy getIsReferencedBy() {
            return isReferencedBy;
        }

        /** 
         * Set the 'isReferencedBy' element value.
         * 
         * @param isReferencedBy
         */
        public void setIsReferencedBy(IsReferencedBy isReferencedBy) {
            setChoiceListSelect(IS_REFERENCED_BY_CHOICE);
            this.isReferencedBy = isReferencedBy;
        }

        /** 
         * Check if IsReplacedBy is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifIsReplacedBy() {
            return choiceListSelect == IS_REPLACED_BY_CHOICE;
        }

        /** 
         * Get the 'isReplacedBy' element value.
         * 
         * @return value
         */
        public IsReplacedBy getIsReplacedBy() {
            return isReplacedBy;
        }

        /** 
         * Set the 'isReplacedBy' element value.
         * 
         * @param isReplacedBy
         */
        public void setIsReplacedBy(IsReplacedBy isReplacedBy) {
            setChoiceListSelect(IS_REPLACED_BY_CHOICE);
            this.isReplacedBy = isReplacedBy;
        }

        /** 
         * Check if IsRequiredBy is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifIsRequiredBy() {
            return choiceListSelect == IS_REQUIRED_BY_CHOICE;
        }

        /** 
         * Get the 'isRequiredBy' element value.
         * 
         * @return value
         */
        public IsRequiredBy getIsRequiredBy() {
            return isRequiredBy;
        }

        /** 
         * Set the 'isRequiredBy' element value.
         * 
         * @param isRequiredBy
         */
        public void setIsRequiredBy(IsRequiredBy isRequiredBy) {
            setChoiceListSelect(IS_REQUIRED_BY_CHOICE);
            this.isRequiredBy = isRequiredBy;
        }

        /** 
         * Check if Issued is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifIssued() {
            return choiceListSelect == ISSUED_CHOICE;
        }

        /** 
         * Get the 'issued' element value.
         * 
         * @return value
         */
        public Issued getIssued() {
            return issued;
        }

        /** 
         * Set the 'issued' element value.
         * 
         * @param issued
         */
        public void setIssued(Issued issued) {
            setChoiceListSelect(ISSUED_CHOICE);
            this.issued = issued;
        }

        /** 
         * Check if IsVersionOf is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifIsVersionOf() {
            return choiceListSelect == IS_VERSION_OF_CHOICE;
        }

        /** 
         * Get the 'isVersionOf' element value.
         * 
         * @return value
         */
        public IsVersionOf getIsVersionOf() {
            return isVersionOf;
        }

        /** 
         * Set the 'isVersionOf' element value.
         * 
         * @param isVersionOf
         */
        public void setIsVersionOf(IsVersionOf isVersionOf) {
            setChoiceListSelect(IS_VERSION_OF_CHOICE);
            this.isVersionOf = isVersionOf;
        }

        /** 
         * Check if Medium is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifMedium() {
            return choiceListSelect == MEDIUM_CHOICE;
        }

        /** 
         * Get the 'medium' element value.
         * 
         * @return value
         */
        public Medium getMedium() {
            return medium;
        }

        /** 
         * Set the 'medium' element value.
         * 
         * @param medium
         */
        public void setMedium(Medium medium) {
            setChoiceListSelect(MEDIUM_CHOICE);
            this.medium = medium;
        }

        /** 
         * Check if Provenance is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifProvenance() {
            return choiceListSelect == PROVENANCE_CHOICE;
        }

        /** 
         * Get the 'provenance' element value.
         * 
         * @return value
         */
        public Provenance getProvenance() {
            return provenance;
        }

        /** 
         * Set the 'provenance' element value.
         * 
         * @param provenance
         */
        public void setProvenance(Provenance provenance) {
            setChoiceListSelect(PROVENANCE_CHOICE);
            this.provenance = provenance;
        }

        /** 
         * Check if References is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifReferences() {
            return choiceListSelect == REFERENCES_CHOICE;
        }

        /** 
         * Get the 'references' element value.
         * 
         * @return value
         */
        public References getReferences() {
            return references;
        }

        /** 
         * Set the 'references' element value.
         * 
         * @param references
         */
        public void setReferences(References references) {
            setChoiceListSelect(REFERENCES_CHOICE);
            this.references = references;
        }

        /** 
         * Check if Replaces is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifReplaces() {
            return choiceListSelect == REPLACES_CHOICE;
        }

        /** 
         * Get the 'replaces' element value.
         * 
         * @return value
         */
        public Replaces getReplaces() {
            return replaces;
        }

        /** 
         * Set the 'replaces' element value.
         * 
         * @param replaces
         */
        public void setReplaces(Replaces replaces) {
            setChoiceListSelect(REPLACES_CHOICE);
            this.replaces = replaces;
        }

        /** 
         * Check if Requires is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifRequires() {
            return choiceListSelect == REQUIRES_CHOICE;
        }

        /** 
         * Get the 'requires' element value.
         * 
         * @return value
         */
        public Requires getRequires() {
            return requires;
        }

        /** 
         * Set the 'requires' element value.
         * 
         * @param requires
         */
        public void setRequires(Requires requires) {
            setChoiceListSelect(REQUIRES_CHOICE);
            this.requires = requires;
        }

        /** 
         * Check if Spatial is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifSpatial() {
            return choiceListSelect == SPATIAL_CHOICE;
        }

        /** 
         * Get the 'spatial' element value.
         * 
         * @return value
         */
        public Spatial getSpatial() {
            return spatial;
        }

        /** 
         * Set the 'spatial' element value.
         * 
         * @param spatial
         */
        public void setSpatial(Spatial spatial) {
            setChoiceListSelect(SPATIAL_CHOICE);
            this.spatial = spatial;
        }

        /** 
         * Check if TableOfContents is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifTableOfContents() {
            return choiceListSelect == TABLE_OF_CONTENTS_CHOICE;
        }

        /** 
         * Get the 'tableOfContents' element value.
         * 
         * @return value
         */
        public TableOfContents getTableOfContents() {
            return tableOfContents;
        }

        /** 
         * Set the 'tableOfContents' element value.
         * 
         * @param tableOfContents
         */
        public void setTableOfContents(TableOfContents tableOfContents) {
            setChoiceListSelect(TABLE_OF_CONTENTS_CHOICE);
            this.tableOfContents = tableOfContents;
        }

        /** 
         * Check if Temporal is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifTemporal() {
            return choiceListSelect == TEMPORAL_CHOICE;
        }

        /** 
         * Get the 'temporal' element value.
         * 
         * @return value
         */
        public Temporal getTemporal() {
            return temporal;
        }

        /** 
         * Set the 'temporal' element value.
         * 
         * @param temporal
         */
        public void setTemporal(Temporal temporal) {
            setChoiceListSelect(TEMPORAL_CHOICE);
            this.temporal = temporal;
        }
    }
}
