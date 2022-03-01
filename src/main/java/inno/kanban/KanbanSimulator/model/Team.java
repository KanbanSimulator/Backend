package inno.kanban.KanbanSimulator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne
    private Room game;

    @OneToMany
    private Set<Player> playerSet = new HashSet<>();

    @ManyToOne
    private Room room;

    @Column(nullable = false)
    @ColumnDefault(value = "0")
    @Builder.Default
    private Integer version = 0;

    @Column(nullable = false)
    @ColumnDefault(value = "1")
    @Builder.Default
    private Integer dayNum = 1;

    @Column(nullable = false)
    @ColumnDefault(value = "0")
    @Builder.Default
    private Integer businessValueSum = 0;

    @OneToMany
    @Builder.Default
    private Set<Card> cardSet = new HashSet<>();

    @Column(nullable = false)
    @ColumnDefault(value = "4")
    @Builder.Default
    private Integer wip1 = 4;

    @Column(nullable = false)
    @ColumnDefault(value = "4")
    @Builder.Default
    private Integer wip2 = 4;

    @Column(nullable = false)
    @ColumnDefault(value = "4")
    @Builder.Default
    private Integer wip3 = 4;

    public void incVersion() {
        this.version += 1;
    }
}
