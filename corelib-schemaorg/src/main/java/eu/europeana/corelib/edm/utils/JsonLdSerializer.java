package eu.europeana.corelib.edm.utils;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import eu.europeana.corelib.edm.model.schemaorg.Text;
import eu.europeana.corelib.edm.model.schemaorg.Thing;
import ioinformarics.oss.jackson.module.jsonld.JsonldModule;
import ioinformarics.oss.jackson.module.jsonld.JsonldResource;
import ioinformarics.oss.jackson.module.jsonld.JsonldResourceBuilder;

public class JsonLdSerializer {
    ObjectMapper mapper = new ObjectMapper();

    /**
     * This method provides full serialization
     * @param object
     * @return full user set view
     * @throws IOException
     */
    public String serialize(Thing object) throws IOException {
    	init();
        JsonldResourceBuilder<Thing> jsonResourceBuilder = createResourceBuilder(object);
        return mapper.writer().writeValueAsString(jsonResourceBuilder.build(object));
    }

	/**
     * This method provides full serialization
     * @param objects
     * @return full user set view
     * @throws IOException
     */
    public String serialize(List<Thing> objects) throws IOException {
        init();
        JsonldResourceBuilder<List<Thing>> jsonResourceBuilder = createResourceBuilder(objects);
        return mapper.writer().writeValueAsString(jsonResourceBuilder.build(objects));
    }

    private JsonldResourceBuilder<Thing> createResourceBuilder(Thing object) {
		JsonldResourceBuilder<Thing> jsonResourceBuilder = JsonldResource.Builder.create();
		jsonResourceBuilder.context("http://schema.org");
        return jsonResourceBuilder;
	}

	private JsonldResourceBuilder<List<Thing>> createResourceBuilder(List<Thing> objects) {
		JsonldResourceBuilder<List<Thing>> jsonResourceBuilder = JsonldResource.Builder.create();
        jsonResourceBuilder.context("http://schema.org");
		return jsonResourceBuilder;
	}

    private void init() {
        JsonldModule module = new JsonldModule();
        module.addSerializer(new TextSerializer(Text.class));
        mapper.registerModule(module);
        //TODO: Sergiu actually we don't have any properties using java type Date 
        mapper.setDateFormat(new ISO8601DateFormat());
    }
}
