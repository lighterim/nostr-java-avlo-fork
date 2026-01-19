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
import nostr.event.tag.CreatedByTag;
import nostr.event.tag.EIP712Tag;
import nostr.event.tag.EscrowTag;
import nostr.event.tag.LedgerTag;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Event(name = NIP77Event.TRADE_MESSAGE_EVENT, nip = 77)
public class TradeMessageEvent extends NIP77Event {

    @JsonIgnore
    private CreatedByTag createdByTag;
    @JsonIgnore
    private LedgerTag ledgerTag;
    @JsonIgnore
    private EIP712Tag eip712Tag;
    @JsonIgnore
    private EscrowTag escrowTag;


    public TradeMessageEvent(@NonNull PublicKey pubKey, @NonNull List<BaseTag> tags, @NonNull String content) {
        super(pubKey, Kind.TRADE_MESSAGE, tags, content);
        initTags();
    }

    private void initTags() {
        if (createdByTag == null) {
            this.createdByTag = findTag(CreatedByTag.class, CREATED_BY_TAG_CODE);
        }
        if (ledgerTag == null) {
            this.ledgerTag = findTag(LedgerTag.class, LEDGER_TAG_CODE);
        }
        if (eip712Tag == null) {
            this.eip712Tag = findTag(EIP712Tag.class, EIP712_TAG_CODE);
        }
        if (escrowTag == null) {
            this.escrowTag = findTag(EscrowTag.class, ESCROW_TAG_CODE);
        }
    }

    @Override
    public void setTags(List<BaseTag> tags) {
        super.setTags(tags);
        initTags();
    }

    public void setCreatedByTag(CreatedByTag createdByTag) {
        this.createdByTag = createdByTag;
        CreatedByTag findTag = findTag(CreatedByTag.class, CREATED_BY_TAG_CODE);
        if(findTag==null) {
            this.getTags().add(createdByTag);
        } else {
            OptionalInt index = IntStream.range(0, this.getTags().size())
                    .filter(i -> CREATED_BY_TAG_CODE.equals(this.getTags().get(i).getCode()))
                    .findFirst();

            if (index.isPresent()) {
                this.getTags().set(index.getAsInt(), createdByTag);
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

    public void setEscrowTag(EscrowTag escrowTag) {
        this.escrowTag = escrowTag;
        EscrowTag findTag = findTag(EscrowTag.class, ESCROW_TAG_CODE);
        if(findTag ==null) {
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



    @Override
    public void validate() {
        super.validate();
        if (createdByTag == null || isBlank(createdByTag.getNip05()) || isBlank(createdByTag.getPubkey())
                || (ledgerTag == null && isBlank(createdByTag.getTakeIntentEventId()))
                || (ledgerTag != null && createdByTag.getTradeId() <= 0)) {
            throw new AssertionError(String.format("createdByTag is invalid.%s", createdByTag));
        }
    }
}
