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
   private String payeeDetails;
   private String amount;
   private String currency;
   private String confirmationTs;
   private String tradeId;
   private String signature;

    public static <T extends BaseTag> T deserialize(@NonNull JsonNode node) {
        String paymentMethod = Optional.ofNullable(node.get(1)).orElseThrow().asText();
        String paymentId = Optional.ofNullable(node.get(2)).orElseThrow().asText();
        String payeeDetails = Optional.ofNullable(node.get(3)).orElseThrow().asText();
        String amount = Optional.ofNullable(node.get(4)).orElseThrow().asText();
        String currency = Optional.ofNullable(node.get(5)).orElseThrow().asText();
        String confirmationTs = Optional.ofNullable(node.get(6)).isPresent() ? node.get(6).asText() : "0";
        String tradeId = Optional.ofNullable(node.get(7)).orElseThrow().asText();
        String signature = Optional.ofNullable(node.get(8)).isPresent() ? node.get(8).asText() : null;

        return (T) TlsnProofTag.builder()
                .paymentMethod(paymentMethod).payeeDetails(payeeDetails).paymentId(paymentId)
                .amount(amount).currency(currency).confirmationTs(confirmationTs)
                .tradeId(tradeId).signature(signature)
                .build();
    }
}
