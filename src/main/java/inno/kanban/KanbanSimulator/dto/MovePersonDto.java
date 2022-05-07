package inno.kanban.KanbanSimulator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovePersonDto {

    private Long prevCard;

    private Long currentCard;
}
