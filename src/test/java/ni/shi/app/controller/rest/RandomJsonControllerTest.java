package ni.shi.app.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RandomJsonControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new RandomJsonController()).build();
    }

    @Test
    @SneakyThrows
    void testGetRandomJsonByEmbeddedTemplate() {
        mockMvc.perform(get("/v1/rest/json/random/embedded-template")
                        .param("complexity", "SIMPLE"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/v1/rest/json/random/embedded-template")
                        .param("complexity", "S"))
                .andExpect(status().is4xxClientError());

        mockMvc.perform(get("/v1/rest/json/random/embedded-template"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @SneakyThrows
    void testGenerateByCustomTemplate() {
        ObjectMapper jsonReader = new ObjectMapper();
        Path pathToJson = Path.of(this.getClass()
                .getResource("/jackson-test/real-deal-test-json.json")
                .toURI());
        String json = Files.readString(pathToJson);

        mockMvc.perform(post("/v1/rest/json/random/by-field-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(post("/v1/rest/json/random/by-field-types"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @SneakyThrows
    void testGenerateJsonByInputTypes() {
        ObjectMapper jsonReader = new ObjectMapper();
        Path pathToJson = Path.of(this.getClass()
                .getResource("/jackson-test/test-json-template-fields.json")
                .toURI());
        String json = Files.readString(pathToJson);

        mockMvc.perform(post("/v1/rest/json/random/by-input-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(post("/v1/rest/json/random/by-input-types"))
                .andExpect(status().is4xxClientError());

        pathToJson = Path.of(this.getClass()
                .getResource("/jackson-test/real-deal-test-json.json")
                .toURI());
        json = Files.readString(pathToJson);

        mockMvc.perform(post("/v1/rest/json/random/by-input-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

    }
}
