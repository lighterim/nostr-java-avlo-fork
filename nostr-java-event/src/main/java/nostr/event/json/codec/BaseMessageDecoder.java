package nostr.event.json.codec;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import lombok.NonNull;
import lombok.SneakyThrows;
import nostr.base.IDecoder;
import nostr.event.BaseMessage;
import nostr.event.impl.GenericMessage;
import nostr.event.message.*;

import java.util.Map;

/**
 * @author eric
 */
public class BaseMessageDecoder<T extends BaseMessage> implements IDecoder<T> {
    private final ObjectMapper mapper;

    public BaseMessageDecoder() {
        mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    }

    @SneakyThrows
    @Override
    public T decode(@NonNull String jsonString) {
        Object[] msgArr = mapper.readValue(jsonString, Object[].class);
        final String strCmd = msgArr[0].toString();
        final Object arg = msgArr.length>1?msgArr[1]:null; //[PING]

        return switch (strCmd) {
            case "AUTH" -> arg instanceof Map map ?
                CanonicalAuthenticationMessage.decode(map, mapper) :
                RelayAuthenticationMessage.decode(arg);
            case "CLOSE" -> CloseMessage.decode(arg);
            case "EOSE" -> EoseMessage.decode(arg);
            case "EVENT" -> EventMessage.decode(msgArr, mapper);
            case "NOTICE" -> NoticeMessage.decode(arg);
            case "OK" -> OkMessage.decode(msgArr);
            case "REQ" -> ReqMessage.decode(msgArr, mapper);
            case "PING" -> PingMessage.decode(arg);
            case "PONG" -> PongMessage.decode(arg);
            default -> GenericMessage.decode(msgArr);
        };
    }
}
