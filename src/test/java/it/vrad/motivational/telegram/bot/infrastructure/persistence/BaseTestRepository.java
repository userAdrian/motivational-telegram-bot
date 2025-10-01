package it.vrad.motivational.telegram.bot.infrastructure.persistence;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@DataJpaTest
@EntityScan(basePackages = "it.vrad.motivational.telegram.bot.infrastructure.persistence.entity")
@EnableJpaRepositories(basePackages = "it.vrad.motivational.telegram.bot.infrastructure.persistence.repository")
public abstract class BaseTestRepository {

}
