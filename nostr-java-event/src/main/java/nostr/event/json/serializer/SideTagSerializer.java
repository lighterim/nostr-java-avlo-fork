package nostr.event.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import nostr.event.tag.SideTag;

import java.io.IOException;

public class SideTagSerializer extends JsonSerializer<SideTag> {
    @Override
    public void serialize(SideTag sideTag, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        jsonGenerator.writeString(sideTag.getCode());
        jsonGenerator.writeString(sideTag.getSide());
        jsonGenerator.writeEndArray();
    }
}
