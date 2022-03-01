package inno.kanban.KanbanSimulator.service;

import inno.kanban.KanbanSimulator.dto.BoardDto;
import inno.kanban.KanbanSimulator.dto.WipLimitDto;
import inno.kanban.KanbanSimulator.exception.PlayerNotFoundException;

public interface BoardService {

    BoardDto editBoard(Long playerId,
                       WipLimitDto wipLimitDto) throws PlayerNotFoundException;

    BoardDto getBoard(Long playerId) throws PlayerNotFoundException;
}
