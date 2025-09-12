package it.vrad.motivational.telegram.bot.infrastructure.web.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * MDC (Mapped Diagnostic Context) keys used for logging and tracing.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MdcConstants {
    public static final String REQUEST_ID = "requestId";
    public static final String REQUEST_ID_HEADER_NAME = "x-request-id";

}
