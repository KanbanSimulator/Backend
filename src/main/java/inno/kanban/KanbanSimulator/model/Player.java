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
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @ColumnDefault(value = "'name'")
    @Builder.Default
    private String name = "name";

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;

    @Column(nullable = false)
    @ColumnDefault(value = "false")
    @Builder.Default
    private Boolean spectator = false;

    @Column(nullable = false)
    @ColumnDefault(value = "false")
    @Builder.Default
    private Boolean creator = false;
}
