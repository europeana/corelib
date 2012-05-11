package eu.europeana.corelib.tools;

import org.springframework.context.ApplicationContext;  

public class AppContext {  
  
    private static ApplicationContext applicationContext;  
  
    public static void setApplicationContext(ApplicationContext applicationContext) {  
        AppContext.applicationContext = applicationContext;  
    }  
  
    public static ApplicationContext getApplicationContext() {  
        return applicationContext;  
    }  
}