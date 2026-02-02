module nostr.util {
    requires static lombok;
    requires java.logging;

    requires nostr.context;
    requires org.apache.commons.lang3;

    exports nostr.util;
    exports nostr.util.thread;
    exports nostr.util.validator;
}
