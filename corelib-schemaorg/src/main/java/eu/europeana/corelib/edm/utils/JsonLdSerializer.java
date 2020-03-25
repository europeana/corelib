package eu.europeana.corelib.edm.utils;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import eu.europeana.corelib.edm.model.schemaorg.GraphRoot;
import eu.europeana.corelib.edm.model.schemaorg.Text;
import eu.europeana.corelib.edm.model.schemaorg.Thing;
import ioinformarics.oss.jackson.module.jsonld.JsonldModule;
import ioinformarics.oss.jackson.module.jsonld.JsonldResource;
import ioinformarics.oss.jackson.module.jsonld.JsonldResourceBuilder;

public class JsonLdSerializer {
    private static final String CONTEXT = "http://schema.org";

    private static final ObjectMapper mapper = new ObjectMapper();
    static {
        JsonldModule module = new JsonldModule();
        module.addSerializer(new TextSerializer(Text.class));
        mapper.registerModule(module);
        mapper.setDateFormat(new ISO8601DateFormat());
    }

    /**
     * Returns string with json-ld representation of the provided Thing
     * @param thing thing to serialize
     * @return string
     * @throws IOException when there is an error processing the thing
     */
    public String serialize(Thing thing) throws IOException {
        JsonldResourceBuilder<Thing> builder = JsonldResource.Builder.create();
        builder.context(CONTEXT);
        return mapper.writer().writeValueAsString(builder.build(thing));
    }

    /**
     * Returns string with json-ld representation of the provided list of Things
     * @param things list of things to serialize to json-ld
     * @return string
     * @throws IOException when there is an error processing the things
     */
    public String serialize(List<Thing> things) throws IOException {
        JsonldResourceBuilder<GraphRoot> builder = JsonldResource.Builder.create();
        builder.context(CONTEXT);
        return mapper.writer().writeValueAsString(builder.build(new GraphRoot(things)));
    }
}
