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

    private Long teamId;

    private List<CardDto> queue;

    private List<CardDto> analyticsCards;

    private List<CardDto> developerCards;

    private List<CardDto> testingCards;

    private List<CardDto> finishedCards;
}
