package ni.shi.app.test;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import org.junit.jupiter.api.Test;

public class LoremTest {

    @Test
    public void generateSomeLoremTest() {
        Lorem lorem = LoremIpsum.getInstance();

       printText(lorem.getWords(5, 10));

       printText(lorem.getParagraphs(2, 4));

       printText(lorem.getHtmlParagraphs(2, 4));

       printText(lorem.getTitle(2, 4));

       printText(lorem.getPhone());
       printText(lorem.getPhone());
    }

    private void printText(String text) {
        System.out.println(text);
    }
}

