package inno.kanban.KanbanSimulator.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "user_team", schema = "public")
public class UserTeamRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_team_id", nullable = false)
    private Long user_team_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

}
