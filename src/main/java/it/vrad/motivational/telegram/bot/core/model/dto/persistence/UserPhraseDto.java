package it.vrad.motivational.telegram.bot.core.model.dto.persistence;

import it.vrad.motivational.telegram.bot.infrastructure.persistence.entity.ids.UserPhraseId;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserPhraseDto {
    private UserPhraseId id;
    private Boolean read;
    private Integer readCount;

    private UserDto userDto;
    private PhraseDto phraseDto;
}
