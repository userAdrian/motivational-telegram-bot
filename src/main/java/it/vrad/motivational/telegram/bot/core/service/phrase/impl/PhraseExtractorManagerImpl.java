package it.vrad.motivational.telegram.bot.core.service.phrase.impl;

import it.vrad.motivational.telegram.bot.core.model.constants.CommandConstants;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.PhraseDto;
import it.vrad.motivational.telegram.bot.core.service.phrase.PhraseExtractorManager;
import it.vrad.motivational.telegram.bot.core.service.phrase.PhraseService;
import it.vrad.motivational.telegram.bot.core.service.telegram.TelegramService;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Document;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;
import it.vrad.motivational.telegram.bot.shared.util.FileUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Implementation of {@link PhraseExtractorManager}
 */
@RequiredArgsConstructor
@Service
public class PhraseExtractorManagerImpl implements PhraseExtractorManager {
    private final PhraseService phraseService;
    private final TelegramService telegramService;

    /**
     * {@inheritDoc}
     *
     * @param chatId  {@inheritDoc}
     * @param message {@inheritDoc}
     * @return {@inheritDoc}
     * @throws NullPointerException     {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Override
    public List<PhraseDto> extractAndPersistPhrasesFromFile(Long chatId, Message message) {
        Document document = Objects.requireNonNull(message.getDocument(), "No file present inside the message");

        // Ensure format is supported
        FileUtility.validateFileFormat(document.getMimeType(), CommandConstants.LoadFilePhrases.FILE_MIME_TYPE);

        // Download the file from Telegram
        byte[] downloadedFile = telegramService.downloadFile(document, chatId);

        // Parse phrases from the CSV file and save them
        Set<PhraseDto> phrases = FileUtility.getPhrasesFromCSVFile(downloadedFile);

        return phraseService.save(phrases);
    }
}
