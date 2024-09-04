package nostr.api.factory.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nostr.api.factory.EventFactory;
import nostr.event.BaseTag;
import nostr.event.Kind;
import nostr.event.impl.PostIntentEvent;
import nostr.event.impl.TakeIntentEvent;
import nostr.event.impl.TradeMessageEvent;
import nostr.id.Identity;

import java.util.List;

public class NIP77Impl {

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class PostIntentEventFactory extends EventFactory<PostIntentEvent>{

        private final Kind kind;

        public PostIntentEventFactory(Identity sender, List<BaseTag> tags, String content) {
            super(sender, tags, content);
            this.kind = Kind.POST_INTENT;
        }

        @Override
        public PostIntentEvent create() {
            return new PostIntentEvent(getSender(), getTags(), getContent());
        }
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class TakeIntentEventFactory extends EventFactory<TakeIntentEvent>{

        private final Kind kind;
        private final long tradeId;

        public TakeIntentEventFactory(Long tradeId, Identity sender, List<BaseTag> tags, String content){
            super(sender, tags, content);
            this.kind = Kind.TAKE_INTENT;
            this.tradeId = tradeId;
        }

        @Override
        public TakeIntentEvent create(){
            return new TakeIntentEvent(tradeId, getSender(), getTags(), getContent());
        }
    }

    @Data
    @EqualsAndHashCode(callSuper=false)
    public static class TradeMessageEventFactory extends EventFactory<TradeMessageEvent>{
        private final Kind kind;

        public TradeMessageEventFactory(Identity identity, List<BaseTag> tags, String content){
            super(identity, tags, content);
            this.kind = Kind.TRADE_MESSAGE;
        }

        @Override
        public TradeMessageEvent create(){
            return new TradeMessageEvent(getSender(), getTags(), getContent());
        }
    }
}
