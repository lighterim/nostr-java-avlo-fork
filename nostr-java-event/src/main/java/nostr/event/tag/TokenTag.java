package nostr.event.tag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import nostr.base.annotation.Tag;
import nostr.event.BaseTag;
import nostr.event.json.serializer.TokenTagSerializer;

import java.math.BigDecimal;
import java.util.Optional;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@Tag(code = "token", nip=77)
@RequiredArgsConstructor
@JsonSerialize(using = TokenTagSerializer.class)
public class TokenTag extends nostr.event.BaseTag {
    private final BigDecimal amount;
    private final String symbol;
    private final String chain;
    private final String network;
    private final String address;


    public static <T extends BaseTag> T deserialize(@NonNull JsonNode node) {
        String text = Optional.ofNullable(node.get(1)).orElseThrow().asText();
        final BigDecimal amount = new BigDecimal(text);
        final String symbol = Optional.ofNullable(node.get(2)).orElseThrow().asText();

        TokenTagBuilder tag = TokenTag.builder().amount(amount).symbol(symbol);
        if(Optional.ofNullable(node.get(3)).isPresent()){
            tag.chain(node.get(3).asText());
        }
        if(Optional.ofNullable(node.get(4)).isPresent()){
            tag.network(node.get(4).asText());
        }
        tag.address(Optional.ofNullable(node.get(5)).orElseThrow().asText());
        return (T) tag.build();
    }
}
