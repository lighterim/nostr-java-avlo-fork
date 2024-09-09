package nostr.event.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import nostr.event.tag.CreatedByTag;
import nostr.event.tag.LedgerTag;

import java.io.IOException;

public class LedgerTagSerializer extends JsonSerializer<LedgerTag> {

    @Override
    public void serialize(LedgerTag value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartArray();
        gen.writeString(value.getCode());
        gen.writeString(value.getChain());
        gen.writeString(value.getNetwork());
        gen.writeString(value.getTxId());
        gen.writeString(value.getTxUrl());
        gen.writeString(value.getTradeStatus().name());
        gen.writeEndArray();
    }
}
