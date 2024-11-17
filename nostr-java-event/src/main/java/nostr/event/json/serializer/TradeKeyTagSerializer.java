package nostr.event.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import nostr.event.tag.TradeKeyTag;

import java.io.IOException;

public class TradeKeyTagSerializer extends JsonSerializer<TradeKeyTag> {

    @Override
    public void serialize(TradeKeyTag value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartArray();
        gen.writeString(value.getCode());
        gen.writeString(value.getKeyForBuyer());
        gen.writeString(value.getKeyForSeller());
        gen.writeString(value.getKeyForWitness());
        gen.writeString(value.getKeyForSomeone());
        gen.writeString(value.getPubkey());
        gen.writeEndArray();
    }
}
