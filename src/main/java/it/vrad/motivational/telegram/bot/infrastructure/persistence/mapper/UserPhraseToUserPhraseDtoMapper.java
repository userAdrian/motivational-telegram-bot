package it.vrad.motivational.telegram.bot.infrastructure.persistence.mapper;

import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserPhraseDto;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.entity.UserPhrase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserPhraseToUserPhraseDtoMapper extends EntityToDtoMapper<UserPhrase, UserPhraseDto> {

    @Mapping(source = "user", target = "userDto")
    @Mapping(source = "phrase", target = "phraseDto")
    @Override
    UserPhraseDto toDto(UserPhrase entity);

    @Mapping(source = "userDto", target = "user")
    @Mapping(source = "phraseDto", target = "phrase")
    @Override
    UserPhrase toEntity(UserPhraseDto dto);

}

