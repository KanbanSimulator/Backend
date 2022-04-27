package inno.kanban.KanbanSimulator.controller;

import inno.kanban.KanbanSimulator.dto.PlayerJoinDto;
import inno.kanban.KanbanSimulator.dto.RoomCreateDto;
import inno.kanban.KanbanSimulator.dto.RoomDto;
import inno.kanban.KanbanSimulator.dto.StartRoomDto;
import inno.kanban.KanbanSimulator.exception.PlayerNotFoundException;
import inno.kanban.KanbanSimulator.exception.RoomNotFoundException;
import inno.kanban.KanbanSimulator.service.RoomService;
import inno.kanban.KanbanSimulator.utils.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/create")
    ResponseWrapper<RoomDto> createRoom(@RequestBody RoomCreateDto roomCreateDto) {
        return ResponseWrapper.from(roomService.createRoom(roomCreateDto));
    }

    @PostMapping("/{roomId}/join")
    public ResponseWrapper<RoomDto> joinRoom(@PathVariable("roomId") Long roomId,
                                             @RequestBody PlayerJoinDto playerJoinDto) throws RoomNotFoundException {
        return ResponseWrapper.from(roomService.joinRoom(roomId, playerJoinDto));
    }

    @PostMapping("/{roomId}/start")
    public ResponseWrapper<RoomDto> startTheGame(@PathVariable("roomId") Long roomId,
                                                 @RequestBody StartRoomDto startRoomDto) {
        return ResponseWrapper.from(roomService.startRoom(roomId, startRoomDto));
    }

    @GetMapping("/{roomId}")
    public ResponseWrapper<RoomDto> checkRoomState(@PathVariable("roomId") Long roomId,
                                                   @RequestParam("playerId") Long playerId) throws RoomNotFoundException, PlayerNotFoundException {
        return ResponseWrapper.from(roomService.checkRoomState(roomId, playerId));
    }
}
