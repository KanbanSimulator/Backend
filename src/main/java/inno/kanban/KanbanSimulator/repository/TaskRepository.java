package inno.kanban.KanbanSimulator.repository;

import inno.kanban.KanbanSimulator.model.Task;
import inno.kanban.KanbanSimulator.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> getAllByUser(User user);
}
