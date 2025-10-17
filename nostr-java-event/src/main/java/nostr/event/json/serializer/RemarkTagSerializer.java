package nostr.event.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import nostr.event.tag.AccountTag;
import nostr.event.tag.RemarkTag;

import java.io.IOException;

public class RemarkTagSerializer extends JsonSerializer<RemarkTag> {
    @Override
    public void serialize(RemarkTag tag, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        jsonGenerator.writeString(tag.getCode());
        jsonGenerator.writeString(tag.getAddress());
        jsonGenerator.writeString(tag.getNftId());
        jsonGenerator.writeNumber(tag.getChainId());
        jsonGenerator.writeString(tag.getName());
        jsonGenerator.writeString(tag.getCreatedBy());
        jsonGenerator.writeString(tag.getPubkey());
        jsonGenerator.writeEndArray();
    }
}
