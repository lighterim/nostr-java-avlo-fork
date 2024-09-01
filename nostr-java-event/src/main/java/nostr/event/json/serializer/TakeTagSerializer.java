package nostr.event.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import nostr.event.tag.TakeTag;

import java.io.IOException;

public class TakeTagSerializer extends JsonSerializer<TakeTag> {
    @Override
    public void serialize(TakeTag takeTag, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        jsonGenerator.writeStartArray();
        jsonGenerator.writeString(takeTag.getCode());
        jsonGenerator.writeString(takeTag.getSide().getSide());
        jsonGenerator.writeString(takeTag.getIntentEventId());
        jsonGenerator.writeString(takeTag.getMakerNip05());
        jsonGenerator.writeString(takeTag.getMakerPubkey());
        jsonGenerator.writeNumber(takeTag.getVolume());
        jsonGenerator.writeString(takeTag.getTakerNip05());
        jsonGenerator.writeString(takeTag.getTakerPubkey());
        jsonGenerator.writeEndArray();
    }
}
