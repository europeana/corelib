package eu.europeana.corelib.edm.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import eu.europeana.corelib.edm.model.schema.org.CreativeWork;
import eu.europeana.corelib.edm.model.schema.org.Text;
import eu.europeana.corelib.edm.model.schema.org.Thing;
import ioinformarics.oss.jackson.module.jsonld.JsonldModule;
import ioinformarics.oss.jackson.module.jsonld.JsonldResource;
import ioinformarics.oss.jackson.module.jsonld.JsonldResourceBuilder;
import org.codehaus.jackson.map.ser.StdSerializerProvider;

import java.io.IOException;

public class JsonLdSerializer {
    ObjectMapper mapper = new ObjectMapper();

    /**
     * This method provides full serialization
     * @param object
     * @return full user set view
     * @throws IOException
     */
    public String serialize(Thing object) throws IOException {
        JsonldModule module = new JsonldModule();
        module.addSerializer(new TextSerializer(Text.class));
        mapper.registerModule(module);
        mapper.setDateFormat(new ISO8601DateFormat());
        JsonldResourceBuilder<Thing> jsonResourceBuilder = JsonldResource.Builder.create();
        jsonResourceBuilder.context("http://schema.org");
        String jsonString = mapper.writer().writeValueAsString(jsonResourceBuilder.build(object));
        return jsonString;
    }
}
