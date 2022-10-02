package ni.shi.app.service.media;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SpringBootTest
public class ImgServiceTest {

    private final ImgService imgService;

    @Test
    public void getPngAsBytes() {
        assertThat(imgService.getPngAsBytes().isPresent()).isTrue();
    }
}
