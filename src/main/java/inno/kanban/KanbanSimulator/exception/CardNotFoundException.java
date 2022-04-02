package inno.kanban.KanbanSimulator.exception;

import org.springframework.http.HttpStatus;

public class CardNotFoundException extends KanbanException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    public CardNotFoundException(Long cardId) {
        super(String.format("Card with id %d not found", cardId));
    }
}
