package inno.kanban.KanbanSimulator.repository;

import inno.kanban.KanbanSimulator.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}