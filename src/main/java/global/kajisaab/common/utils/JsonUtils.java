package global.kajisaab.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.*;

public class JsonUtils {
    private final static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> fromJsonList(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<T>>() {});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> toMap(JsonNode jsonNode) {
        var map = objectMapper.convertValue(jsonNode, Map.class);

        return Optional.ofNullable(map)
                .orElseGet(HashMap::new);
    }

    public static <K> Map toMaps(K ob) {
        var map = objectMapper.convertValue(ob, Map.class);

        return Optional.ofNullable(map)
                .orElseGet(HashMap::new);
    }

    public static <K, V> JsonNode toJsonNode(Map<K, V> map) {
        return objectMapper.valueToTree(map);
    }

    public static JsonNode toJsonNode(String json) {
        try {
            return objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            return objectMapper.createObjectNode();
        }
    }

    public static JsonNode extractAndRemove(JsonNode original, String ... keys) {
        if (original == null || !original.isObject()) {
            throw new IllegalArgumentException("Provided JsonNode must be an ObjectNode");
        }

        ObjectNode remaining = objectMapper.createObjectNode();
        Iterator<Map.Entry<String, JsonNode>> fields = original.fields();

        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            String fieldName = field.getKey();
            JsonNode fieldValue = field.getValue();

            Arrays.stream(keys).forEach(key -> {
                if (!fieldName.equals(key)) {
                    remaining.set(fieldName, fieldValue);
                }
            });
        }

        return remaining;
    }

    public static ObjectMapper getObjectMapper(){
        return objectMapper;
    }


}