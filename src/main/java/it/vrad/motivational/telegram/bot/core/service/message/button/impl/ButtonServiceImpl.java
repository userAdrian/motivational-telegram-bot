package it.vrad.motivational.telegram.bot.core.service.message.button.impl;

import it.vrad.motivational.telegram.bot.integration.telegram.model.response.InlineKeyboardButton;
import it.vrad.motivational.telegram.bot.core.service.title.TitleService;
import it.vrad.motivational.telegram.bot.core.service.message.button.ButtonService;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Implementation of {@link ButtonService}
 * for building inline keyboard buttons.
 */
@Service
public class ButtonServiceImpl implements ButtonService {

    private final TitleService titleService;

    /**
     * Constructs a ButtonServiceImpl with the provided TitleService.
     *
     * @param titleService the service for retrieving localized titles
     */
    public ButtonServiceImpl(TitleService titleService) {
        this.titleService = titleService;
    }

    /**
     * {@inheritDoc}
     *
     * @param locale       {@inheritDoc}
     * @param propertyName {@inheritDoc}
     * @param callback     {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public InlineKeyboardButton buildInlineKeyboardButton(Locale locale, String propertyName, String callback) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();

        inlineKeyboardButton.setText(titleService.getButtonTitle(propertyName, locale));
        inlineKeyboardButton.setCallbackData(callback);

        return inlineKeyboardButton;
    }
}
