/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nostr.api;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import nostr.api.factory.impl.GenericEventFactory;
import nostr.base.PublicKey;
import nostr.event.BaseTag;
import nostr.event.impl.GenericEvent;
import nostr.id.Identity;

import java.util.Map;

/**
 * @author guilhermegps
 */
public abstract class EventNostr<T extends GenericEvent> extends Nostr {

    @Getter
    @Setter
    private T event;

    @Getter
    private PublicKey recipient;

    public EventNostr sign() {
        super.sign(getSender(), event);

        return this;
    }

    public T send() {
        super.send(this.event);

        return this.event;
    }

    public T send(Map<String, String> relays) {
        super.setRelays(relays);

        return send();
    }

    public T signAndSend() {
        return (T) sign().send();
    }

    public T signAndSend(Map<String, String> relays) {
        super.setRelays(relays);

        return signAndSend();
    }

    public EventNostr setSender(@NonNull Identity sender) {
        super.setSender(sender);

        return this;
    }

    public EventNostr setRecipient(@NonNull PublicKey recipient) {
        this.recipient = recipient;

        return this;
    }

    public EventNostr addTag(@NonNull BaseTag tag) {
        getEvent().addTag(tag);
        return this;
    }

    public static class Event<T extends GenericEvent> extends EventNostr<T> {
        /**
         * @param content
         * @return
         */
        public Event createGenericEvent(@NonNull Integer kind, @NonNull String content) {
            var factory = new GenericEventFactory(getSender(), kind, content);
            var event = factory.create();
            setEvent((T) event);

            return this;
        }

    }
}
