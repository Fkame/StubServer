package ni.shi.app.service.json.generator.template.custom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonCustomTemplateGenerator {

    public String generateByCustomTemplate(String customTemplate) {
        ObjectMapper jsonMapper = new ObjectMapper();
        try {
            JsonNode customJson = jsonMapper.readTree(customTemplate);
            return generateByTemplate(customJson);
        } catch (JsonProcessingException ex) {
            log.error(ex.getMessage() + "\n" + ex.getStackTrace().toString());
            return null;
        }
    }

    private String generateByTemplate(JsonNode node) throws JsonProcessingException {

        for (int idx = 0; idx < node.size(); idx++) {
            JsonNode current = node.get(idx);

        }
        return null;
    }
}
