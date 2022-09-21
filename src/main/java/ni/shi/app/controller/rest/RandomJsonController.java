package ni.shi.app.controller.rest;

import ni.shi.app.controller.dto.FullyRandomJsonParamsDto;
import ni.shi.app.service.json.generator.completely.CompletelyRandomJsonGenerator;
import ni.shi.app.service.json.generator.template.custom.JsonCustomTemplateGenerator;
import ni.shi.app.service.json.generator.template.embedded.JsonByTemplateGenerator;
import ni.shi.app.utils.StructureComplexity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "v1/rest/json", produces = MediaType.APPLICATION_JSON_VALUE)

public class RandomJsonController {

    @GetMapping(value = "/random/embedded-template")
    public ResponseEntity<String> getRandomJsonByEmbeddedTemplate(@RequestParam(name = "complexity", required = true)
                                                                  StructureComplexity structureComplexity) {
        JsonByTemplateGenerator jsonGenerator = new JsonByTemplateGenerator();
        String json = jsonGenerator.generateByEmbeddedTemplate(structureComplexity);
        if (json == null) {
            return ResponseEntity.internalServerError().body("Cannot create json, sorry(");
        }
        return ResponseEntity.ok(json);
    }

    @GetMapping(value = "/random/completely")
    public ResponseEntity<String> getRandomJson(@RequestBody FullyRandomJsonParamsDto generationParams) {
        CompletelyRandomJsonGenerator jsonGenerator = new CompletelyRandomJsonGenerator();
        String json = jsonGenerator.generateJson(generationParams);
        if (json == null) {
            return ResponseEntity.internalServerError().body("Cannot create json, sorry(");
        }
        return ResponseEntity.ok(json);
    }

    @PostMapping(value = "/random/fill-only")
    public ResponseEntity<String> getRandomJsonFromUserTemplate(@RequestBody String jsonTemplate) {
        JsonCustomTemplateGenerator jsonGenerator = new JsonCustomTemplateGenerator();
        String json = jsonGenerator.generateByCustomTemplate(jsonTemplate);
        if (json == null) {
            return ResponseEntity.internalServerError().body("Cannot create json, sorry(");
        }
        return ResponseEntity.ok(json);
    }
}
