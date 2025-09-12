package it.vrad.motivational.telegram.bot.core.model.enums.pages.types;

import it.vrad.motivational.telegram.bot.core.model.constants.PageConstants;
import it.vrad.motivational.telegram.bot.core.model.enums.pages.PageEnum;
import lombok.Getter;

@Getter
public enum InitialPage implements PageEnum {
    INFO(PageConstants.InfoPage.TITLE_PROPERTY, PageConstants.InfoPage.PAGE_REFERENCE),
    STATISTICS(PageConstants.StatisticsPage.TITLE_PROPERTY, PageConstants.StatisticsPage.PAGE_REFERENCE),
    ADMIN(PageConstants.AdminPage.TITLE_PROPERTY, PageConstants.AdminPage.PAGE_REFERENCE);

    private final String titlePropertyName;
    private final String reference;

    InitialPage(String titlePropertyName, String reference) {
        this.titlePropertyName = titlePropertyName;
        this.reference = reference;
    }
}
