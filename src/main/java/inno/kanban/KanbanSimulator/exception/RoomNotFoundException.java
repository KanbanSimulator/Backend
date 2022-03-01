package inno.kanban.KanbanSimulator.exception;

import org.springframework.http.HttpStatus;

public class RoomNotFoundException extends KanbanException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    public RoomNotFoundException(Long roomId) {
        super(String.format("Room with id %d not found", roomId));
    }
}
