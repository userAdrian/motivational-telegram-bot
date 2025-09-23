package it.vrad.motivational.telegram.bot.core.service.chatmember.impl;

import it.vrad.motivational.telegram.bot.core.exception.NoSuchUserException;
import it.vrad.motivational.telegram.bot.core.model.enums.persistence.UserRole;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.ChatMember;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.ChatMemberUpdated;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.UserDao;
import it.vrad.motivational.telegram.bot.core.service.chatmember.ChatMemberUpdatedService;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Implementation of {@link ChatMemberUpdatedService}
 * for handling updates to chat member status.
 */
@Service
public class ChatMemberUpdatedServiceImpl implements ChatMemberUpdatedService {

    private final UserDao userDao;

    public ChatMemberUpdatedServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * {@inheritDoc}
     *
     * @param chatMemberUpdated {@inheritDoc}
     */
    @Override
    public void updateUserStatus(ChatMemberUpdated chatMemberUpdated) throws NoSuchUserException {
        Objects.requireNonNull(chatMemberUpdated);
        ChatMember newChatMember = Objects.requireNonNull(chatMemberUpdated.getNewChatMember());

        Long telegramId = Objects.requireNonNull(chatMemberUpdated.getFrom()).getId();

        // Extract the new status and update the user's role accordingly
        String status = newChatMember.getStatus();
        UserDto userDto = UserDto.builder()
                .userRole(UserRole.fromTelegramValue(status))
                .build();

        // Update the user in the database with the new role
        userDao.update(telegramId, userDto);
    }
}
