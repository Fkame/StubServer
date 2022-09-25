package ni.shi.app.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Deprecated
public class FullyRandomJsonParamsDto {

    private int elementsAmount;
    private int maxDepth;
    private int maxElementsInArray;
}
