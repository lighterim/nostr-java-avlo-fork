package nostr.event.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import nostr.event.tag.AddressBookTag;

import java.io.IOException;

public class AddressBookTagSerializer extends JsonSerializer<AddressBookTag> {
    @Override
    public void serialize(AddressBookTag tag, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
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
