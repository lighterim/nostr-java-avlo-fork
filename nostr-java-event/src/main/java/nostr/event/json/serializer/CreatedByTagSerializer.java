package nostr.event.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import nostr.event.tag.CreatedByTag;

import java.io.IOException;

public class CreatedByTagSerializer extends JsonSerializer<CreatedByTag> {

    @Override
    public void serialize(CreatedByTag value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartArray();
        gen.writeString(value.getCode());
        gen.writeString(value.getTakeIntentEventId());
        gen.writeString(value.getNip05());
        gen.writeString(value.getPubkey());
        gen.writeEndArray();
    }
}
