package inno.kanban.KanbanSimulator.dao;

import inno.kanban.KanbanSimulator.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
}
