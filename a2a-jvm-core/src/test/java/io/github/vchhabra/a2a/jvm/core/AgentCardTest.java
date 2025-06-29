package io.github.vchhabra.a2a.jvm.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.util.Collections;

class AgentCardTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // Configure the ObjectMapper for the test, just as our transport module will.
        // This setup correctly simulates the real-world serialization behavior.
        this.objectMapper = new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    }

    @Test
    void agentCardShouldSerializeAndDeserializeCorrectly() throws Exception {
        // 1. Create a sample AgentCard object
        Auth auth = new Auth("oauth2", "test-client", URI.create("http://auth.com/auth"), URI.create("http://auth.com/token"), Collections.singletonList("read"));
        Api api = new Api("json_rpc_http", URI.create("http://agent.com/tasks"));
        Action action = new Action("testAction", "A test action", Collections.emptyMap(), Collections.emptyMap());
        AgentCard originalCard = new AgentCard("agent-123", "0.1.0", Collections.singletonList(action), auth, api);

        // 2. Serialize it to a JSON string
        String jsonString = this.objectMapper.writeValueAsString(originalCard);
        System.out.println("Serialized JSON: " + jsonString);

        // 3. Assert that the JSON contains the correct snake_case keys
        assertThat(jsonString).contains("\"id\""); // AgentCard id is not expected to be snake_cased
        assertThat(jsonString).contains("\"input_schema\"");

        // 4. Deserialize the JSON string back into an object
        AgentCard deserializedCard = this.objectMapper.readValue(jsonString, AgentCard.class);

        // 5. Assert that the deserialized object is equal to the original
        assertThat(deserializedCard).isEqualTo(originalCard);
    }
}
