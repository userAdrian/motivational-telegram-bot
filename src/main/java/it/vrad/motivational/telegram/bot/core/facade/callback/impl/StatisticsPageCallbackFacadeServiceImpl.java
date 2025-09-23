package it.vrad.motivational.telegram.bot.core.facade.callback.impl;

import it.vrad.motivational.telegram.bot.core.facade.callback.StatisticsPageCallbackFacadeService;
import it.vrad.motivational.telegram.bot.core.facade.callback.base.BaseCallbackFacadeService;
import it.vrad.motivational.telegram.bot.core.facade.message.StatisticsPageMessageFacadeService;
import it.vrad.motivational.telegram.bot.core.model.context.IncomingCallbackContext;
import it.vrad.motivational.telegram.bot.core.model.dto.CallbackQueryDto;
import it.vrad.motivational.telegram.bot.core.model.factory.ObjectsFactory;
import it.vrad.motivational.telegram.bot.core.service.telegram.TelegramService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of {@link StatisticsPageCallbackFacadeService}
 * for handling statistics page callback actions.
 */
@Slf4j
@Service
public class StatisticsPageCallbackFacadeServiceImpl extends BaseCallbackFacadeService implements StatisticsPageCallbackFacadeService {
    private final StatisticsPageMessageFacadeService statisticsPageMessageFacadeService;

    public StatisticsPageCallbackFacadeServiceImpl(
            TelegramService telegramService,
            StatisticsPageMessageFacadeService statisticsPageMessageFacadeService
    ) {
        super(telegramService);
        this.statisticsPageMessageFacadeService = statisticsPageMessageFacadeService;
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
                "statistics page callback",
                message -> statisticsPageMessageFacadeService.forwardToStatisticsPage(
                        ObjectsFactory.buildIncomingMessageContext(message))
        );
    }
}
