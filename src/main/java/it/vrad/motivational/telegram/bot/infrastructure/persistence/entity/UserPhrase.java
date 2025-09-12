package it.vrad.motivational.telegram.bot.infrastructure.persistence.entity;

import it.vrad.motivational.telegram.bot.infrastructure.persistence.entity.ids.UserPhraseId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_phrase")
@Getter
@Setter
public class UserPhrase {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "userId", column = @Column(name = "user_id")),
            @AttributeOverride(name = "phraseId", column = @Column(name = "phrase_id"))
    })
    private UserPhraseId id;

    @Column(name = "read", nullable = false)
    private boolean read;

    @Column(name = "read_count")
    private int readCount;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("phraseId")
    @JoinColumn(name = "phrase_id")
    private Phrase phrase;


}
