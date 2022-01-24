package inno.kanban.KanbanSimulator.repository;

import inno.kanban.KanbanSimulator.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Board, Long> {

}
