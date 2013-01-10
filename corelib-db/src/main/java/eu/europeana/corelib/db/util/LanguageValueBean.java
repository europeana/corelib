/**
 * 
 */
package eu.europeana.corelib.db.util;

/**
 *
 * @author Georgios Markakis (gwarkx@hotmail.com)
 * @since 9 Jan 2013
 *
 */
public class LanguageValueBean {

	private String value;
	private String language;
	
	/**
	 * Private Constructor
	 */
	private LanguageValueBean(){
		
	}
	
	
	/**
	 * @param value
	 * @param language
	 * @return
	 */
	public static LanguageValueBean getInstance(String value,String language){
		LanguageValueBean lvBean = new LanguageValueBean();
		lvBean.setValue(value);
		lvBean.setLanguage(language);
		return lvBean;
	}


	public static LanguageValueBean getInstance(){
		LanguageValueBean lvBean = new LanguageValueBean();
		return lvBean;
	}
	
	
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}


	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}


	/**
	 * @return the language
	 */
	public String getLanguage() {
		if(language == null){
			return "x-default";
		}	
		return language;
	}


	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	
	
}
