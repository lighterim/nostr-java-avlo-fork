package nostr.event.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import nostr.event.tag.MakeTag;
import org.apache.commons.lang3.StringUtils;

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
        jsonGenerator.writeString(makeTag.getIntentStatus()==null?"1":String.valueOf(makeTag.getIntentStatus().ordinal()));
        jsonGenerator.writeString(makeTag.getFeeRateBp()==null?"0":String.valueOf(makeTag.getFeeRateBp()));
        jsonGenerator.writeString(StringUtils.isBlank(makeTag.getClientId())?"0":makeTag.getClientId());
        jsonGenerator.writeString(makeTag.getAccumulatedUsd()==null?"0":String.valueOf(makeTag.getAccumulatedUsd()));
        jsonGenerator.writeString(makeTag.getCompletedRatioBp()==null?"0":String.valueOf(makeTag.getCompletedRatioBp()));
        jsonGenerator.writeEndArray();
    }
}
