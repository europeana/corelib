package eu.europeana.corelib.solr.entity;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.mongodb.morphia.annotations.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import eu.europeana.corelib.definitions.edm.entity.Address;

@JsonInclude(Include.NON_EMPTY)
@Entity ("Address")
public class AddressImpl implements Address {

	private String about;
	private String vcardStreetAddress;
	private String vcardLocality;
	private String vcardPostalCode;
	private String vcardCountryName;
	private String vcardPostOfficeBox;
	private String vcardHasGeo;
	
	@Override
	public String getAbout() {
		return about;
	}
	@Override
	public void setAbout(String about) {
		this.about = about;
	}
	@Override
	public String getVcardStreetAddress() {
		return vcardStreetAddress;
	}
	@Override
	public void setVcardStreetAddress(String vcardStreetAddress) {
		this.vcardStreetAddress = vcardStreetAddress;
	}
	@Override
	public String getVcardLocality() {
		return vcardLocality;
	}
	@Override
	public void setVcardLocality(String vcardLocality) {
		this.vcardLocality = vcardLocality;
	}
	@Override
	public String getVcardPostalCode() {
		return vcardPostalCode;
	}
	@Override
	public void setVcardPostalCode(String vcardPostalCode) {
		this.vcardPostalCode = vcardPostalCode;
	}
	@Override
	public String getVcardCountryName() {
		return vcardCountryName;
	}
	@Override
	public void setVcardCountryName(String vcardCountryName) {
		this.vcardCountryName = vcardCountryName;
	}
	@Override
	public String getVcardPostOfficeBox() {
		return vcardPostOfficeBox;
	}
	@Override
	public void setVcardPostOfficeBox(String vcardPostOfficeBox) {
		this.vcardPostOfficeBox = vcardPostOfficeBox;
	}
	@Override
	public String getVcardHasGeo() {
		return vcardHasGeo;
	}
	@Override
	public void setVcardHasGeo(String vcardHasGeo) {
		this.vcardHasGeo = vcardHasGeo;
	}
}
