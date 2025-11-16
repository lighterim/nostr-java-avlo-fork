package nostr.event.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import nostr.event.tag.AccountTag;

import java.io.IOException;

public class AccountTagSerializer extends JsonSerializer<AccountTag> {
    @Override
    public void serialize(AccountTag tag, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        jsonGenerator.writeString(tag.getCode());
        jsonGenerator.writeString(tag.getNftId());
        jsonGenerator.writeNumber(tag.getChainId());
        jsonGenerator.writeString(tag.getTba());
        jsonGenerator.writeString(tag.getNostrPubKey());
        jsonGenerator.writeString(tag.getIpfsHash());
        jsonGenerator.writeEndArray();
    }
}
