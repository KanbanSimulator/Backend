package inno.kanban.KanbanSimulator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StartRoomDto {

    List<StartPlayerDto> players;
}
