package it.vrad.motivational.telegram.bot.infrastructure.persistence.repository;

import it.vrad.motivational.telegram.bot.infrastructure.persistence.entity.PhraseSentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhraseSentHistoryRepository extends JpaRepository<PhraseSentHistory, Long> {
}
