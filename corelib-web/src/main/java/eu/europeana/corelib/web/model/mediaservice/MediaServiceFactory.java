package eu.europeana.corelib.web.model.mediaservice;

import java.lang.reflect.InvocationTargetException;

import javax.annotation.Resource;

import eu.europeana.corelib.definitions.ApplicationContextContainer;
import eu.europeana.corelib.web.service.SoundCloudApiService;

public class MediaServiceFactory {

	@Resource
	private SoundCloudApiService soundCloudApiService;

	public static MediaServiceFactory getFactory() {
		return ApplicationContextContainer.getBean(MediaServiceFactory.class);
	}

	public MediaService create(Class<? extends MediaService> clazz, String id) {
		try {
			if (clazz.equals(SoundCloud.class)) {
				return clazz.getConstructor(String.class, SoundCloudApiService.class).newInstance(id, soundCloudApiService);
			} else {
				return clazz.getConstructor(String.class).newInstance(id);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
}
