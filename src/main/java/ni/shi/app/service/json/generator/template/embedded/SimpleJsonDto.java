package ni.shi.app.service.json.generator.template.embedded;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimpleJsonDto {
    private String name;
    private String surname;
    private Integer age;
    private BigDecimal cardBalance;
    private Boolean isOnline;
}
