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

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Index;

import eu.europeana.corelib.definitions.db.entity.RelationalDatabase;
import eu.europeana.corelib.definitions.db.entity.relational.SavedItem;
import eu.europeana.corelib.definitions.db.entity.relational.SavedSearch;
import eu.europeana.corelib.definitions.db.entity.relational.SocialTag;
import eu.europeana.corelib.definitions.db.entity.relational.User;
import eu.europeana.corelib.definitions.db.entity.relational.abstracts.IdentifiedEntity;
import eu.europeana.corelib.definitions.users.Role;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
@Entity
@NamedQueries({
	@NamedQuery(name=UserImpl.QUERY_FINDBY_EMAIL, query="from UserImpl u where u.email = ?"),
	@NamedQuery(name=UserImpl.QUERY_FINDBY_APIKEY, query="from UserImpl u where u.apiKey = ?")
})
@Table(name = RelationalDatabase.TABLENAME_USER)
public class UserImpl implements IdentifiedEntity<Long>, RelationalDatabase, User {
	private static final long serialVersionUID = 3830841148649674534L;
	
	public static final String QUERY_FINDBY_EMAIL = "User.findByEmail";
	public static final String QUERY_FINDBY_APIKEY = "User.findByApiKey";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(length = FIELDSIZE_PERSONAL, unique = true, nullable = false)
	@Index(name = "email_index")
	private String email;

    @Column(length = FIELDSIZE_USERNAME)
    @Index(name = "username_index")
    private String userName;

	@Column(length = FIELDSIZE_PASSWORD)
	private String password;

	@Column(length = FIELDSIZE_APIKEY)
	@Index(name = "apikey_index")
	private String apiKey;

	@Column
	@Temporal(TemporalType.DATE)
	private Date registrationDate;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLogin;

	@Column(length = FIELDSIZE_ROLE)
	@Enumerated(EnumType.STRING)
	private Role role = Role.ROLE_USER;

    @OneToMany(targetEntity=SavedItemImpl.class, cascade = CascadeType.ALL, fetch=FetchType.EAGER, orphanRemoval=true)
    @JoinColumn(name = "userid", nullable = false)
    private Set<SavedItem> savedItems = new HashSet<SavedItem>();

    @OneToMany(targetEntity=SavedSearchImpl.class, cascade = CascadeType.ALL, fetch=FetchType.EAGER, orphanRemoval=true)
    @JoinColumn(name = "userid", nullable = false)
    private Set<SavedSearch> savedSearches = new HashSet<SavedSearch>();

    @OneToMany(targetEntity=SocialTagImpl.class, cascade = CascadeType.ALL, fetch=FetchType.EAGER, orphanRemoval=true)
    @JoinColumn(name = "userid", nullable = false)
    private Set<SocialTag> socialTags = new HashSet<SocialTag>();

	/**
	 * GETTERS & SETTTERS
	 */

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setEmail(String email) {
		this.email = StringUtils.lowerCase(email);
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getApiKey() {
		return apiKey;
	}

	@Override
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	@Override
	public Date getRegistrationDate() {
		return registrationDate;
	}

	@Override
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	@Override
	public Date getLastLogin() {
		return lastLogin;
	}

	@Override
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	@Override
	public Role getRole() {
		return role;
	}

	@Override
	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String getUserName() {
		return userName;
	}

	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Override
	public Set<SavedItem> getSavedItems() {
		return savedItems;
	}
	
	@Override
	public Set<SavedSearch> getSavedSearches() {
		return savedSearches;
	}
	
	@Override
	public Set<SocialTag> getSocialTags() {
		return socialTags;
	}
	
}
