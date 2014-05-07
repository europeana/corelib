/*
 * Copyright 2007 EDL FOUNDATION
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * you may not use this work except in compliance with the
 * Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://ec.europa.eu/idabc/eupl
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 */
package eu.europeana.uim.sugarcrmclient.ws.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import eu.europeana.uim.sugarcrmclient.internal.helpers.ClientUtils;
import eu.europeana.uim.sugarcrmclient.ws.SugarWsClient;
import eu.europeana.uim.sugarcrmclient.ws.exceptions.JIXBLoginFailureException;

/**
 * This Class implements the Quartz-based polling mechanism for sugarcrm
 * plugin.It refreshes the session by logging in in given time intervals in
 * order to keep the client connection alive.
 * 
 * @author Georgios Markakis
 */
public class PollingBean extends QuartzJobBean {

	private SugarWsClient sugarWsClient;

	/**
	 * Setter for sugarcrmPlugin spring injected property
	 */
	public void setSugarWsClient(SugarWsClient sugarWsClient) {
		this.sugarWsClient = sugarWsClient;
	}

	/*
	 * (non-Javadoc)s
	 * 
	 * @see
	 * org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org
	 * .quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		String username = sugarWsClient.getUsername();
		String password = sugarWsClient.getPassword();

		try {
			sugarWsClient.setSessionID(
				sugarWsClient.login(
					ClientUtils.createStandardLoginObject(username, password)));
		} catch (JIXBLoginFailureException e) {
			sugarWsClient.setSessionID("-1");
		} catch (Exception e) {
			sugarWsClient.setSessionID("-1");
		}
	}
}
