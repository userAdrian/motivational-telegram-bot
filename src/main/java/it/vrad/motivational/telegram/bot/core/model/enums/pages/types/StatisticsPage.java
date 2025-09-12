package it.vrad.motivational.telegram.bot.core.model.enums.pages.types;

import it.vrad.motivational.telegram.bot.core.model.constants.PageConstants;
import it.vrad.motivational.telegram.bot.core.model.enums.pages.PageEnum;
import lombok.Getter;

@Getter
public enum StatisticsPage implements PageEnum {

    PREVIOUS(PageConstants.InitialPage.TITLE_PROPERTY, PageConstants.InitialPage.PAGE_REFERENCE);

    private final String titlePropertyName;
    private final String reference;

    StatisticsPage(String titlePropertyName, String reference) {
        this.titlePropertyName = titlePropertyName;
        this.reference = reference;
    }
}
