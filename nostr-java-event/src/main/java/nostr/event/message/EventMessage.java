package nostr.event.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import nostr.base.Command;
import nostr.base.IEncoder;
import nostr.base.IEvent;
import nostr.event.BaseEvent;
import nostr.event.BaseMessage;
import nostr.event.Kind;
import nostr.event.NIP77Event;
import nostr.event.impl.GenericEvent;
import nostr.event.impl.PostIntentEvent;
import nostr.event.impl.TakeIntentEvent;
import nostr.event.impl.TradeMessageEvent;
import nostr.event.json.codec.BaseEventEncoder;

import java.util.Map;

@Setter
@Getter
public class EventMessage extends BaseMessage {

    @JsonProperty
    private final IEvent event;

    @JsonProperty
    private String subscriptionId;

    public EventMessage(@NonNull IEvent event) {
        this(event, null);
    }

    public EventMessage(@NonNull IEvent event, String subscriptionId) {
        super(Command.EVENT.name());
        this.event = event;
        this.subscriptionId = subscriptionId;
    }

    @Override
    public String encode() throws JsonProcessingException {
//        return IEncoder.MAPPER.writeValueAsString(
//            getArrayNode()
//                .add(getCommand())
//                .add(IEncoder.MAPPER.readTree(
//                    new BaseEventEncoder<>((BaseEvent)getEvent()).encode())));
//
        ArrayNode arrayNode = IEncoder.MAPPER.createArrayNode();

        // 将命令添加到 JSON 数组中
        arrayNode.add(this.getCommand());

        // 将 subscriptionId 添加到 JSON 数组中
        if (this.subscriptionId != null) {
            arrayNode.add(this.subscriptionId);
        } else {
            // 如果 subscriptionId 为空，添加一个空字符串
            arrayNode.add("");
        }

        // 将事件编码为 JSON 并添加到数组中
        String eventJson = new BaseEventEncoder((BaseEvent)this.getEvent()).encode();
        JsonNode eventNode = IEncoder.MAPPER.readTree(eventJson);
        arrayNode.add(eventNode);

        // 将数组编码为字符串并返回
        return IEncoder.MAPPER.writeValueAsString(arrayNode);
    }

    public static <T extends BaseMessage> T decode(@NonNull Object[] msgArr, ObjectMapper mapper) {
        var arg = msgArr[1];
        if (msgArr.length == 2 && arg instanceof Map map) {
            return (T) new EventMessage(convertEvent(map, mapper));
        } else if (msgArr.length == 3 && arg instanceof String) {
            var subId = arg.toString();
            if (msgArr[2] instanceof Map map) {
//                var event = mapper.convertValue(map, new TypeReference<GenericEvent>() {});
                var event = convertEvent(map, mapper);
                return (T) new EventMessage(event, subId);
            }
        }
        throw new AssertionError("Invalid argument: " + arg);
    }

    private static IEvent  convertEvent(Map map, ObjectMapper mapper) {
        Kind kind;
        if(map.get("kind") instanceof  Integer v){
            kind = Kind.valueOf(v);
        }
        else{
            kind = Kind.TEXT_NOTE;
        }
        return switch (kind) {
            case TAKE_INTENT -> mapper.convertValue(map, TakeIntentEvent.class);
            case POST_INTENT -> mapper.convertValue(map, PostIntentEvent.class);
            case TRADE_MESSAGE -> mapper.convertValue(map, TradeMessageEvent.class);
            default -> mapper.convertValue(map, new TypeReference<GenericEvent>() {
            });
        };
    }
}
