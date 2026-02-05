package nostr.event;

import lombok.Getter;

@Getter
public enum Role {
    TAKER("taker"),
    MAKER("maker");

    private final String role;

    Role(String role){
        this.role = role;
    }

    public static Role reverse(Role s){
        return (s == TAKER) ? TAKER : MAKER;
    }

}
