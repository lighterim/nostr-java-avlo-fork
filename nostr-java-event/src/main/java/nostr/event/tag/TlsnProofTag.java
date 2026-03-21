package nostr.event.tag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import nostr.base.annotation.Tag;
import nostr.event.BaseTag;
import nostr.event.NIP77Event;
import nostr.event.json.serializer.TlsnProofTagSerializer;

import java.util.Optional;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@Tag(code = NIP77Event.TLSN_PROOF_TAG_CODE, nip = 77)
//@RequiredArgsConstructor
@JsonSerialize(using = TlsnProofTagSerializer.class)
public class TlsnProofTag extends BaseTag {

   private String paymentMethod;
   private String paymentId;
   private String account1;
   private String account2;
   private String account3;
   private String amount;
   private String currency;
   private String state;
   private String confirmationTs;
   private String tradeId;
   private String signature;

    public static <T extends BaseTag> T deserialize(@NonNull JsonNode node) {
        String paymentMethod = Optional.ofNullable(node.get(1)).orElseThrow().asText();
        String paymentId = Optional.ofNullable(node.get(2)).orElseThrow().asText();
        String account1 = Optional.ofNullable(node.get(3)).orElseThrow().asText();
        String account2 = Optional.ofNullable(node.get(4)).isPresent() ? node.get(4).asText() : null;
        String account3 = Optional.ofNullable(node.get(5)).isPresent() ? node.get(5).asText() : null;
        String amount = Optional.ofNullable(node.get(6)).orElseThrow().asText();
        String currency = Optional.ofNullable(node.get(7)).orElseThrow().asText();
        String state = Optional.ofNullable(node.get(8)).orElseThrow().asText();
        String confirmationTs = Optional.ofNullable(node.get(9)).isPresent() ? node.get(9).asText() : null;
        String tradeId = Optional.ofNullable(node.get(10)).orElseThrow().asText();
        String signature = Optional.ofNullable(node.get(11)).isPresent() ? node.get(11).asText() : null;

        return (T) TlsnProofTag.builder()
                .paymentMethod(paymentMethod).account1(account1).account2(account2).account3(account3).paymentId(paymentId)
                .amount(amount).currency(currency).state(state).confirmationTs(confirmationTs)
                .tradeId(tradeId).signature(signature)
                .build();
    }
}
