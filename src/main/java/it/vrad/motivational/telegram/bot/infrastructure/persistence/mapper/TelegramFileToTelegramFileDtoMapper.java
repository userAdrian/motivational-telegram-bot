package it.vrad.motivational.telegram.bot.infrastructure.persistence.mapper;

import it.vrad.motivational.telegram.bot.core.model.dto.persistence.TelegramFileDto;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.entity.TelegramFile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TelegramFileToTelegramFileDtoMapper extends EntityToDtoMapper<TelegramFile, TelegramFileDto> {
}
