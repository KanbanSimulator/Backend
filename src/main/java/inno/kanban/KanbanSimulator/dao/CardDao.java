package inno.kanban.KanbanSimulator.dao;

import inno.kanban.KanbanSimulator.model.Card;
import inno.kanban.KanbanSimulator.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardDao extends JpaRepository<Card, Long> {

    List<Card> findAllByTeamAndColumnType(Team team, Card.ColumnType columnType);
}
