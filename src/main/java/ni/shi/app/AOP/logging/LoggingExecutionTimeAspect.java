package ni.shi.app.AOP.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Aspect
@Slf4j
@Component
public class LoggingExecutionTimeAspect {

    private static final long NANO_SECONDS_IN_MILLIS = 1_000_000;
    private static final long MILLIS_IN_SECOND = 1_000;

    private static final String LOG_TEMPLATE = "Executing %s: startTime = %s millis, endTime = %s millis" +
            ", execution = %s millis / %s seconds";

    @Around(value = "@annotation(ToLog)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object returnValue = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        BigDecimal executionTimeMillis = BigDecimal.valueOf(endTime)
                .subtract(BigDecimal.valueOf(startTime))
                .setScale(3, RoundingMode.HALF_UP);
        BigDecimal executionTimeSeconds = executionTimeMillis
                .divide(BigDecimal.valueOf(MILLIS_IN_SECOND), RoundingMode.HALF_UP)
                .setScale(5, RoundingMode.HALF_UP);

        String methodName = joinPoint.getSignature().getName();
        log.info(
                String.format(LOG_TEMPLATE, methodName,
                        startTime, endTime, executionTimeMillis, executionTimeSeconds)
        );

        return returnValue;
    }

}
