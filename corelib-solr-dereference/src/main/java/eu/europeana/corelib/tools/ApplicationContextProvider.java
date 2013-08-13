/*
 * Copyright 2007-2012 The Europeana Foundation
 *
 *  Licenced under the EUPL, Version 1.1 (the "Licence") and subsequent versions as approved
 *  by the European Commission;
 *  You may not use this work except in compliance with the Licence.
 * 
 *  You may obtain a copy of the Licence at:
 *  http://joinup.ec.europa.eu/software/page/eupl
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under
 *  the Licence is distributed on an "AS IS" basis, without warranties or conditions of
 *  any kind, either express or implied.
 *  See the Licence for the specific language governing permissions and limitations under
 *  the Licence.
 */
package eu.europeana.corelib.tools;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
/**
 * Class to access the ApplicationContext
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
public class ApplicationContextProvider implements ApplicationContextAware{

	/**
	 * Sets the application context for Spring
	 */
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {  
        AppContext.setApplicationContext(applicationContext);  
    }  
}
