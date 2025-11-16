package nostr.event.tag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import nostr.base.annotation.Tag;
import nostr.event.BaseTag;
import nostr.event.NIP77Event;
import nostr.event.json.serializer.AccountTagSerializer;
import nostr.event.json.serializer.Permit2TagSerializer;

import java.math.BigInteger;
import java.util.Optional;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@Tag(code = NIP77Event.PERMIT2_TAG_CODE, nip=77)
@RequiredArgsConstructor
@JsonSerialize(using = Permit2TagSerializer.class)
public class Permit2Tag extends BaseTag {
    private final String nonce;
    private final String signature;
    private final String payer;
    private final String spender;
    private final String walletAddress;
    private final String contractAddress;
    private final String domainAppName;

    public static <T extends BaseTag> T deserialize(@NonNull JsonNode node) {
        final String nonce = getText(node, 1);
        final String signature = getText(node, 2);
        final String payer = getText(node, 3);
        final String spender = getText(node, 4);
        final String walletAddress = getText(node, 5);
        final String contractAddress = getText(node, 6);
        final String domainAppName = getText(node, 7);

        Permit2Tag.Permit2TagBuilder tag = Permit2Tag.builder().nonce(nonce).payer(payer)
                .signature(signature).spender(spender).walletAddress(walletAddress)
                .contractAddress(contractAddress).domainAppName(domainAppName);

        return (T) tag.build();
    }

    private static String getText(JsonNode node, int index) {
        return Optional.ofNullable(node.get(index)).orElseThrow().asText();
    }
}
