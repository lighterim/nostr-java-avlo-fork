package nostr.event;

import nostr.base.PublicKey;
import nostr.event.impl.GenericEvent;

import java.util.List;

public class NIP77Event extends GenericEvent {

    public NIP77Event(){
        super();
        this.setNip(77);
    }

    public NIP77Event(PublicKey pubKey, Kind kind, List<BaseTag> tags, String content) {
        super(pubKey, kind, tags, content);
    }
}
