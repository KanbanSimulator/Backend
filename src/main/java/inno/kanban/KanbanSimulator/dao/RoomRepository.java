package inno.kanban.KanbanSimulator.dao;

import inno.kanban.KanbanSimulator.model.Room;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Override
    @EntityGraph(attributePaths = {"players", "players.team", "teamSet"})
    Optional<Room> findById(Long aLong);
}
