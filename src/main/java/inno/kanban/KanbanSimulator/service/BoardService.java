package inno.kanban.KanbanSimulator.service;

import inno.kanban.KanbanSimulator.dto.BoardDto;
import inno.kanban.KanbanSimulator.dto.MoveCardDto;
import inno.kanban.KanbanSimulator.dto.PopulateBacklogDto;
import inno.kanban.KanbanSimulator.dto.WipLimitDto;
import inno.kanban.KanbanSimulator.exception.PlayerNotFoundException;

public interface BoardService {

    BoardDto moveCard(MoveCardDto moveCardDto);

    BoardDto getBoard(Long teamId);

    BoardDto populateBacklog(PopulateBacklogDto populateBacklogDto);
}
