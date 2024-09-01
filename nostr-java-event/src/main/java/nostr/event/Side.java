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

    public static Side reverse(Side s){
        return (s == BUY) ? SELL : BUY;
    }

}
