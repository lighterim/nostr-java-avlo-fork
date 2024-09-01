package nostr.event.tag;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;
import nostr.base.annotation.Key;
import nostr.base.annotation.Tag;
import nostr.event.BaseTag;
import nostr.event.NIP77Event;

import java.math.BigDecimal;
import java.util.Optional;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@Tag(code = NIP77Event.QUOTE_TAG_CODE, nip = 77)
@RequiredArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"number", "currency", "usd_rate"})
public class QuoteTag extends BaseTag {

  @Key
  @JsonProperty
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private BigDecimal number;

  @Key
  @JsonProperty
  private String currency;

  @Key
  @JsonProperty
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private BigDecimal usdRate;

  public static <T extends BaseTag> T deserialize(@NonNull JsonNode node) {
    String text = Optional.ofNullable(node.get(1)).orElseThrow().asText();
    final BigDecimal number = new BigDecimal(text);
    final String currency = Optional.ofNullable(node.get(2)).orElseThrow().asText();

    QuoteTag tag = QuoteTag.builder().number(number).currency(currency).build();
    if(Optional.ofNullable(node.get(3)).isPresent()) {
      String usdRateStr = Optional.ofNullable(node.get(3)).orElseThrow().asText();
      tag.setUsdRate(new BigDecimal(usdRateStr));
    }

    return (T) tag;
  }
}
