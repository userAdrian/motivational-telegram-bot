package it.vrad.motivational.telegram.bot.core.processor.update.impl;

import it.vrad.motivational.telegram.bot.core.processor.update.UpdateProcessor;
import it.vrad.motivational.telegram.bot.core.model.enums.UpdateProcessorType;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Update;
import it.vrad.motivational.telegram.bot.core.service.chatmember.ChatMemberUpdatedService;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Implementation of {@link UpdateProcessor} for handling chat member updates.
 * Delegates the update to the {@link ChatMemberUpdatedService}.
 */
@Component(UpdateProcessorType.CHAT_MEMBER_UPDATED_PROCESSOR_BEAN_NAME)
public class MyChatMemberProcessor implements UpdateProcessor {
    private final ChatMemberUpdatedService chatMemberUpdatedService;

    public MyChatMemberProcessor(ChatMemberUpdatedService chatMemberUpdatedService) {
        this.chatMemberUpdatedService = chatMemberUpdatedService;
    }

    /**
     * Processes the chat member update by updating the user's status.
     *
     * @param update the update to process
     *
     */
    @Override
    public void accept(Update update) {
        Objects.requireNonNull(update);
        // Delegate to the chat member updated service
        chatMemberUpdatedService.updateUserStatus(update.getMyChatMember());
    }

    /**
     * {@inheritDoc}
     * <p>
     * Returns a predicate that matches updates containing a chat member update.
     *
     * @return {@inheritDoc}
     */
    @Override
    public Predicate<Update> getProcessorFinder() {
        return (update) -> update.getMyChatMember() != null;
    }
}
