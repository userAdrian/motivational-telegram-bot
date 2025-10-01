package it.vrad.motivational.telegram.bot.core.facade.callback.impl;

import it.vrad.motivational.telegram.bot.core.facade.callback.InitialPageCallbackFacadeService;
import it.vrad.motivational.telegram.bot.core.facade.callback.base.BaseCallbackFacadeService;
import it.vrad.motivational.telegram.bot.core.facade.message.InitialPageMessageFacadeService;
import it.vrad.motivational.telegram.bot.core.model.context.IncomingCallbackContext;
import it.vrad.motivational.telegram.bot.core.model.dto.CallbackQueryDto;
import it.vrad.motivational.telegram.bot.core.model.factory.ContextFactory;
import it.vrad.motivational.telegram.bot.core.service.telegram.TelegramService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of {@link InitialPageCallbackFacadeService}
 * for handling initial page callback actions
 */
@Slf4j
@Service
public class InitialPageCallbackFacadeServiceImpl extends BaseCallbackFacadeService implements InitialPageCallbackFacadeService {
    private final InitialPageMessageFacadeService initialPageMessageFacadeService;

    public InitialPageCallbackFacadeServiceImpl(
            TelegramService telegramService,
            InitialPageMessageFacadeService initialPageMessageFacadeService
    ) {
        super(telegramService);
        this.initialPageMessageFacadeService = initialPageMessageFacadeService;
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
                "initial page callback",
                message -> initialPageMessageFacadeService.forwardToInitialPage(ContextFactory.buildIncomingMessageContext(message))
        );
    }

}
