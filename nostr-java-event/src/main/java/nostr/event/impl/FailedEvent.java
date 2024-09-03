package nostr.event.impl;


import lombok.Getter;
import nostr.base.annotation.Event;

@Getter
@Event(name = "FailedEvent")
public class FailedEvent extends GenericEvent{

    private String message;

    public FailedEvent(String message){
        this.message = message;
    }

}
