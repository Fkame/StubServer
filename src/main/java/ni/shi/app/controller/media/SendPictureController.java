package ni.shi.app.controller.media;

import lombok.RequiredArgsConstructor;
import ni.shi.app.AOP.logging.ToLog;
import ni.shi.app.service.media.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
@RequestMapping(value = "v1/img", produces = MediaType.IMAGE_PNG_VALUE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SendPictureController {

    private final ImgService imgService;

    @ToLog
    @GetMapping("/html")
    public ResponseEntity<byte[]> getPictureInHtml() {
        Optional<byte[]> imgBytes = imgService.getPngAsBytes();
        return imgBytes.isPresent()
                ? ResponseEntity.ok(imgBytes.get())
                : ResponseEntity.internalServerError().build();
    }

    @ToLog
    @GetMapping(value = "/json")
    public @ResponseBody ResponseEntity<byte[]> getPictureInJson() {
        Optional<byte[]> imgBytes = imgService.getPngAsBytes();
        return imgBytes.isPresent()
                ? ResponseEntity.ok(imgBytes.get())
                : ResponseEntity.internalServerError().build();
    }

}
