package inno.kanban.KanbanSimulator.exception;

import org.springframework.http.HttpStatus;

public class TeamNotFoundException extends KanbanException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    public TeamNotFoundException(Long teamId) {
        super(String.format("Team with id %d not found", teamId));
    }
}
