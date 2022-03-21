package inno.kanban.KanbanSimulator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomCreateDto {

    private Integer teamsAmount;

    private PlayerJoinDto player;
}
