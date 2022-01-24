package inno.kanban.KanbanSimulator.dto;

import inno.kanban.KanbanSimulator.model.Task;
import inno.kanban.KanbanSimulator.model.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long userId;
    private String name;
    private String surname;
    private String email;
    private Set<Task> tasks;
    private Board board;
}