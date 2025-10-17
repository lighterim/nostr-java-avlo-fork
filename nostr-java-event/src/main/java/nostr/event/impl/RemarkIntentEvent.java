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
import nostr.event.tag.AccountTag;
import nostr.event.tag.RemarkTag;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Event(name=NIP77Event.REMARK_INTENT_EVENT, nip=77)
public class RemarkIntentEvent extends NIP77Event {

    @JsonIgnore
    private RemarkTag remarkTag;
    public RemarkIntentEvent(@NonNull PublicKey pubKey, @NonNull List<BaseTag> tags, @NonNull String content){
        super(pubKey, Kind.REMARK_INTENT, tags, content);
        initTags();
    }

    public RemarkIntentEvent(@NonNull PublicKey pubKey, @NonNull List<BaseTag> tags, @NonNull String content, @NonNull String eventIdString, @NonNull Integer nip, long createdAt){
        super(pubKey, Kind.REMARK_INTENT, tags, content);
        this.setNip(nip);
        this.setId(eventIdString);
        this.setCreatedAt(createdAt);
        initTags();
    }

    private void initTags() {
        if(remarkTag == null) {
            this.remarkTag = findTag(RemarkTag.class, REMARK_TAG_CODE);
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
        if(remarkTag == null){
            throw new AssertionError("remarkTag must not be empty!");
        }
    }
}
