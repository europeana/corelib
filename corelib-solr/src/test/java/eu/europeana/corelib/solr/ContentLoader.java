package eu.europeana.corelib.solr;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.lang.StringUtils;

public class ContentLoader {

	private static String solrHome;
	private static String mongoHost="localhost";
	private static String mongoPort = "27017";
	private static String databaseName = "europeana";
	private static String collectionName = "09102_Ag_EU_MIMO_EDM.xml";
	
	/**
	 * Class to load content in SOLR and MongoDB
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, String> params = new HashMap<String,String>();
		for(String parameter:args){
			params.put(StringUtils.split(parameter,":")[0],StringUtils.split(parameter,":")[1]);
		}
		
		if(params.get("solrHome")!=null && params.get("solrHome").length()>0){
			solrHome = params.get("solrHome");
		}
		else{
			System.out.println("Parameter solrHome is not set correctly");
			System.out.println("Using default solrHome: " + solrHome);
		}
		
		if(params.get("mongoHost")!=null && params.get("mongoHost").length()>0){
			mongoHost = params.get("mongoHost");
		}
		else{
			System.out.println("Parameter mongoHost is not set correctly");
			System.out.println("Using default mongoHost: " + mongoHost);
		}
		
		if(params.get("mongoPort")!=null && params.get("mongoPort").length()>0){
			try{
				Integer.parseInt(mongoPort);
				mongoPort = params.get("mongoPort");
			}
			catch(NumberFormatException nbe){
				System.out.println("Parameter mongoPort is not set correctly");
				System.out.println("Using default mongoPort: " + mongoPort);
			}
		}
		else{
			System.out.println("Parameter mongoPort is not set correctly");
			System.out.println("Using default mongoPort: " + mongoPort);
		}
		
		if(params.get("databaseName")!=null && params.get("databaseName").length()>0){
			databaseName = params.get("databaseName");
		}
		else{
			System.out.println("Parameter databaseName is not set correctly");
			System.out.println("Using default databaseName: " + databaseName);
		}
		
		if(params.get("collectionName")!=null && params.get("collectionName").length()>0){
			collectionName = params.get("collectionName");
		}
		else{
			System.out.println("Parameter collectionName is not set correctly");
			System.out.println("Using default collectionName: " + collectionName);
		}
		
		File collectionXML = null;
		if(isZipped(collectionName)){
			collectionXML=unzip(collectionName);
		}
		else{
			collectionXML = new File(collectionName);
		}
		
		
	}
	
	private static File unzip(String collectionName) {
		 BufferedOutputStream dest = null;
		 String fileName="";
		try {
	        
	         FileInputStream fis = new FileInputStream(collectionName);
	         ZipInputStream zis = new 
	         ZipInputStream(new BufferedInputStream(fis));
	         ZipEntry entry;
	         while((entry = zis.getNextEntry()) != null) {
	            System.out.println("Extracting: " +entry);
	            int count;
	            byte data[] = new byte[2048];
	            fileName = entry.getName();
	            FileOutputStream fos = new FileOutputStream(entry.getName());
	            dest = new BufferedOutputStream(fos, 2048);
	            while ((count = zis.read(data, 0, 2048)) != -1) {
	               dest.write(data, 0, count);
	            }
	            dest.flush();
	            dest.close();
	            
	         }
	         zis.close();
	       
	      } catch(Exception e) {
	         System.out.println("The zip file is destroyed. Could not import records");
	      }
		return new File(fileName);
	}

	private static boolean isZipped(String collectionName){
		return StringUtils.endsWith(collectionName,".gzip")||StringUtils.endsWith(collectionName, "gzip");
	}
}
