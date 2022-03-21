package inno.kanban.KanbanSimulator.controller;

import inno.kanban.KanbanSimulator.exception.KanbanException;
import inno.kanban.KanbanSimulator.utils.ResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ControllerAdviceConfig {

    @ExceptionHandler
    public ResponseEntity<ResponseWrapper<Object>> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseWrapper.error(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ResponseWrapper<Object>> handleException(KanbanException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(e.getStatus())
                .body(ResponseWrapper.error(e.getMessage()));
    }
}
