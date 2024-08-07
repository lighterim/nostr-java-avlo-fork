package nostr.api.factory.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nostr.api.factory.EventFactory;
import nostr.event.BaseTag;
import nostr.event.impl.CommunityCreateEvent;
import nostr.id.Identity;

import java.util.List;

public class NIP72Impl {

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class CommunityCreateEventFactory extends EventFactory<CommunityCreateEvent> {
        public CommunityCreateEventFactory(Identity sender, List<BaseTag> tags){
            super(sender, tags,null);
        }

        @Override
        public CommunityCreateEvent create() {
            return new CommunityCreateEvent(getSender(), getTags());
        }
    }
}
