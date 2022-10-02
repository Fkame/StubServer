package ni.shi.app.service.media;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

@Service
@Slf4j
public class ImgService {

    public static final String TEST_PNG_PATH = "/media/testPng.png";

    public Optional<byte[]> getPngAsBytes() {
        return getMediaAsBytes(TEST_PNG_PATH);
    }

    private Optional<byte[]> getMediaAsBytes(String path) {
        try {
            URL pathToPng = ImgService.class.getResource(path);
            return Optional.of(Files.readAllBytes(Path.of(pathToPng.toURI())));
        } catch (Exception ex) {
            log.warn("Error while reading media/testPng.png");
            log.error(ex.getMessage() + "\n" + Arrays.toString(ex.getStackTrace()));
            return Optional.empty();
        }
    }
}
