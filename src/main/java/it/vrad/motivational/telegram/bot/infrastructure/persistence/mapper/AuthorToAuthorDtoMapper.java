package it.vrad.motivational.telegram.bot.infrastructure.persistence.mapper;

import it.vrad.motivational.telegram.bot.core.model.dto.persistence.AuthorDto;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PhraseToPhraseDtoMapper.class})
public interface AuthorToAuthorDtoMapper extends EntityToDtoMapper<Author, AuthorDto> {

    @Mapping(source = "phrases", target = "phraseDtos")
    @Override
    AuthorDto toDto(Author author);
}

