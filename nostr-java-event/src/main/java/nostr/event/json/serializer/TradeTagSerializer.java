package nostr.event.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import nostr.event.tag.TradeTag;

import java.io.IOException;

public class TradeTagSerializer extends JsonSerializer<TradeTag> {


    @Override
    public void serialize(TradeTag tag, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        jsonGenerator.writeString(tag.getCode());
        jsonGenerator.writeString(String.valueOf(tag.getId()));
        jsonGenerator.writeString(tag.getStatus().getValue());
        jsonGenerator.writeString(tag.getEscrowHash());
        jsonGenerator.writeEndArray();
    }
}
