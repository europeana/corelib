package eu.europeana.corelib.db.entity.relational;

import eu.europeana.corelib.definitions.db.entity.RelationalDatabase;
import eu.europeana.corelib.definitions.db.entity.relational.*;
import eu.europeana.corelib.definitions.db.entity.relational.abstracts.IdentifiedEntity;
import eu.europeana.corelib.definitions.users.Role;
import eu.europeana.corelib.utils.DateUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.util.*;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * @deprecated January 2018 OAuth and MyEuropeana are no longer being used
 */
@Entity
@NamedQueries({
		@NamedQuery(name = User.QUERY_FINDBY_EMAIL, query = "select u from UserImpl u where u.email = ?"),
		@NamedQuery(name = User.QUERY_FINDBY_NAME, query = "select u from UserImpl u where u.userName = ?")
})
@Table(name = RelationalDatabase.TABLENAME_USER)
@Deprecated
public class UserImpl implements IdentifiedEntity<Long>, RelationalDatabase, User {
	private static final long serialVersionUID = 3830841148649674534L;

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

	@Column
	@Temporal(TemporalType.DATE)
	private Date registrationDate;

	@Column
	private Date activationDate;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLogin;

	@Column(length = FIELDSIZE_NAME)
	private String firstName;

	@Column(length = FIELDSIZE_SURNAME)
	private String lastName;

	@Column(length = FIELDSIZE_COMPANY)
	private String company;

	@Column(length = FIELDSIZE_COUNTRY)
	private String country;

	@Column(length = FIELDSIZE_PHONE)
	private String phone;

	@Column(length = FIELDSIZE_ADDRESS)
	private String address;

	@Column(length = FIELDSIZE_WEBSITE)
	private String website;

	@Column(length = FIELDSIZE_ROLE)
	@Enumerated(EnumType.STRING)
	private Role role = Role.ROLE_USER;

	@Column(length = FIELDSIZE_FIELDOFWORK)
	private String fieldOfWork;

	@Column(length = FIELDSIZE_LANGUAGEPORTAL)
	private String languagePortal;

	@Column(length = FIELDSIZE_LANGUAGEITEM)
	private String languageItem;

	@Column(length = FIELDSIZE_LANGUAGESEARCH)
	private String languageSearch;

	@Column(columnDefinition = "boolean default true")
	private Boolean languageSearchApplied;

	@OneToMany(targetEntity = SavedItemImpl.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name = "userid", nullable = false)
	@Fetch(FetchMode.SELECT)
	private Set<SavedItem> savedItems = new HashSet<>();

	@OneToMany(targetEntity = SavedSearchImpl.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name = "userid", nullable = false)
	@Fetch(FetchMode.SELECT)
	private Set<SavedSearch> savedSearches = new HashSet<>();

	@OneToMany(targetEntity = SocialTagImpl.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name = "userid", nullable = false)
	@Fetch(FetchMode.SELECT)
	private Set<SocialTag> socialTags = new HashSet<>();

	/**
	 * GETTERS & SETTTERS
	 */

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public void setEmail(String email) {
		this.email = StringUtils.lowerCase(email);
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
	public List<SocialTag> getSocialTagsOrdered() {
		List<SocialTag> list = new ArrayList<>(socialTags);

		Comparator<SocialTag> alphabetical = new Comparator<SocialTag>() {
			public int compare(SocialTag s1, SocialTag s2) {
				return s1.getTag().compareTo(s2.getTag());
			}
		};
		Collections.sort(list, alphabetical);
		return list;
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

	@Override
	public String getFieldOfWork() {
		return fieldOfWork;
	}

	@Override
	public void setFieldOfWork(String fieldOfWork) {
		this.fieldOfWork = fieldOfWork;
	}

	@Override
	public String[] getLanguageSearch() {
		if (StringUtils.isNotBlank(this.languageSearch)) {
			return StringUtils.split(languageSearch, SEARCH_LANGUAGES_SEPARATOR);
		}
		return ArrayUtils.EMPTY_STRING_ARRAY;
	}

	@Override
	public void setLanguageSearch(String... languageCodes) {
		if (languageCodes != null) {
			if ((languageCodes.length == 1) &&
					StringUtils.contains(languageCodes[0], SEARCH_LANGUAGES_SEPARATOR)) {
				languageSearch = StringUtils.trimToNull(
						StringUtils.lowerCase(languageCodes[0]));
			} else {
				languageSearch = StringUtils.trimToNull(
						StringUtils.lowerCase(
								StringUtils.join(languageCodes, SEARCH_LANGUAGES_SEPARATOR)));
			}
		}
	}

	@Override
	public String getLanguageItem() {
		return languageItem;
	}

	@Override
	public void setLanguageItem(String languageCode) {
		languageItem = StringUtils.trimToNull(StringUtils.lowerCase(languageCode));
	}

	@Override
	public String getLanguagePortal() {
		return languagePortal;
	}

	@Override
	public void setLanguagePortal(String languageCode) {
		languagePortal = StringUtils.trimToNull(StringUtils.lowerCase(languageCode));
	}

	@Override
	public Boolean getLanguageSearchApplied() {
		return languageSearchApplied;
	}

	@Override
	public void setLanguageSearchApplied(Boolean languageSearchApplied) {
		this.languageSearchApplied = languageSearchApplied;
	}

	@Override
	public Date getActivationDate() {
		return activationDate;
	}

	@Override
	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}
}