package nostr.event.tag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import nostr.base.annotation.Tag;
import nostr.event.BaseTag;
import nostr.event.NIP77Event;
import nostr.event.json.serializer.EscrowTagSerializer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@Tag(code = NIP77Event.ESCROW_TAG_CODE, nip=77)
@RequiredArgsConstructor
@JsonSerialize(using = EscrowTagSerializer.class)
public class EscrowTag extends BaseTag {
    private final Long id;
    private final String token;
    private final BigDecimal volume;
    private final BigDecimal price;
    private final BigDecimal usdRate;

    private final String payer;
    private final String seller;
    private final BigDecimal sellerFeeRate;
    private final String paymentMethod;
    private final String currency;
    private final String payeeDetails;

    private final String buyer;
    private final BigDecimal buyerFeeRate;

    private final String signature;

    public static <T extends BaseTag> T deserialize(@NonNull JsonNode node) {
        final Long id = Optional.ofNullable(node.get(1)).orElseThrow().asLong();
        final String token = Optional.ofNullable(node.get(2)).orElseThrow().asText();
        final String volume = Optional.ofNullable(node.get(3)).orElseThrow().asText();
        final String price = Optional.ofNullable(node.get(4)).orElseThrow().asText();
        final String usdRate = Optional.ofNullable(node.get(5)).orElseThrow().asText();
        final String payer = Optional.ofNullable(node.get(6)).orElseThrow().asText();
        final String seller = Optional.ofNullable(node.get(7)).orElseThrow().asText();
        final String sellerFeeRate = Optional.ofNullable(node.get(8)).orElseThrow().asText();
        final String paymentMethod = Optional.ofNullable(node.get(9)).orElseThrow().asText();
        final String currency = Optional.ofNullable(node.get(10)).orElseThrow().asText();
        final String payeeDetails = Optional.ofNullable(node.get(11)).orElseThrow().asText();
        final String buyer = Optional.ofNullable(node.get(12)).orElseThrow().asText();
        final String buyerFeeRate = Optional.ofNullable(node.get(13)).orElseThrow().asText();
        final String signature = Optional.ofNullable(node.get(14)).orElseThrow().asText();

        EscrowTag.EscrowTagBuilder tag = EscrowTag.builder().id(id)
                .token(token).volume(new BigDecimal(volume).stripTrailingZeros())
                .price(new BigDecimal(price).stripTrailingZeros())
                .usdRate(new BigDecimal(usdRate).stripTrailingZeros())
                .payer(payer).seller(seller)
                .sellerFeeRate(new BigDecimal(sellerFeeRate).stripTrailingZeros())
                .paymentMethod(paymentMethod)
                .currency(currency)
                .payeeDetails(payeeDetails)
                .buyer(buyer)
                .buyerFeeRate(new BigDecimal(buyerFeeRate).stripTrailingZeros())
                .signature(signature);

        return (T) tag.build();
    }
}
