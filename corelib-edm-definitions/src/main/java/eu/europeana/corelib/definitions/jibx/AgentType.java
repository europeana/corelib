
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
 * &lt;xs:complexType xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:ns1="http://www.w3.org/2004/02/skos/core#" xmlns:ns2="http://xmlns.com/foaf/0.1/" xmlns:ns3="http://www.europeana.eu/schemas/edm/" xmlns:ns4="http://www.w3.org/2002/07/owl#" xmlns:ns5="http://purl.org/dc/terms/" xmlns:ns6="http://purl.org/dc/elements/1.1/" xmlns:ns7="http://rdvocab.info/ElementsGr2/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="AgentType">
 *   &lt;xs:complexContent>
 *     &lt;xs:extension base="ns:AboutType">
 *       &lt;xs:sequence>
 *         &lt;xs:element ref="ns1:prefLabel" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns1:altLabel" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns1:note" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns6:date" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns6:identifier" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns5:hasPart" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns5:isPartOf" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns3:begin" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns3:end" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns3:hasMet" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns3:isRelatedTo" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns2:name" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns7:biographicalInformation" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns7:dateOfBirth" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns7:dateOfDeath" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns7:dateOfEstablishment" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns7:dateOfTermination" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns7:gender" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns7:placeOfBirth" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns7:placeOfDeath" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns7:professionOrOccupation" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns4:sameAs" minOccurs="0" maxOccurs="unbounded"/>
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
    private BiographicalInformation biographicalInformation;
    private DateOfBirth dateOfBirth;
    private DateOfDeath dateOfDeath;
    private DateOfEstablishment dateOfEstablishment;
    private DateOfTermination dateOfTermination;
    private Gender gender;
    private PlaceOfBirth placeOfBirth;
    private PlaceOfDeath placeOfDeath;
    private ProfessionOrOccupation professionOrOccupation;
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
     * Get the 'biographicalInformation' element value.
     * 
     * @return value
     */
    public BiographicalInformation getBiographicalInformation() {
        return biographicalInformation;
    }

    /** 
     * Set the 'biographicalInformation' element value.
     * 
     * @param biographicalInformation
     */
    public void setBiographicalInformation(
            BiographicalInformation biographicalInformation) {
        this.biographicalInformation = biographicalInformation;
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
     * Get the 'placeOfBirth' element value.
     * 
     * @return value
     */
    public PlaceOfBirth getPlaceOfBirth() {
        return placeOfBirth;
    }

    /** 
     * Set the 'placeOfBirth' element value.
     * 
     * @param placeOfBirth
     */
    public void setPlaceOfBirth(PlaceOfBirth placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    /** 
     * Get the 'placeOfDeath' element value.
     * 
     * @return value
     */
    public PlaceOfDeath getPlaceOfDeath() {
        return placeOfDeath;
    }

    /** 
     * Set the 'placeOfDeath' element value.
     * 
     * @param placeOfDeath
     */
    public void setPlaceOfDeath(PlaceOfDeath placeOfDeath) {
        this.placeOfDeath = placeOfDeath;
    }

    /** 
     * Get the 'professionOrOccupation' element value.
     * 
     * @return value
     */
    public ProfessionOrOccupation getProfessionOrOccupation() {
        return professionOrOccupation;
    }

    /** 
     * Set the 'professionOrOccupation' element value.
     * 
     * @param professionOrOccupation
     */
    public void setProfessionOrOccupation(
            ProfessionOrOccupation professionOrOccupation) {
        this.professionOrOccupation = professionOrOccupation;
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
