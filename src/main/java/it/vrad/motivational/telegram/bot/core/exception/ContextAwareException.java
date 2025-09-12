package it.vrad.motivational.telegram.bot.core.exception;

public interface ContextAwareException {
    void setChatId(Long chatId);
}
