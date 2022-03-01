package inno.kanban.KanbanSimulator.exception;

import org.springframework.http.HttpStatus;

public class PlayerNotFoundException extends KanbanException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    public PlayerNotFoundException(Long playerId) {
        super(String.format("Player with id %d not found", playerId));
    }
}
