package nostr.event.tag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import nostr.base.annotation.Tag;
import nostr.event.BaseTag;
import nostr.event.NIP77Event;
import nostr.event.TradeStatus;
import nostr.event.json.serializer.LedgerTagSerializer;
import nostr.event.json.serializer.TradeKeyTagSerializer;

import java.util.Optional;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@Tag(code = NIP77Event.TRADE_KEY_TAG_CODE, nip=77)
@RequiredArgsConstructor
@JsonSerialize(using = TradeKeyTagSerializer.class)
public class TradeKeyTag extends BaseTag {
    /** ["keys", "$key_for_buyer", "$key_for_seller","$key_for_witness","$key_for_someone", "$pub_key" **/
    private final String keyForBuyer;
    private final String keyForSeller;
    private final String keyForWitness;
    private final String keyForSomeone;
    private final String pubkey;


    public static <T extends BaseTag> T deserialize(@NonNull JsonNode node) {
        final String keyForBuyer = node.get(1).asText();
        final String keyForSeller = node.get(2).asText();
        final String keyForWitness = node.get(3).asText();
        final String keyForSomeone = node.get(4).asText();
        final String pubKey = node.get(5).asText();

        return (T) TradeKeyTag.builder()
                .keyForBuyer(keyForBuyer).keyForSeller(keyForSeller).keyForWitness(keyForWitness)
                .keyForSomeone(keyForSomeone).pubkey(pubKey)
                .build();
    }

}
