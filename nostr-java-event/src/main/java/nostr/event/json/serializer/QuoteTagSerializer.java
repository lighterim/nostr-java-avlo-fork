package nostr.event.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import nostr.event.tag.PaymentTag;
import nostr.event.tag.QuoteTag;

import java.io.IOException;

public class QuoteTagSerializer extends JsonSerializer<QuoteTag> {
    @Override

    public void serialize(QuoteTag t, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        jsonGenerator.writeString(t.getCode());
        jsonGenerator.writeNumber(t.getNumber().stripTrailingZeros());
        jsonGenerator.writeString(t.getCurrency());
        jsonGenerator.writeNumber(t.getUsdRate().stripTrailingZeros());
        jsonGenerator.writeEndArray();
    }
}
