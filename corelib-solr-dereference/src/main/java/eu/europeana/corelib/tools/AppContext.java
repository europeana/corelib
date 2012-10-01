package eu.europeana.corelib.tools;

import org.springframework.context.ApplicationContext;  
/**
 * The Application Context
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
public class AppContext {  
  
    private static ApplicationContext applicationContext;  
  
    public static void setApplicationContext(ApplicationContext applicationContext) {  
        AppContext.applicationContext = applicationContext;  
    }  
  
    public static ApplicationContext getApplicationContext() {  
        return applicationContext;  
    }  
}