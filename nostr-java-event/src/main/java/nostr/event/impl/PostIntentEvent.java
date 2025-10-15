package nostr.event.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import nostr.base.PublicKey;
import nostr.base.annotation.Event;
import nostr.event.BaseTag;
import nostr.event.Kind;
import nostr.event.NIP77Event;
import nostr.event.tag.*;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Event(name=NIP77Event.POST_INTENT_EVENT, nip=77)
public class PostIntentEvent extends NIP77Event {

    @JsonIgnore
    private EIP712Tag eip712Tag;
    @JsonIgnore
    private TokenTag tokenTag;
    @JsonIgnore
    private QuoteTag quoteTag;
    @JsonIgnore
    private MakeTag sideTag;
    @JsonIgnore
    private Permit2Tag permit2Tag;
    @JsonIgnore
    private LimitTag limitTag;
    @JsonIgnore
    private List<PaymentTag> paymentTags;

    public PostIntentEvent(@NonNull PublicKey pubKey, @NonNull List<BaseTag> tags, @NonNull String content){
        super(pubKey, Kind.POST_INTENT, tags, content);
        initTags();
    }

    private void initTags() {
        if(permit2Tag == null) {
            this.permit2Tag = findTag(Permit2Tag.class, PERMIT2_TAG_CODE);
        }
        if(eip712Tag == null) {
            this.eip712Tag = findTag(EIP712Tag.class, EIP712_TAG_CODE);
        }
        if(quoteTag == null) {
            this.quoteTag = findTag(QuoteTag.class, QUOTE_TAG_CODE);
        }
        if(tokenTag == null) {
            this.tokenTag = findTag(TokenTag.class, TOKEN_TAG_CODE);
        }
        if(sideTag == null) {
            this.sideTag = findTag(MakeTag.class, MAKE_TAG_CODE);
        }
        if(limitTag == null) {
            this.limitTag = findTag(LimitTag.class, LIMIT_TAG_CODE);
        }
        if(paymentTags == null){
            this.paymentTags = findTags(PaymentTag.class, PAYMENT_TAG_CODE);
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
        if(permit2Tag == null ||eip712Tag == null || tokenTag == null || isBlank(tokenTag.getSymbol()) || isBlank(tokenTag.getChain()) || isBlank(tokenTag.getNetwork()) || isBlank(tokenTag.getAddress())
                || sideTag == null || sideTag.getSide()==null || quoteTag == null || isBlank(quoteTag.getCurrency()) || !gtZero(quoteTag.getNumber())
                || paymentTags == null || paymentTags.isEmpty()){
            throw new AssertionError("permit2Tag, eip712Tag, tokenTag, sideTag, quoteTag, payment must not be empty!");
        }
    }
}
