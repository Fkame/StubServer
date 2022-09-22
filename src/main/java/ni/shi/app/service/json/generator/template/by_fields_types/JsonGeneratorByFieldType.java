package ni.shi.app.service.json.generator.template.by_fields_types;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
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
public class JsonGeneratorByFieldType {

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

    private void regenerateValues(JsonNode parentNode, JsonNode node) {
        if (node.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            fields.forEachRemaining(keyAndValue -> {
                JsonNode field = keyAndValue.getValue();
                regenerateValues(node, field);
            });
        } else if (node.isArray()) {
            Iterator<JsonNode> elements = node.elements();
            elements.forEachRemaining(jsonNode -> {
                regenerateValues(node, jsonNode);
            });
        } else {
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
        ExclusiveJsonNode universalParentNode = ExclusiveJsonNode.createAsObject(parentNode);
        generateValuesUniversally(universalParentNode, node, fieldName, null);
    }

    private void generateNodeValueByType(ArrayNode parentNode, JsonNode node) {
        if (node.isArray() || node.isObject()) {
            throw new IllegalArgumentException();
        }
        Integer nodeIdxInArray = findFieldInArray(parentNode, node);
        ExclusiveJsonNode universalParentNode = ExclusiveJsonNode.createAsArray(parentNode);
        generateValuesUniversally(universalParentNode, node, null, nodeIdxInArray);
    }

    private void generateValuesUniversally(ExclusiveJsonNode parentNode, JsonNode node,
                                           String fieldName, Integer valueIdx) {
        if (node.isBoolean()) {
            parentNode.updateNodeData(fieldName, valueIdx, bools().get());
        } else if (node.isDouble()) {
            parentNode.updateNodeData(fieldName, valueIdx, doubles().get());
        } else if (node.isInt()) {
            parentNode.updateNodeData(fieldName, valueIdx, ints().get());
        } else if (node.isTextual()) {
            parentNode.updateNodeData(fieldName, valueIdx, strings().get());
        } else {
            log.warn("Unknown node type: " + node.getNodeType());
        }
    }

    private String findFieldNameByValue(JsonNode parentNode, JsonNode node) {
        if (parentNode == null) return null;
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

    private int findFieldInArray(JsonNode parentNode, JsonNode node) {
        int idx = -1;
        for (JsonNode subNode : parentNode) {
            idx += 1;
            if (subNode.equals(node)) {
                break;
            }
        }
        if (idx == parentNode.size()) {
            throw new IllegalArgumentException("Parent object node: " + parentNode +
                    " does not contains node: " + node);
        }
        return idx;
    }

    @Getter
    private static class ExclusiveJsonNode {
        private ObjectNode objectNode = null;

        private ArrayNode arrayNode = null;

        public static ExclusiveJsonNode createAsObject(JsonNode node) {
            ExclusiveJsonNode obj = new ExclusiveJsonNode();
            obj.objectNode = (ObjectNode) node;
            return obj;
        }

        public static ExclusiveJsonNode createAsArray(JsonNode node) {
            ExclusiveJsonNode array = new ExclusiveJsonNode();
            array.arrayNode = (ArrayNode) node;
            return array;
        }

        public <T> void updateNodeData(String nodeFieldName, Integer valueIdx, T generated) {
            if (objectNode != null) {
                objectNode.put(nodeFieldName, generated.toString());
            } else {
                arrayNode.set(valueIdx, generated.toString());
            }
        }

        private ExclusiveJsonNode() {
        }
    }
}
