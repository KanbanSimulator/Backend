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
@Table(name = "column", schema = "public")
public class ColumnStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "column_id", nullable = false)
    private Long column_id;

    @Column(name = "name")
    private String name;

    @Column(name = "position")
    private int position;

    @OneToMany(mappedBy = "column")
    private List<Task> task;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
}

