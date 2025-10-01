package it.vrad.motivational.telegram.bot.core.facade.callback.impl;

import it.vrad.motivational.telegram.bot.core.facade.callback.InfoPageCallbackFacadeService;
import it.vrad.motivational.telegram.bot.core.facade.callback.base.BaseCallbackFacadeService;
import it.vrad.motivational.telegram.bot.core.facade.message.InfoPageMessageFacadeService;
import it.vrad.motivational.telegram.bot.core.model.context.IncomingCallbackContext;
import it.vrad.motivational.telegram.bot.core.model.dto.CallbackQueryDto;
import it.vrad.motivational.telegram.bot.core.model.factory.ContextFactory;
import it.vrad.motivational.telegram.bot.core.service.telegram.TelegramService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of {@link InfoPageCallbackFacadeService}
 * for handling info page callback actions.
 */
@Slf4j
@Service
public class InfoPageCallbackFacadeServiceImpl extends BaseCallbackFacadeService implements InfoPageCallbackFacadeService {
    private final InfoPageMessageFacadeService infoPageMessageFacadeService;

    public InfoPageCallbackFacadeServiceImpl(
            TelegramService telegramService,
            InfoPageMessageFacadeService infoPageMessageFacadeService
    ) {
        super(telegramService);
        this.infoPageMessageFacadeService = infoPageMessageFacadeService;
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
                "info page callback",
                message -> infoPageMessageFacadeService.forwardToInfoPage(ContextFactory.buildIncomingMessageContext(message))
        );
    }
}
