package it.vrad.motivational.telegram.bot.core.model.enums.pages.types;

import it.vrad.motivational.telegram.bot.core.model.constants.PageConstants;
import it.vrad.motivational.telegram.bot.core.model.enums.pages.PageButton;
import lombok.Getter;

@Getter
public enum AdminPageButton implements PageButton {
    LOAD_FILE_PHRASES(
            PageConstants.AdminPage.Button.LoadFilePhrases.TITLE_PROPERTY,
            PageConstants.AdminPage.Button.LoadFilePhrases.BUTTON_REFERENCE
    ),

    PREVIOUS(PageConstants.InitialPage.TITLE_PROPERTY, PageConstants.InitialPage.PAGE_REFERENCE);

    private final String titlePropertyName;
    private final String reference;

    AdminPageButton(String titlePropertyName, String reference) {
        this.titlePropertyName = titlePropertyName;
        this.reference = reference;
    }

    @Override
    public String getPageReference() {
        return PageConstants.AdminPage.PAGE_REFERENCE;
    }
}
