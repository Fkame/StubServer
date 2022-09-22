package ni.shi.app.service.json.generator.template.by_input_types;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

@Slf4j
public class JsonGeneratorByInputTypes {
/*
    public String generateByCustomTemplate(String customTemplate) {
        ObjectMapper jsonMapper = new ObjectMapper();
        try {
            JsonNode rootNode = jsonMapper.readTree(customTemplate);
            regenerateValues(null, rootNode);
            return jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
        } catch (JsonProcessingException ex) {
            log.error(ex.getMessage() + "\n" + Arrays.toString(ex.getStackTrace()));
            return null;
        }
    }

    private void regenerateValues (JsonNode parentNode, JsonNode node)  {
        if (node.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            fields.forEachRemaining(keyAndValue -> {
                JsonNode field = keyAndValue.getValue();
                regenerateValues(node, field);
            });
        }
        else if (node.isArray()) {
            Iterator<JsonNode> elements = node.elements();
            elements.forEachRemaining(jsonNode -> {
                regenerateValues(node, jsonNode);
            });
        }
        else {
            if (parentNode.isObject()) {
                generateNodeValueByType((ObjectNode) parentNode, node);
            }
            if (parentNode.isArray()) {
                generateNodeValueByType((ArrayNode) parentNode, node);
            }
        }
    }
    */
}
