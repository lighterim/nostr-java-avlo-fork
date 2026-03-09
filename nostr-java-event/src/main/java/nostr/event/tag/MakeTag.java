package nostr.event.tag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import nostr.base.annotation.Tag;
import nostr.event.*;
import nostr.event.json.serializer.MakeTagSerializer;
import org.apache.commons.lang3.StringUtils;

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
    private final IntentStatus intentStatus;
    private final Integer feeRateBp;
    private final String clientId;
    /** unit: USD_DECIMALS **/
    private final Long accumulatedUsd;
    private final Integer completedRatioBp;


    public static <T extends BaseTag> T deserialize(@NonNull JsonNode node){
        MakeTag.MakeTagBuilder b = MakeTag.builder();
        String side = Optional.ofNullable(node.get(1)).orElseThrow().asText().toUpperCase();
        String makerNip05 = Optional.ofNullable(node.get(2)).orElseThrow().asText();
        String makerPubkey = Optional.ofNullable(node.get(3)).orElseThrow().asText();
        IntentType intentType = IntentType.valueOf(Optional.ofNullable(node.get(4)).orElseThrow().asText());
        String statusValue = Optional.ofNullable(node.get(5)).orElseThrow().asText();
        Integer feeRateBp = Optional.ofNullable(node.get(6)).isPresent()?node.get(6).asInt():0;
        IntentStatus intentStatus = "1".equals(statusValue)?IntentStatus.OPEN:IntentStatus.CLOSED;
        Optional.ofNullable(node.get(7)).ifPresent(n-> b.clientId(n.asText()));
        Optional.ofNullable(node.get(8)).ifPresent(n-> {if(!StringUtils.isBlank(n.asText())) b.accumulatedUsd(Long.valueOf(n.asText()));});
        Optional.ofNullable(node.get(9)).ifPresent(n-> {if(!StringUtils.isBlank(n.asText())) b.completedRatioBp(Integer.valueOf(n.asText()));});
        MakeTag tag = b.intentType(intentType).intentStatus(intentStatus).feeRateBp(feeRateBp).side(Side.valueOf(side))
                .makerNip05(makerNip05).makerPubkey(makerPubkey)
                .build();
        return (T)tag;
    }
}
