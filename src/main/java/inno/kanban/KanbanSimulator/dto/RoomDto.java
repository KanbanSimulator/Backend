package inno.kanban.KanbanSimulator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomDto {

    private Long id;

    private Boolean started;

    private Set<PlayerDto> players;

    private Set<TeamDto> teams;

    private PlayerDto player;
}
