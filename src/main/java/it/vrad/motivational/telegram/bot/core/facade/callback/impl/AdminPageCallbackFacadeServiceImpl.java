package it.vrad.motivational.telegram.bot.core.facade.callback.impl;

import it.vrad.motivational.telegram.bot.core.facade.callback.AdminPageCallbackFacadeService;
import it.vrad.motivational.telegram.bot.core.facade.callback.base.BaseCallbackFacadeService;
import it.vrad.motivational.telegram.bot.core.facade.message.admin.AdminCommandFacadeService;
import it.vrad.motivational.telegram.bot.core.facade.message.admin.AdminPageMessageFacadeService;
import it.vrad.motivational.telegram.bot.core.model.context.IncomingCallbackContext;
import it.vrad.motivational.telegram.bot.core.model.dto.CallbackQueryDto;
import it.vrad.motivational.telegram.bot.core.model.factory.ContextFactory;
import it.vrad.motivational.telegram.bot.core.service.telegram.TelegramService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of {@link AdminPageCallbackFacadeService}
 * for handling admin page callback actions.
 */
@Slf4j
@Service
public class AdminPageCallbackFacadeServiceImpl extends BaseCallbackFacadeService implements AdminPageCallbackFacadeService {
    private final AdminPageMessageFacadeService adminPageMessageFacadeService;
    private final AdminCommandFacadeService adminCommandFacadeService;

    public AdminPageCallbackFacadeServiceImpl(
            TelegramService telegramService,
            AdminPageMessageFacadeService adminPageMessageFacadeService,
            AdminCommandFacadeService adminCommandFacadeService
    ) {
        super(telegramService);
        this.adminPageMessageFacadeService = adminPageMessageFacadeService;
        this.adminCommandFacadeService = adminCommandFacadeService;
    }

    /**
     * {@inheritDoc}
     *
     * @param incomingCallbackContext {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public Optional<CallbackQueryDto> processCallbackQuery(IncomingCallbackContext incomingCallbackContext) {
        return handleCallbackAction(
                incomingCallbackContext,
                "admin page callback",
                message -> adminPageMessageFacadeService.forwardToAdminPage(
                        ContextFactory.buildIncomingMessageContext(message))
        );
    }

    /**
     * {@inheritDoc}
     *
     * @param incomingCallbackContext {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public Optional<CallbackQueryDto> processLoadPhrasesFileButton(IncomingCallbackContext incomingCallbackContext) {
        return handleCallbackAction(
                incomingCallbackContext,
                "load phrases file button callback",
                message -> adminCommandFacadeService.processLoadFilePhrasesCommand(
                        ContextFactory.buildIncomingMessageContext(message))
        );
    }
}
