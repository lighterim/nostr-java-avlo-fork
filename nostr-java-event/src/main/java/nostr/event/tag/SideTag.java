package nostr.event.tag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import nostr.base.annotation.Tag;
import nostr.event.BaseTag;
import nostr.event.json.serializer.SideTagSerializer;

import java.util.Optional;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@Tag(code = "side", nip = 77)
@RequiredArgsConstructor
@JsonSerialize(using = SideTagSerializer.class)
public class SideTag extends BaseTag {

    private final String side;

    public static <T extends BaseTag> T deserialize(@NonNull JsonNode node){
        String side = Optional.ofNullable(node.get(1)).orElseThrow().asText();
        SideTag tag = SideTag.builder().side(side).build();
        return (T)tag;
    }
}
