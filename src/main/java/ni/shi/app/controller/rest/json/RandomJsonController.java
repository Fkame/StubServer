package ni.shi.app.controller.rest.json;

import ni.shi.app.AOP.logging.ToLog;
import ni.shi.app.controller.dto.FullyRandomJsonParamsDto;
import ni.shi.app.service.json.generator.completely.CompletelyRandomJsonGenerator;
import ni.shi.app.service.json.generator.template.by_fields_types.JsonGeneratorByFieldType;
import ni.shi.app.service.json.generator.template.by_input_types.JsonGeneratorByInputTypes;
import ni.shi.app.service.json.generator.template.embedded.JsonByTemplateGenerator;
import ni.shi.app.utils.StructureComplexity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "v1/rest/json", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class RandomJsonController {

    @GetMapping(value = "/random/embedded-template")
    @ToLog
    public ResponseEntity<String> getRandomJsonByEmbeddedTemplate(@RequestParam(name = "complexity", required = true)
                                                                  StructureComplexity structureComplexity) {
        JsonByTemplateGenerator jsonGenerator = new JsonByTemplateGenerator();
        String json = jsonGenerator.generateByEmbeddedTemplate(structureComplexity);
        if (json == null) {
            return ResponseEntity.internalServerError().body("Cannot create json, sorry(");
        }
        return ResponseEntity.ok(json);
    }

    @Deprecated
    @GetMapping(value = "/random/completely")
    public ResponseEntity<String> getRandomJson(@RequestBody FullyRandomJsonParamsDto generationParams) {
        CompletelyRandomJsonGenerator jsonGenerator = new CompletelyRandomJsonGenerator();
        String json = jsonGenerator.generateJson(generationParams);
        if (json == null) {
            return ResponseEntity.internalServerError().body("Cannot create json, sorry(");
        }
        return ResponseEntity.ok(json);
    }

    @PostMapping(value = "/random/by-field-types")
    @ToLog
    public ResponseEntity<String> generateJsonByFieldsTypes(@RequestBody String jsonTemplate) {
        JsonGeneratorByFieldType jsonGenerator = new JsonGeneratorByFieldType();
        String json = jsonGenerator.generateByCustomTemplate(jsonTemplate);
        if (json == null) {
            return ResponseEntity.internalServerError().body("Cannot create json, sorry(");
        }
        return ResponseEntity.ok(json);
    }

    @PostMapping(value = "/random/by-input-types")
    @ToLog
    public ResponseEntity<String> generateJsonByInputTypes(@RequestBody String jsonTemplate) {
        JsonGeneratorByInputTypes jsonGenerator = new JsonGeneratorByInputTypes();
        String json = jsonGenerator.generateByInputTemplate(jsonTemplate);
        if (json == null) {
            return ResponseEntity.internalServerError().body("Cannot create json, sorry(");
        }
        return ResponseEntity.ok(json);
    }
}
