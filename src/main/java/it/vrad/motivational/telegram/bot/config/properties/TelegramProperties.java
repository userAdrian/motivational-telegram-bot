package it.vrad.motivational.telegram.bot.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration properties for Telegram API integration.
 * Loads Telegram bot API URLs and tokens from the application's properties.
 */
@Data
@ConfigurationProperties(prefix = "motivational.telegram.bot.telegram")
@Component
public class TelegramProperties {
    private String baseUrl;
    private String baseFileUrl;
    private Endpoints endpoints;

    @Data
    public static class Endpoints {
        private String sendMessage;
        private String sendPhoto;
        private String editMessageMedia;
        private String answerCallbackQuery;
        private String getFile;
    }

}
