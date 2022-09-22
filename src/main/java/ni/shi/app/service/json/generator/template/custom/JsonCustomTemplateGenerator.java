package ni.shi.app.service.json.generator.template.custom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import static net.andreinc.mockneat.unit.text.Strings.strings;
import static net.andreinc.mockneat.unit.types.Bools.bools;
import static net.andreinc.mockneat.unit.types.Doubles.doubles;
import static net.andreinc.mockneat.unit.types.Ints.ints;

@Slf4j
@NoArgsConstructor
public class JsonCustomTemplateGenerator {

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

    private void generateNodeValueByType(ObjectNode parentNode, JsonNode node) {
        if (node.isArray() || node.isObject()) {
            throw new IllegalArgumentException();
        }
        String fieldName = findFieldNameByValue(parentNode, node);

        if (node.isBoolean()) {
            boolean generated = bools().get();
            parentNode.put(fieldName, generated);
        } else if (node.isDouble()) {
            double generated = doubles().get();
            parentNode.put(fieldName, generated);
        } else if (node.isInt()) {
            int generated = ints().get();
            parentNode.put(fieldName, generated);
        } else if (node.isTextual()) {
            String generated = strings().get();
            parentNode.put(fieldName, generated);
        }
        else {
            log.warn("Unknown node type: " + node.getNodeType());
        }
    }

    private String findFieldNameByValue(JsonNode parentNode, JsonNode node) {
        Iterator<Map.Entry<String, JsonNode>> fields = parentNode.fields();
        while (fields.hasNext()) {
            var field = fields.next();
            if (field.getValue().equals(node)) {
                return field.getKey();
            }
        }
        throw new IllegalArgumentException("Parent object node: " + parentNode +
                " does not contains node " + node);
    }

    private void generateNodeValueByType(ArrayNode parentNode, JsonNode node) {
        if (node.isArray() || node.isObject()) {
            throw new IllegalArgumentException();
        }
        int idx = -1;
        for (JsonNode subNode : parentNode) {
            idx += 1;
            if (subNode.equals(node)) {
                break;
            }
        }
        if (idx == parentNode.size()) {
            throw new IllegalArgumentException("Parent object node: " + parentNode +
                    " does not contains node " + node);
        }

        if (node.isBoolean()) {
            boolean generated = bools().get();
            parentNode.set(idx, generated);
        } else if (node.isDouble()) {
            double generated = doubles().get();
            parentNode.set(idx, generated);
        } else if (node.isInt()) {
            int generated = ints().get();
            parentNode.set(idx, generated);
        } else if (node.isTextual()) {
            String generated = strings().get();
            parentNode.set(idx, generated);
        } else {
            log.warn("Unknown node type: " + node.getNodeType());
        }
    }
}
