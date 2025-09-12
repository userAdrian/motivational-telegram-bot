package it.vrad.motivational.telegram.bot.infrastructure.persistence.mapper;

import it.vrad.motivational.telegram.bot.core.model.dto.persistence.PhraseDto;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.entity.Phrase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserPhraseToUserPhraseDtoMapper.class})
public interface PhraseToPhraseDtoMapper extends EntityToDtoMapper<Phrase, PhraseDto> {

    @Mapping(source = "userPhrases", target = "userPhraseDtos")
    @Override
    PhraseDto toDto(Phrase phrase);

}
