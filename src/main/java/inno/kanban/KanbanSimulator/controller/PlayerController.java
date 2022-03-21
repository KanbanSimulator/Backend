package inno.kanban.KanbanSimulator.controller;

import inno.kanban.KanbanSimulator.service.PlayerService;
import inno.kanban.KanbanSimulator.utils.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/{playerId}")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @PostMapping("/waiting-room")
    public ResponseWrapper<Object> waitingRoom() {
        return null;
    }

    @PostMapping("/waiting-room/players-check")
    public ResponseWrapper<Object> checkPlayers(@RequestParam("roomId") Long roomId,
                                                @RequestParam("varsion") Long version) {
        return null;
    }
}
