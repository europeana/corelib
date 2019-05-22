package eu.europeana.corelib.utils.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import eu.europeana.corelib.utils.service.MltStopwordsService;
import org.apache.log4j.Logger;

/**
 * @deprecated July 2017 MoreLikeThis / SimilarItems for records is no longer being used (and doesn't work properly)
 */
@Deprecated
public class MltStopwordsServiceImpl implements MltStopwordsService {

	private final File stopwordFile;

	/**
	 * The frequency (in minutes) of rereading the opt-out list.
	 */
	private static final int CHECK_FREQUENCY_IN_MINUTE = 5;

	/**
	 * The time of file last check
	 */
	private Calendar lastCheck;

	/**
	 * The list of opted-out datasets
	 */
	private List<String> stopwords = new ArrayList<>();

	public MltStopwordsServiceImpl(File stopwordFile) {
		this.stopwordFile = stopwordFile;
		reloadStopwords();
	}

	@Override
	public boolean check(String word) {
		if (StringUtils.isNotBlank(word)) {
			return stopwords.contains(StringUtils.lowerCase(word));
		}
		return false;
	}

	private void reloadStopwords() {
		Calendar timeout = DateUtils.toCalendar(DateUtils.addMinutes(new Date(), -CHECK_FREQUENCY_IN_MINUTE));

		if (lastCheck == null || lastCheck.before(timeout)) {
			stopwords.clear();

			LineIterator it = null;
			try {
				it = FileUtils.lineIterator(stopwordFile, "UTF-8");
				while (it.hasNext()) {
					String line = it.nextLine();
					stopwords.add(StringUtils.lowerCase(line));
				}
			} catch (IOException e) {
				Logger.getLogger(MltStopwordsServiceImpl.class).error("Error reading stopwords", e);
			} finally {
				LineIterator.closeQuietly(it);
			}

			lastCheck = Calendar.getInstance();
		}
	}

}
