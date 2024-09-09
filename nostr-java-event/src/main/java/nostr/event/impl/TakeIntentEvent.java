package nostr.event.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import nostr.base.PublicKey;
import nostr.base.annotation.Event;
import nostr.event.BaseTag;
import nostr.event.Kind;
import nostr.event.NIP77Event;
import nostr.event.TradeStatus;
import nostr.event.tag.PaymentTag;
import nostr.event.tag.QuoteTag;
import nostr.event.tag.TakeTag;
import nostr.event.tag.TokenTag;

import java.util.List;

import static nostr.event.NIP77Event.TAKE_INTENT_EVENT;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Event(name = TAKE_INTENT_EVENT, nip = 77)
public class TakeIntentEvent extends NIP77Event {

    @JsonIgnore
    private TakeTag takeTag;
    @JsonIgnore
    private TokenTag tokenTag;
    @JsonIgnore
    private QuoteTag quoteTag;
    @JsonIgnore
    private PaymentTag paymentTag;

    @JsonProperty("trade_id")
    private long tradeId;

    @JsonProperty("status")
    private String tradeStatus;

    public TakeIntentEvent(@NonNull Long tradeId, @NonNull PublicKey pubKey, @NonNull List<BaseTag> tags, @NonNull String content, TradeStatus tradeStatus) {
        super(pubKey, Kind.TAKE_INTENT, tags, content);
        this.tradeId = tradeId;
        if(tradeStatus!=null) {
            this.tradeStatus = tradeStatus.getValue();
        }
        initTags();
    }

    public TakeIntentEvent(@NonNull Long tradeId, @NonNull PublicKey pubKey, @NonNull Integer nip, @NonNull List<BaseTag> tags, @NonNull String eventIdString, @NonNull String content, TradeStatus tradeStatus, long createdAt) {
        super(pubKey, Kind.TAKE_INTENT, tags, content);
        this.tradeId = tradeId;
        if(tradeStatus!=null) {
            this.tradeStatus = tradeStatus.getValue();
        }
        this.setNip(nip);
        this.setId(eventIdString);
        this.setCreatedAt(createdAt);
        initTags();
    }

    private void initTags() {
        if (takeTag == null) {
            takeTag = findTag(TakeTag.class, TAKE_TAG_CODE);
        }
        if (tokenTag == null) {
            tokenTag = findTag(TokenTag.class, TOKEN_TAG_CODE);
        }
        if (quoteTag == null) {
            quoteTag = findTag(QuoteTag.class, QUOTE_TAG_CODE);
        }
        if (paymentTag == null) {
            paymentTag = findTag(PaymentTag.class, PAYMENT_TAG_CODE);
        }
    }

    @Override
    public void setTags(List<BaseTag> tags) {
        super.setTags(tags);
        initTags();
    }

    @Override
    public void validate() {
        super.validate();
        if (
                takeTag == null || isBlank(takeTag.getIntentEventId()) || !gtZero(takeTag.getVolume())
                        || tokenTag == null || quoteTag == null || paymentTag == null
        ) {
            throw new AssertionError("take tag incorrect.", null);
        }
    }
}
