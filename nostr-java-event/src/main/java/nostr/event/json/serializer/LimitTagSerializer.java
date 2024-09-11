package nostr.event.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import nostr.event.tag.LimitTag;
import nostr.event.tag.PaymentTag;

import java.io.IOException;
import java.math.BigDecimal;

public class LimitTagSerializer extends JsonSerializer<LimitTag> {
    @Override

    public void serialize(LimitTag t, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        jsonGenerator.writeString(t.getCode());
        if(t.getCurrency()!=null && !t.getCurrency().isBlank()){
            jsonGenerator.writeString(t.getCurrency());
        }
        if(t.getLowLimit()!=null && t.getLowLimit().compareTo(BigDecimal.ZERO) > 0){
            jsonGenerator.writeNumber(t.getLowLimit().stripTrailingZeros());
        }
        if(t.getUpLimit()!=null && t.getUpLimit().compareTo(BigDecimal.ZERO) > 0){
            jsonGenerator.writeNumber(t.getUpLimit().stripTrailingZeros());
        }
        jsonGenerator.writeEndArray();
    }
}
