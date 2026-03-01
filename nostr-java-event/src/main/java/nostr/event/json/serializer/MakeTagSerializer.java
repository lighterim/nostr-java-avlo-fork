package nostr.event.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import nostr.event.tag.MakeTag;

import java.io.IOException;

public class MakeTagSerializer extends JsonSerializer<MakeTag> {
    @Override
    public void serialize(MakeTag makeTag, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        jsonGenerator.writeString(makeTag.getCode());
        jsonGenerator.writeString(makeTag.getSide().getSide());
        jsonGenerator.writeString(makeTag.getMakerNip05());
        jsonGenerator.writeString(makeTag.getMakerPubkey());
        jsonGenerator.writeString(makeTag.getIntentType().getDesc());
        jsonGenerator.writeString(makeTag.getClientId());
        jsonGenerator.writeString(makeTag.getFeeRate()==null ? null : makeTag.getFeeRate().toString());
        jsonGenerator.writeString(makeTag.getTotalTransAmount()==null ? null : makeTag.getTotalTransAmount().stripTrailingZeros().toPlainString());
        jsonGenerator.writeString(makeTag.getOrderCompletionRate()==null ? null : makeTag.getOrderCompletionRate().toString());
        jsonGenerator.writeString(makeTag.getKycAddress()==null ? null : makeTag.getKycAddress());
        jsonGenerator.writeString(makeTag.getKycResult()==null ? null : makeTag.getKycResult());
        jsonGenerator.writeEndArray();
    }
}
