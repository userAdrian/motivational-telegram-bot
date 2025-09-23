package it.vrad.motivational.telegram.bot.infrastructure.web.controller;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiTestConstants {
    public static final Long USER_TELEGRAM_ID = 99939299L;

    public static class TelegramApiImpl {
        public static final String WEBHOOK_REQUEST_200_PATH = "mock/web/telegram-api-impl/webhook/request200.json";
        public static final String WEBHOOK_RESPONSE_500_PATH = "mock/web/telegram-api-impl/webhook/response500.json";
    }
}
