/*
 * Copyright 2007 EDL FOUNDATION
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they 
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "License");
 * you may not use this work except in compliance with the
 * License.
 * You may obtain a copy of the License at:
 *
 * http://ec.europa.eu/idabc/eupl
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the License is
 * distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 */

package eu.europeana.corelib.db.entity;

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

import eu.europeana.corelib.db.entity.abstracts.IdentifiedEntity;
import eu.europeana.corelib.definitions.db.DatabaseDefinition;
import eu.europeana.corelib.definitions.users.Role;

/**
 * @author Willem-Jan Boogerd <europeana [at] eledge.net>
 */
@Entity
@NamedQueries({
	@NamedQuery(name=User.QUERY_FINDBY_EMAIL, query="from User u where u.email = ?")
})
@Table(name = DatabaseDefinition.TABLENAME_USER)
public class User implements IdentifiedEntity<Long>, DatabaseDefinition {
	private static final long serialVersionUID = 3830841148649674534L;
	
	public static final String QUERY_FINDBY_EMAIL = "User.findByEmail";

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

	@Column(length = FIELDSIZE_IDENTIFIER)
	@Index(name = "apikey_index")
	private String apiKey;

	@Column
	@Temporal(TemporalType.DATE)
	private Date registrationDate;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLogin;

	@Column(length = 25)
	@Enumerated(EnumType.STRING)
	private Role role = Role.ROLE_USER;

    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name = "userid", nullable = false)
    private Set<SavedItem> savedItems = new HashSet<SavedItem>();

    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER, orphanRemoval=true)
    @JoinColumn(name = "userid", nullable = false)
    private Set<SavedSearch> savedSearches = new HashSet<SavedSearch>();

    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name = "userid", nullable = false)
    private Set<SocialTag> socialTags = new HashSet<SocialTag>();

	/**
	 * GETTERS & SETTTERS
	 */

	@Override
	public Long getId() {
		return id;
	}

	public void setEmail(String email) {
		this.email = StringUtils.lowerCase(email);
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Set<SavedItem> getSavedItems() {
		return savedItems;
	}
	
	public Set<SavedSearch> getSavedSearches() {
		return savedSearches;
	}
	
	public Set<SocialTag> getSocialTags() {
		return socialTags;
	}
	
}
