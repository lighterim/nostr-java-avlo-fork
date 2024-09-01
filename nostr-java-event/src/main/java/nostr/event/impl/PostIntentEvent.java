package nostr.event.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import nostr.base.PublicKey;
import nostr.base.annotation.Event;
import nostr.event.BaseTag;
import nostr.event.Kind;
import nostr.event.NIP77Event;
import nostr.event.tag.LimitTag;
import nostr.event.tag.QuoteTag;
import nostr.event.tag.MakeTag;
import nostr.event.tag.TokenTag;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Event(name=NIP77Event.POST_INTENT_EVENT, nip=77)
public class PostIntentEvent extends NIP77Event {

    private TokenTag tokenTag;
    private QuoteTag quoteTag;
    private MakeTag sideTag;
    private LimitTag limitTag;

    public PostIntentEvent(@NonNull PublicKey pubKey, @NonNull List<BaseTag> tags, @NonNull String content){
        super(pubKey, Kind.POST_INTENT, tags, content);
        this.quoteTag = findTag(QuoteTag.class, QUOTE_TAG_CODE);
        this.tokenTag = findTag(TokenTag.class, TOKEN_TAG_CODE);
        this.sideTag = findTag(MakeTag.class, MAKE_TAG_CODE);
        this.limitTag = findTag(LimitTag.class, LIMIT_TAG_CODE);
    }

    @Override
    protected void validate() {
        super.validate();
        if(tokenTag == null || isBlank(tokenTag.getSymbol()) || isBlank(tokenTag.getChain()) || isBlank(tokenTag.getNetwork()) || isBlank(tokenTag.getAddress())
                || sideTag == null || sideTag.getSide()==null){
            throw new AssertionError("tokenTag, sideTag, quoteTag must not be empty!");
        }
    }
}
