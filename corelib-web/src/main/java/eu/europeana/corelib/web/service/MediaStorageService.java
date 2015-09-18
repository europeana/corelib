package eu.europeana.corelib.web.service;

import eu.europeana.corelib.domain.MediaFile;


public interface MediaStorageService {
    Boolean checkIfExists(String var1);

    MediaFile retrieve(String var1, Boolean var2);

    void createOrModify(MediaFile var1);

    void delete(String var1);
}
