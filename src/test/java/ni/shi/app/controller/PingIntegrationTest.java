package ni.shi.app.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PingIntegrationTest {


    @Test
    public void testPingCall() {
        WebTestClient webTestClient = WebTestClient.bindToController(PingController.class).build();
        webTestClient.get().uri("/ping")
                .header(APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).value(body ->
                    assertThat(body.contains("Ok")).isTrue()
                )
                .returnResult();
    }
}
