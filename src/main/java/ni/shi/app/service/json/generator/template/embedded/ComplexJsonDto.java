package ni.shi.app.service.json.generator.template.embedded;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplexJsonDto {
    List<ObjectJsonDto> object;
    List<String> ipv4s;
    SimpleJsonDto personObj;
    List<List<String>> macs;
    String loremIpsum;
}
