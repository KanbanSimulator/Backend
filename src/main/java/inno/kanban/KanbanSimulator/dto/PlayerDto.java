package inno.kanban.KanbanSimulator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerDto {

    private Long id;

    private String name;

    private Boolean creator;

    private Long teamNumber;

    private Long teamId;

    private Boolean spectator;
}
