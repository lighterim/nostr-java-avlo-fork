package nostr.event.tag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import nostr.base.annotation.Tag;
import nostr.event.BaseTag;
import nostr.event.NIP77Event;
import nostr.event.json.serializer.PaymentTagSerializer;
import nostr.event.json.serializer.TokenTagSerializer;

import java.math.BigDecimal;
import java.util.Optional;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@Tag(code = NIP77Event.PAYMENT_TAG_CODE, nip=77)
@RequiredArgsConstructor
@JsonSerialize(using = PaymentTagSerializer.class)
public class PaymentTag extends BaseTag {

    private final String method;
    private final String account;
    private final String qrCode;
    private final String memo;


    public static <T extends BaseTag> T deserialize(@NonNull JsonNode node) {
        String method = Optional.ofNullable(node.get(1)).orElseThrow().asText();
        PaymentTag.PaymentTagBuilder tag = PaymentTag.builder().method(method);
        Optional.ofNullable(node.get(2)).ifPresent(n -> tag.account(n.asText()));
        Optional.ofNullable(node.get(3)).ifPresent(n -> tag.qrCode(n.asText()));
        Optional.ofNullable(node.get(4)).ifPresent(n -> tag.memo(n.asText()));

        return (T) tag.build();
    }
}
