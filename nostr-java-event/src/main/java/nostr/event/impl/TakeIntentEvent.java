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
import nostr.event.tag.TakeTag;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Event(name=NIP77Event.TAKE_INTENT_EVENT, nip=77)
public class TakeIntentEvent extends NIP77Event {

    private TakeTag takeTag;

    public TakeIntentEvent(@NonNull PublicKey pubKey, @NonNull List<BaseTag> tags, @NonNull String content){
        super(pubKey, Kind.TAKE_INTENT, tags, content);
        takeTag = findTag(TakeTag.class, NIP77Event.TAKE_TAG_CODE);
    }

    @Override
    protected void validate() {
        super.validate();
        if(takeTag == null || isBlank(takeTag.getIntentEventId()) || !gtZero(takeTag.getVolume())) {
            throw new AssertionError("take tag incorrect.", null);
        }
    }
}
