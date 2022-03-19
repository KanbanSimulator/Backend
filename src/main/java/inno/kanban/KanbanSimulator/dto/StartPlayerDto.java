package inno.kanban.KanbanSimulator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StartPlayerDto {

    private Long id;

    private Boolean spectator;

    private Long teamNumber;
}
