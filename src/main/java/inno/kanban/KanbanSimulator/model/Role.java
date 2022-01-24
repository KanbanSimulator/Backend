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
@Table(name = "role", schema = "public")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false)
    private Long user_team_id;

    @Column(name = "role_name")
    private String roleName;

}
