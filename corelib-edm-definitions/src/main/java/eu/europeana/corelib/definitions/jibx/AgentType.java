
package eu.europeana.corelib.definitions.jibx;

import java.util.ArrayList;
import java.util.List;

/** 
 *  This class comprises people, either individually or in groups, who have
 the potential to perform intentional actions for which they can be held responsible.
 Example:Leonardo da Vinci, the British Museum, W3C

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:ns1="http://purl.org/dc/elements/1.1/" xmlns:ns2="http://purl.org/dc/terms/" xmlns:ns3="http://www.w3.org/2002/07/owl#" xmlns:ns4="http://rdvocab.info/ElementsGr2/" xmlns:ns5="http://www.w3.org/2004/02/skos/core#" xmlns:ns6="http://www.europeana.eu/schemas/edm/" xmlns:ns7="http://xmlns.com/foaf/0.1/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="AgentType">
 *   &lt;xs:complexContent>
 *     &lt;xs:extension base="ns:AboutType">
 *       &lt;xs:sequence>
 *         &lt;xs:element ref="ns5:prefLabel" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns5:altLabel" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns5:note" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns1:date" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns1:identifier" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns2:hasPart" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns2:isPartOf" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns6:begin" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns6:end" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns6:hasMet" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns6:isRelatedTo" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns7:name" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns4:biographicalInformation" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns4:dateOfBirth" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns4:dateOfDeath" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns4:dateOfEstablishment" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns4:dateOfTermination" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns4:gender" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns4:placeOfBirth" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns4:placeOfDeath" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns4:professionOrOccupation" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns3:sameAs" minOccurs="0" maxOccurs="unbounded"/>
 *       &lt;/xs:sequence>
 *     &lt;/xs:extension>
 *   &lt;/xs:complexContent>
 * &lt;/xs:complexType>
 * </pre>
 */
public class AgentType extends AboutType
{
    private List<PrefLabel> prefLabelList = new ArrayList<PrefLabel>();
    private List<AltLabel> altLabelList = new ArrayList<AltLabel>();
    private List<Note> noteList = new ArrayList<Note>();
    private List<Date> dateList = new ArrayList<Date>();
    private List<Identifier> identifierList = new ArrayList<Identifier>();
    private List<HasPart> hasPartList = new ArrayList<HasPart>();
    private List<IsPartOf> isPartOfList = new ArrayList<IsPartOf>();
    private Begin begin;
    private End end;
    private List<HasMet> hasMetList = new ArrayList<HasMet>();
    private List<IsRelatedTo> isRelatedToList = new ArrayList<IsRelatedTo>();
    private List<Name> nameList = new ArrayList<Name>();
    private List<BiographicalInformation> biographicalInformationList = new ArrayList<BiographicalInformation>();
    private DateOfBirth dateOfBirth;
    private DateOfDeath dateOfDeath;
    private DateOfEstablishment dateOfEstablishment;
    private DateOfTermination dateOfTermination;
    private Gender gender;
    private List<PlaceOfBirth> placeOfBirthList = new ArrayList<PlaceOfBirth>();
    private List<PlaceOfDeath> placeOfDeathList = new ArrayList<PlaceOfDeath>();
    private List<ProfessionOrOccupation> professionOrOccupationList = new ArrayList<ProfessionOrOccupation>();
    private List<SameAs> sameAList = new ArrayList<SameAs>();

    /** 
     * Get the list of 'prefLabel' element items.
     * 
     * @return list
     */
    public List<PrefLabel> getPrefLabelList() {
        return prefLabelList;
    }

    /** 
     * Set the list of 'prefLabel' element items.
     * 
     * @param list
     */
    public void setPrefLabelList(List<PrefLabel> list) {
        prefLabelList = list;
    }

    /** 
     * Get the list of 'altLabel' element items.
     * 
     * @return list
     */
    public List<AltLabel> getAltLabelList() {
        return altLabelList;
    }

    /** 
     * Set the list of 'altLabel' element items.
     * 
     * @param list
     */
    public void setAltLabelList(List<AltLabel> list) {
        altLabelList = list;
    }

    /** 
     * Get the list of 'note' element items.
     * 
     * @return list
     */
    public List<Note> getNoteList() {
        return noteList;
    }

    /** 
     * Set the list of 'note' element items.
     * 
     * @param list
     */
    public void setNoteList(List<Note> list) {
        noteList = list;
    }

    /** 
     * Get the list of 'date' element items.
     * 
     * @return list
     */
    public List<Date> getDateList() {
        return dateList;
    }

    /** 
     * Set the list of 'date' element items.
     * 
     * @param list
     */
    public void setDateList(List<Date> list) {
        dateList = list;
    }

    /** 
     * Get the list of 'identifier' element items.
     * 
     * @return list
     */
    public List<Identifier> getIdentifierList() {
        return identifierList;
    }

    /** 
     * Set the list of 'identifier' element items.
     * 
     * @param list
     */
    public void setIdentifierList(List<Identifier> list) {
        identifierList = list;
    }

    /** 
     * Get the list of 'hasPart' element items.
     * 
     * @return list
     */
    public List<HasPart> getHasPartList() {
        return hasPartList;
    }

    /** 
     * Set the list of 'hasPart' element items.
     * 
     * @param list
     */
    public void setHasPartList(List<HasPart> list) {
        hasPartList = list;
    }

    /** 
     * Get the list of 'isPartOf' element items.
     * 
     * @return list
     */
    public List<IsPartOf> getIsPartOfList() {
        return isPartOfList;
    }

    /** 
     * Set the list of 'isPartOf' element items.
     * 
     * @param list
     */
    public void setIsPartOfList(List<IsPartOf> list) {
        isPartOfList = list;
    }

    /** 
     * Get the 'begin' element value.
     * 
     * @return value
     */
    public Begin getBegin() {
        return begin;
    }

    /** 
     * Set the 'begin' element value.
     * 
     * @param begin
     */
    public void setBegin(Begin begin) {
        this.begin = begin;
    }

    /** 
     * Get the 'end' element value.
     * 
     * @return value
     */
    public End getEnd() {
        return end;
    }

    /** 
     * Set the 'end' element value.
     * 
     * @param end
     */
    public void setEnd(End end) {
        this.end = end;
    }

    /** 
     * Get the list of 'hasMet' element items.
     * 
     * @return list
     */
    public List<HasMet> getHasMetList() {
        return hasMetList;
    }

    /** 
     * Set the list of 'hasMet' element items.
     * 
     * @param list
     */
    public void setHasMetList(List<HasMet> list) {
        hasMetList = list;
    }

    /** 
     * Get the list of 'isRelatedTo' element items.
     * 
     * @return list
     */
    public List<IsRelatedTo> getIsRelatedToList() {
        return isRelatedToList;
    }

    /** 
     * Set the list of 'isRelatedTo' element items.
     * 
     * @param list
     */
    public void setIsRelatedToList(List<IsRelatedTo> list) {
        isRelatedToList = list;
    }

    /** 
     * Get the list of 'name' element items.
     * 
     * @return list
     */
    public List<Name> getNameList() {
        return nameList;
    }

    /** 
     * Set the list of 'name' element items.
     * 
     * @param list
     */
    public void setNameList(List<Name> list) {
        nameList = list;
    }

    /** 
     * Get the list of 'biographicalInformation' element items.
     * 
     * @return list
     */
    public List<BiographicalInformation> getBiographicalInformationList() {
        return biographicalInformationList;
    }

    /** 
     * Set the list of 'biographicalInformation' element items.
     * 
     * @param list
     */
    public void setBiographicalInformationList(
            List<BiographicalInformation> list) {
        biographicalInformationList = list;
    }

    /** 
     * Get the 'dateOfBirth' element value.
     * 
     * @return value
     */
    public DateOfBirth getDateOfBirth() {
        return dateOfBirth;
    }

    /** 
     * Set the 'dateOfBirth' element value.
     * 
     * @param dateOfBirth
     */
    public void setDateOfBirth(DateOfBirth dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /** 
     * Get the 'dateOfDeath' element value.
     * 
     * @return value
     */
    public DateOfDeath getDateOfDeath() {
        return dateOfDeath;
    }

    /** 
     * Set the 'dateOfDeath' element value.
     * 
     * @param dateOfDeath
     */
    public void setDateOfDeath(DateOfDeath dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

    /** 
     * Get the 'dateOfEstablishment' element value.
     * 
     * @return value
     */
    public DateOfEstablishment getDateOfEstablishment() {
        return dateOfEstablishment;
    }

    /** 
     * Set the 'dateOfEstablishment' element value.
     * 
     * @param dateOfEstablishment
     */
    public void setDateOfEstablishment(DateOfEstablishment dateOfEstablishment) {
        this.dateOfEstablishment = dateOfEstablishment;
    }

    /** 
     * Get the 'dateOfTermination' element value.
     * 
     * @return value
     */
    public DateOfTermination getDateOfTermination() {
        return dateOfTermination;
    }

    /** 
     * Set the 'dateOfTermination' element value.
     * 
     * @param dateOfTermination
     */
    public void setDateOfTermination(DateOfTermination dateOfTermination) {
        this.dateOfTermination = dateOfTermination;
    }

    /** 
     * Get the 'gender' element value.
     * 
     * @return value
     */
    public Gender getGender() {
        return gender;
    }

    /** 
     * Set the 'gender' element value.
     * 
     * @param gender
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /** 
     * Get the list of 'placeOfBirth' element items.
     * 
     * @return list
     */
    public List<PlaceOfBirth> getPlaceOfBirthList() {
        return placeOfBirthList;
    }

    /** 
     * Set the list of 'placeOfBirth' element items.
     * 
     * @param list
     */
    public void setPlaceOfBirthList(List<PlaceOfBirth> list) {
        placeOfBirthList = list;
    }

    /** 
     * Get the list of 'placeOfDeath' element items.
     * 
     * @return list
     */
    public List<PlaceOfDeath> getPlaceOfDeathList() {
        return placeOfDeathList;
    }

    /** 
     * Set the list of 'placeOfDeath' element items.
     * 
     * @param list
     */
    public void setPlaceOfDeathList(List<PlaceOfDeath> list) {
        placeOfDeathList = list;
    }

    /** 
     * Get the list of 'professionOrOccupation' element items.
     * 
     * @return list
     */
    public List<ProfessionOrOccupation> getProfessionOrOccupationList() {
        return professionOrOccupationList;
    }

    /** 
     * Set the list of 'professionOrOccupation' element items.
     * 
     * @param list
     */
    public void setProfessionOrOccupationList(List<ProfessionOrOccupation> list) {
        professionOrOccupationList = list;
    }

    /** 
     * Get the list of 'sameAs' element items.
     * 
     * @return list
     */
    public List<SameAs> getSameAList() {
        return sameAList;
    }

    /** 
     * Set the list of 'sameAs' element items.
     * 
     * @param list
     */
    public void setSameAList(List<SameAs> list) {
        sameAList = list;
    }
}
