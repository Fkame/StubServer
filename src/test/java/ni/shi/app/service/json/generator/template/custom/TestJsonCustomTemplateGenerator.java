package ni.shi.app.service.json.generator.template.custom;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ni.shi.app.service.JacksonTest;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
public class TestJsonCustomTemplateGenerator {

    @Test
    @SneakyThrows
    void testReturnGeneratedData() {
        JsonCustomTemplateGenerator generator = new JsonCustomTemplateGenerator();
        URL pathToJson =
                JacksonTest.class.getResource("/jackson-test/real-deal-test-json.json");
        String json = Files.readString(Paths.get(pathToJson.toURI()));

        log.info("InputData: " + json);
        String output = generator.generateByCustomTemplate(json);
        log.info("OutputData: " + output);

        assertThat(output).isNotNull();

    }
}
