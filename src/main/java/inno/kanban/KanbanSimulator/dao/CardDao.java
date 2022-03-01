package inno.kanban.KanbanSimulator.dao;

import inno.kanban.KanbanSimulator.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardDao extends JpaRepository<Card, Long> {
}
