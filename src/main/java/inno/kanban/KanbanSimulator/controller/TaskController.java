package inno.kanban.KanbanSimulator.controller;

import inno.kanban.KanbanSimulator.dto.TaskDTO;
import inno.kanban.KanbanSimulator.model.Task;
import inno.kanban.KanbanSimulator.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/task")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {
        RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping(value = "/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskDTO> getTaskByTaskId(@PathVariable final Long taskId) {
        if (taskId == null) {
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        final TaskDTO task = taskService.getTaskByTaskId(taskId);
        return task == null
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TaskDTO>> getTasksByUserId(@PathVariable final Long userId){
        if (userId == null){
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        final List<TaskDTO> taskList = taskService.getAllTasksByUserId(userId);
        return taskList == null
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(taskList,HttpStatus.OK);
    }

}
