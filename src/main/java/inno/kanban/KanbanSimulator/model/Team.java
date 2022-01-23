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
@Table(name = "team", schema = "public")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;

    @Column(name = "team_name", nullable = false, length = 64)
    private String teamName;

    @OneToMany(mappedBy = "team")
    private List<User> userList;

    @OneToMany(mappedBy = "team")
    private List<Task> taskList;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Team team = (Team) o;
        return teamId != null && Objects.equals(teamId, team.teamId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
