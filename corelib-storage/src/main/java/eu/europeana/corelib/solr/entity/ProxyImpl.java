package eu.europeana.corelib.solr.entity;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.mongodb.morphia.annotations.Converters;
import org.mongodb.morphia.annotations.Entity;
import com.fasterxml.jackson.annotation.JsonInclude;
import eu.europeana.corelib.definitions.edm.entity.Proxy;
import eu.europeana.corelib.definitions.solr.DocType;

/**
 * @author Yorgos.Mamakis@ kb.nl
 */
@JsonInclude(Include.NON_EMPTY)
@Entity("Proxy")
@Converters(DocType.DocTypeConverter.class)
public class ProxyImpl extends BasicProxyImpl implements Proxy {

	private DocType edmType;

	private Map<String,List<String>> year;

	private Map<String,List<String>> userTags;

	private boolean europeanaProxy;

	@Override
	public void setEdmType(DocType edmType) {
		this.edmType = edmType;
	}

	@Override
	public DocType getEdmType() {
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
