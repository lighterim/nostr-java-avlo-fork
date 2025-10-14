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
@Event(name=NIP77Event.ACCOUNT_INTENT_EVENT, nip=77)
public class AccountIntentEvent extends NIP77Event {

    @JsonIgnore
    private AccountTag accountTag;
    public AccountIntentEvent(@NonNull PublicKey pubKey, @NonNull List<BaseTag> tags, @NonNull String content){
        super(pubKey, Kind.ACCOUNT_INTENT, tags, content);
        initTags();
    }

    public AccountIntentEvent(@NonNull PublicKey pubKey, @NonNull List<BaseTag> tags, @NonNull String content, @NonNull String eventIdString, @NonNull Integer nip, long createdAt){
        super(pubKey, Kind.ACCOUNT_INTENT, tags, content);
        this.setNip(nip);
        this.setId(eventIdString);
        this.setCreatedAt(createdAt);
        initTags();
    }

    private void initTags() {
        if(accountTag == null) {
            this.accountTag = findTag(AccountTag.class, ACCOUNT_TAG_CODE);
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
        if(accountTag == null){
            throw new AssertionError("accountTag must not be empty!");
        }
    }
}
