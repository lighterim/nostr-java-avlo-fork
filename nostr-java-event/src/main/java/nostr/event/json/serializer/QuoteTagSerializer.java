package nostr.event.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import nostr.event.tag.PaymentTag;
import nostr.event.tag.QuoteTag;

import java.io.IOException;
import java.math.BigDecimal;

public class QuoteTagSerializer extends JsonSerializer<QuoteTag> {
    @Override

    public void serialize(QuoteTag t, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        jsonGenerator.writeString(t.getCode());
        jsonGenerator.writeString(t.getNumber().stripTrailingZeros().toPlainString());
        jsonGenerator.writeString(t.getCurrency());
        jsonGenerator.writeString(t.getUsdRate().stripTrailingZeros().toPlainString());
        jsonGenerator.writeEndArray();
    }


    public static void main(String[] args) {
//        BigDecimal bd = new BigDecimal("0.00000000000001000000");
        BigDecimal bd = new BigDecimal("10.00");
        System.out.println("默认使用科学计数法：" + bd);
        System.out.println("去掉末尾的0：" + bd.stripTrailingZeros().toPlainString());
        String n = bd.stripTrailingZeros().toPlainString();
        System.out.println(String.format("不使用科学计数法：n:%s, n_str:%s", n, new BigDecimal(n)));
    }
}
