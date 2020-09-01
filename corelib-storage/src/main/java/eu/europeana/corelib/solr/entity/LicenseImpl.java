package eu.europeana.corelib.solr.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import dev.morphia.annotations.Entity;
import eu.europeana.corelib.definitions.edm.entity.License;
import java.util.Date;

@JsonInclude(Include.NON_EMPTY)
@Entity(value = "License", useDiscriminator = false)
public class LicenseImpl extends AbstractEdmEntityImpl implements License {

	private String odrlInheritFrom;
	private Date ccDeprecatedOn;
	
	@Override
	public String getOdrlInheritFrom() {
		return this.odrlInheritFrom;
	}

	@Override
	public void setOdrlInheritFrom(String odrlInheritFrom) {
		this.odrlInheritFrom = odrlInheritFrom;
	}

	@Override
	public Date getCcDeprecatedOn() {
		return this.ccDeprecatedOn;
	}

	@Override
	public void setCcDeprecatedOn(Date ccDeprecatedOn) {
		this.ccDeprecatedOn = ccDeprecatedOn;
	}

}
