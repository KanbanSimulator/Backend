package inno.kanban.KanbanSimulator.controller;

import inno.kanban.KanbanSimulator.dto.BoardDto;
import inno.kanban.KanbanSimulator.dto.MoveCardDto;
import inno.kanban.KanbanSimulator.dto.PopulateBacklogDto;
import inno.kanban.KanbanSimulator.dto.WipLimitDto;
import inno.kanban.KanbanSimulator.exception.PlayerNotFoundException;
import inno.kanban.KanbanSimulator.service.BoardService;
import inno.kanban.KanbanSimulator.utils.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/move-card")
    public ResponseWrapper<BoardDto> moveCard(@RequestBody MoveCardDto moveCardDto){
        return ResponseWrapper.from(boardService.moveCard(moveCardDto));
    }

    @PostMapping("/{teamId}")
    public ResponseWrapper<BoardDto> getBoard(@PathVariable("teamId") Long teamId){
        return ResponseWrapper.from(boardService.getBoard(teamId));
    }

    @PostMapping("/populate-backlog")
    public ResponseWrapper<BoardDto> populateBacklog(PopulateBacklogDto populateBacklogDto) {
        return ResponseWrapper.from(boardService.populateBacklog(populateBacklogDto));
    }

    @PostMapping("/start-day")
    public ResponseWrapper<BoardDto> startDay() {
        return null;
    }

    @PostMapping("/move-player")
    public ResponseWrapper<BoardDto> movePlayer() {
        return null;
    }
//
//    @PostMapping("/version-check")
//    public ResponseWrapper<Object> versionCheck() {
//        return null;
//    }
}
