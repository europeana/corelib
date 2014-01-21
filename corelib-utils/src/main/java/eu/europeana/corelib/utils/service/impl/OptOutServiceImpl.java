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

import eu.europeana.corelib.utils.service.OptOutService;

public class OptOutServiceImpl implements OptOutService {

	private final File optOutList;

	/**
	 * The frequency (in minutes) of rereading the opt-out list.
	 */
	private final static int CHECK_FREQUENCY_IN_MINUTE = 5;

	/**
	 * The time of file last check
	 */
	private Calendar lastCheck;

	/**
	 * The list of opted-out datasets
	 */
	private List<String> optOutDatasets = new ArrayList<String>();

	public OptOutServiceImpl(File optOutList) {
		this.optOutList = optOutList;
		reloadOptOutDatasets();
	}

	@Override
	public boolean check(String id) {
		if (StringUtils.isNotBlank(id)) {
			if (StringUtils.contains(id, "record/")) {
				id = StringUtils.substringBetween(id, "record/", "/");
			} else if (StringUtils.contains(id, "/")) {
				id = StringUtils.substringBetween(id, "/");
			}
			return optOutDatasets.contains(StringUtils.lowerCase(id));
		}
		return false;
	}

	private void reloadOptOutDatasets() {
		Calendar timeout = DateUtils.toCalendar(DateUtils.addMinutes(new Date(), -CHECK_FREQUENCY_IN_MINUTE));

		if (optOutDatasets == null || lastCheck == null || lastCheck.before(timeout)) {
			optOutDatasets.clear();

			LineIterator it = null;
			try {
				it = FileUtils.lineIterator(optOutList, "UTF-8");
				while (it.hasNext()) {
					String line = it.nextLine();
					if (StringUtils.contains(line, "_")) {
						optOutDatasets.add(StringUtils.lowerCase(StringUtils.substringBefore(line, "_")));
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				LineIterator.closeQuietly(it);
			}

			lastCheck = Calendar.getInstance();
		}
	}

}
