package eu.europeana.corelib.edm.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import eu.europeana.corelib.edm.model.schema.org.Text;
import eu.europeana.corelib.edm.model.schema.org.Thing;
import ioinformarics.oss.jackson.module.jsonld.JsonldModule;
import ioinformarics.oss.jackson.module.jsonld.JsonldResource;
import ioinformarics.oss.jackson.module.jsonld.JsonldResourceBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonLdSerializer {
    ObjectMapper mapper = new ObjectMapper();

    /**
     * This method provides full serialization
     * @param object
     * @return full user set view
     * @throws IOException
     */
    public String serialize(Thing object) throws IOException {
        List<Thing> objects = new ArrayList<>();
        objects.add(object);
        return serialize(objects);
    }

    /**
     * This method provides full serialization
     * @param objects
     * @return full user set view
     * @throws IOException
     */
    public String serialize(List<Thing> objects) throws IOException {
        init();
        JsonldResourceBuilder<List<Thing>> jsonResourceBuilder = JsonldResource.Builder.create();
        jsonResourceBuilder.context("http://schema.org");
        return mapper.writer().writeValueAsString(jsonResourceBuilder.build(objects));
    }

    private void init() {
        JsonldModule module = new JsonldModule();
        module.addSerializer(new TextSerializer(Text.class));
        mapper.registerModule(module);
        mapper.setDateFormat(new ISO8601DateFormat());
    }
}
