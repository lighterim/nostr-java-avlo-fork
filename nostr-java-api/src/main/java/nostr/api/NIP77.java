package nostr.api;

import lombok.NonNull;
import nostr.api.factory.impl.NIP77Impl.*;
import nostr.base.PublicKey;
import nostr.event.BaseTag;
import nostr.event.NIP77Event;
import nostr.event.impl.PostIntentEvent;
import nostr.id.Identity;

import java.util.List;

public class NIP77<T extends NIP77Event> extends EventNostr<T> {

    public NIP77(@NonNull Identity sender){
        setSender(sender);
    }

    public NIP77<T> createPostIntentEvent(@NonNull List<BaseTag> tags, String content){
        setEvent((T) new PostIntentEventFactory(getSender(), tags, content).create());
        return this;
    }


}
