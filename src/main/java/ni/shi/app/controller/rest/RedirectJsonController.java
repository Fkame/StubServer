package ni.shi.app.controller.rest;

import ni.shi.app.AOP.logging.ToLog;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "v1/rest/json", produces = MediaType.APPLICATION_JSON_VALUE)
public class RedirectJsonController {

    @PostMapping(value = "/get-back")
    @ToLog
    public ResponseEntity<String> returnJsonBack(@RequestBody String json,
                                                 @RequestParam(value = "status", defaultValue = "200", required = false)
                                                 int status) {
        return ResponseEntity.status(status).body(json);
    }
}
