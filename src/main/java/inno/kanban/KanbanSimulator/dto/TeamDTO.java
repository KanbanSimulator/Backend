package inno.kanban.KanbanSimulator.dto;

import inno.kanban.KanbanSimulator.model.Task;
import inno.kanban.KanbanSimulator.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO {
    private Long teamId;
    private String teamName;
    private List<User> userList;
    private List<Task> taskList;
}
