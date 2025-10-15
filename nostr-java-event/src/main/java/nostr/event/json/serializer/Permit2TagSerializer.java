package nostr.event.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import nostr.event.tag.AccountTag;
import nostr.event.tag.Permit2Tag;

import java.io.IOException;

public class Permit2TagSerializer extends JsonSerializer<Permit2Tag> {
    @Override
    public void serialize(Permit2Tag tag, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        jsonGenerator.writeString(tag.getCode());
        jsonGenerator.writeString(tag.getNonce());
        jsonGenerator.writeString(tag.getSignature());
        jsonGenerator.writeEndArray();
    }
}
