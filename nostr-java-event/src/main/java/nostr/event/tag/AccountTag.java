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
@Tag(code = NIP77Event.ACCOUNT_TAG_CODE, nip=77)
@RequiredArgsConstructor
@JsonSerialize(using = AccountTagSerializer.class)
public class AccountTag extends BaseTag {
    private final String nftId;
    private final BigInteger chainId;
    private final String tba;
    private final String nostrPubKey;

    public static <T extends BaseTag> T deserialize(@NonNull JsonNode node) {
        final String nftId = Optional.ofNullable(node.get(1)).orElseThrow().asText();
        final BigInteger chainId = BigInteger.valueOf(Optional.ofNullable(node.get(2)).orElseThrow().asLong());
        final String tba = Optional.ofNullable(node.get(3)).orElseThrow().asText();
        final String nostrPubKey = Optional.ofNullable(node.get(4)).orElseThrow().asText();

        AccountTag.AccountTagBuilder tag = AccountTag.builder().nftId(nftId)
                .chainId(chainId).tba(tba).nostrPubKey(nostrPubKey);

        return (T) tag.build();
    }
}
