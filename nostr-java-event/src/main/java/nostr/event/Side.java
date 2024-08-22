package nostr.event;

import lombok.Getter;

@Getter
public enum Side {
    BUY("buy"),
    SELL("sell");

    private final String side;

    Side(String side){
        this.side = side;
    }
}
