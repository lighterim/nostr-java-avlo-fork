package nostr.event.tag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import nostr.base.annotation.Tag;
import nostr.event.BaseTag;
import nostr.event.NIP77Event;
import nostr.event.json.serializer.EIP712TagSerializer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@Tag(code = NIP77Event.EIP712_TAG_CODE, nip=77)
@RequiredArgsConstructor
@JsonSerialize(using = EIP712TagSerializer.class)
public class EIP712Tag extends BaseTag {
    private final String walletAddress;
    private final String contractAddress;
    private final String domainAppName;
    private final String domainVersion;
    private final String sign;

    public static <T extends BaseTag> T deserialize(@NonNull JsonNode node) {
        String walletAddress = Optional.ofNullable(node.get(1)).orElseThrow().asText();
        String contractAddress = Optional.ofNullable(node.get(2)).orElseThrow().asText();
        String domainAppName = Optional.ofNullable(node.get(3)).orElseThrow().asText();
        String domainVersion = node.has(4) ? node.get(4).asText() : "";
        String sign = node.has(5) ? node.get(5).asText() : "";

        EIP712Tag tag = EIP712Tag.builder().domainAppName(domainAppName)
                .domainVersion(domainVersion).contractAddress(contractAddress).walletAddress(walletAddress).sign(sign).build();
        return (T) tag;
    }
}
