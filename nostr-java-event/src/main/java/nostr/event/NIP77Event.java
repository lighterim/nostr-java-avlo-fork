package nostr.event;

import nostr.base.PublicKey;
import nostr.event.impl.GenericEvent;

import java.math.BigDecimal;
import java.util.List;

public class NIP77Event extends GenericEvent {


    public static final String TAKE_TAG_CODE = "take";
    public static final String TOKEN_TAG_CODE = "token";
    public static final String QUOTE_TAG_CODE = "quote";
    public static final String PAYMENT_TAG_CODE = "payment";
    public static final String LIMIT_TAG_CODE = "limit";
    public static final String MAKE_TAG_CODE = "make";
    public static final String CREATED_BY_TAG_CODE = "created_by";
    public static final String POST_INTENT_EVENT = "post_intent";
    public static final String TAKE_INTENT_EVENT = "take_intent";
    public static final String TRADE_MESSAGE_EVENT = "trade_message";


    public NIP77Event(){
        super();
        this.setNip(77);
    }

    public NIP77Event(PublicKey pubKey, Kind kind, List<BaseTag> tags, String content) {
        super(pubKey, kind, tags, content);
    }

    public <T extends  BaseTag> T findTag(Class<T> tagClass, String tagCode){
        List<BaseTag> tags = getTags();
        if(tags == null || tags.isEmpty()){
            return null;
        }
        for(BaseTag t : tags){
            if (t.getCode().equals(tagCode) && tagClass.isInstance(t)){
                return (T)t;
            }
        }
        return null;
    }

    protected static boolean isBlank(String s){
        return s == null || s.isBlank();
    }

    protected static boolean gtZero(BigDecimal dec){
        return dec != null && dec.compareTo(BigDecimal.ZERO) > 0;
    }
}
