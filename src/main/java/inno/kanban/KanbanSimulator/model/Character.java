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
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Team team;

    @Column(nullable = false)
    @ColumnDefault(value = "-1")
    @Builder.Default
    private Integer cardId = -1;

    @Enumerated(EnumType.STRING)
    private Role role;

    @NoArgsConstructor
    @AllArgsConstructor
    public enum Role {
        ANALYTIC("Analytic"),
        DEVELOPER("Developer"),
        TESTER("Tester");

        private String value;
    }
}
