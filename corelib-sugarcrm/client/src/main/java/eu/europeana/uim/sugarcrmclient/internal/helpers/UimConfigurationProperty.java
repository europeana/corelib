package eu.europeana.uim.sugarcrmclient.internal.helpers;

/**
 * UIM configuration enumeration
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
public enum UimConfigurationProperty {

	SUGARCRM_HOST("sugarcrm.host"),
	SUGARCRM_USERNAME("sugarcrm.username"),
	SUGARCRM_PASSWORD("sugarcrm.password"),
	SUGARCRM_DEFAULTMAXRESULTS("sugarcrm.default.maxresults");
	
	String field;
	private UimConfigurationProperty(String field){
		this.field = field;
	}
	
	@Override
	public String toString(){
		return this.field;
	}
}
