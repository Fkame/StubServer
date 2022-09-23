package ni.shi.app.service.json.generator.template.embedded;

import lombok.extern.slf4j.Slf4j;
import ni.shi.app.utils.StructureComplexity;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
public class TestJsonByTemplateGenerator {

    @Test
    public void generateByEmbeddedSimpleTemplate() {
        JsonByTemplateGenerator jsonGenerator = new JsonByTemplateGenerator();
        String json = jsonGenerator.generateByEmbeddedTemplate(StructureComplexity.SIMPLE);
        assertThat(json).isNotNull();
        for (Field field: SimpleJsonDto.class.getDeclaredFields()) {
            assertThat(json.contains(field.getName())).isTrue();
        }
        log.info(json);
    }

    @Test
    public void generateByEmbeddedSimpleArrayTemplate() {
        JsonByTemplateGenerator jsonGenerator = new JsonByTemplateGenerator();
        String json = jsonGenerator.generateByEmbeddedTemplate(StructureComplexity.SIMPLE_ARRAY);
        String[] values = json.split(",");
        assertThat(json).isNotNull();
        assertThat(values.length > 1).isTrue();
        log.info(json);
    }

    @Test
    public void generateByEmbeddedObjectTemplate() {
        JsonByTemplateGenerator jsonGenerator = new JsonByTemplateGenerator();
        String json = jsonGenerator.generateByEmbeddedTemplate(StructureComplexity.SIMPLE_OBJECT);
        assertThat(json).isNotNull();
        for (Field field: ObjectJsonDto.class.getDeclaredFields()) {
            assertThat(json.contains(field.getName())).isTrue();
        }
        log.info(json);
    }

    @Test
    public void generateByEmbeddedObjectArrayTemplate() {
        JsonByTemplateGenerator jsonGenerator = new JsonByTemplateGenerator();
        String json = jsonGenerator.generateByEmbeddedTemplate(StructureComplexity.OBJECTS_ARRAY);
        assertThat(json).isNotNull();
        for (Field field: ObjectJsonDto.class.getDeclaredFields()) {
            assertThat(json.contains(field.getName())).isTrue();
        }
        log.info(json);
    }

    @Test
    public void generateByEmbeddedComplexTemplate() {
        JsonByTemplateGenerator jsonGenerator = new JsonByTemplateGenerator();
        String json = jsonGenerator.generateByEmbeddedTemplate(StructureComplexity.COMPLEX);
        assertThat(json).isNotNull();
        for (Field field: ComplexJsonDto.class.getDeclaredFields()) {
            assertThat(json.contains(field.getName())).isTrue();
        }
        log.info(json);
    }
}
