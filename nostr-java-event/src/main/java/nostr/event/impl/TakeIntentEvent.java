package nostr.event.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import nostr.base.PublicKey;
import nostr.base.annotation.Event;
import nostr.event.BaseTag;
import nostr.event.Kind;
import nostr.event.NIP77Event;
import nostr.event.tag.PaymentTag;
import nostr.event.tag.QuoteTag;
import nostr.event.tag.TakeTag;
import nostr.event.tag.TokenTag;

import java.math.BigDecimal;
import java.util.List;

import static nostr.event.NIP77Event.TAKE_INTENT_EVENT;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Event(name=TAKE_INTENT_EVENT, nip=77)
public class TakeIntentEvent extends NIP77Event {

    private TakeTag takeTag;
    private TokenTag tokenTag;
    private QuoteTag quoteTag;
    private PaymentTag paymentTag;

    public TakeIntentEvent(@NonNull PublicKey pubKey, @NonNull List<BaseTag> tags, @NonNull String content){
        super(pubKey, Kind.TAKE_INTENT, tags, content);
        initTags();
    }

    private void initTags() {
        if(takeTag == null) {
            takeTag = findTag(TakeTag.class, TAKE_TAG_CODE);
        }
        if(tokenTag == null) {
            tokenTag = findTag(TokenTag.class, TOKEN_TAG_CODE);
        }
        if(quoteTag == null) {
            quoteTag = findTag(QuoteTag.class, QUOTE_TAG_CODE);
        }
        if(paymentTag == null) {
            paymentTag = findTag(PaymentTag.class, PAYMENT_TAG_CODE);
        }
    }

    @Override
    public void setTags(List<BaseTag> tags) {
        super.setTags(tags);
        initTags();
    }

    @Override
    protected void validate() {
        super.validate();
        if(
                takeTag == null || isBlank(takeTag.getIntentEventId()) || !gtZero(takeTag.getVolume())
                || tokenTag == null || quoteTag == null || paymentTag == null
        ) {
            throw new AssertionError("take tag incorrect.", null);
        }
    }
}
