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
