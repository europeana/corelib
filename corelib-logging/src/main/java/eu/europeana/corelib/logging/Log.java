package eu.europeana.corelib.logging;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
* Indicates Logger of appropriate type to 
* be supplied at runtime to the annotated field. 
* 
* The injected logger is an appropriate implementation 
* of org.slf4j.Logger. 
*/

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)  
public @interface Log {
	
	String value() default "";
	
}
