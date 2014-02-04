package eu.europeana.corelib.logging.injector;

import java.lang.reflect.Field;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;

import eu.europeana.corelib.logging.Log;
import eu.europeana.corelib.logging.Logger;

public class LogInjector implements BeanPostProcessor, Ordered {

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		// does nothing
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(final Object bean, String beanName) throws BeansException {
		ReflectionUtils.doWithFields(bean.getClass(), new FieldCallback() {
			@Override
			public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
				// make the field accessible if defined private
				ReflectionUtils.makeAccessible(field);
				Log log = field.getAnnotation(Log.class);
				if (log != null) {
					if (StringUtils.isNotBlank(log.value())) {
						field.set(bean, Logger.getLogger(log.value()));
					} else {
						field.set(bean, Logger.getLogger(bean.getClass())); 
					}
				}
			}
		});
		return bean;
	}

}
