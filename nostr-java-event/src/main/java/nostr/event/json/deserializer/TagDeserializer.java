package nostr.event.json.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import nostr.event.BaseTag;
import nostr.event.NIP77Event;
import nostr.event.json.codec.GenericTagDecoder;
import nostr.event.tag.*;

import java.io.IOException;

public class TagDeserializer<T extends BaseTag> extends JsonDeserializer<T> {

    @Override
    public T deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        // Extract relevant data from the JSON node
        String code = node.get(0).asText();

        if (null == code) {
            throw new IOException("Unknown tag code: " + null);
        } else // Perform custom deserialization logic based on the concrete class
        {
            return switch (code) {
                case "e" -> EventTag.deserialize(node);
                case "g" -> GeohashTag.deserialize(node);
                case "p" -> PubKeyTag.deserialize(node);
                case "t" -> HashtagTag.deserialize(node);

                case "nonce" -> NonceTag.deserialize(node);
                case "price" -> PriceTag.deserialize(node);
                case "relays" -> RelaysTag.deserialize(node);
                case "subject" -> SubjectTag.deserialize(node);
                case NIP77Event.TOKEN_TAG_CODE -> TokenTag.deserialize(node);
                case NIP77Event.QUOTE_TAG_CODE -> QuoteTag.deserialize(node);
                case NIP77Event.PAYMENT_TAG_CODE -> PaymentTag.deserialize(node);
                case NIP77Event.MAKE_TAG_CODE -> MakeTag.deserialize(node);
                case NIP77Event.TAKE_TAG_CODE -> TakeTag.deserialize(node);
                case NIP77Event.LIMIT_TAG_CODE -> LimitTag.deserialize(node);
                case NIP77Event.CREATED_BY_TAG_CODE -> CreatedByTag.deserialize(node);
                case NIP77Event.LEDGER_TAG_CODE -> LedgerTag.deserialize(node);
                default -> (T) new GenericTagDecoder<>().decode(node.toString());
            };
        }
    }
}
