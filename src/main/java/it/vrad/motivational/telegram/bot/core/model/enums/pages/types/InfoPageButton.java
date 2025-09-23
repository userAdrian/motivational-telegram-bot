package it.vrad.motivational.telegram.bot.core.model.enums.pages.types;

import it.vrad.motivational.telegram.bot.core.model.constants.PageConstants;
import it.vrad.motivational.telegram.bot.core.model.enums.pages.PageButton;
import lombok.Getter;

@Getter
public enum InfoPageButton implements PageButton {
    PREVIOUS(PageConstants.InitialPage.TITLE_PROPERTY, PageConstants.InitialPage.PAGE_REFERENCE);

    private final String titlePropertyName;
    private final String reference;

    InfoPageButton(String titlePropertyName, String reference) {
        this.titlePropertyName = titlePropertyName;
        this.reference = reference;
    }

    @Override
    public String getPageReference() {
        return PageConstants.InfoPage.PAGE_REFERENCE;
    }
}
