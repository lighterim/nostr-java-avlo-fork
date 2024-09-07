package nostr.event.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import nostr.base.GenericTagQuery;
import nostr.base.IEncoder;
import nostr.event.query.CompositionQuery;

import java.io.IOException;
import java.io.Serial;

/**
 * @author guilhermegps
 */
public class CompositionQuerySerializer extends StdSerializer<CompositionQuery> {

    @Serial
    private static final long serialVersionUID = 2025410108166396427L;

    public CompositionQuerySerializer() {
        super(CompositionQuery.class);
    }

    @Override
    public void serialize(CompositionQuery value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        var mapper = IEncoder.MAPPER;
        gen.writeObjectField("kind", mapper.valueToTree(value.getKind()));
        ArrayNode anyMatchList = (ArrayNode) mapper.valueToTree(value.getAnyMatchList());
        if(anyMatchList !=null && !anyMatchList.isEmpty()){
            gen.writeArrayFieldStart("anyMatchList");
            for(int i=0; i<anyMatchList.size(); i++){
                JsonNode n = toJson(anyMatchList.get(i));
                gen.writeStartObject();
                gen.writeObjectField(n.fieldNames().next(), n.get(n.fieldNames().next()));
                gen.writeEndObject();
            }
            gen.writeEndArray();
        }
        gen.writeEndObject();
    }

    private JsonNode toJson(JsonNode node) {
        try {
            ObjectNode objNode = (ObjectNode) node;
            objNode.set("#" + node.get("tagName").textValue(), node.get("value"));
            objNode.remove("tagName");
            objNode.remove("value");
            objNode.remove("nip");

            return objNode;
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

}
