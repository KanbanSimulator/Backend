package inno.kanban.KanbanSimulator.dto;

import inno.kanban.KanbanSimulator.model.Team;
import inno.kanban.KanbanSimulator.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private long taskId;
    private String name;
    private String description;
    private int storyPoints;
    private String status;
    private User userId;
}
