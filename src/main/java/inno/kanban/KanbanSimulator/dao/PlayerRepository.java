package inno.kanban.KanbanSimulator.dao;

import inno.kanban.KanbanSimulator.model.Player;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Override
    @EntityGraph(attributePaths = {"team"})
    Optional<Player> findById(Long aLong);
}
