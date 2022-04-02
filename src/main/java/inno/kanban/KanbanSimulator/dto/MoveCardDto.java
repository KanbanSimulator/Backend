package inno.kanban.KanbanSimulator.dto;

import inno.kanban.KanbanSimulator.model.Card;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoveCardDto {

    private Long cardId;

    private Card.ColumnType columnType;

    private Integer priority;
}
