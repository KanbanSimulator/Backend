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
public class UserStory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(nullable = false)
    @ColumnDefault(value = "false")
    @Builder.Default
    private Boolean isExpedite = false;

    private Integer analyticsPoints;

    private Integer developPoints;

    private Integer testPoints;

    private Integer businessValue;
}
