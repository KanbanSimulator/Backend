package inno.kanban.KanbanSimulator.service;

import inno.kanban.KanbanSimulator.dto.BoardDto;
import inno.kanban.KanbanSimulator.dto.MoveCardDto;
import inno.kanban.KanbanSimulator.dto.PopulateBacklogDto;

public interface BoardService {

    BoardDto moveCard(MoveCardDto moveCardDto);

    BoardDto getBoard(Long teamId);

    BoardDto getOrCreateBoard(PopulateBacklogDto populateBacklogDto);
}
