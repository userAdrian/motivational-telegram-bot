package it.vrad.motivational.telegram.bot.infrastructure.persistence.mapper;

import it.vrad.motivational.telegram.bot.core.model.dto.persistence.PhraseSentHistoryDto;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.entity.PhraseSentHistory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PhraseSentHistoryToPhraseSentHistoryDtoMapper extends EntityToDtoMapper<PhraseSentHistory, PhraseSentHistoryDto> {
}
