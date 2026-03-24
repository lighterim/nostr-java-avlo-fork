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
    @JsonIgnore
    private TlsnProofTag tlsnProofTag;
    @JsonIgnore
    private ArbitrationTag arbitrationTag;


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
        if (tlsnProofTag == null) {
            this.tlsnProofTag = findTag(TlsnProofTag.class, TLSN_PROOF_TAG_CODE);
        }
        if(arbitrationTag == null) {
            this.arbitrationTag = findTag(ArbitrationTag.class, ARBITRATION_TAG_CODE);
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

    public void setTlsnProofTag(TlsnProofTag tlsnProofTag) {
        this.tlsnProofTag = tlsnProofTag;
        TlsnProofTag  findTag = findTag(TlsnProofTag.class, TLSN_PROOF_TAG_CODE);
        if(findTag==null) {
            this.getTags().add(tlsnProofTag);
        } else {
            OptionalInt index = IntStream.range(0, this.getTags().size())
                    .filter( i-> TLSN_PROOF_TAG_CODE.equals(this.getTags().get(i).getCode()))
                    .findFirst();
            if (index.isPresent()) {
                this.getTags().set(index.getAsInt(), tlsnProofTag);
            }
        }
    }

    public void setArbitrationTag(ArbitrationTag tag) {
        this.arbitrationTag = tag;
        ArbitrationTag  findTag = findTag(ArbitrationTag.class, ARBITRATION_TAG_CODE);
        if(findTag==null) {
            this.getTags().add(tag);
        } else {
            OptionalInt index = IntStream.range(0, this.getTags().size())
                    .filter( i-> ARBITRATION_TAG_CODE.equals(this.getTags().get(i).getCode()))
                    .findFirst();
            if (index.isPresent()) {
                this.getTags().set(index.getAsInt(), tag);
            }
        }
    }

    @Override
    public void validate() {
        super.validate();
        // 系统发出时，必须有 tradeId. 同时有 ledgerTag, tlsnPoofTag, arbitrationTag之一。
        if (createdByTag == null || isBlank(createdByTag.getNip05()) || isBlank(createdByTag.getPubkey())
                || (isBlank(createdByTag.getTakeIntentEventId()) && ledgerTag == null &&  tlsnProofTag == null && arbitrationTag == null)
                || (createdByTag.getTradeId() <= 0) && (ledgerTag!=null || tlsnProofTag!=null || arbitrationTag!=null)) {
            throw new AssertionError(String.format("createdByTag is invalid.%s, %s", createdByTag, this));
        }
    }
}
