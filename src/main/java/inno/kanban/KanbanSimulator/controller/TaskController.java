package inno.kanban.KanbanSimulator.controller;

import inno.kanban.KanbanSimulator.repository.TaskRepository;
import inno.kanban.KanbanSimulator.repository.TeamRepository;
import inno.kanban.KanbanSimulator.repository.UserRepository;
import inno.kanban.KanbanSimulator.service.TaskService;
import inno.kanban.KanbanSimulator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/task")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {
        RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;
    private final TaskRepository taskRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskController(TaskService taskService, UserService userService, TaskRepository taskRepository, TeamRepository teamRepository, UserRepository userRepository) {
        this.taskService = taskService;
        this.userService = userService;
        this.taskRepository = taskRepository;
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

//    @GetMapping(value = "/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<TaskDTO> getTaskByTaskId(@PathVariable final Long taskId) {
//        if (taskId == null) {
//            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        final TaskDTO task = taskService.getTaskByTaskId(taskId);
//        return task == null
//                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
//                : new ResponseEntity<>(task, HttpStatus.OK);
//    }
//
//    @GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<TaskDTO>> getTasksByUserId(@PathVariable final Long userId){
//        if (userId == null){
//            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        final List<TaskDTO> taskList = taskService.getAllTasksByUserId(userId);
//        return taskList == null
//                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
//                : new ResponseEntity<>(taskList,HttpStatus.OK);
//    }
//    @PostMapping(value = "/createTask")
//    public ResponseEntity<Task> createNewTask(@RequestBody final Task task){
//        ResponseEntity<Task> response;
//        if (task == null) {
//            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        } else {
//
//            Team team = new Team();
//            team.setTeamName("Team 1");
//            team.setTaskList(null);
//            team.setUserList(null);
//            teamRepository.save(team);
//            User user = new User();
//            user.setTeam(team);
//            user.setSurname("Livitin");
//            user.setName("Daniil");
//            user.setEmail("Danya_livitin@mail.ru");
//            userRepository.save(user);
//            task.setUser(user);
//            task.setTeam(user.getTeam());
//
//
//            final Task result = taskRepository.save(task);
//            response = new ResponseEntity<>(result, HttpStatus.CREATED);
//        }
//        return response;
//    }


}
