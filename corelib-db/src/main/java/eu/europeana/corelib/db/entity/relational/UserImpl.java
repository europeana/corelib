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
import eu.europeana.corelib.definitions.db.entity.relational.ApiKey;
import eu.europeana.corelib.definitions.db.entity.relational.SavedItem;
import eu.europeana.corelib.definitions.db.entity.relational.SavedSearch;
import eu.europeana.corelib.definitions.db.entity.relational.SocialTag;
import eu.europeana.corelib.definitions.db.entity.relational.User;
import eu.europeana.corelib.definitions.db.entity.relational.abstracts.IdentifiedEntity;
import eu.europeana.corelib.definitions.users.Role;
import eu.europeana.corelib.utils.DateUtils;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
@Entity
@NamedQueries({
	@NamedQuery(name=UserImpl.QUERY_FINDBY_EMAIL, query="from UserImpl u where u.email = ?"),
	@NamedQuery(name=UserImpl.QUERY_FINDBY_APIKEY, query="from UserImpl u where u.apiKey = ?"),
	@NamedQuery(name=UserImpl.QUERY_FINDBY_NAME, query="from UserImpl u where u.userName = ?")
})
@Table(name = RelationalDatabase.TABLENAME_USER)
public class UserImpl implements IdentifiedEntity<Long>, RelationalDatabase, User {
	private static final long serialVersionUID = 3830841148649674534L;

	public static final String QUERY_FINDBY_EMAIL = "User.findByEmail";
	public static final String QUERY_FINDBY_APIKEY = "User.findByApiKey";
	public static final String QUERY_FINDBY_NAME = "User.findByName";

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
	
	@Column 
	private Boolean disclaimer;
	
	@Column(length= FIELDSIZE_NAME)
	private String firstName;
	
	@Column(length= FIELDSIZE_SURNAME)
	private String lastName;
	
	@Column(length=FIELDSIZE_COMPANY)
	private String company;
	
	@Column(length=FIELDSIZE_COUNTRY)
	private String country;
	
	@Column(length=FIELDSIZE_PHONE)
	private String phone;
	
	@Column(length=FIELDSIZE_ADDRESS)
	private String address;
	
	@Column(length=FIELDSIZE_WEBSITE)
	private String website;
	
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

	@OneToMany(targetEntity=ApiKeyImpl.class, cascade = CascadeType.ALL, fetch=FetchType.EAGER, orphanRemoval=true)
	@JoinColumn(name = "userid", nullable = false)
	private Set<ApiKey> apiKeys = new HashSet<ApiKey>();

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
		return DateUtils.clone(registrationDate);
	}

	@Override
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = DateUtils.clone(registrationDate);
	}

	@Override
	public Date getLastLogin() {
		return DateUtils.clone(lastLogin);
	}

	@Override
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = DateUtils.clone(lastLogin);
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

	@Override
	public Set<ApiKey> getApiKeys() {
		return apiKeys;
	}
	@Override
	public Boolean getDisclaimer() {
		return disclaimer;
	}
	@Override
	public void setDisclaimer(Boolean disclaimer) {
		this.disclaimer = disclaimer;
	}
	@Override
	public String getFirstName() {
		return firstName;
	}
	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	@Override
	public String getLastName() {
		return lastName;
	}
	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@Override
	public String getCompany() {
		return company;
	}
	@Override
	public void setCompany(String company) {
		this.company = company;
	}
	@Override
	public String getCountry() {
		return country;
	}
	@Override
	public void setCountry(String country) {
		this.country = country;
	}
	@Override
	public String getPhone() {
		return phone;
	}
	@Override
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Override
	public String getAddress() {
		return address;
	}
	@Override
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String getWebsite() {
		return website;
	}
	@Override
	public void setWebsite(String website) {
		this.website = website;
	}
}