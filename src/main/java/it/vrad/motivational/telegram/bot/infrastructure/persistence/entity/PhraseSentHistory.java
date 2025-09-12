package it.vrad.motivational.telegram.bot.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;

@Entity
@Table(name = "phrase_sent_history")
@Data
public class PhraseSentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phrase_id", nullable = false)
    private Long phraseId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "sending_date", nullable = false)
    private OffsetDateTime sendingDate;
}
