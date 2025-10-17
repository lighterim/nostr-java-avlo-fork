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
import nostr.event.tag.AddressBookTag;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Event(name=NIP77Event.ACCOUNT_BOOK_INTENT_EVENT, nip=77)
public class AddressBookIntentEvent extends NIP77Event {

    @JsonIgnore
    private AddressBookTag addressBookTag;
    public AddressBookIntentEvent(@NonNull PublicKey pubKey, @NonNull List<BaseTag> tags, @NonNull String content){
        super(pubKey, Kind.ADDRESS_BOOK_INTENT, tags, content);
        initTags();
    }

    public AddressBookIntentEvent(@NonNull PublicKey pubKey, @NonNull List<BaseTag> tags, @NonNull String content, @NonNull String eventIdString, @NonNull Integer nip, long createdAt){
        super(pubKey, Kind.ADDRESS_BOOK_INTENT, tags, content);
        this.setNip(nip);
        this.setId(eventIdString);
        this.setCreatedAt(createdAt);
        initTags();
    }

    private void initTags() {
        if(addressBookTag == null) {
            this.addressBookTag = findTag(AddressBookTag.class, ADDRESS_BOOK_TAG_CODE);
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
        if(addressBookTag == null){
            throw new AssertionError("addressBookTag must not be empty!");
        }
    }
}
