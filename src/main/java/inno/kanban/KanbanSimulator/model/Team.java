package inno.kanban.KanbanSimulator.model;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Builder
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false)
    private Long number;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "team")
    private Set<Player> playerSet = new HashSet<>();

    @JoinColumn(name = "room_id")
    @ManyToOne(fetch = FetchType.LAZY)
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "team")
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

    @Column(nullable = false)
    @ColumnDefault(value = "5")
    @Builder.Default
    private Integer freeAnalyticsPersons = 5;

    @Column(nullable = false)
    @ColumnDefault(value = "5")
    @Builder.Default
    private Integer freeDevelopmentPersons = 5;

    @Column(nullable = false)
    @ColumnDefault(value = "5")
    @Builder.Default
    private Integer freeTestingPersons = 5;

    public void incVersion() {
        this.version += 1;
    }

}
