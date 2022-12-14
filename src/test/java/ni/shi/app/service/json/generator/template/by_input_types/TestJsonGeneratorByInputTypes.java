package ni.shi.app.service.json.generator.template.by_input_types;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ni.shi.app.service.JacksonTest;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
public class TestJsonGeneratorByInputTypes {

    @Test
    @SneakyThrows
    void testGenerateByInputTemplate() {
        JsonGeneratorByInputTypes generator = new JsonGeneratorByInputTypes();
        URL pathToJson =
                JacksonTest.class.getResource("/jackson-test/test-json-template-fields.json");
        String json = Files.readString(Paths.get(pathToJson.toURI()));

        log.info("InputData: " + json);
        String output = generator.generateByInputTemplate(json);
        log.info("OutputData: " + output);

        assertThat(output).isNotNull();

    }
}
