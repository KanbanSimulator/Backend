package inno.kanban.KanbanSimulator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {

    private Integer analyticsFreePersons;

    private Integer developmentFreePersons;

    private Integer testFreePersons;

    private Long teamId;

    private Integer day;

    private List<CardDto> cards;
}
