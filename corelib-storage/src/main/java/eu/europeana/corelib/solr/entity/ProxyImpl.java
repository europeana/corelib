package eu.europeana.corelib.solr.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import dev.morphia.annotations.Entity;
import eu.europeana.corelib.definitions.edm.entity.Proxy;
import eu.europeana.corelib.definitions.solr.DocType;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Yorgos.Mamakis@ kb.nl
 */
@JsonInclude(Include.NON_EMPTY)
@Entity(value = "Proxy", useDiscriminator = false)
public class ProxyImpl extends BasicProxyImpl implements Proxy {

	private String edmType;

	private Map<String,List<String>> year;

	private Map<String,List<String>> userTags;

	private boolean europeanaProxy;

	@Override
	public void setEdmType(String edmType) {
		this.edmType = Optional.ofNullable(DocType.safeValueOf(edmType)).map(DocType::getEnumNameValue)
				.orElse(null);
	}

	@Override
	public String getEdmType() {
		return this.edmType;
	}

	@Override
	public boolean isEuropeanaProxy() {
		return europeanaProxy;
	}

	@Override
	public void setEuropeanaProxy(boolean europeanaProxy) {
		this.europeanaProxy = europeanaProxy;
	}

	@Override
	public Map<String,List<String>> getYear() {
		return year;
	}

	@Override
	public void setYear(Map<String,List<String>> year) {
		this.year = year;
	}

	@Override
	public Map<String,List<String>> getUserTags() {
		return userTags;
	}

	@Override
	public void setUserTags(Map<String,List<String>> userTags) {
		this.userTags = userTags;
	}
}
