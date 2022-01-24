package inno.kanban.KanbanSimulator.model;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "label", schema = "public")
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "label_id", nullable = false)
    private Long label_id;

    @Column(name = "name")
    private String name;

    @Column(name = "color")
    private String color;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToMany
    @JoinTable(name = "task_label",
    joinColumns = @JoinColumn(name="label_id"),
    inverseJoinColumns = @JoinColumn(name="task_id"))
    private List<Task> tasks;
}
