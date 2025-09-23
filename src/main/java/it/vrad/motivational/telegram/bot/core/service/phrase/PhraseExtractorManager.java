package it.vrad.motivational.telegram.bot.core.service.phrase;

import it.vrad.motivational.telegram.bot.core.model.dto.persistence.PhraseDto;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;

import java.util.List;

/**
 * Service for extracting phrases.
 */
public interface PhraseExtractorManager {

    /**
     * Extracts phrases from the file contained in the given Telegram message and persists them.
     *
     * @param chatId  the chat identifier where the file was sent
     * @param message the Telegram message containing the file
     * @return a list of persisted {@link PhraseDto} objects
     * @throws NullPointerException     if the message does not contain a document
     * @throws IllegalArgumentException if the file format is not supported
     */
    List<PhraseDto> extractAndPersistPhrasesFromFile(Long chatId, Message message);
}
