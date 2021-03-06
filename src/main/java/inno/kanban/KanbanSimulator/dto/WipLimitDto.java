package inno.kanban.KanbanSimulator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WipLimitDto {

    @Builder.Default
    private Integer wip1 = 5;

    @Builder.Default
    private Integer wip2 = 5;

    @Builder.Default
    private Integer wip3 = 5;
}
