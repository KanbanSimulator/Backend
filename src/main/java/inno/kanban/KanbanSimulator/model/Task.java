package inno.kanban.KanbanSimulator.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "task", schema = "public")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id", nullable = false)
    private Long taskId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "story_points",nullable = false)
    private int storyPoints;

    @OneToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToMany
    @JoinColumn(name = "label_id")
    private List<Label> label;

    @ManyToMany(mappedBy = "tasks")
    private List<Label> labels;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Task task = (Task) o;
        return taskId != null && Objects.equals(taskId, task.taskId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
