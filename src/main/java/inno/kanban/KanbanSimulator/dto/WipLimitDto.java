package inno.kanban.KanbanSimulator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WipLimitDto {

    private Integer wip1;

    private Integer wip2;

    private Integer wip3;
}
