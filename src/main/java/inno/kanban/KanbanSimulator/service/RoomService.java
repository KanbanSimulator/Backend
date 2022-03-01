package inno.kanban.KanbanSimulator.service;

import inno.kanban.KanbanSimulator.dto.PlayerJoinDto;
import inno.kanban.KanbanSimulator.dto.RoomCreateDto;
import inno.kanban.KanbanSimulator.dto.RoomDto;
import inno.kanban.KanbanSimulator.exception.RoomNotFoundException;

public interface RoomService {

    RoomDto joinRoom(Long roomId,
                     PlayerJoinDto playerJoinDto) throws RoomNotFoundException;

    RoomDto createRoom(RoomCreateDto roomCreateDto);
}
