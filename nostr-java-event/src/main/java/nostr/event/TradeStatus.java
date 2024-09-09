package nostr.event;

import lombok.Getter;

@Getter
public enum TradeStatus {
    TakeEvent("take"),
    CreateEscrowEvent("escrow"),
    BuyerPaidEvent("paid"),
    SellerReleasedEvent("release"),
    SellerRequestCancelEvent("seller_request_cancel"),
    BuyerCancelEvent("buyer_cancel"),
    SellerCancelEvent("seller_cancel");



    private final String value;

    TradeStatus(String value){
        this.value = value;
    }

    public static TradeStatus forValue(String value){
        return switch (value){
            case "take" -> TakeEvent;
            case "escrow" -> CreateEscrowEvent;
            case "paid" -> BuyerPaidEvent;
            case "release" -> SellerReleasedEvent;
            case "seller_request_cancel" -> SellerRequestCancelEvent;
            case "buyer_cancel" -> BuyerCancelEvent;
            case "seller_cancel" -> SellerCancelEvent;
            default -> throw new IllegalArgumentException("value="+value);
        };
    }

}
