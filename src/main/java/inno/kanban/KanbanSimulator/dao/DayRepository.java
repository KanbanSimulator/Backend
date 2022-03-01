package inno.kanban.KanbanSimulator.dao;

import inno.kanban.KanbanSimulator.model.Day;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DayRepository extends JpaRepository<Day, Long> {
}
