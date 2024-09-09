package nostr.event.json.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import nostr.base.GenericTagQuery;
import nostr.event.Kind;
import nostr.event.query.CompositionQuery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CompositionQueryDeserializer extends JsonDeserializer<CompositionQuery> {

    @Override
    public CompositionQuery deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);

        var compositionQuery = new CompositionQuery();
        Iterator<Map.Entry<String, JsonNode>> fields = rootNode.fields();
        fields.forEachRemaining(field -> {
            switch (field.getKey()) {
                case "kind" -> {
                    Kind kind = Kind.valueOf(field.getValue().asInt());
                    compositionQuery.setKind(kind);
                }
                case "anyMatchList" -> setMatchList(field, objectMapper, compositionQuery.getAnyMatchList());
                case "allMatchList" -> setMatchList(field, objectMapper, compositionQuery.getAllMatchList());
            }
        });

        return compositionQuery;
    }

    private static void setMatchList(Map.Entry<String, JsonNode> field, ObjectMapper objectMapper, List<GenericTagQuery> matchList) {
        ArrayNode any = (ArrayNode) field.getValue();
        for (int i = 0; i < any.size(); i++) {
            GenericTagQuery tagQuery = new GenericTagQuery();
            String tagName = any.get(i).fieldNames().next();
            JsonNode valuesNode = any.get(i).get(tagName);
            List<String> values = objectMapper.convertValue(valuesNode, ArrayList.class);
            tagQuery.setTagName(tagName); // Assuming tagName is always a single character preceded by '#'
            tagQuery.setValue(values);
            matchList.add(tagQuery);
        }
    }
}
