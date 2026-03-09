package nostr.event.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import nostr.base.Command;
import nostr.base.IEncoder;
import nostr.event.BaseMessage;

@Setter
@Getter
public class PongMessage extends BaseMessage {

    public PongMessage(){
        super(Command.PONG.name());
    }

    @Override
    public String encode() throws JsonProcessingException {
        return IEncoder.MAPPER.writeValueAsString(
                getArrayNode().add(getCommand())
        );
    }

    public static <T extends  BaseMessage> T decode(Object arg) throws JsonProcessingException {
        return (T) new PongMessage();
    }


}
