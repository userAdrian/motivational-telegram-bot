package it.vrad.motivational.telegram.bot.core.model.enums.pages.types;

import it.vrad.motivational.telegram.bot.core.model.constants.PageConstants;
import it.vrad.motivational.telegram.bot.core.model.enums.pages.PageButton;
import lombok.Getter;

@Getter
public enum InitialPageButton implements PageButton {
    INFO(PageConstants.InfoPage.TITLE_PROPERTY, PageConstants.InfoPage.PAGE_REFERENCE),
    STATISTICS(PageConstants.StatisticsPage.TITLE_PROPERTY, PageConstants.StatisticsPage.PAGE_REFERENCE),
    ADMIN(PageConstants.AdminPage.TITLE_PROPERTY, PageConstants.AdminPage.PAGE_REFERENCE);

    private final String titlePropertyName;
    private final String reference;

    InitialPageButton(String titlePropertyName, String reference) {
        this.titlePropertyName = titlePropertyName;
        this.reference = reference;
    }

    @Override
    public String getPageReference() {
        return PageConstants.InitialPage.PAGE_REFERENCE;
    }
}
