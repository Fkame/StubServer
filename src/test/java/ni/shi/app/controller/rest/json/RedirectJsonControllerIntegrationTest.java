package ni.shi.app.controller.rest.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RedirectJsonControllerIntegrationTest {

    private final Random rand = new Random();

    String[] testJsons = new String[]{
            "\"random\": \"1\"",

            "\"firstname\": \"Shel\",\n" +
                    "\"lastname\": \"Mott\"",

            "\"array\": [\n" +
                    "\"Sara-Ann\",\n" +
                    "\t\t\"Clo\",\n" +
                    "\t\t\"Sam\",\n" +
                    "\t\t\"Ellette\",\n" +
                    "\t\t\"Jean\"\n" +
                    "\t]",

            "\"array of objects\": [\n" +
                    "\t\t{\n" +
                    "\t\t\t\"index\": \"0\",\n" +
                    "\t\t\t\"index start at 5\": \"5\"\n" +
                    "\t\t},\n" +
                    "\t\t{\n" +
                    "\t\t\t\"index\": \"1\",\n" +
                    "\t\t\t\"index start at 5\": \"6\"\n" +
                    "\t\t},\n" +
                    "\t\t{\n" +
                    "\t\t\t\"index\": \"2\",\n" +
                    "\t\t\t\"index start at 5\": \"7\"\n" +
                    "\t\t}\n" +
                    "\t]"
    };

    int[] statuses = new int[]{200, 404, 502, 202};

    @Test
    public void restReturnJsonBack() {
        List<ReqPair> testData = Arrays.stream(testJsons).map(json -> ReqPair.builder()
                        .reqJson(json)
                        .reqStatus(statuses[rand.nextInt(statuses.length)])
                        .build())
                .toList();

        WebTestClient webTestClient = WebTestClient
                .bindToController(RedirectJsonController.class)
                .build();

        for (int i = 0; i < testJsons.length; i++) {
            int status = i;
            webTestClient.post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/v1/rest/json/get-back")
                            .queryParam("status", testData.get(status).getReqStatus())
                            .build()
                    )
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(testData.get(i).getReqJson())
                    .exchange()
                    .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                    .expectStatus().isEqualTo(testData.get(i).getReqStatus())
                    .expectBody().json(testData.get(i).getReqJson())
                    .returnResult();
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class ReqPair {
        private int reqStatus;
        private String reqJson;
    }
}
