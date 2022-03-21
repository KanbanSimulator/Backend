package inno.kanban.KanbanSimulator.controller;

import inno.kanban.KanbanSimulator.dto.WipLimitDto;
import inno.kanban.KanbanSimulator.exception.PlayerNotFoundException;
import inno.kanban.KanbanSimulator.service.BoardService;
import inno.kanban.KanbanSimulator.utils.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/{playerId}")
    public ResponseWrapper<Object> board(@PathVariable("playerId") Long playerId,
                                         @RequestBody WipLimitDto wipLimitDto) throws PlayerNotFoundException {
        return ResponseWrapper.from(boardService.editBoard(playerId, wipLimitDto));
    }

    @GetMapping("/{playerId}")
    public ResponseWrapper<Object> getBoard(@PathVariable("playerId") Long playerId) {
        return null;
    }

    @PostMapping("/populate-backlog")
    public ResponseWrapper<Object> populateBacklog() {
        return null;
    }

    @PostMapping("/start-day")
    public ResponseWrapper<Object> startDay() {
        return null;
    }

    @PostMapping("/move-card")
    public ResponseWrapper<Object> moveCard() {
        return null;
    }

    @PostMapping("/move-player")
    public ResponseWrapper<Object> movePlayer() {
        return null;
    }

    @PostMapping("/version-check")
    public ResponseWrapper<Object> versionCheck() {
        return null;
    }
}
