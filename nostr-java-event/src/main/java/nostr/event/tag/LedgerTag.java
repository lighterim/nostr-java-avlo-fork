package nostr.event.tag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import nostr.base.annotation.Tag;
import nostr.event.BaseTag;
import nostr.event.NIP77Event;
import nostr.event.json.serializer.CreatedByTagSerializer;
import nostr.event.json.serializer.LedgerTagSerializer;

import java.util.Optional;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@Tag(code = NIP77Event.LEDGER_TAG_CODE, nip=77)
@RequiredArgsConstructor
@JsonSerialize(using = LedgerTagSerializer.class)
public class LedgerTag extends BaseTag {
    /** ["ledger", "$chain","$network","$tx_id", "$tx_url"] **/
    private final String chain;
    private final String network;
    private final String txId;
    private final String txUrl;


    public static <T extends BaseTag> T deserialize(@NonNull JsonNode node) {
        final String chain = Optional.ofNullable(node.get(1)).orElseThrow().asText();
        final String network = Optional.ofNullable(node.get(2)).orElseThrow().asText();
        final String txId = Optional.ofNullable(node.get(3)).orElseThrow().asText();
        final String txUrl = Optional.ofNullable(node.get(4)).orElseThrow().asText();

        return (T) LedgerTag.builder()
                .chain(chain).network(network).txId(txId).txUrl(txUrl)
                .build();
    }
}
