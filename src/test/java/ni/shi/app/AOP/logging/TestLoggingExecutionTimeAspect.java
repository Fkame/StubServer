package ni.shi.app.AOP.logging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SpringBootTest
public class TestLoggingExecutionTimeAspect {

    private final TestClassForAspect testClassForAspect;

    @Test
    void testOutput() {
        assertThat(testClassForAspect.testMethodToLog()).isNotNull();
    }
}

@Component
@Slf4j
class TestClassForAspect {

    @ToLog
    public List<Integer> testMethodToLog() {
        log.info("Test method has been called!");
        List<Integer> testContainer = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            testContainer.add(i);
        }
        log.info("End of test method!");

        return testContainer;
    }

}
