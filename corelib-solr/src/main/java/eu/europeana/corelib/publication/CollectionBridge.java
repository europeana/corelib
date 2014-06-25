/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.europeana.corelib.publication;

import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import eu.europeana.corelib.solr.server.impl.EdmMongoServerImpl;
import eu.europeana.corelib.solr.utils.construct.FullBeanHandler;
import eu.europeana.corelib.solr.utils.construct.SolrDocumentHandler;
import eu.europeana.corelib.tools.lookuptable.EuropeanaId;
import eu.europeana.corelib.tools.lookuptable.impl.EuropeanaIdMongoServerImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.europeana.publication.common.ICollection;
import eu.europeana.publication.common.IDocument;
import eu.europeana.publication.common.State;
import javax.annotation.Resource;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

/**
 *
 * @author Yorgos.Mamakis@ europeana.eu
 */
public class CollectionBridge implements ICollection{

    @Resource
    EdmMongoServerImpl mongoServer;
    
    @Resource
    EuropeanaIdMongoServerImpl europeanaIdServer;
    
    @Resource
    HttpSolrServer solrServer;
    
    @Override
    public IDocument getDocumentById(IDocument id) {
        for(ICollection collection : getCollectionHandlers(id.getClass())){
           return collection.getDocumentById(id);
        }
        return null;
    }

    @Override
    public void insertDocument(IDocument document) {
        for(ICollection collection : getCollectionHandlers(document.getClass())){
            collection.insertDocument(document);
        }
    }


    @Override
    public void updateDocumentUsingId(IDocument document) {
        for(ICollection collection : getCollectionHandlers(document.getClass())){
            collection.updateDocumentUsingId(document);
        }
    }

    @Override
    public void cloneDocument(IDocument originalDocument, IDocument clonedDocument) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
        @Override
    public List<IDocument> getDocumentsByStatesUsingBatch(List<State> stateVlues,
            Map<String, List<String>> queryChoices, int batchSize) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private  List<ICollection> getCollectionHandlers(Class<?> clazz){
        List<ICollection> collectionHandlers = new ArrayList<ICollection>();
        if(clazz.isAssignableFrom(FullBeanImpl.class)){
            collectionHandlers.add(new FullBeanHandler(mongoServer));
            collectionHandlers.add(new SolrDocumentHandler(solrServer));
        } else if(clazz.isAssignableFrom(EuropeanaId.class)) {
            collectionHandlers.add(europeanaIdServer);
        }
        return collectionHandlers;
    }

    @Override
    public void deleteDocument(IDocument id) {
         for(ICollection collection : getCollectionHandlers(id.getClass())){
            collection.deleteDocument(id);
        }
    }

    @Override
    public void commit() throws Exception {
//         new SolrDocumentHandler().commit();
    }
}
