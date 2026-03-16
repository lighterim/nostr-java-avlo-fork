package nostr.event.tag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import nostr.base.annotation.Tag;
import nostr.event.BaseTag;
import nostr.event.NIP77Event;
import nostr.event.TradeStatus;
import nostr.event.json.serializer.TradeTagSerializer;

import java.util.Optional;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@Tag(code = NIP77Event.TRADE_TAG_CODE, nip=77)
@RequiredArgsConstructor
@JsonSerialize(using= TradeTagSerializer.class)
public class TradeTag extends BaseTag {

    private final Long id;
    private final TradeStatus status;
    private final String escrowHash;

    public static <T extends BaseTag> T deserialize(@NonNull JsonNode node) {
        final Long id = Optional.ofNullable(node.get(1)).orElseThrow().asLong();
        final TradeStatus status = TradeStatus.forValue(Optional.ofNullable(node.get(2)).orElseThrow().asText());
        final String escrowHash = Optional.ofNullable(node.get(3)).isPresent() ? node.get(3).asText() : "";
        return (T) new TradeTag(id, status, escrowHash);
    }

}
