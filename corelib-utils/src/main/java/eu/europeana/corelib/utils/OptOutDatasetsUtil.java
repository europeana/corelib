package eu.europeana.corelib.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.lang.time.DateUtils;

public class OptOutDatasetsUtil {

	/**
	 * The frequency (in minutes) of rereading the opt-out list.
	 */
	final static int CHECK_FREQUENCY_IN_MINUTE = 5;

	/**
	 * The class logger
	 */
	private static Logger log = Logger.getLogger(OptOutDatasetsUtil.class.getCanonicalName());

	/**
	 * The time of file last check
	 */
	private static Calendar lastCheck;

	/**
	 * The list of opted-out datasets
	 */
	private static Map<String, Integer> optOutDatasets = null;

	/**
	 * Gets the list of opted-out datasets
	 * 
	 * @return The list of opted-out datasets
	 */
	public static Map<String, Integer> getOptOutDatasets() {
		return optOutDatasets;
	}

	/**
	 * Sets/refreshes the list of opted-out dataset from the file
	 * 
	 * @param optOutList
	 *            The file name of the opt-out list (absolute path)
	 */
	public static void setOptOutDatasets(String optOutList) {
		Calendar timeout = DateUtils.toCalendar(DateUtils.addMinutes(new Date(), -CHECK_FREQUENCY_IN_MINUTE));

		if (optOutDatasets == null 
				|| lastCheck == null
				|| lastCheck.before(timeout)) {
			log.info("Reading in opt-out dataset.");
			optOutDatasets = new HashMap<String, Integer>();
			DataInputStream in = null;
			BufferedReader br = null;
			try {
				in = new DataInputStream(new FileInputStream(optOutList));
				br = new BufferedReader(new InputStreamReader(in));
				String strLine;
				while ((strLine = br.readLine()) != null) {
					if (strLine.indexOf("_") <= 0) {
						continue;
					}
					String collectionId = strLine.substring(0,
							strLine.indexOf("_"));
					optOutDatasets.put(collectionId, 1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			lastCheck = Calendar.getInstance();
		}
	}

	/**
	 * Checks whether a full doc URL contains an opted out dataset
	 * 
	 * @param fullDocUrl
	 *            The full doc URL in the form of
	 *            .../portal/record/[collectionId]/...
	 * 
	 * @return TRUE if it is opted out, otherwise FALSE
	 */

	public static boolean checkByFullDocUrl(String fullDocUrl) {
		String collectionId = fullDocUrl.substring(15,
				fullDocUrl.indexOf("/", 15));
		return checkCollectionId(collectionId);
	}

	/**
	 * Checks whether a document ID refers to an opted out dataset
	 * 
	 * @param id
	 *            The document ID in the following format:
	 *            http://www.europeana.eu/portal/record/[collectionId]/...
	 * 
	 * @return TRUE if it is opted out, otherwise FALSE
	 */
	public static boolean checkByFullId(String id) {
		String collectionId = id.substring(39, id.indexOf("/", 39));
		return checkCollectionId(collectionId);
	}

	public static boolean checkById(String id) {
		String collectionId = id.substring(1, id.indexOf("/", 1));
		return checkCollectionId(collectionId);
	}

	/**
	 * Checks whether a collectionId refers to an opted-out dataset
	 * 
	 * @param collectionId
	 *            The collection ID
	 * 
	 * @return TRUE if it is opted out, otherwise FALSE
	 */
	public static boolean checkCollectionId(String collectionId) {
		return optOutDatasets.containsKey(collectionId);
	}
}
