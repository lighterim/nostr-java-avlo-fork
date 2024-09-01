package nostr.event.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import nostr.event.tag.MakeTag;

import java.io.IOException;

public class MakeTagSerializer extends JsonSerializer<MakeTag> {
    @Override
    public void serialize(MakeTag makeTag, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        jsonGenerator.writeString(makeTag.getCode());
        jsonGenerator.writeString(makeTag.getSide().getSide());
        jsonGenerator.writeString(makeTag.getMakerNip05());
        jsonGenerator.writeString(makeTag.getMakerPubkey());
        jsonGenerator.writeEndArray();
    }
}
