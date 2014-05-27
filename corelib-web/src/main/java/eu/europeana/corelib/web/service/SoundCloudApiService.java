package eu.europeana.corelib.web.service;

import eu.europeana.corelib.web.model.mediaservice.soundcloud.Track;

public interface SoundCloudApiService {

	public Track resolvePath(String path);
	public Track getTrackInfo(String trackId);

}
