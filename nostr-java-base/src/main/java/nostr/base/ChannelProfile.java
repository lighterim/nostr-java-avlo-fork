package nostr.base;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 *
 * @author eric
 */
@Data
//@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ChannelProfile extends Profile {    

    public ChannelProfile(String name, String about, URL picture) {
        super(name, about, picture);
    }

    public ChannelProfile(String name, String about, String url) throws MalformedURLException, URISyntaxException {
        this(name, about, new URI(url).toURL());
    }

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }
}
