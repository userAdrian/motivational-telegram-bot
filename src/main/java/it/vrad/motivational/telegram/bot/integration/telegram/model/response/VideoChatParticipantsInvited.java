package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import java.util.List;
import lombok.Data;

@Data
public class VideoChatParticipantsInvited {
    private List<User> users;
}
