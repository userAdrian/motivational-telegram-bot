package it.vrad.motivational.telegram.bot.infrastructure.util;

import it.vrad.motivational.telegram.bot.integration.telegram.TelegramConstants;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.PhraseDto;
import it.vrad.motivational.telegram.bot.core.model.factory.PersistenceDtoFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility class for file operations and CSV parsing.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FilesUtility {

    /**
     * Gets a File object from a file name or classpath resource.
     *
     * @param fileName the file name or classpath resource
     * @return File object
     * @throws NullPointerException if fileName is null
     * @throws UncheckedIOException if classpath resource cannot be loaded
     */
    public static File getFile(String fileName) {
        Objects.requireNonNull(fileName);

        if (fileName.startsWith("classpath:")) {
            String resourcePath = fileName.substring("classpath:".length());
            Resource resource = new ClassPathResource(resourcePath);
            try {
                return resource.getFile();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        return new File(fileName);
    }

    /**
     * Gets the media value for Telegram API (fileId, attach name, or URL).
     *
     * @param telegramFileId Telegram file ID
     * @param media          File object
     * @param mediaUrl       media URL
     * @return media value string
     * @throws IllegalStateException if all input fields are empty or null
     */
    public static String getMediaValue(String telegramFileId, File media, String mediaUrl) {
        if (StringUtils.isNotBlank(telegramFileId)) {
            return telegramFileId;
        }

        if (Objects.nonNull(media)) {
            return createInputMediaAttachName(media);
        }

        if (StringUtils.isNotBlank(mediaUrl)) {
            return mediaUrl;
        }

        throw new IllegalStateException("One of the input fields must be filled");
    }

    /**
     * Creates Telegram attach name for a file.
     *
     * @param media File object
     * @return attach name string
     */
    private static String createInputMediaAttachName(File media) {
        return TelegramConstants.TELEGRAM_INPUT_MEDIA_ATTACH_PREFIX + media.getName();
    }

    /**
     * Parses phrases from a CSV file byte array.
     *
     * @param bytes CSV file bytes
     * @return set of valid PhraseDto objects
     * @throws UncheckedIOException if CSV parsing fails
     */
    public static Set<PhraseDto> getPhrasesFromCSVFile(byte[] bytes) {
        try (CSVParser csvParser = getPhraseCSVParser(bytes)) {
            return csvParser.stream()
                    .map(PersistenceDtoFactory::buildPhraseDto)
                    .filter(PhraseDto::isValid)
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Gets a CSVParser for phrase CSV files.
     *
     * @param bytes CSV file bytes
     * @return CSVParser object
     * @throws IOException if parsing fails
     */
    private static CSVParser getPhraseCSVParser(byte[] bytes) throws IOException {
        CSVFormat csvFormat = CSVFormat.Builder.create()
                .setHeader()
                .setSkipHeaderRecord(true)
                .build();

        return new CSVParser(
                new InputStreamReader(new ByteArrayInputStream(bytes)),
                csvFormat
        );
    }
}
