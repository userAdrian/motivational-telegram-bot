package it.vrad.motivational.telegram.bot.infrastructure.persistence.mapper;

import org.hibernate.Hibernate;
import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
import java.util.stream.Collectors;

public interface EntityToDtoMapper<E, D> {

    D toDto(E entity);

    E toEntity(D dto);

    default List<D> toDto(List<E> entityList){
        // Only map if the collection is initialized and not null
        if (Hibernate.isInitialized(entityList) && entityList != null) {
            return entityList.stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());
        }
        return null;
    }

    List<E> toEntity(List<D> dtoList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    E partialUpdate(@MappingTarget E entity, D dto);
}
