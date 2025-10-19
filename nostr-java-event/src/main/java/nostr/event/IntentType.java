package nostr.event;

public enum IntentType {

    BULK_SELL("BULK_SELL"),
    SIGNATURE_SELL("SIGNATURE_SELL"),
    BUYER_INTENT("BUYER_INTENT");

    private final String desc;

    IntentType(String desc){
        this.desc = desc;
    }

    public String getDesc() {
        return this.desc;
    }
}
