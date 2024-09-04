package nostr.event.tag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import nostr.base.annotation.Tag;
import nostr.event.BaseTag;
import nostr.event.NIP77Event;
import nostr.event.json.serializer.CreatedByTagSerializer;

import java.util.Optional;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@Tag(code = NIP77Event.CREATED_BY_TAG_CODE, nip = 77)
@RequiredArgsConstructor
@JsonSerialize(using = CreatedByTagSerializer.class)
public class CreatedByTag extends BaseTag {
    /**
     * ["create_by", "$take_intent_event_id", "$maker_nip05|$taker_nip05", "$maker_pubkey|$taker_pubkey", "$trade_id"]
     **/
    private final String takeIntentEventId;
    private final String nip05;
    private final String pubkey;
    private final long tradeId;


    public static <T extends BaseTag> T deserialize(@NonNull JsonNode node) {
        final String takeIntentEventId = Optional.ofNullable(node.get(1)).orElseThrow().asText();
        final String nip05 = Optional.ofNullable(node.get(2)).orElseThrow().asText();
        final String pubkey = Optional.ofNullable(node.get(3)).orElseThrow().asText();
        final long tradeId = Optional.ofNullable(node.get(4)).isPresent() ? node.get(4).asLong() : 0L;

        return (T) CreatedByTag.builder()
                .takeIntentEventId(takeIntentEventId).nip05(nip05).pubkey(pubkey).tradeId(tradeId)
                .build();
    }
}
