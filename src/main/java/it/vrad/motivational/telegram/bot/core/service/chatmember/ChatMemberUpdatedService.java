package it.vrad.motivational.telegram.bot.core.service.chatmember;

import it.vrad.motivational.telegram.bot.integration.telegram.model.response.ChatMemberUpdated;

/**
 * Service interface for handling updates to chat member status.
 */
public interface ChatMemberUpdatedService {
    /**
     * Updates the user status in the system based on the provided chat member update event.
     *
     * @param chatMemberUpdated the chat member update event from Telegram
     */
    void updateUserStatus(ChatMemberUpdated chatMemberUpdated);
}
