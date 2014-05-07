package eu.europeana.uim.sugarcrmclient.ws;

import org.apache.log4j.Logger;
import org.springframework.ws.client.core.WebServiceTemplate;
import eu.europeana.uim.sugarcrmclient.internal.helpers.ClientUtils;
import eu.europeana.uim.sugarcrmclient.internal.helpers.PropertyReader;
import eu.europeana.uim.sugarcrmclient.internal.helpers.UimConfigurationProperty;
import eu.europeana.uim.sugarcrmclient.ws.exceptions.JIXBLoginFailureException;

/**
 * Class used by Spring to instantiate a client
 *  
 * @author Georgios Markakis
 */
public class ClientFactory {

	private WebServiceTemplate webServiceTemplate;
	private static Logger LOGGER = Logger.getLogger(ClientUtils.class);

	/**
	 * Internal factory method used by Spring 
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	public SugarWsClient createInstance(String uri, String userName, String password) {

		SugarWsClientImpl client = new SugarWsClientImpl();
		client.setUsername(userName);
		client.setPassword(password);
		webServiceTemplate.setDefaultUri(uri);
		client.setWebServiceTemplate(webServiceTemplate);

		try {
			client.setSessionID(client.login(ClientUtils.createStandardLoginObject(userName,password)));
		} catch (JIXBLoginFailureException e) {
			client.setSessionID("-1");
		} catch (Exception e){
			LOGGER.info("======= Warning: could not connect to SugarCrm Server =====");
			client.setSessionID("-1");
		}

		return client;
	}

	/**
	 * Internal factory method used by Spring 
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	public  SugarWsClient createInstance(){

		SugarWsClientImpl client = new SugarWsClientImpl();
		String userName = PropertyReader.getProperty(UimConfigurationProperty.SUGARCRM_USERNAME);
		String password = PropertyReader.getProperty(UimConfigurationProperty.SUGARCRM_PASSWORD);
		String uri = PropertyReader.getProperty(UimConfigurationProperty.SUGARCRM_HOST);
		webServiceTemplate.setDefaultUri(uri);
		client.setUsername(userName);
		client.setPassword(password);
		client.setWebServiceTemplate(webServiceTemplate);

		try {
			client.setSessionID(client.login(ClientUtils.createStandardLoginObject(userName,password)));
		} catch (JIXBLoginFailureException e) {
			client.setSessionID("-1");
			e.printStackTrace();
		} catch (Exception e){
			LOGGER.info("======= Warning: could not connect to SugarCrm Server =====");
			client.setSessionID("-1");
			e.printStackTrace();
		}

		return client;
	}

	/**
	 * @return
	 */
	public WebServiceTemplate getWebServiceTemplate(){
		return this.webServiceTemplate;
	}

	/**
	 * @param webServiceTemplate
	 */
	public void setWebServiceTemplate(WebServiceTemplate webServiceTemplate){
		this.webServiceTemplate = webServiceTemplate;
	}
}
