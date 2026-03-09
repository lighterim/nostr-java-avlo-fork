package nostr.event.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import nostr.base.Command;
import nostr.base.IEncoder;
import nostr.event.BaseMessage;

@Setter
@Getter
public class PingMessage  extends BaseMessage {

    public PingMessage() {
        super(Command.PING.name());
    }

    @Override
    public String encode() throws JsonProcessingException {
        return IEncoder.MAPPER.writeValueAsString(
                getArrayNode().add(getCommand())
        );
    }

    public static <T extends BaseMessage> T decode(Object arg) {
        return (T) new PingMessage();
    }
}
