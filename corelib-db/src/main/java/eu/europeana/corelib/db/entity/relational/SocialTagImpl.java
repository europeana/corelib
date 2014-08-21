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

package eu.europeana.corelib.db.entity.relational;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import eu.europeana.corelib.db.entity.relational.abstracts.EuropeanaUserObjectImpl;
import eu.europeana.corelib.definitions.db.entity.RelationalDatabase;
import eu.europeana.corelib.definitions.db.entity.relational.SocialTag;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
@Entity
@Table(name = RelationalDatabase.TABLENAME_SOCIALTAGS)
@NamedQueries ({
	@NamedQuery(name=SocialTag.QUERY_CREATECLOUD_BYUSER,
			query="SELECT NEW eu.europeana.corelib.db.entity.relational.custom.TagCloudItem(e.tag, count(*)) FROM SocialTagImpl e WHERE e.user.id = ? GROUP BY e.tag"),
	@NamedQuery(name=SocialTag.QUERY_FINDBY_USER_TAG, query="SELECT e FROM SocialTagImpl e WHERE e.user.id = ? AND lower(e.tag) = ?"),
	@NamedQuery(name=SocialTag.QUERY_FINDBY_USER_EUROPEANAID, query="SELECT e FROM SocialTagImpl e WHERE e.user.id = ? AND e.europeanaUri = ?"),
	@NamedQuery(name=SocialTag.QUERY_FINDBY_USER_TAG_EUROPEANAID, query="SELECT e FROM SocialTagImpl e WHERE e.user.id = ? AND lower(e.tag) = ? AND e.europeanaUri = ?")
})
public class SocialTagImpl extends EuropeanaUserObjectImpl implements SocialTag {
	private static final long serialVersionUID = -3635227115883742004L;

	@Column(length = FIELDSIZE_TAG)
	private String tag;

	/**
	 * GETTERS & SETTTERS
	 */

	@Override
	public String getTag() {
		return tag;
	}

	@Override
	public void setTag(String tag) {
		this.tag = StringUtils.lowerCase(tag);
	}

	@Override
	public int compareTo(SocialTag o) {
		if (this.getDateSaved().before(o.getDateSaved())) {
			return 1;
		}
		if (this.getDateSaved().after(o.getDateSaved())) {
			return -1;
		}
		return 0;
	}
}