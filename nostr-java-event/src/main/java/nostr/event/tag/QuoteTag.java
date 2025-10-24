package nostr.event.tag;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import nostr.base.annotation.Key;
import nostr.base.annotation.Tag;
import nostr.event.BaseTag;
import nostr.event.NIP77Event;
import nostr.event.json.serializer.PaymentTagSerializer;
import nostr.event.json.serializer.QuoteTagSerializer;

import java.math.BigDecimal;
import java.util.Optional;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@Tag(code = NIP77Event.QUOTE_TAG_CODE, nip = 77)
@RequiredArgsConstructor
@AllArgsConstructor
@JsonSerialize(using = QuoteTagSerializer.class)
public class QuoteTag extends BaseTag {

  @Key
  @JsonProperty
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private BigDecimal number; //price

  @Key
  @JsonProperty
  private String currency;

  @Key
  @JsonProperty
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private BigDecimal usdRate;

  @Key
  @JsonProperty
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private String timestamp; //deadline

  @Key
  @JsonProperty
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private String signature;

  public static <T extends BaseTag> T deserialize(@NonNull JsonNode node) {
    String text = Optional.ofNullable(node.get(1)).orElseThrow().asText();
    final BigDecimal number = new BigDecimal(text).stripTrailingZeros();
    final String currency = Optional.ofNullable(node.get(2)).orElseThrow().asText();

    QuoteTag tag = QuoteTag.builder().number(number).currency(currency).build();
    if(Optional.ofNullable(node.get(3)).isPresent()) {
      String usdRateStr = node.get(3).asText();
      if(!usdRateStr.isBlank()) {
        tag.setUsdRate(new BigDecimal(usdRateStr).stripTrailingZeros());
      }
    }
    if(Optional.ofNullable(node.get(4)).isPresent()){
      tag.setTimestamp(node.get(4).asText());
    }
    if(Optional.ofNullable(node.get(5)).isPresent()){
      tag.setSignature(node.get(5).asText());
    }
    return (T) tag;
  }
}
