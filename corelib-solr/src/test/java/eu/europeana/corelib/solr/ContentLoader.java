/*
 * Copyright 2007-2012 The Europeana Foundation
 *
 *  Licenced under the EUPL, Version 1.1 (the "Licence") and subsequent versions as approved
 *  by the European Commission;
 *  You may not use this work except in compliance with the Licence.
 * 
 *  You may obtain a copy of the Licence at:
 *  http://joinup.ec.europa.eu/software/page/eupl
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under
 *  the Licence is distributed on an "AS IS" basis, without warranties or conditions of
 *  any kind, either express or implied.
 *  See the Licence for the specific language governing permissions and limitations under
 *  the Licence.
 */
package eu.europeana.corelib.solr;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import eu.europeana.corelib.definitions.jibx.RDF;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import eu.europeana.corelib.solr.exceptions.MongoDBException;
import eu.europeana.corelib.solr.server.EdmMongoServer;
import eu.europeana.corelib.solr.server.impl.EdmMongoServerImpl;
import eu.europeana.corelib.solr.utils.MongoConstructor;
import eu.europeana.corelib.solr.utils.SolrConstructor;
import eu.europeana.corelib.tools.utils.EuropeanaUriUtils;

/**
 * Sample Class for uploading content in a local Mongo and Solr Instance
 * 
 * @author Yorgos.Mamakis@ kb.nl
 */
public class ContentLoader {

	private static String COLLECTION = "corelib/corelib-solr/src/test/resources/records-test-update.zip";

	private static String TEMP_DIR = "/tmp/europeana/records";

	private static EdmMongoServer mongoDBServer;

	private static SolrServer solrServer;

	private List<File> collectionXML = new ArrayList<File>();

	private List<SolrInputDocument> records = new ArrayList<SolrInputDocument>();

	private int i = 0;
	private int failed = 0;
	private int imported = 0;
	
	private static ContentLoader instance = null;

	public static ContentLoader getInstance(EdmMongoServer mongoDBServer, SolrServer solrServer) {
		if (instance == null) {
			instance = new ContentLoader(mongoDBServer, solrServer);
		}
		return instance;
	}

	private ContentLoader(EdmMongoServer mongoDBServer, SolrServer solrServer) {
		this.mongoDBServer = mongoDBServer;
		this.solrServer = solrServer;
	}

	public void readRecords(String collectionName) {
		if (StringUtils.isBlank(collectionName)) {
			System.out.println("Parameter collectionName is not set correctly");
			System.out.println("Using default collectionName: " + collectionName);
		}
		if (isZipped(collectionName)) {
			collectionXML = unzip(collectionName);
		} else {
			if (new File(collectionName).isDirectory()) {
				for (File file : new File(collectionName).listFiles()) {
					if (StringUtils.endsWith(file.getName(), ".xml")) {
						collectionXML.add(file);
					}
				}
			} else {
				collectionXML.add(new File(collectionName));
			}
		}
	}

	public int parse() {
		MongoConstructor mongoConstructor = new MongoConstructor();
		for (File f : collectionXML) {
			try {
				IBindingFactory bfact = BindingDirectory.getFactory(RDF.class);
				IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
				i++;
				RDF rdf = (RDF) uctx.unmarshalDocument(new FileInputStream(f), null);
				
				FullBeanImpl fullBean = mongoConstructor.constructFullBean(rdf,(EdmMongoServerImpl) mongoDBServer);
				String about = EuropeanaUriUtils.createEuropeanaId("00000", fullBean.getAbout());
				fullBean.setAbout(about);
				if(mongoDBServer.getFullBean(about)==null){
					mongoDBServer.getDatastore().save(fullBean);
					Assert.assertNotNull("Is null",mongoDBServer.getFullBean(fullBean.getAbout()));
				}
				SolrInputDocument document = new SolrConstructor().constructSolrDocument(rdf);
				document.setField("europeana_id", fullBean.getAbout());
				records.add(document);

				if (i % 1000 == 0 || i == collectionXML.size()) {
					System.out.println("Sending " + i + " records to SOLR");
					imported += records.size();
					solrServer.add(records);
					records.clear();
				}

			} catch (JiBXException e) {
				failed++;
				System.out.println("Error unmarshalling document " + f.getName()
						+ " from the input file. Check for Schema changes ("+e.getMessage()+")");
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				System.out.println("File does not exist");

			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SolrServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MongoDBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return failed;
	}
	
	public void commit() {
		try {

			solrServer.commit();
			solrServer.optimize();

			System.out.println("Finished Importing");
			System.out.println("Records read: " + i);
			System.out.println("Records imported: " + imported);
			System.out.println("Records failed: " + failed);
			solrServer = null;
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void cleanFiles() {
		System.out.println("Deleting files");
		for (File f : collectionXML) {
			f.delete();
		}
		System.out.println("Files deleted");
	}

	/**
	 * Unzip a zipped collection file
	 * 
	 * @param collectionName
	 *            The path to the collection file
	 * @return The unzipped file
	 */
	private ArrayList<File> unzip(String collectionName) {
		BufferedOutputStream dest = null;
		String fileName = "";
		ArrayList<File> records = new ArrayList<File>();
		try {
			FileInputStream fis = new FileInputStream(collectionName);
			ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
			ZipEntry entry;
			File workingDir = new File(TEMP_DIR);
			workingDir.mkdirs();
			while ((entry = zis.getNextEntry()) != null) {
				int count;
				byte data[] = new byte[2048];
				fileName = workingDir.getAbsolutePath() + "/" + entry.getName();
				records.add(new File(fileName));
				FileOutputStream fos = new FileOutputStream(fileName);
				dest = new BufferedOutputStream(fos, 2048);
				while ((count = zis.read(data, 0, 2048)) != -1) {
					dest.write(data, 0, count);
				}
				dest.flush();
				dest.close();

			}
			zis.close();

		} catch (Exception e) {
			System.out.println("The zip file is destroyed. Could not import records");
			e.printStackTrace();
		}
		return records;
	}

	/**
	 * Check if the collection file is zipped
	 * 
	 * @param collectionName
	 * @return true if it is in gzip or zip format, false if not
	 */
	private boolean isZipped(String collectionName) {
		return StringUtils.endsWith(collectionName, ".gzip") || StringUtils.endsWith(collectionName, ".zip");
	}
	
	/**
	 * Method to load content in SOLR and MongoDB
	 * 
	 * @param args
	 *            solrHome parameter holding "solr.solr.home" mongoHost the host the mongoDB is located mongoPort the
	 *            port mongoDB listens databaseName the databaseName to save collectionName the name of the collection
	 *            (an XML, a folder or a Zip file)
	 */
	public static void main(String[] args) {
		
		ApplicationContext context = new ClassPathXmlApplicationContext( "/corelib-solr-context.xml" );
		
		SolrServer solrServer = context.getBean("corelib_solr_solrEmbedded", SolrServer.class);
		EdmMongoServer mongoDBServer =  context.getBean("corelib_solr_mongoServer", EdmMongoServer.class);
		
		if ( (solrServer != null) && (mongoDBServer != null)) {
			ContentLoader contentLoader = null;
			try {
				contentLoader = ContentLoader.getInstance(mongoDBServer, solrServer);
				contentLoader.readRecords(COLLECTION);
				contentLoader.parse();
				contentLoader.commit();
			}
			catch(Exception e){
				e.printStackTrace();
			}
			finally {
				if (contentLoader != null) {
					contentLoader.cleanFiles();
				}
			}
		}
	}
}
