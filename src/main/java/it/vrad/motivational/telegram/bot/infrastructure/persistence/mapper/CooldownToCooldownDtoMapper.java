package it.vrad.motivational.telegram.bot.infrastructure.persistence.mapper;

import it.vrad.motivational.telegram.bot.core.model.dto.persistence.CooldownDto;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.entity.Cooldown;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CooldownToCooldownDtoMapper extends EntityToDtoMapper<Cooldown, CooldownDto> {

    @Mapping(source = "user", target = "userDto")
    @Override
    CooldownDto toDto(Cooldown cooldown);

    @Mapping(source = "userDto", target = "user")
    @Override
    Cooldown toEntity(CooldownDto cooldownDto);
}

