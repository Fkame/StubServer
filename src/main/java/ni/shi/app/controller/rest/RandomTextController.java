package ni.shi.app.controller.rest;

import com.thedeanda.lorem.LoremIpsum;
import ni.shi.app.AOP.logging.ToLog;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Random;

@RequestMapping(value = "v1/rest/text", produces = MediaType.TEXT_PLAIN_VALUE)
public class RandomTextController {

    private static final Random rand = new Random();

    @GetMapping("/loreum")
    @ToLog
    public String getTextLoreum(@RequestParam(name = "min-paragraphs", defaultValue = "2", required = false)
                                Integer minParagraphs,
                                @RequestParam(name = "max-paragraphs", defaultValue = "4", required = false)
                                Integer maxParagraphs) {
        return LoremIpsum.getInstance().getParagraphs(minParagraphs, maxParagraphs);
    }

    @GetMapping("/random")
    @ToLog
    public String getRandomText(@RequestParam(name = "min-words", defaultValue = "10", required = false)
                                Integer minWords,
                                @RequestParam(name = "max-words", defaultValue = "50", required = false)
                                Integer maxWords) {
        int length = minWords + rand.nextInt(maxWords + 1);
        boolean useLetters = true;
        boolean useNumbers = true;
        return RandomStringUtils.random(length, useLetters, useNumbers);
    }
}
