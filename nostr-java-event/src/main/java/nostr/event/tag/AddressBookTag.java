package nostr.event.tag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import nostr.base.annotation.Tag;
import nostr.event.BaseTag;
import nostr.event.NIP77Event;
import nostr.event.json.serializer.AddressBookTagSerializer;

import java.math.BigInteger;
import java.util.Optional;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@Tag(code = NIP77Event.ADDRESS_BOOK_TAG_CODE, nip=77)
@RequiredArgsConstructor
@JsonSerialize(using = AddressBookTagSerializer.class)
public class AddressBookTag extends BaseTag {
    private final String name;
    private final String address;
    private final String pubkey;
    private final String nftId;
    private final BigInteger chainId;
    private final String createdBy;

    public static <T extends BaseTag> T deserialize(@NonNull JsonNode node) {
        final String name = Optional.ofNullable(node.get(1)).orElseThrow().asText();
        final String address = Optional.ofNullable(node.get(2)).orElseThrow().asText();
        final String pubkey = Optional.ofNullable(node.get(3)).orElseThrow().asText();
        final String nftId = Optional.ofNullable(node.get(4)).orElseThrow().asText();
        final BigInteger chainId = BigInteger.valueOf(Optional.ofNullable(node.get(5)).orElseThrow().asLong());
        final String createdBy = Optional.ofNullable(node.get(6)).orElseThrow().asText();

        AddressBookTag.AddressBookTagBuilder tag = AddressBookTag.builder().nftId(nftId)
                .chainId(chainId).name(name).address(address).pubkey(pubkey)
                .createdBy(createdBy);

        return (T) tag.build();
    }
}
