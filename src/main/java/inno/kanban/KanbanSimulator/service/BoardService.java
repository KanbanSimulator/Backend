package inno.kanban.KanbanSimulator.service;

import inno.kanban.KanbanSimulator.dto.BoardDto;
import inno.kanban.KanbanSimulator.dto.MoveCardDto;
import inno.kanban.KanbanSimulator.dto.MovePersonDto;
import inno.kanban.KanbanSimulator.dto.PopulateBacklogDto;

public interface BoardService {

    BoardDto moveCard(MoveCardDto moveCardDto);

    BoardDto movePerson(Long teamId,
                        MovePersonDto movePersonDto);

    BoardDto getBoard(Long teamId);

    BoardDto getOrCreateBoard(PopulateBacklogDto populateBacklogDto);

    BoardDto startDay(Long teamId);
}
