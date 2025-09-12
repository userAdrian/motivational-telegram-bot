package it.vrad.motivational.telegram.bot.infrastructure.persistence.entity;

import it.vrad.motivational.telegram.bot.core.model.enums.persistence.PhraseType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "phrase")
@Data
public class Phrase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @Column(name = "text", nullable = false, length = 1024)
    private String text;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PhraseType type;

    @Column(name = "disabled")
    private boolean disabled;

    @OneToMany(mappedBy = "phrase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserPhrase> userPhrases;
}
