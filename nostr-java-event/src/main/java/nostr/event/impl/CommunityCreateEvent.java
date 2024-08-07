package nostr.event.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;
import nostr.base.CommunityProfile;
import nostr.base.PublicKey;
import nostr.base.annotation.Event;
import nostr.event.BaseTag;
import nostr.event.Kind;

import java.util.List;
import java.util.UUID;


@Event(name = "Create Community", nip = 72)
public class CommunityCreateEvent extends GenericEvent{

    public CommunityCreateEvent(@NonNull PublicKey pubKey, List<BaseTag> tags){
        super(pubKey, Kind.COMMUNITY_CREATE, tags);
    }

    public static class CommunityProfile{

        @JsonProperty
        private String id;

        @JsonProperty
        private String name;

        @JsonProperty
        private String description;

        @JsonProperty
        private String img;

        @JsonProperty
        private String imgWH;
        public CommunityProfile(@NonNull String name, String description, String img, String imgWH) {
            this.id = UUID.randomUUID().toString();
            this.name = name;
            this.description = description;
            this.img = img;
            this.imgWH = imgWH;
        }
    }

    public static class Moderator{
        private String pubKey;

        private String relay;


    }
}
