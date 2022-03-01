package inno.kanban.KanbanSimulator.controller;

import inno.kanban.KanbanSimulator.exception.KanbanException;
import inno.kanban.KanbanSimulator.utils.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdviceConfig {

    @ExceptionHandler
    public ResponseEntity<ResponseWrapper<Object>> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseWrapper.error(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ResponseWrapper<Object>> handleException(KanbanException e) {
        return ResponseEntity.status(e.getStatus())
                .body(ResponseWrapper.error(e.getMessage()));
    }
}
