package nostr.event.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import nostr.event.tag.PaymentTag;

import java.io.IOException;

public class PaymentTagSerializer extends JsonSerializer<PaymentTag> {
    @Override

    public void serialize(PaymentTag t, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        jsonGenerator.writeString(t.getCode());
        jsonGenerator.writeString(t.getMethod());
        jsonGenerator.writeString(t.getAccount());
        jsonGenerator.writeString(t.getQrCode());
        jsonGenerator.writeString(t.getMemo());
        jsonGenerator.writeEndArray();
    }
}
