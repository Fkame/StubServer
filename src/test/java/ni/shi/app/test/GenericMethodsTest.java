package ni.shi.app.test;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class GenericMethodsTest {

    @Test
    public void testGenericMethod() {
        Integer a1 = 5;
        BigDecimal a2 = BigDecimal.TEN;
        String a3 = "1234";
        double a4 = 22.22;

        genericMethod(a1);
        genericMethod(a2);
        genericMethod(a3);
        genericMethod(a4);
    }

    <T> void genericMethod(T input) {
        System.out.println(input);
    }
}
