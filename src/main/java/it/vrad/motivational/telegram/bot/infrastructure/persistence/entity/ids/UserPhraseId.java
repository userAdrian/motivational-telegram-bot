package it.vrad.motivational.telegram.bot.infrastructure.persistence.entity.ids;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class UserPhraseId {

    private long userId;
    private long phraseId;

    public UserPhraseId(){};

    public UserPhraseId(Long userId, Long phraseId){
        this.userId = userId;
        this.phraseId = phraseId;
    }
}
