package nostr.event.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import nostr.event.tag.TlsnProofTag;

import java.io.IOException;

public class TlsnProofTagSerializer extends JsonSerializer<TlsnProofTag> {

    @Override
    public void serialize(TlsnProofTag t, JsonGenerator g, SerializerProvider serializers) throws IOException {
        g.writeStartArray();
        g.writeString(t.getCode());
        g.writeString(t.getPaymentMethod());
        g.writeString(t.getPaymentId());
        g.writeString(t.getPayeeDetails());
        g.writeString(t.getAmount());
        g.writeString(t.getCurrency());
        g.writeString(t.getConfirmationTs());
        g.writeString(t.getTradeId());
        g.writeString(t.getSignature());
        g.writeEndArray();
    }
}
