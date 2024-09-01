package nostr.event.tag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import nostr.base.annotation.Tag;
import nostr.event.BaseTag;
import nostr.event.NIP77Event;
import nostr.event.Side;
import nostr.event.json.serializer.MakeTagSerializer;

import java.util.Optional;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@Tag(code = NIP77Event.MAKE_TAG_CODE, nip = 77)
@RequiredArgsConstructor
@JsonSerialize(using = MakeTagSerializer.class)
public class MakeTag extends BaseTag {

    private final Side side;
    private final String makerNip05;
    private final String makerPubkey;

    public static <T extends BaseTag> T deserialize(@NonNull JsonNode node){
        String side = Optional.ofNullable(node.get(1)).orElseThrow().asText().toUpperCase();
        String makerNip05 = Optional.ofNullable(node.get(2)).orElseThrow().asText();
        String makerPubkey = Optional.ofNullable(node.get(3)).orElseThrow().asText();
        MakeTag tag = MakeTag.builder().side(Side.valueOf(side)).makerNip05(makerNip05).makerPubkey(makerPubkey).build();
        return (T)tag;
    }
}
