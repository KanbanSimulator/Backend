package inno.kanban.KanbanSimulator.controller;

import inno.kanban.KanbanSimulator.dto.PlayerJoinDto;
import inno.kanban.KanbanSimulator.dto.RoomCreateDto;
import inno.kanban.KanbanSimulator.dto.RoomDto;
import inno.kanban.KanbanSimulator.exception.RoomNotFoundException;
import inno.kanban.KanbanSimulator.service.RoomService;
import inno.kanban.KanbanSimulator.utils.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
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
}
