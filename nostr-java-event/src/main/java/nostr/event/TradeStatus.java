package nostr.event;

import lombok.Getter;

@Getter
public enum TradeStatus {
    /**
     *  enum EscrowStatus {
     *         Escrowed,
     *         SellerRequestCancel,
     *         Paid,
     *         SellerCancelled,
     *         BuyerCancelled,
     *         BuyerDisputed,
     *         SellerDisputed,
     *         Resolved,
     *         ThresholdReachedReleased,
     *         SellerReleased
     *     }
     */
    TakeEvent("take"),
    CreateEscrowEvent("escrow"),
    SellerRequestCancelEvent("seller_request_cancel"),
    BuyerPaidEvent("paid"),
    SellerCancelEvent("seller_cancel"),
    BuyerCancelEvent("buyer_cancel"),
    BuyerDisputedEvent("buyer_disputed"),
    SellerDisputedEvent("seller_disputed"),
    ResolvedEvent("resolved"),
    ThresholdReachedEvent("threshold_reached_released"),
    SellerReleasedEvent("release");



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
