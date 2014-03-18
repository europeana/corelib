/*
 * Copyright 2007-2013 The Europeana Foundation
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

package eu.europeana.corelib.definitions;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Gives access to Spring Beans outside of the Bean Container.
 * 
 * WARNING: HANDLE WITH CARE, only use this if there is no other way of using Spring configuration to inject directly.
 * 
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * 
 */
public class ApplicationContextContainer implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		applicationContext = context;
	}

	public static <T> T getBean(Class<T> beanClazz) {
		return applicationContext.getBean(beanClazz);
	}

	public static <T> T getBean(Class<T> beanClazz, String beanName) {
		return applicationContext.getBean(beanName, beanClazz);
	}
}
