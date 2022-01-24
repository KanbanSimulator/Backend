package inno.kanban.KanbanSimulator.service;

import inno.kanban.KanbanSimulator.dto.TaskDTO;
import inno.kanban.KanbanSimulator.model.Task;
import inno.kanban.KanbanSimulator.repository.TaskRepository;
import inno.kanban.KanbanSimulator.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;


//    public TaskDTO getTaskByTaskId(Long taskId) {
//        Task task = taskRepository.getById(taskId);
//        return new TaskDTO(task.getTaskId(), task.getName(), task.getDescription(), task.getStoryPoints(), task.getStatus(), task.getUser(), task.getTeam());
//    }

//    public List<TaskDTO> getAllTasksByUserId(Long userId) {
//        final List<TaskDTO> taskDTOList = new ArrayList<>();
//        for (Task task : taskRepository.getAllByUser(userRepository.getById(userId))) {
//            taskDTOList.add(new TaskDTO(task.getTaskId(), task.getName(), task.getDescription(), task.getStoryPoints(), task.getStatus(), task.getUser(), task.getTeam()));
//        }
//        return taskDTOList;
//    }
}
