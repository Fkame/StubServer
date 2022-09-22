package ni.shi.app.service.json.generator.template.by_fields_types;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ni.shi.app.service.JacksonTest;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
public class TestJsonGeneratorByFieldType {

    @Test
    @SneakyThrows
    void testReturnGeneratedData() {
        JsonGeneratorByFieldType generator = new JsonGeneratorByFieldType();
        URL pathToJson =
                JacksonTest.class.getResource("/jackson-test/real-deal-test-json.json");
        String json = Files.readString(Paths.get(pathToJson.toURI()));

        log.info("InputData: " + json);
        String output = generator.generateByCustomTemplate(json);
        log.info("OutputData: " + output);

        assertThat(output).isNotNull();

    }
}
