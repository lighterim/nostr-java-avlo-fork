package nostr.event.tag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import nostr.base.annotation.Tag;
import nostr.event.BaseTag;
import nostr.event.json.serializer.TakeTagSerializer;
import nostr.event.json.serializer.TokenTagSerializer;

import java.math.BigDecimal;
import java.util.Optional;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@Tag(code = "take", nip=77)
@RequiredArgsConstructor
@JsonSerialize(using = TakeTagSerializer.class)
public class TakeTag extends BaseTag {

    private final String intentEventId;
    /** volume of SYMBOL(tokenTag) **/
    private final BigDecimal volume;


    public static <T extends BaseTag> T deserialize(@NonNull JsonNode node) {
        String eventId = Optional.ofNullable(node.get(1)).orElseThrow().asText();

        final String volumeStr = Optional.ofNullable(node.get(3)).orElseThrow().asText();

        return (T)TakeTag.builder().intentEventId(eventId).volume(new BigDecimal(volumeStr)).build();
    }
}
