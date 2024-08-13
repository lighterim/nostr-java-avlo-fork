package nostr.event.json.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import nostr.base.GenericTagQuery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author eric
 */
@NoArgsConstructor
public class CustomGenericTagQueryListDeserializer extends JsonDeserializer<List<GenericTagQuery>> {
    @Override
    public List<GenericTagQuery> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);

        List<GenericTagQuery> tagQueryList = new ArrayList<>();

        for (JsonNode node : rootNode) {
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            if (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                String tagName = field.getKey();
                JsonNode valuesNode = field.getValue();
                List<String> values = objectMapper.convertValue(valuesNode, ArrayList.class);

                GenericTagQuery genericTagQuery = new GenericTagQuery();
                genericTagQuery.setTagName(tagName);
                genericTagQuery.setValue(values);

                tagQueryList.add(genericTagQuery);
            }
        }

        return tagQueryList;
    }
}
