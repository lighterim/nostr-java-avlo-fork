package nostr.event.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import nostr.event.tag.TokenTag;

import java.io.IOException;

public class TokenTagSerializer extends JsonSerializer<TokenTag> {
    @Override
    public void serialize(TokenTag tokenTag, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        jsonGenerator.writeString(tokenTag.getCode());
        jsonGenerator.writeString(tokenTag.getSymbol());
        jsonGenerator.writeString(tokenTag.getChain());
        jsonGenerator.writeString(tokenTag.getNetwork());
        jsonGenerator.writeString(tokenTag.getAddress());
        jsonGenerator.writeNumber(tokenTag.getAmount().stripTrailingZeros());
        jsonGenerator.writeEndArray();
    }
}
