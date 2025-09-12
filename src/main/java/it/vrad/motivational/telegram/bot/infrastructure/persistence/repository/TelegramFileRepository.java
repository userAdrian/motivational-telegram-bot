package it.vrad.motivational.telegram.bot.infrastructure.persistence.repository;

import it.vrad.motivational.telegram.bot.infrastructure.persistence.entity.TelegramFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TelegramFileRepository extends JpaRepository<TelegramFile, Long> {

    Optional<TelegramFile> findByName(String name);
}
