package nostr.event.serializer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.extern.java.Log;
import nostr.base.GenericTagQuery;
import nostr.base.IMarshaller;
import nostr.event.list.GenericTagQueryList;


/**
 * @author guilhermegps
 *
 */
@Log
public class CustomGenericTagListSerializer extends JsonSerializer<GenericTagQueryList> {

	@Override
	public void serialize(GenericTagQueryList value, JsonGenerator gen, SerializerProvider serializers) {
		try {
			var list = value.getList().parallelStream().map(gtq -> toJson(gtq))
					.collect(Collectors.toList());
			
			gen.writePOJO(list);
		} catch (IOException e) {
            log.log(Level.SEVERE, null, e);
            throw new RuntimeException(e);
		}
	}
    
    protected JsonNode toJson(GenericTagQuery gtq) {
	    var mapper = IMarshaller.MAPPER;
    	try {
	    	JsonNode node = mapper.valueToTree(gtq);
	    	ObjectNode objNode = (ObjectNode) node;
	    	objNode.set("#" + node.get("tagName").textValue(), node.get("value"));
	    	objNode.remove("tagName");
	    	objNode.remove("value");
	    	
	    	return node;
		} catch (Exception e) {
            log.log(Level.SEVERE, null, e);
            throw new RuntimeException(e);
		} 
    }

}
