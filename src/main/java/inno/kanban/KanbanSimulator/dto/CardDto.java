package inno.kanban.KanbanSimulator.dto;

import inno.kanban.KanbanSimulator.model.Card;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {

    private Long id;

    private String title;

    private String team;

    private Boolean isExpedite;

    private Integer readyDay;

    private Integer analyticRemaining;

    private Integer analyticCompleted;

    private Integer developRemaining;

    private Integer developCompleted;

    private Integer testingRemaining;

    private Integer testingCompleted;

    private Card.ColumnType columnType;

    private Integer businessValue;
}
