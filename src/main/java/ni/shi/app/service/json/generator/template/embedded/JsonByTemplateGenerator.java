package ni.shi.app.service.json.generator.template.embedded;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.andreinc.mockneat.abstraction.MockUnit;
import net.andreinc.mockneat.abstraction.MockUnitString;
import ni.shi.app.utils.StructureComplexity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static net.andreinc.mockneat.unit.address.Cities.cities;
import static net.andreinc.mockneat.unit.networking.IPv4s.ipv4s;
import static net.andreinc.mockneat.unit.networking.Macs.macs;
import static net.andreinc.mockneat.unit.networking.URLs.urls;
import static net.andreinc.mockneat.unit.objects.Filler.filler;
import static net.andreinc.mockneat.unit.text.Markovs.markovs;
import static net.andreinc.mockneat.unit.types.Bools.bools;
import static net.andreinc.mockneat.unit.types.Doubles.doubles;
import static net.andreinc.mockneat.unit.types.Ints.ints;
import static net.andreinc.mockneat.unit.types.Longs.longs;
import static net.andreinc.mockneat.unit.user.Names.names;


@Slf4j
@Service
public class JsonByTemplateGenerator {

    public static int MIN_SIMPLE_ARRAY_LENGTH = 3;

    public static int MAX_SIMPLE_ARRAY_LENGTH = 10;

    public static Long MIN_UID = 100_000_000_000_000L;

    public static Long MAX_UID = 999_000_000_000_000L;


    public String generateByEmbeddedTemplate(StructureComplexity complexity) {
        String generatedJson = null;
        try {
            generatedJson = switch (complexity) {
                case SIMPLE -> createSimpleJson();
                case SIMPLE_ARRAY -> createSimpleArrayJson();
                case SIMPLE_OBJECT -> createSimpleObjectJson();
                case OBJECTS_ARRAY -> createObjectArrayJson();
                case COMPLEX -> createComplexJson();
            };
        } catch (JsonProcessingException ex) {
            System.out.println("Json parsing error!");
            log.error("Exception during json generation. Json type = [" + complexity + "]." +
                    "Exception:\n" + ex.getMessage() + "\n" + ex.getStackTrace());
            return null;
        }
        return generatedJson;
    }

    private String createSimpleJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(generateSimpleJson().get());
    }

    private MockUnit<SimpleJsonDto> generateSimpleJson() {
        return filler(SimpleJsonDto::new)
                .setter(SimpleJsonDto::setName, names().first())
                .setter(SimpleJsonDto::setSurname, names().last())
                .setter(SimpleJsonDto::setAge, ints().range(15, 60))
                .setter(SimpleJsonDto::setIsOnline, bools())
                .setter(SimpleJsonDto::setCardBalance,
                        doubles().range(0.0, 100000.0)
                                .map(balance -> BigDecimal.valueOf(balance)
                                        .setScale(2, RoundingMode.CEILING)));
    }

    private MockUnit<ObjectJsonDto> generateObjectJson() {
        int citiesAmount = 10;
        MockUnitString mockUnitString = cities().capitals();
        MockUnit<List<String>> cities = mockUnitString.list(ArrayList::new, citiesAmount);

        return filler(ObjectJsonDto::new)
                .setter(ObjectJsonDto::setUid, longs().range(MIN_UID, MAX_UID))
                .setter(ObjectJsonDto::setCities, cities)
                .setter(ObjectJsonDto::setUrl, urls())
                .setter(ObjectJsonDto::setPersonParams, generateSimpleJson());
    }

    private String createSimpleArrayJson() throws JsonProcessingException {
        int length = ints().range(MIN_SIMPLE_ARRAY_LENGTH, MAX_SIMPLE_ARRAY_LENGTH).get();

        ObjectMapper mapper = new ObjectMapper();
        List<String> array = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            String randomName = generateSimpleJson().get().getName();
            array.add(randomName);
        }
        return mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(array);
    }

    private String createSimpleObjectJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(generateObjectJson().get());
    }

    private String createObjectArrayJson() throws JsonProcessingException {
        int length = ints().range(MIN_SIMPLE_ARRAY_LENGTH, MAX_SIMPLE_ARRAY_LENGTH).get();

        ObjectMapper mapper = new ObjectMapper();
        List<ObjectJsonDto> array = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            ObjectJsonDto randomDto = generateObjectJson().get();
            array.add(randomDto);
        }
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(array);
    }

    private String createComplexJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(generateComplexJson().get());
    }

    private MockUnit<ComplexJsonDto> generateComplexJson() {
        int objectJsonArraySize = 3;
        int ipv4sAmount = 10;
        int macsOneChunkSize = 2;
        int macsChunksAmount = 5;
        int textSize = 50;
        return filler(ComplexJsonDto::new)
                .setter(ComplexJsonDto::setObject, generateObjectJson().list(objectJsonArraySize))
                .setter(ComplexJsonDto::setIpv4s, ipv4s().list(ipv4sAmount))
                .setter(ComplexJsonDto::setPersonObj, generateSimpleJson())
                .setter(ComplexJsonDto::setMacs, macs().list(macsOneChunkSize).list(macsChunksAmount))
                .setter(ComplexJsonDto::setLoremIpsum, markovs().size(textSize).loremIpsum());
    }
}