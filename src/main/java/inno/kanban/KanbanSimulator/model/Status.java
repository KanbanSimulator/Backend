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
@Table(name = "status", schema = "public")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id", nullable = false)
    private Long status_id;

    @Column(name = "name")
    private String name;

    @Column(name = "position")
    private int position;

    @OneToOne(mappedBy = "status")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
}
