package it.vrad.motivational.telegram.bot.infrastructure.persistence.mapper;

import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CooldownToCooldownDtoMapper.class, UserPhraseToUserPhraseDtoMapper.class})
public interface UserToUserDtoMapper extends EntityToDtoMapper<User, UserDto> {

    @Mapping(source = "chat", target = "chatDto")
    @Mapping(source = "userPhrases", target = "userPhraseDtos")
    @Override
    UserDto toDto(User user);

    @Mapping(source = "chatDto", target = "chat")
    @Override
    User toEntity(UserDto userDto);
}
