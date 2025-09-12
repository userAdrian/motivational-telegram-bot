package it.vrad.motivational.telegram.bot.infrastructure.persistence.dao;

import it.vrad.motivational.telegram.bot.infrastructure.persistence.mapper.EntityToDtoMapper;

import java.util.List;

/**
 * Abstract base class for Data Access Objects (DAOs).
 * <p>
 * Provides generic methods for converting between entities and DTOs,
 * as well as for performing partial updates using a mapper.
 *
 * @param <E> the entity type
 * @param <D> the DTO type
 */
public abstract class AbstractDao<E, D> {
    // Mapper for converting between entity and DTO
    protected final EntityToDtoMapper<E, D> entityToDtoMapper;


    public AbstractDao(EntityToDtoMapper<E, D> entityToDtoMapper) {
        this.entityToDtoMapper = entityToDtoMapper;
    }

    /**
     * Converts a DTO to its corresponding entity.
     *
     * @param dto the DTO to convert
     * @return the entity representation
     */
    protected E toEntity(D dto) {
        return entityToDtoMapper.toEntity(dto);
    }

    /**
     * Converts an entity to its corresponding DTO.
     *
     * @param entity the entity to convert
     * @return the DTO representation
     */
    protected D toDto(E entity) {
        return entityToDtoMapper.toDto(entity);
    }

    /**
     * Converts a list of DTOs to a list of entities.
     *
     * @param dto the list of DTOs to convert
     * @return the list of entity representations
     */
    protected List<E> toEntity(List<D> dto) {
        return entityToDtoMapper.toEntity(dto);
    }

    /**
     * Converts a list of entities to a list of DTOs.
     *
     * @param entity the list of entities to convert
     * @return the list of DTO representations
     */
    protected List<D> toDto(List<E> entity) {
        return entityToDtoMapper.toDto(entity);
    }

    /**
     * Performs a partial update of an entity using the provided DTO.
     * Only non-null fields in the DTO are applied to the entity.
     *
     * @param entity the entity to update
     * @param dto    the DTO containing updated fields
     * @return the updated entity
     */
    protected E partialUpdate(E entity, D dto) {
        return entityToDtoMapper.partialUpdate(entity, dto);
    }
}
