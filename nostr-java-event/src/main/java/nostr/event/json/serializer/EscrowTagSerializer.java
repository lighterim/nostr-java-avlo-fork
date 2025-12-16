package nostr.event.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import nostr.event.tag.AccountTag;
import nostr.event.tag.EscrowTag;

import java.io.IOException;

public class EscrowTagSerializer extends JsonSerializer<EscrowTag> {
    @Override
    public void serialize(EscrowTag tag, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        jsonGenerator.writeString(tag.getCode());
        jsonGenerator.writeNumber(tag.getId());
        jsonGenerator.writeString(tag.getToken());
        jsonGenerator.writeString(tag.getVolume().stripTrailingZeros().toPlainString());
        jsonGenerator.writeString(tag.getPrice().stripTrailingZeros().toPlainString());
        jsonGenerator.writeString(tag.getUsdRate().stripTrailingZeros().toPlainString());

        jsonGenerator.writeString(tag.getPayer());
        jsonGenerator.writeString(tag.getSeller());
        jsonGenerator.writeString(tag.getSellerFeeRate().stripTrailingZeros().toPlainString());
        jsonGenerator.writeString(tag.getPaymentMethod());
        jsonGenerator.writeString(tag.getCurrency());
        jsonGenerator.writeString(tag.getPayeeDetails());
        jsonGenerator.writeString(tag.getBuyer());
        jsonGenerator.writeString(tag.getBuyerFeeRate().stripTrailingZeros().toPlainString());
        jsonGenerator.writeString(tag.getSignature());
        jsonGenerator.writeEndArray();
    }
}
