package eu.europeana.corelib.edm.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import eu.europeana.corelib.edm.model.schema.org.Text;

import java.io.IOException;

public class TextSerializer extends StdSerializer<Text> {
    protected TextSerializer(Class<Text> t) {
        super(t);
    }

    public void serialize(Text value, JsonGenerator jgen,
                          SerializerProvider provider)
            throws IOException
    {
        jgen.writeString(value.toString());
    }
}
