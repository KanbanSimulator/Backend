package inno.kanban.KanbanSimulator.model;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Builder
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Duration getAge() {
        return Duration.between(now(), createdAt);
    }

    @Column(nullable = false)
    @ColumnDefault(value = "false")
    @Builder.Default
    private Boolean isExpedite = false;

    @Column(nullable = false)
    @ColumnDefault(value = "-1")
    @Builder.Default
    private Integer readyDay = -1;

    private Integer analyticRemaining;

    @Column(nullable = false)
    @ColumnDefault(value = "0")
    @Builder.Default
    private Integer analyticCompleted = 0;

    private Integer developRemaining;

    @Column(nullable = false)
    @ColumnDefault(value = "0")
    @Builder.Default
    private Integer developCompleted = 0;

    private Integer testingRemaining;

    @Column(nullable = false)
    @ColumnDefault(value = "0")
    @Builder.Default
    private Integer testingCompleted = 0;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ColumnDefault(value = "'QUEUE'")
    @Builder.Default
    private ColumnType columnType = ColumnType.QUEUE;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ColumnDefault(value = "'IN_PROGRESS'")
    @Builder.Default
    private ColumnStatus columnStatus = ColumnStatus.IN_PROGRESS;

    @Column(nullable = false)
    @ColumnDefault(value = "0")
    @Builder.Default
    private Integer priority = 0;

    private Integer businessValue;

    @AllArgsConstructor
    @NoArgsConstructor
    public enum ColumnType {
        QUEUE(0),
        ANALYTICS(1),
        DEVELOPMENT(3),
        TESTING(5),
        COMPLETED(4);

        @Getter
        private int value;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    public enum ColumnStatus {
        IN_PROGRESS(0),
        FINISHED(1);

        @Getter
        private int value;
    }

    public int getFrontValue() {
        switch (this.columnType) {
            case QUEUE: return 0;
            case COMPLETED: return 7;
            default: return this.columnType.getValue() + this.columnStatus.getValue();
        }
    }
}
