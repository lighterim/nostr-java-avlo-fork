package nostr.event.impl;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nostr.base.PublicKey;
import nostr.base.annotation.Event;
import nostr.event.BaseTag;
import nostr.event.Kind;
import nostr.event.NIP99Event;
import nostr.event.tag.ClassifiedListing;

import java.util.List;

@Getter
@EqualsAndHashCode(callSuper = false)
@Event(name = "ClassifiedListingEvent", nip = 99)
public class ClassifiedListingEvent extends NIP99Event {
  private final ClassifiedListing classifiedListing;

  public ClassifiedListingEvent(PublicKey sender, List<BaseTag> baseTags, String content, ClassifiedListing classifiedListing) {
    this(sender, Kind.CLASSIFIED_LISTING, baseTags, content, classifiedListing);
  }

  public ClassifiedListingEvent(PublicKey sender, Kind kind, List<BaseTag> baseTags, String content, ClassifiedListing classifiedListing) {
    super(sender, kind, baseTags, content);
    this.classifiedListing = classifiedListing;
  }
}
