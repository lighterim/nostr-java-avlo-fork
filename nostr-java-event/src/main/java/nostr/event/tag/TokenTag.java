package nostr.event.tag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import nostr.base.annotation.Tag;
import nostr.event.BaseTag;
import nostr.event.NIP77Event;
import nostr.event.json.serializer.TokenTagSerializer;

import java.math.BigDecimal;
import java.util.Optional;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@Tag(code = NIP77Event.TOKEN_TAG_CODE, nip=77)
@RequiredArgsConstructor
@JsonSerialize(using = TokenTagSerializer.class)
public class TokenTag extends BaseTag {
    private final String symbol;
    private final String chain;
    private final String network;
    private final String address;
    private final BigDecimal amount;


    public static <T extends BaseTag> T deserialize(@NonNull JsonNode node) {
        final String symbol = Optional.ofNullable(node.get(1)).orElseThrow().asText();

        TokenTagBuilder tag = TokenTag.builder().symbol(symbol);
        if(Optional.ofNullable(node.get(2)).isPresent()){
            tag.chain(node.get(2).asText());
        }
        if(Optional.ofNullable(node.get(3)).isPresent()){
            tag.network(node.get(3).asText());
        }
        tag.address(Optional.ofNullable(node.get(4)).orElseThrow().asText());
        String text = Optional.ofNullable(node.get(5)).orElseThrow().asText();
        final BigDecimal amount = new BigDecimal(text);
        tag.amount(amount);
        return (T) tag.build();
    }
}
