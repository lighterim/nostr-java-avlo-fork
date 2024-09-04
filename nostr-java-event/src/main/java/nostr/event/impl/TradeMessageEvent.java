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
import nostr.event.tag.LedgerTag;

import java.util.List;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Event(name=NIP77Event.TRADE_MESSAGE_EVENT, nip=77)
public class TradeMessageEvent extends NIP77Event {

    @JsonIgnore
    private CreatedByTag createdByTag;
    @JsonIgnore
    private LedgerTag ledgerTag;
    public TradeMessageEvent(@NonNull PublicKey pubKey, @NonNull List<BaseTag> tags, @NonNull String content){
        super(pubKey, Kind.TRADE_MESSAGE, tags, content);
        initTags();
    }

    private void initTags() {
        if(createdByTag == null) {
            this.createdByTag = findTag(CreatedByTag.class, CREATED_BY_TAG_CODE);
        }
        if(ledgerTag == null){
            this.ledgerTag = findTag(LedgerTag.class, LEDGER_TAG_CODE);
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
        if(createdByTag != null){
            throw  new AssertionError("createdByTag is null");
        }
    }
}
