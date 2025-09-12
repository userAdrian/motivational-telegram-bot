package it.vrad.motivational.telegram.bot.integration.telegram.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true)
@Getter
public class IntegrationApiException extends RuntimeException {

  private final Long chatId;
  private final String errorCode;
  private final String description;
  private final String api;

  @Builder
  public IntegrationApiException(Long chatId, String description, String errorCode, String api, Throwable throwable) {
    super(description, throwable);

    this.chatId = chatId;
    this.description = description;
    this.errorCode = errorCode;
    this.api = api;
  }

}
