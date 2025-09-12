package it.vrad.motivational.telegram.bot.infrastructure.persistence.repository;

import it.vrad.motivational.telegram.bot.core.model.enums.persistence.CooldownType;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.entity.Cooldown;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CooldownRepository extends JpaRepository<Cooldown, Long> {

    Optional<Cooldown> findByUserIdAndType(Long userId, CooldownType type);
}
