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
import nostr.event.tag.*;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import static nostr.event.NIP77Event.TAKE_INTENT_EVENT;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Event(name = TAKE_INTENT_EVENT, nip = 77)
public class TakeIntentEvent extends NIP77Event {

    @JsonIgnore
    private TakeTag takeTag;
    @JsonIgnore
    private EIP712Tag eip712Tag;
    @JsonIgnore
    private TokenTag tokenTag;
    @JsonIgnore
    private QuoteTag quoteTag;
    @JsonIgnore
    private PaymentTag paymentTag;
    @JsonIgnore
    private Permit2Tag permit2Tag;
    @JsonIgnore
    private EscrowTag escrowTag;
    /** when taker take intent, retrieve original(maker) intent. */
    @JsonIgnore
    private LimitTag limitTag;
    @JsonIgnore
    private TradeKeyTag tradeKeyTag;

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
        if (eip712Tag == null) {
            eip712Tag = findTag(EIP712Tag.class, EIP712_TAG_CODE);
        }
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
        if(permit2Tag == null){
            permit2Tag = findTag(Permit2Tag.class, PERMIT2_TAG_CODE);
        }
        if(limitTag == null){
            limitTag = findTag(LimitTag.class, LIMIT_TAG_CODE);
        }
        if (tradeKeyTag == null){
            tradeKeyTag = findTag(TradeKeyTag.class, TRADE_KEY_TAG_CODE);
        }
        if (escrowTag == null){
            escrowTag = findTag(EscrowTag.class, ESCROW_TAG_CODE);
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
                        || tokenTag == null || limitTag == null
                        || quoteTag == null || paymentTag == null
        ) {
            throw new AssertionError("take tag incorrect.", null);
        }
    }

    public void setEscrowTag(EscrowTag escrowTag) {
        this.escrowTag = escrowTag;
        EscrowTag findEscrowTag = findTag(EscrowTag.class, ESCROW_TAG_CODE);
        if(findEscrowTag==null) {
            this.getTags().add(escrowTag);
        } else {
            OptionalInt index = IntStream.range(0, this.getTags().size())
                    .filter(i -> ESCROW_TAG_CODE.equals(this.getTags().get(i).getCode()))
                    .findFirst();

            if (index.isPresent()) {
                this.getTags().set(index.getAsInt(), escrowTag);
            }
        }
    }

    public void setEip712Tag(EIP712Tag eip712Tag) {
        this.eip712Tag = eip712Tag;
        EIP712Tag findEIP712Tag = findTag(EIP712Tag.class, EIP712_TAG_CODE);
        if(findEIP712Tag==null) {
            this.getTags().add(eip712Tag);
        } else {
            OptionalInt index = IntStream.range(0, this.getTags().size())
                    .filter(i -> EIP712_TAG_CODE.equals(this.getTags().get(i).getCode()))
                    .findFirst();

            if (index.isPresent()) {
                this.getTags().set(index.getAsInt(), eip712Tag);
            }
        }
    }

    public void setPermit2Tag(Permit2Tag permit2Tag) {
        this.permit2Tag = permit2Tag;
        Permit2Tag findPermit2Tag = findTag(Permit2Tag.class, PERMIT2_TAG_CODE);
        if(findPermit2Tag==null) {
            this.getTags().add(permit2Tag);
        } else {
            OptionalInt index = IntStream.range(0, this.getTags().size())
                    .filter(i -> PERMIT2_TAG_CODE.equals(this.getTags().get(i).getCode()))
                    .findFirst();

            if (index.isPresent()) {
                this.getTags().set(index.getAsInt(), permit2Tag);
            }
        }
    }
}
