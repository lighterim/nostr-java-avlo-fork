package nostr.event.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import nostr.event.tag.CreatedByTag;

import java.io.IOException;

public class CreatedByTagSerializer extends JsonSerializer<CreatedByTag> {

    @Override
    public void serialize(CreatedByTag t, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartArray();
        gen.writeString(t.getCode());
        gen.writeString(t.getTakeIntentEventId());
        gen.writeString(t.getNip05());
        gen.writeString(t.getPubkey());
        gen.writeNumber(t.getTradeId());
        gen.writeEndArray();
    }
}
