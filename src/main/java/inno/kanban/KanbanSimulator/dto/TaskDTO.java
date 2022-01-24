package inno.kanban.KanbanSimulator.dto;

import inno.kanban.KanbanSimulator.model.Board;
import inno.kanban.KanbanSimulator.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private Long taskId;
    private String name;
    private String description;
    private int storyPoints;
    private String status;
    private User user;
    private Board team;
}
