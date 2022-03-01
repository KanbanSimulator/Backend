package inno.kanban.KanbanSimulator.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import inno.kanban.KanbanSimulator.model.Character;

@Repository
public interface CharacterDao extends JpaRepository<Character, Long> {
}
