package nostr.event.tag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import nostr.base.annotation.Tag;
import nostr.event.BaseTag;,
import nostr.event.IntentType;
import nostr.event.NIP77Event;
import nostr.event.Side;
import nostr.event.json.serializer.MakeTagSerializer;

import java.math.BigDecimal;
import java.util.Optional;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@Tag(code = NIP77Event.MAKE_TAG_CODE, nip = 77)
@RequiredArgsConstructor
@JsonSerialize(using = MakeTagSerializer.class)
public class MakeTag extends BaseTag {

    private final Side side;
    private final String makerNip05;
    private final String makerPubkey;
    private final IntentType intentType;
    private final String clientId;
    private final Integer feeRate;
    private final BigDecimal totalTransAmount;
    private final Integer orderCompletionRate;
    private final String kycAddress;
    private final String kycResult;

    public static <T extends BaseTag> T deserialize(@NonNull JsonNode node){
        String side = Optional.ofNullable(node.get(1)).orElseThrow().asText().toUpperCase();
        String makerNip05 = Optional.ofNullable(node.get(2)).orElseThrow().asText();
        String makerPubkey = Optional.ofNullable(node.get(3)).orElseThrow().asText();
        IntentType intentType = IntentType.valueOf(Optional.ofNullable(node.get(4)).orElseThrow().asText());
        String clientId = Optional.ofNullable(node.get(5)).orElseThrow().asText();
        Integer feeRate = null;
        BigDecimal totalTransAmount = null;
        Integer orderCompletionRate = null;
        String kycAddress = null;
        String kycResult = null;
        if(Optional.ofNullable(node.get(6)).isPresent()) {
            feeRate = node.get(6).asInt();
        }
        if(Optional.ofNullable(node.get(7)).isPresent()) {
            totalTransAmount = new BigDecimal(node.get(7).asText());
        }
        if(Optional.ofNullable(node.get(8)).isPresent()) {
            orderCompletionRate = node.get(8).asInt();
        }
        if(Optional.ofNullable(node.get(9)).isPresent()) {
            kycAddress = node.get(9).asText();
        }
        if(Optional.ofNullable(node.get(10)).isPresent()) {
            kycResult = node.get(10).asText();
        }

        MakeTag tag = MakeTag.builder().feeRate(feeRate)
                .orderCompletionRate(orderCompletionRate)
                .kycAddress(kycAddress)
                .kycResult(kycResult)
                .totalTransAmount(totalTransAmount).clientId(clientId)
                .intentType(intentType).side(Side.valueOf(side))
                .makerNip05(makerNip05).makerPubkey(makerPubkey).build();
        return (T)tag;
    }
}
