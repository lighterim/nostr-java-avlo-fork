package nostr.event.tag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import nostr.base.annotation.Tag;
import nostr.event.BaseTag;
import nostr.event.NIP77Event;
import nostr.event.json.serializer.LimitTagSerializer;
import nostr.event.json.serializer.TokenTagSerializer;

import java.math.BigDecimal;
import java.util.Optional;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@Tag(code = NIP77Event.LIMIT_TAG_CODE, nip=77)
@RequiredArgsConstructor
@JsonSerialize(using = LimitTagSerializer.class)
public class LimitTag extends BaseTag {
    private final String currency;
    private final BigDecimal lowLimit;
    private final BigDecimal upLimit;

    public static <T extends BaseTag> T deserialize(@NonNull JsonNode node) {
        LimitTag.LimitTagBuilder tag = LimitTag.builder();
        Optional.ofNullable(node.get(1)).ifPresent(n->tag.currency(n.asText()));
        Optional.ofNullable(node.get(2)).ifPresent(n -> tag.lowLimit(new BigDecimal(n.asText()).stripTrailingZeros()));
        Optional.ofNullable(node.get(3)).ifPresent(n -> tag.upLimit(new BigDecimal(n.asText()).stripTrailingZeros()));
        return (T) tag.build();
    }
}
