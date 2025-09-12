package it.vrad.motivational.telegram.bot.infrastructure.persistence.mapper;

import it.vrad.motivational.telegram.bot.core.model.dto.persistence.ChatDto;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.entity.Chat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChatToChatDtoMapper extends EntityToDtoMapper<Chat, ChatDto>{

    @Mapping(source = "user", target = "userDto")
    @Override
    ChatDto toDto(Chat entity);

    @Mapping(source = "userDto", target = "user")
    @Override
    Chat toEntity(ChatDto dto);
}
