package inno.kanban.KanbanSimulator.controller;

import inno.kanban.KanbanSimulator.dto.*;
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
    public ResponseWrapper<BoardDto> getOrCreateBoard(@PathVariable("teamId") Long teamId){
        return ResponseWrapper.from(boardService.getOrCreateBoard(new PopulateBacklogDto(teamId)));
    }

    @PostMapping("/{teamId}/start-day")
    public ResponseWrapper<BoardDto> startDay(@PathVariable("teamId") Long teamId) {
        return ResponseWrapper.from(boardService.startDay(teamId));
    }

    @PostMapping("/{teamId}/move-person")
    public ResponseWrapper<BoardDto> movePerson(@PathVariable("teamId") Long teamId,
                                                @RequestBody MovePersonDto movePersonDto) {
        return ResponseWrapper.from(boardService.movePerson(teamId, movePersonDto));
    }
//
//    @PostMapping("/version-check")
//    public ResponseWrapper<Object> versionCheck() {
//        return null;
//    }
}
