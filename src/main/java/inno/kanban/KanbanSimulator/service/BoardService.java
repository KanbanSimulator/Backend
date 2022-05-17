package inno.kanban.KanbanSimulator.service;

import inno.kanban.KanbanSimulator.dto.*;

public interface BoardService {

    BoardDto moveCard(MoveCardDto moveCardDto);

    BoardDto movePerson(Long teamId,
                        MovePersonDto movePersonDto);

    BoardDto getBoard(Long teamId);

    WipLimitDto setWipLimit(Long teamId, WipLimitDto wipLimitDto);

    WipLimitDto getWipLimit(Long teamId);

    BoardDto getOrCreateBoard(PopulateBacklogDto populateBacklogDto);

    BoardDto startDay(Long teamId);

    StatisticsDto getStatistics(Long roomId);
}
