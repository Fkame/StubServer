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
public class ObjectJsonDto {

    public long uid;
    public List<String> cities;
    public String url;
    public SimpleJsonDto personParams;
}
