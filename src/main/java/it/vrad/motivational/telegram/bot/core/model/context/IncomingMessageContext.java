package it.vrad.motivational.telegram.bot.core.model.context;

import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IncomingMessageContext {
    private Message sentMessage;
    private UserDto userFromDB;
}
