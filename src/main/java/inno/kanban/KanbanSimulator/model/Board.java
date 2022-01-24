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
@Table(name = "board", schema = "public")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id", nullable = false)
    private Long boardId;

    @Column(name = "board_name", nullable = false, length = 64)
    private String boardName;

    @OneToMany(mappedBy = "board")
    private List<Task> taskList;

    @OneToMany(mappedBy = "board")
    private List<UserTeamRole> usersAndRoles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Board team = (Board) o;
        return boardId != null && Objects.equals(boardId, team.boardId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
