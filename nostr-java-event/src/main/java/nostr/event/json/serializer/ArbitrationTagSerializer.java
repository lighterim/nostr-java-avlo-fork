package nostr.event.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import nostr.event.tag.ArbitrationTag;

import java.io.IOException;

public class ArbitrationTagSerializer extends JsonSerializer<ArbitrationTag> {

    @Override
    public void serialize(ArbitrationTag t, JsonGenerator g, SerializerProvider serializers) throws IOException {
        g.writeStartArray();
        g.writeString(t.getCode());
        g.writeString(t.getArbitrator());
        g.writeNumber(t.getBuyerThresholdBp());
        g.writeString(t.getSignature());
        g.writeEndArray();
    }
}
