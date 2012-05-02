package eu.europeana.corelib.solr.context;

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