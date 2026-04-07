package nostr.event.tag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import nostr.base.annotation.Tag;
import nostr.event.BaseTag;
import nostr.event.NIP77Event;
import nostr.event.json.serializer.ArbitrationTagSerializer;
import nostr.event.json.serializer.TlsnProofTagSerializer;

import java.util.Optional;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@Tag(code = NIP77Event.ARBITRATION_TAG_CODE, nip = 77)
//@RequiredArgsConstructor
@JsonSerialize(using = ArbitrationTagSerializer.class)
public class ArbitrationTag extends BaseTag {

   private String arbitrator;
   private Integer buyerThresholdBp;
   private String resolutionTs;
   private Integer nonce;
   private String signature;

    public static <T extends BaseTag> T deserialize(@NonNull JsonNode node) {
        String arbitrator = Optional.ofNullable(node.get(1)).orElseThrow().asText();
        Integer buyerThresholdBp = Optional.ofNullable(node.get(2)).orElseThrow().asInt();
        Integer nonce = Optional.ofNullable(node.get(3)).orElseThrow().asInt();
        String resolutionTs = Optional.ofNullable(node.get(4)).orElseThrow().asText();
        String signature = Optional.ofNullable(node.get(5)).isPresent() ? node.get(5).asText() : null;

        return (T) ArbitrationTag.builder()
                .arbitrator(arbitrator).nonce(nonce).resolutionTs(resolutionTs).buyerThresholdBp(buyerThresholdBp).signature(signature)
                .build();
    }
}
