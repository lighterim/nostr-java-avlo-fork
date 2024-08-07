package nostr.api;

import lombok.NonNull;
import nostr.api.factory.impl.NIP72Impl;
import nostr.base.CommunityProfile;
import nostr.event.BaseTag;
import nostr.event.impl.GenericEvent;
import nostr.id.Identity;

import java.util.List;

public class NIP72<T extends GenericEvent> extends EventNostr<T> {
    public NIP72(@NonNull Identity sender) {
        setSender(sender);
    }

    public NIP72<T> createCommunityCreateEvent(List<BaseTag> tags){
        var factory = new NIP72Impl.CommunityCreateEventFactory(getSender(), tags);
        var event = factory.create();
        setEvent((T) event);
        return this;
    }

    public NIP72<T> createCommunityPostEvent(){
        return this;
    }

    public NIP72<T> createCommunityModerationEvent(){
        return this;
    }
}
