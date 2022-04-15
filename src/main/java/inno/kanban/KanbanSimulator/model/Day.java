package inno.kanban.KanbanSimulator.model;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Builder
public class Day {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer age;

    @OneToOne
    private Team team;

    private Integer anlCompletedTasks;

    private Integer devCompletedTasks;

    private Integer testCompletedTasks;
}
