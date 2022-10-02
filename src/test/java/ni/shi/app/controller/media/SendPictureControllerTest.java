package ni.shi.app.controller.media;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@AutoConfigureMockMvc
public class SendPictureControllerTest {

    private final MockMvc mockMvc;

    @Test
    @SneakyThrows
    public void testGetPictureInHtml() {
        MvcResult result = mockMvc.perform(get("/v1/img/html"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.IMAGE_PNG_VALUE))
                .andReturn();
        assertThat(result.getResponse().getContentAsString().equals("")).isFalse();
    }

    @Test
    @SneakyThrows
    public void testGetPictureInJson() {
        MvcResult result = mockMvc.perform(get("/v1/img/json"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.IMAGE_PNG_VALUE))
                .andReturn();
        assertThat(result.getResponse().getContentAsString().equals("")).isFalse();
    }
}
