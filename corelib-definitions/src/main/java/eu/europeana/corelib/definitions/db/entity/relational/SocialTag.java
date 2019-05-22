package eu.europeana.corelib.definitions.db.entity.relational;

import eu.europeana.corelib.definitions.db.entity.relational.abstracts.EuropeanaUserObject;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * @deprecated January 2018 OAuth and MyEuropeana are no longer being used*
 */
@Deprecated
public interface SocialTag extends EuropeanaUserObject, Comparable<SocialTag> {

	String QUERY_CREATECLOUD_BYUSER = "SocialTag.createCloudByUser";
	String QUERY_FINDBY_USER_TAG = "SocialTag.FindByUserAndTag";
	String QUERY_FINDBY_USER_EUROPEANAID = "SocialTag.FindByUserAndEuropeanaId";
	String QUERY_FINDBY_USER_TAG_EUROPEANAID = "SocialTag.FindByUserAndTagAndEuropeanaId";

	String getTag();

	void setTag(String tag);
}