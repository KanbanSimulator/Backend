package inno.kanban.KanbanSimulator.repository;

import inno.kanban.KanbanSimulator.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {

}
