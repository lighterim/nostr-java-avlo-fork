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
        /**
         * final String symbol = Optional.ofNullable(node.get(1)).orElseThrow().asText();
         *
         *         TokenTagBuilder tag = TokenTag.builder().symbol(symbol);
         *         if(Optional.ofNullable(node.get(2)).isPresent()){
         *             tag.chain(node.get(2).asText());
         *         }
         *         if(Optional.ofNullable(node.get(3)).isPresent()){
         *             tag.network(node.get(3).asText());
         *         }
         *         tag.address(Optional.ofNullable(node.get(4)).orElseThrow().asText());
         *         String text = Optional.ofNullable(node.get(5)).orElseThrow().asText();
         *         final BigDecimal amount = new BigDecimal(text);
         *         tag.amount(amount.stripTrailingZeros());
         *         tag.chainId(BigInteger.valueOf(Optional.ofNullable(node.get(6)).orElseThrow().asLong()));
         *         if(Optional.ofNullable(node.get(7)).isPresent()){
         *             tag.expiryTime(node.get(7).asText());
         *         }
         */
        jsonGenerator.writeString(tokenTag.getCode());
        jsonGenerator.writeString(tokenTag.getSymbol());
        jsonGenerator.writeString(tokenTag.getChain());
        jsonGenerator.writeString(tokenTag.getNetwork());
        jsonGenerator.writeString(tokenTag.getAddress());
        jsonGenerator.writeString(tokenTag.getAmount().stripTrailingZeros().toPlainString());
        jsonGenerator.writeString(String.valueOf(tokenTag.getChainId()));
        jsonGenerator.writeString(tokenTag.getExpiryTime());
        jsonGenerator.writeString(tokenTag.getTradedAmount()==null ? "0" : tokenTag.getTradedAmount().stripTrailingZeros().toPlainString());
        jsonGenerator.writeEndArray();
    }
}
