package nostr.event.tag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import nostr.base.annotation.Tag;
import nostr.event.BaseTag;
import nostr.event.NIP77Event;
import nostr.event.json.serializer.AccountTagSerializer;

import java.math.BigInteger;
import java.util.Optional;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@Tag(code = NIP77Event.PERMIT2_TAG_CODE, nip=77)
@RequiredArgsConstructor
@JsonSerialize(using = AccountTagSerializer.class)
public class Permit2Tag extends BaseTag {
    private final String nonce;
    private final String signature;

    public static <T extends BaseTag> T deserialize(@NonNull JsonNode node) {
        final String nonce = Optional.ofNullable(node.get(1)).orElseThrow().asText();
        final String signature = Optional.ofNullable(node.get(2)).orElseThrow().asText();

        Permit2Tag.Permit2TagBuilder tag = Permit2Tag.builder().nonce(nonce)
                .signature(signature);

        return (T) tag.build();
    }
}
