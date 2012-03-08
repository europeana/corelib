
package eu.europeana.corelib.definitions.jibx;

import java.util.ArrayList;
import java.util.List;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:ns1="http://purl.org/dc/terms/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="DCTermsType">
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
public class DCTermsType extends AboutType
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
     * &lt;xs:choice xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:ns1="http://purl.org/dc/terms/" xmlns:xs="http://www.w3.org/2001/XMLSchema" minOccurs="0" maxOccurs="unbounded">
     *   &lt;xs:element type="ns:LiteralType" name="alternative"/>
     *   &lt;xs:element type="ns:ResourceOrLiteralType" name="conformsTo"/>
     *   &lt;xs:element type="ns:ResourceOrLiteralType" name="created"/>
     *   &lt;xs:element type="ns:ResourceOrLiteralType" name="extent"/>
     *   &lt;xs:element type="ns:ResourceOrLiteralType" name="hasFormat"/>
     *   &lt;xs:element type="ns:ResourceOrLiteralType" name="hasPart"/>
     *   &lt;xs:element type="ns:ResourceOrLiteralType" name="hasVersion"/>
     *   &lt;xs:element type="ns:ResourceOrLiteralType" name="isFormatOf"/>
     *   &lt;xs:element ref="ns1:isPartOf"/>
     *   &lt;xs:element type="ns:ResourceOrLiteralType" name="isReferencedBy"/>
     *   &lt;xs:element type="ns:ResourceOrLiteralType" name="isReplacedBy"/>
     *   &lt;xs:element type="ns:ResourceOrLiteralType" name="isRequiredBy"/>
     *   &lt;xs:element type="ns:ResourceOrLiteralType" name="issued"/>
     *   &lt;xs:element type="ns:ResourceOrLiteralType" name="isVersionOf"/>
     *   &lt;xs:element type="ns:ResourceOrLiteralType" name="medium"/>
     *   &lt;xs:element type="ns:ResourceOrLiteralType" name="provenance"/>
     *   &lt;xs:element type="ns:ResourceOrLiteralType" name="references"/>
     *   &lt;xs:element type="ns:ResourceOrLiteralType" name="replaces"/>
     *   &lt;xs:element type="ns:ResourceOrLiteralType" name="requires"/>
     *   &lt;xs:element type="ns:ResourceOrLiteralType" name="spatial"/>
     *   &lt;xs:element type="ns:ResourceOrLiteralType" name="tableOfContents"/>
     *   &lt;xs:element type="ns:ResourceOrLiteralType" name="temporal"/>
     * &lt;/xs:choice>
     * </pre>
     */
    public static class Choice
    {
        private int choiceListSelect = -1;
        private static final int ALTERNATIVE_CHOICE = 0;
        private static final int CONFORMS_TO_CHOICE = 1;
        private static final int CREATED_CHOICE = 2;
        private static final int EXTENT_CHOICE = 3;
        private static final int HAS_FORMAT_CHOICE = 4;
        private static final int HAS_PART_CHOICE = 5;
        private static final int HAS_VERSION_CHOICE = 6;
        private static final int IS_FORMAT_OF_CHOICE = 7;
        private static final int IS_PART_OF_CHOICE = 8;
        private static final int IS_REFERENCED_BY_CHOICE = 9;
        private static final int IS_REPLACED_BY_CHOICE = 10;
        private static final int IS_REQUIRED_BY_CHOICE = 11;
        private static final int ISSUED_CHOICE = 12;
        private static final int IS_VERSION_OF_CHOICE = 13;
        private static final int MEDIUM_CHOICE = 14;
        private static final int PROVENANCE_CHOICE = 15;
        private static final int REFERENCES_CHOICE = 16;
        private static final int REPLACES_CHOICE = 17;
        private static final int REQUIRES_CHOICE = 18;
        private static final int SPATIAL_CHOICE = 19;
        private static final int TABLE_OF_CONTENTS_CHOICE = 20;
        private static final int TEMPORAL_CHOICE = 21;
        private LiteralType alternative;
        private ResourceOrLiteralType conformsTo;
        private ResourceOrLiteralType created;
        private ResourceOrLiteralType extent;
        private ResourceOrLiteralType hasFormat;
        private ResourceOrLiteralType hasPart;
        private ResourceOrLiteralType hasVersion;
        private ResourceOrLiteralType isFormatOf;
        private IsPartOf isPartOf;
        private ResourceOrLiteralType isReferencedBy;
        private ResourceOrLiteralType isReplacedBy;
        private ResourceOrLiteralType isRequiredBy;
        private ResourceOrLiteralType issued;
        private ResourceOrLiteralType isVersionOf;
        private ResourceOrLiteralType medium;
        private ResourceOrLiteralType provenance;
        private ResourceOrLiteralType references;
        private ResourceOrLiteralType replaces;
        private ResourceOrLiteralType requires;
        private ResourceOrLiteralType spatial;
        private ResourceOrLiteralType tableOfContents;
        private ResourceOrLiteralType temporal;

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
         * Check if Alternative is current selection for choice.
         * 
         * @return <code>true</code> if selection, <code>false</code> if not
         */
        public boolean ifAlternative() {
            return choiceListSelect == ALTERNATIVE_CHOICE;
        }

        /** 
         * Get the 'alternative' element value. 
        								An alternative name for the resource. This can be any form of
        								the title that is used as a substitute or an alternative to the
        								formal
        								title of the resource including abbreviations or
        								translations of the title. Example:
        								<alternative xmlns="http://www.w3.org/2001/XMLSchema">Ocho semanas</alternative>
        								(When
        								<title xmlns="http://www.w3.org/2001/XMLSchema">Eight weeks</title>
        								) Type: String
        							
         * 
         * @return value
         */
        public LiteralType getAlternative() {
            return alternative;
        }

        /** 
         * Set the 'alternative' element value. 
        								An alternative name for the resource. This can be any form of
        								the title that is used as a substitute or an alternative to the
        								formal
        								title of the resource including abbreviations or
        								translations of the title. Example:
        								<alternative xmlns="http://www.w3.org/2001/XMLSchema">Ocho semanas</alternative>
        								(When
        								<title xmlns="http://www.w3.org/2001/XMLSchema">Eight weeks</title>
        								) Type: String
        							
         * 
         * @param alternative
         */
        public void setAlternative(LiteralType alternative) {
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
        								An established standard to which the described resource
        								conforms. Example:
        								<conformsTo xmlns="http://www.w3.org/2001/XMLSchema">W3C WCAG 2.0</conformsTo>
        								(for an HTML document that conforms to web content
        								accessibility guidelines). Type: String
        							
         * 
         * @return value
         */
        public ResourceOrLiteralType getConformsTo() {
            return conformsTo;
        }

        /** 
         * Set the 'conformsTo' element value. 
        								An established standard to which the described resource
        								conforms. Example:
        								<conformsTo xmlns="http://www.w3.org/2001/XMLSchema">W3C WCAG 2.0</conformsTo>
        								(for an HTML document that conforms to web content
        								accessibility guidelines). Type: String
        							
         * 
         * @param conformsTo
         */
        public void setConformsTo(ResourceOrLiteralType conformsTo) {
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
        								Date of creation of the resource Example:
        								<created xmlns="http://www.w3.org/2001/XMLSchema">1564</created>
        								<created xmlns="http://www.w3.org/2001/XMLSchema">Iron Age</created>
        								Type: String
        							
         * 
         * @return value
         */
        public ResourceOrLiteralType getCreated() {
            return created;
        }

        /** 
         * Set the 'created' element value. 
        								Date of creation of the resource Example:
        								<created xmlns="http://www.w3.org/2001/XMLSchema">1564</created>
        								<created xmlns="http://www.w3.org/2001/XMLSchema">Iron Age</created>
        								Type: String
        							
         * 
         * @param created
         */
        public void setCreated(ResourceOrLiteralType created) {
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
        								The size or duration of the resource. Example:
        								<extent xmlns="http://www.w3.org/2001/XMLSchema">13 cm</extent>
        								(the width of an original object).
        								<extent xmlns="http://www.w3.org/2001/XMLSchema">34 minutes</extent>
        								(the length of an audio file). Type: String
        							
         * 
         * @return value
         */
        public ResourceOrLiteralType getExtent() {
            return extent;
        }

        /** 
         * Set the 'extent' element value. 
        								The size or duration of the resource. Example:
        								<extent xmlns="http://www.w3.org/2001/XMLSchema">13 cm</extent>
        								(the width of an original object).
        								<extent xmlns="http://www.w3.org/2001/XMLSchema">34 minutes</extent>
        								(the length of an audio file). Type: String
        							
         * 
         * @param extent
         */
        public void setExtent(ResourceOrLiteralType extent) {
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
        								A related resource that is substantially the same as the
        								pre-existing described resource, but in another format.
        								Example:
        								<hasFormat xmlns="http://www.w3.org/2001/XMLSchema">
        									http://upload.wikimedia.org/wikipedia/en/f/f3/Europeana
        									_logo.png </hasFormat>
        								where the resource being described is a tiff image file. Type:
        								String
        							
         * 
         * @return value
         */
        public ResourceOrLiteralType getHasFormat() {
            return hasFormat;
        }

        /** 
         * Set the 'hasFormat' element value. 
        								A related resource that is substantially the same as the
        								pre-existing described resource, but in another format.
        								Example:
        								<hasFormat xmlns="http://www.w3.org/2001/XMLSchema">
        									http://upload.wikimedia.org/wikipedia/en/f/f3/Europeana
        									_logo.png </hasFormat>
        								where the resource being described is a tiff image file. Type:
        								String
        							
         * 
         * @param hasFormat
         */
        public void setHasFormat(ResourceOrLiteralType hasFormat) {
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
        public ResourceOrLiteralType getHasPart() {
            return hasPart;
        }

        /** 
         * Set the 'hasPart' element value.
         * 
         * @param hasPart
         */
        public void setHasPart(ResourceOrLiteralType hasPart) {
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
        								A related resource that is a version, edition, or adaptation of
        								the described resource. Changes in version imply substantive
        								changes in
        								content rather than differences in format.
        								Example:
        								<hasVersion xmlns="http://www.w3.org/2001/XMLSchema"> The Sorcerer's Apprentice (translation by Edwin
        									Zeydel, 1955) </hasVersion>
        								. In this example the 1955 translation is a version of the
        								described resource. Type: String
        							
         * 
         * @return value
         */
        public ResourceOrLiteralType getHasVersion() {
            return hasVersion;
        }

        /** 
         * Set the 'hasVersion' element value. 
        								A related resource that is a version, edition, or adaptation of
        								the described resource. Changes in version imply substantive
        								changes in
        								content rather than differences in format.
        								Example:
        								<hasVersion xmlns="http://www.w3.org/2001/XMLSchema"> The Sorcerer's Apprentice (translation by Edwin
        									Zeydel, 1955) </hasVersion>
        								. In this example the 1955 translation is a version of the
        								described resource. Type: String
        							
         * 
         * @param hasVersion
         */
        public void setHasVersion(ResourceOrLiteralType hasVersion) {
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
        								A related resource that is substantially the same as the
        								described resource, but in another format. Example:
        								<isFormatOf xmlns="http://www.w3.org/2001/XMLSchema">Europeana_logo.tiff</isFormatOf>
        								where the resource being described is a png image file. Type:
        								String
        							
         * 
         * @return value
         */
        public ResourceOrLiteralType getIsFormatOf() {
            return isFormatOf;
        }

        /** 
         * Set the 'isFormatOf' element value. 
        								A related resource that is substantially the same as the
        								described resource, but in another format. Example:
        								<isFormatOf xmlns="http://www.w3.org/2001/XMLSchema">Europeana_logo.tiff</isFormatOf>
        								where the resource being described is a png image file. Type:
        								String
        							
         * 
         * @param isFormatOf
         */
        public void setIsFormatOf(ResourceOrLiteralType isFormatOf) {
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
        								A related resource that references, cites, or otherwise points
        								to the described resource. Example:
        								<isReferencedBy xmlns="http://www.w3.org/2001/XMLSchema"> Till, Nicholas (1994) Mozart and the
        									Enlightenment: Truth, Virtue and Beauty in Mozart's Operas, W.
        									W. Norton &amp;
        									Company
        								</isReferencedBy>
        								Type: String
        							
         * 
         * @return value
         */
        public ResourceOrLiteralType getIsReferencedBy() {
            return isReferencedBy;
        }

        /** 
         * Set the 'isReferencedBy' element value. 
        								A related resource that references, cites, or otherwise points
        								to the described resource. Example:
        								<isReferencedBy xmlns="http://www.w3.org/2001/XMLSchema"> Till, Nicholas (1994) Mozart and the
        									Enlightenment: Truth, Virtue and Beauty in Mozart's Operas, W.
        									W. Norton &amp;
        									Company
        								</isReferencedBy>
        								Type: String
        							
         * 
         * @param isReferencedBy
         */
        public void setIsReferencedBy(ResourceOrLiteralType isReferencedBy) {
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
        								A related resource that supplants, displaces, or supersedes the
        								described resource. Example:
        								<isReplacedBy xmlns="http://www.w3.org/2001/XMLSchema"> http://dublincore.org/about/2009/01/05/bylaws/ </isReplacedBy>
        								where the resource described is an older version
        								(http://dublincore.org/about/2006/01/01/bylaws/) Type: String
        							
         * 
         * @return value
         */
        public ResourceOrLiteralType getIsReplacedBy() {
            return isReplacedBy;
        }

        /** 
         * Set the 'isReplacedBy' element value. 
        								A related resource that supplants, displaces, or supersedes the
        								described resource. Example:
        								<isReplacedBy xmlns="http://www.w3.org/2001/XMLSchema"> http://dublincore.org/about/2009/01/05/bylaws/ </isReplacedBy>
        								where the resource described is an older version
        								(http://dublincore.org/about/2006/01/01/bylaws/) Type: String
        							
         * 
         * @param isReplacedBy
         */
        public void setIsReplacedBy(ResourceOrLiteralType isReplacedBy) {
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
        								A related resource that requires the described resource to
        								support its function, delivery or coherence. Example:
        								<isRequiredBy xmlns="http://www.w3.org/2001/XMLSchema"> http://www.myslides.com/myslideshow.ppt </isRequiredBy>
        								where the image being described is required for an online
        								slideshow. Type: String
        							
         * 
         * @return value
         */
        public ResourceOrLiteralType getIsRequiredBy() {
            return isRequiredBy;
        }

        /** 
         * Set the 'isRequiredBy' element value. 
        								A related resource that requires the described resource to
        								support its function, delivery or coherence. Example:
        								<isRequiredBy xmlns="http://www.w3.org/2001/XMLSchema"> http://www.myslides.com/myslideshow.ppt </isRequiredBy>
        								where the image being described is required for an online
        								slideshow. Type: String
        							
         * 
         * @param isRequiredBy
         */
        public void setIsRequiredBy(ResourceOrLiteralType isRequiredBy) {
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
        								Date of formal issuance (e.g., publication) of the resource.
        								Example:
        								<issued xmlns="http://www.w3.org/2001/XMLSchema">1993</issued>
        								Type: String
        							
         * 
         * @return value
         */
        public ResourceOrLiteralType getIssued() {
            return issued;
        }

        /** 
         * Set the 'issued' element value. 
        								Date of formal issuance (e.g., publication) of the resource.
        								Example:
        								<issued xmlns="http://www.w3.org/2001/XMLSchema">1993</issued>
        								Type: String
        							
         * 
         * @param issued
         */
        public void setIssued(ResourceOrLiteralType issued) {
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
        								A related resource of which the described resource is a
        								version, edition, or adaptation. Changes in version imply
        								substantive changes in
        								content rather than differences in format
        								Example:
        								<isVersionOf xmlns="http://www.w3.org/2001/XMLSchema">ESE Version 0.5</isVersionOf>
        								Type: String
        							
         * 
         * @return value
         */
        public ResourceOrLiteralType getIsVersionOf() {
            return isVersionOf;
        }

        /** 
         * Set the 'isVersionOf' element value. 
        								A related resource of which the described resource is a
        								version, edition, or adaptation. Changes in version imply
        								substantive changes in
        								content rather than differences in format
        								Example:
        								<isVersionOf xmlns="http://www.w3.org/2001/XMLSchema">ESE Version 0.5</isVersionOf>
        								Type: String
        							
         * 
         * @param isVersionOf
         */
        public void setIsVersionOf(ResourceOrLiteralType isVersionOf) {
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
        								The material or physical carrier of the resource. Example:
        								<medium xmlns="http://www.w3.org/2001/XMLSchema">metal</medium>
        								Type: String
        							
         * 
         * @return value
         */
        public ResourceOrLiteralType getMedium() {
            return medium;
        }

        /** 
         * Set the 'medium' element value. 
        								The material or physical carrier of the resource. Example:
        								<medium xmlns="http://www.w3.org/2001/XMLSchema">metal</medium>
        								Type: String
        							
         * 
         * @param medium
         */
        public void setMedium(ResourceOrLiteralType medium) {
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
        								A statement of any changes in ownership and custody of the
        								resource since its creation that are significant for its
        								authenticity,
        								integrity and interpretation. This may include a
        								description of any changes successive custodians made to the resource.
        								Example:
        								<provenance xmlns="http://www.w3.org/2001/XMLSchema"> Donated by The National Library in 1965 </provenance>
        								Type: String
        							
         * 
         * @return value
         */
        public ResourceOrLiteralType getProvenance() {
            return provenance;
        }

        /** 
         * Set the 'provenance' element value. 
        								A statement of any changes in ownership and custody of the
        								resource since its creation that are significant for its
        								authenticity,
        								integrity and interpretation. This may include a
        								description of any changes successive custodians made to the resource.
        								Example:
        								<provenance xmlns="http://www.w3.org/2001/XMLSchema"> Donated by The National Library in 1965 </provenance>
        								Type: String
        							
         * 
         * @param provenance
         */
        public void setProvenance(ResourceOrLiteralType provenance) {
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
        								A related resource that is referenced, cited, or otherwise
        								pointed to by the described resource Example:
        								<references xmlns="http://www.w3.org/2001/XMLSchema"> Honderd jaar Noorse schilderkunst </references>
        								Type: String
        							
         * 
         * @return value
         */
        public ResourceOrLiteralType getReferences() {
            return references;
        }

        /** 
         * Set the 'references' element value. 
        								A related resource that is referenced, cited, or otherwise
        								pointed to by the described resource Example:
        								<references xmlns="http://www.w3.org/2001/XMLSchema"> Honderd jaar Noorse schilderkunst </references>
        								Type: String
        							
         * 
         * @param references
         */
        public void setReferences(ResourceOrLiteralType references) {
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
        								A related resource that is supplanted, displaced, or superseded
        								by the described resource. Example:
        								<replaces xmlns="http://www.w3.org/2001/XMLSchema"> http://dublincore.org/about/2006/01/01/bylaws/ </replaces>
        								where the resource described is a newer version
        								(http://dublincore.org/about/2009/01/05/bylaws/) Type: String
        							
         * 
         * @return value
         */
        public ResourceOrLiteralType getReplaces() {
            return replaces;
        }

        /** 
         * Set the 'replaces' element value. 
        								A related resource that is supplanted, displaced, or superseded
        								by the described resource. Example:
        								<replaces xmlns="http://www.w3.org/2001/XMLSchema"> http://dublincore.org/about/2006/01/01/bylaws/ </replaces>
        								where the resource described is a newer version
        								(http://dublincore.org/about/2009/01/05/bylaws/) Type: String
        							
         * 
         * @param replaces
         */
        public void setReplaces(ResourceOrLiteralType replaces) {
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
        								A related resource that is required by the described resource
        								to support its function, delivery or coherence. Example:
        								<requires xmlns="http://www.w3.org/2001/XMLSchema">
        									http://ads.ahds.ac.uk/project/userinfo/css/oldbrowsers.css </requires>
        								where the resource described is a HTML file at
        								http://ads.ahds.ac.uk/project/userinfo/digitalTextArchiving.html
        								Type: String
        							
         * 
         * @return value
         */
        public ResourceOrLiteralType getRequires() {
            return requires;
        }

        /** 
         * Set the 'requires' element value. 
        								A related resource that is required by the described resource
        								to support its function, delivery or coherence. Example:
        								<requires xmlns="http://www.w3.org/2001/XMLSchema">
        									http://ads.ahds.ac.uk/project/userinfo/css/oldbrowsers.css </requires>
        								where the resource described is a HTML file at
        								http://ads.ahds.ac.uk/project/userinfo/digitalTextArchiving.html
        								Type: String
        							
         * 
         * @param requires
         */
        public void setRequires(ResourceOrLiteralType requires) {
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
        								Spatial characteristics of the resource. Example:
        								<spatial xmlns="http://www.w3.org/2001/XMLSchema">Portugal</spatial>
        								Type: String
        							
         * 
         * @return value
         */
        public ResourceOrLiteralType getSpatial() {
            return spatial;
        }

        /** 
         * Set the 'spatial' element value. 
        								Spatial characteristics of the resource. Example:
        								<spatial xmlns="http://www.w3.org/2001/XMLSchema">Portugal</spatial>
        								Type: String
        							
         * 
         * @param spatial
         */
        public void setSpatial(ResourceOrLiteralType spatial) {
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
        								A list of subunits of the resource. Example:
        								<tableOfContents xmlns="http://www.w3.org/2001/XMLSchema"> Chapter 1. Introduction, Chapter 2. History </tableOfContents>
        								Type: String
        							
         * 
         * @return value
         */
        public ResourceOrLiteralType getTableOfContents() {
            return tableOfContents;
        }

        /** 
         * Set the 'tableOfContents' element value. 
        								A list of subunits of the resource. Example:
        								<tableOfContents xmlns="http://www.w3.org/2001/XMLSchema"> Chapter 1. Introduction, Chapter 2. History </tableOfContents>
        								Type: String
        							
         * 
         * @param tableOfContents
         */
        public void setTableOfContents(ResourceOrLiteralType tableOfContents) {
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
        								Temporal characteristics of the resource Example:
        								<temporal xmlns="http://www.w3.org/2001/XMLSchema">Roman</temporal>
        								Type: String
        							
         * 
         * @return value
         */
        public ResourceOrLiteralType getTemporal() {
            return temporal;
        }

        /** 
         * Set the 'temporal' element value. 
        								Temporal characteristics of the resource Example:
        								<temporal xmlns="http://www.w3.org/2001/XMLSchema">Roman</temporal>
        								Type: String
        							
         * 
         * @param temporal
         */
        public void setTemporal(ResourceOrLiteralType temporal) {
            setChoiceListSelect(TEMPORAL_CHOICE);
            this.temporal = temporal;
        }
    }
}
