package inno.kanban.KanbanSimulator.repository;

import inno.kanban.KanbanSimulator.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
