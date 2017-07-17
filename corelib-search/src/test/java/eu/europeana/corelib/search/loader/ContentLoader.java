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
package eu.europeana.corelib.search.loader;

import eu.europeana.corelib.definitions.jibx.RDF;
import eu.europeana.corelib.edm.utils.MongoConstructor;
import eu.europeana.corelib.edm.utils.SolrConstructor;
import eu.europeana.corelib.edm.utils.construct.FullBeanHandler;
import eu.europeana.corelib.mongo.server.EdmMongoServer;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import eu.europeana.corelib.utils.EuropeanaUriUtils;
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Sample Class for uploading content in a local Mongo and Solr Instance
 *
 * @author Yorgos.Mamakis@ kb.nl
 */
public class ContentLoader {

    private List<Path> collectionXML = new ArrayList<>();

    private List<SolrInputDocument> records = new ArrayList<>();

    private int i = 0;
    private int failed = 0;
    private int imported = 0;

    private static ContentLoader instance = null;

    public static ContentLoader getInstance() {
        if (instance == null) {
            instance = new ContentLoader();
        }
        return instance;
    }

    private ContentLoader() {
    }

    public void readRecords(String collectionName) {
        if (StringUtils.isBlank(collectionName)) {
            System.out.println("Parameter collectionName is not set correctly");
            System.out.println("Using default collectionName: " + collectionName);
        }
        Path collectionPath = Paths.get(collectionName);


        if (isZipped(collectionName)) {
            collectionXML = unzip(collectionPath);
        } else {
            if (Files.isDirectory(collectionPath)) {
                try (DirectoryStream<Path> stream = Files.newDirectoryStream(collectionPath, "*.xml")) {
                    for (Path file : stream) {
                        collectionXML.add(file);
                    }

                } catch (DirectoryIteratorException | IOException e) {
                    /// TODO: report error
                }
            } else {
                collectionXML.add(collectionPath);
            }
        }
    }

    public int parse(final EdmMongoServer mongoDBServer, final SolrServer solrServer) {
        MongoConstructor mongoConstructor = new MongoConstructor();
        for (Path path : collectionXML) {
            try {
                IBindingFactory bfact = BindingDirectory.getFactory(RDF.class);
                IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
                i++;
                RDF rdf = (RDF) uctx.unmarshalDocument(Files.newBufferedReader(path, StandardCharsets.UTF_8));

                FullBeanImpl fullBean = mongoConstructor.constructFullBean(rdf);
                FullBeanHandler handler = new FullBeanHandler(mongoDBServer);
                handler.saveEdmClasses(fullBean, true);
                String about = EuropeanaUriUtils.createSanitizedEuropeanaId("00000", fullBean.getAbout());
                fullBean.setAbout(about);
                if (mongoDBServer.getFullBean(about) == null) {
                    mongoDBServer.getDatastore().save(fullBean);
                    Assert.assertNotNull("Is null", mongoDBServer.getFullBean(fullBean.getAbout()));
                }
                SolrInputDocument document = new SolrConstructor().constructSolrDocument(rdf);
                document.setField("europeana_id", fullBean.getAbout());
                records.add(document);

                if (i % 1000 == 0 || i == collectionXML.size()) {
                    System.out.println("Sending " + i + " records to SOLR");
                    imported += records.size();
                    solrServer.add(records);
                    solrServer.commit();
                    records.clear();
                }

            } catch (JiBXException e) {
                failed++;
                System.out.println("Error unmarshalling document " + path.toString()
                        + " from the input file. Check for Schema changes (" + e.getMessage() + ")");
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                System.out.println("File does not exist");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return failed;
    }

    public void commit(final SolrServer solrServer) {
        try {

            solrServer.commit();
            solrServer.optimize();

            System.out.println("Finished Importing");
            System.out.println("Records read: " + i);
            System.out.println("Records imported: " + imported);
            System.out.println("Records failed: " + failed);
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
    }

    public void cleanFiles() {
        System.out.println("Deleting files");
        for (Path file : collectionXML) {
            try {
                Files.delete(file);
            } catch (IOException e) {
                // TODO WHAT??
            }
        }
        System.out.println("Files deleted");
    }

    /**
     * Unzip a zipped collection file
     *
     * @param zipPath The path to the collection zip file
     * @return List of unzipped files
     */
    private List<Path> unzip(Path zipPath) {
        final List<Path> records = new ArrayList<>();
        // create file system for zip
        final URI uri = URI.create("jar:file:" + zipPath.toUri().getPath());
        final Map<String, String> env = new HashMap<>();
        try (FileSystem zip = FileSystems.newFileSystem(uri, env)) {
            // create destination directory
            String TEMP_DIR = "/tmp/europeana/records";
            final Path destDir = Paths.get(TEMP_DIR);
            if (Files.notExists(destDir)) {
                Files.createDirectories(destDir);
            }
            final Path root = zip.getPath("/");

            //walk the zip file tree and copy files to the destination
            Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file,
                                                 BasicFileAttributes attrs) throws IOException {
                    final Path destFile = Paths.get(destDir.toString(),
                            file.toString());
                    System.out.printf("Extracting file %s to %s\n", file, destFile);
                    Files.copy(file, destFile, StandardCopyOption.REPLACE_EXISTING);
                    records.add(destFile);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult preVisitDirectory(Path dir,
                                                         BasicFileAttributes attrs) throws IOException {
                    final Path dirToCreate = Paths.get(destDir.toString(),
                            dir.toString());
                    if (Files.notExists(dirToCreate)) {
                        System.out.printf("Creating directory %s\n", dirToCreate);
                        Files.createDirectory(dirToCreate);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            // TODO Oops What now?
        }
        return records;
    }

//    private List<Path> unzip(String collectionName) {
//        BufferedOutputStream dest;
//        String fileName;
//        List<File> records = new ArrayList<>();
//        try {
//            FileInputStream fis = new FileInputStream(collectionName);
//            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
//            ZipEntry entry;
//            File workingDir = new File(TEMP_DIR);
//            workingDir.mkdirs();
//            while ((entry = zis.getNextEntry()) != null) {
//                int count;
//                byte data[] = new byte[2048];
//                fileName = workingDir.getAbsolutePath() + "/" + entry.getName();
//                records.add(new File(fileName));
//                FileOutputStream fos = new FileOutputStream(fileName);
//                dest = new BufferedOutputStream(fos, 2048);
//                while ((count = zis.read(data, 0, 2048)) != -1) {
//                    dest.write(data, 0, count);
//                }
//                dest.flush();
//                dest.close();
//
//            }
//            zis.close();
//
//        } catch (Exception e) {
//            System.out.println("The zip file is damaged. Could not import records");
//            e.printStackTrace();
//        }
//        return records;
//    }

    /**
     * Check if the collection file is zipped
     *
     * @param collectionName The path to the collection file
     * @return true if it is in gzip or zip format, false if not
     */
    private boolean isZipped(String collectionName) {
        return StringUtils.endsWith(collectionName, ".gzip") || StringUtils.endsWith(collectionName, ".zip");
    }

    /**
     * Method to load content in SOLR and MongoDB
     *
     * @param args solrHome parameter holding "solr.solr.home" mongoHost the host the mongoDB is located mongoPort the
     *             port mongoDB listens databaseName the databaseName to save collectionName the name of the collection
     *             (an XML, a folder or a Zip file)
     */
    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("/corelib-solr-context.xml");

        SolrServer solrServer = context.getBean("corelib_solr_solrEmbedded", SolrServer.class);
        EdmMongoServer mongoDBServer = context.getBean("corelib_solr_mongoServer", EdmMongoServer.class);

        if ((solrServer != null) && (mongoDBServer != null)) {
            ContentLoader contentLoader = null;
            try {
                contentLoader = ContentLoader.getInstance();
                String COLLECTION = "corelib/corelib-solr/src/test/resources/records-test-update.zip";
                contentLoader.readRecords(COLLECTION);
                contentLoader.parse(mongoDBServer, solrServer);
                contentLoader.commit(solrServer);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (contentLoader != null) {
                    contentLoader.cleanFiles();
                }
            }
        }
    }
}
