package nostr.event.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import nostr.event.tag.TakeTag;

import java.io.IOException;

public class TakeTagSerializer extends JsonSerializer<TakeTag> {
    @Override
    public void serialize(TakeTag tokenTag, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        jsonGenerator.writeStartArray();
        jsonGenerator.writeString(tokenTag.getCode());
        jsonGenerator.writeString(tokenTag.getIntentEventId());
        jsonGenerator.writeNumber(tokenTag.getVolume());
        jsonGenerator.writeEndArray();
    }
}
