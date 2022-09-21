package ni.shi.app.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

@Slf4j
public class JacksonTest {

    /**
     * asText - вывод значения узла
     * elements - дочерние элементы текущего узла (только 1 уровень)
     * fields - получить дочерние поля и их значения для текущего узла
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
    @SneakyThrows
    public void showAllNodes() {
        LinkedList<Map.Entry<String,JsonNode>> nodesBuffer = new LinkedList<>();
        rootNode.fields().forEachRemaining(nodesBuffer::add);
        rootNode.fields().forEachRemaining(elem -> log.info(elem.getKey() + " -> " + elem.getValue()));

        while (!nodesBuffer.isEmpty()) {
            Map.Entry<String, JsonNode> currentNode = nodesBuffer.removeLast();
            currentNode.getValue().fields().forEachRemaining(nodesBuffer::add);
            currentNode.getValue().fields().forEachRemaining(elem -> log.info(elem.getKey() + " -> " + elem.getValue()));
        }
    }
}
