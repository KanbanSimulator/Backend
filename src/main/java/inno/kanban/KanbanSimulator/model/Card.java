package inno.kanban.KanbanSimulator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    @ColumnDefault(value = "'NEW'")
    @Builder.Default
    private ColumnType columnType = ColumnType.NEW;

    @Column(nullable = false)
    @ColumnDefault(value = "0")
    @Builder.Default
    private Integer priority = 0;

    private Integer businessValue;

    public enum ColumnType {
        NEW,
        ANALYTICS,
        DEVELOPMENT,
        TESTING,
        FINISHED,
    }
}
