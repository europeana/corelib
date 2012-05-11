package eu.europeana.corelib.tools;

import org.springframework.beans.BeansException;  
import org.springframework.context.ApplicationContext;  
import org.springframework.context.ApplicationContextAware;  

public class ApplicationContextProvider implements ApplicationContextAware{

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {  
        AppContext.setApplicationContext(applicationContext);  
    }  
}
