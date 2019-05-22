package eu.europeana.corelib.search.loader;

import eu.europeana.corelib.mongo.server.EdmMongoServer;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.net.URI;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
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

    public int parse(final EdmMongoServer mongoDBServer, final SolrClient solrServer) {
        for (Path path : collectionXML) {
            // TODO JV this is where the file that is contained in the path should be indexed using the
            // Metis indexing library and the right versions for the Mongo and Solr connection.
        }
        return failed;
    }

    public void commit(final SolrClient solrServer) {
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
}
