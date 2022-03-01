package inno.kanban.KanbanSimulator.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
public class KanbanException extends Exception {

    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public KanbanException(String message) {
        super(message);
    }
}
