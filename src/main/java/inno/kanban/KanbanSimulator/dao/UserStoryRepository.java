package inno.kanban.KanbanSimulator.dao;

import inno.kanban.KanbanSimulator.model.UserStory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStoryRepository extends JpaRepository<UserStory, Long> {
}
