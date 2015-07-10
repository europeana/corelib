package eu.europeana.corelib.mocks;

import com.google.code.morphia.Datastore;
import eu.europeana.corelib.tools.lookuptable.EuropeanaId;
import eu.europeana.corelib.tools.lookuptable.EuropeanaIdMongoServer;
import eu.europeana.publication.common.IDocument;
import eu.europeana.publication.common.State;

import java.util.List;
import java.util.Map;

public class EuropeanaIdMongoServerMock implements EuropeanaIdMongoServer {
    @Override
    public void createDatastore() {

    }

    @Override
    public EuropeanaId retrieveEuropeanaIdFromOld(String oldId) {
        return null;
    }

    @Override
    public List<EuropeanaId> retrieveEuropeanaIdFromNew(String newId) {
        return null;
    }

    @Override
    public boolean oldIdExists(String newId) {
        return false;
    }

    @Override
    public boolean newIdExists(String oldId) {
        return false;
    }

    @Override
    public void setLastAccessed(String oldId) {

    }

    @Override
    public void saveEuropeanaId(EuropeanaId europeanaId) {

    }

    @Override
    public void deleteEuropeanaId(String oldId, String newId) {

    }

    @Override
    public void deleteEuropeanaIdFromOld(String oldId) {

    }

    @Override
    public void deleteEuropeanaIdFromNew(String newId) {

    }

    @Override
    public void updateTime(String newId, String oldId) {

    }

    @Override
    public void setDatastore(Datastore datastore) {

    }

    @Override
    public EuropeanaId find() {
        return null;
    }

    @Override
    public EuropeanaId findOne(String oldId) {
        return null;
    }

    @Override
    public List<IDocument> getDocumentsByStatesUsingBatch(List<State> list, Map<String, List<String>> map, int i) {
        return null;
    }

    @Override
    public IDocument getDocumentById(String s) {
        return null;
    }

    @Override
    public void insertDocument(IDocument iDocument) {

    }

    @Override
    public void deleteDocument(IDocument iDocument) {

    }

    @Override
    public void updateDocumentUsingId(IDocument iDocument) {

    }

    @Override
    public void cloneDocument(IDocument iDocument, IDocument iDocument1) {

    }

    @Override
    public void commit() throws Exception {

    }
}
