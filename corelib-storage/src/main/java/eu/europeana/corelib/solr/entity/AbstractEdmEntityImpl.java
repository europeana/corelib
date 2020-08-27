package eu.europeana.corelib.solr.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.morphia.annotations.Field;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Index;
import dev.morphia.annotations.IndexOptions;
import dev.morphia.annotations.Indexes;
import eu.europeana.corelib.definitions.edm.entity.AbstractEdmEntity;
import org.bson.types.ObjectId;

/**
 * @see AbstractEdmEntity
 * 
 * @author Yorgos.Mamakis@ kb.nl
 */
@Indexes({
		@Index(fields = {@Field("about")}, options = @IndexOptions(unique = true))})
public class AbstractEdmEntityImpl implements AbstractEdmEntity {

	protected String about;

	@Id
	protected ObjectId id = new ObjectId();

	@JsonIgnore
	@Override
	public ObjectId getId() {
		return this.id;
	}

	@Override
	public void setId(ObjectId id) {
		this.id = id;
	}

	@Override
	public String getAbout() {
		return this.about;
	}

	@Override
	public void setAbout(String about) {
		this.about = about;
	}
}
