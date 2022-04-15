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
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @ColumnDefault(value = "false")
    @Builder.Default
    private Boolean ready = false;

    @Column(nullable = false)
    @ColumnDefault(value = "false")
    @Builder.Default
    private Boolean started = false;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
    @Builder.Default
    private Set<Team> teamSet = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
    @Builder.Default
    private Set<Player> players = new HashSet<>();

    @Column(nullable = false)
    @ColumnDefault(value = "0")
    @Builder.Default
    private Integer version = 0;

    public void incVersion() {
        this.version += 1;
    }
}
