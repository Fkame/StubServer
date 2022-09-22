package ni.shi.app.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Slf4j
public class JacksonTest {

    /**
     * asText - вывод значения узла
     * elements - дочерние элементы текущего узла (только 1 уровень).
     * Если попадается object или array - заменяет на null
     * fields - получить дочерние поля и их значения для текущего узла - выводятся рекурсивно массивы и объекты
     */
    JsonNode rootNode;
    ObjectMapper mapper;
    String json;

    @BeforeEach
    @SneakyThrows
    public void setUp() {
        mapper = new ObjectMapper();

        URL jsonFile = JacksonTest.class.getResource("/jackson-test/test-json.json");
        json = Files.readString(Paths.get(jsonFile.toURI()));
        rootNode = mapper.readTree(json);
        System.out.println(json);
    }

    @Test
    public void elementsVsFields() {
        log.info("Fields ---");
        rootNode.fields()
                .forEachRemaining(
                        elem -> log.info(elem.getKey() + " -> " +
                                elem.getValue() +
                                " type:" + elem.getValue().getNodeType())
                );
        log.info("");
        log.info("Elements ---");
        rootNode.elements().forEachRemaining(elem -> log.info(
                elem.asText() + " type:" + elem.getNodeType())
        );
    }

    @Test
    @SneakyThrows
    public void showAllKeys() {
        List<String> keys = new ArrayList<>();
        getAllKeysUsingJsonNodeFieldNames(rootNode, keys);
        keys.forEach(key -> log.info(key));
    }

    private void getAllKeysUsingJsonNodeFieldNames(JsonNode jsonNode, List<String> keys) {

        if (jsonNode.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
            fields.forEachRemaining(field -> {
                keys.add(field.getKey());
                getAllKeysUsingJsonNodeFieldNames((JsonNode) field.getValue(), keys);
            });
        } else if (jsonNode.isArray()) {
            ArrayNode arrayField = (ArrayNode) jsonNode;
            arrayField.forEach(node -> {
                getAllKeysUsingJsonNodeFieldNames(node, keys);
            });
        }
    }


}
