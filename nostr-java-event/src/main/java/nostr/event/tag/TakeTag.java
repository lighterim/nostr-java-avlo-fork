package nostr.event.tag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import nostr.base.annotation.Tag;
import nostr.event.BaseTag;
import nostr.event.NIP77Event;
import nostr.event.Side;
import nostr.event.json.serializer.TakeTagSerializer;
import nostr.event.json.serializer.TokenTagSerializer;

import java.math.BigDecimal;
import java.util.Optional;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@Tag(code = NIP77Event.TAKE_TAG_CODE, nip=77)
@RequiredArgsConstructor
@JsonSerialize(using = TakeTagSerializer.class)
public class TakeTag extends BaseTag {

    private final Side side;
    private final String intentEventId;
    private final String makerNip05;
    private final String makerPubkey;
    /** volume of SYMBOL(tokenTag) **/
    private final BigDecimal volume;
    private final String takerNip05;
    private final String takerPubkey;


    public static <T extends BaseTag> T deserialize(@NonNull JsonNode node) {
        String side = Optional.ofNullable(node.get(1)).orElseThrow().asText().toUpperCase();
        String eventId = Optional.ofNullable(node.get(2)).orElseThrow().asText();
        String makerNip05 = Optional.ofNullable(node.get(3)).orElseThrow().asText();
        String makerPubkey = Optional.ofNullable(node.get(4)).orElseThrow().asText();
        final String volumeStr = Optional.ofNullable(node.get(5)).orElseThrow().asText();
        String takerNip05 = Optional.ofNullable(node.get(6)).orElseThrow().asText();
        String takerPubkey = Optional.ofNullable(node.get(7)).orElseThrow().asText();

        return (T)TakeTag.builder().side(Side.valueOf(side)).intentEventId(eventId).makerNip05(makerNip05)
                .makerPubkey(makerPubkey).volume(new BigDecimal(volumeStr)).takerNip05(takerNip05).takerPubkey(takerPubkey)
                .build();
    }
}
