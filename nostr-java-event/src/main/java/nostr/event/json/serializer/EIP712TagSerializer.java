package nostr.event.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import nostr.event.tag.EIP712Tag;

import java.io.IOException;

public class EIP712TagSerializer extends JsonSerializer<EIP712Tag> {
    @Override
    public void serialize(EIP712Tag eip712Tag, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        jsonGenerator.writeString(eip712Tag.getCode());
        /**
         * private final String walletAddress;
         *     private final String contractAddress;
         *     private final String domainAppName;
         *     private final String domainVersion;
         *     private final String sign;
         */
        jsonGenerator.writeString(eip712Tag.getWalletAddress());
        jsonGenerator.writeString(eip712Tag.getContractAddress());
        jsonGenerator.writeString(eip712Tag.getDomainAppName());
        jsonGenerator.writeString(eip712Tag.getDomainVersion());
        jsonGenerator.writeString(eip712Tag.getSign());
        jsonGenerator.writeEndArray();
    }
}
