package it.vrad.motivational.telegram.bot.infrastructure.persistence.entity;

import it.vrad.motivational.telegram.bot.core.model.enums.persistence.ChatType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "chat")
@Getter
@Setter
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "telegram_id", nullable = false)
    private long telegramId;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ChatType type;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
