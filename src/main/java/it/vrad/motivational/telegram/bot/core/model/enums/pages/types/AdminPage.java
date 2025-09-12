package it.vrad.motivational.telegram.bot.core.model.enums.pages.types;

import it.vrad.motivational.telegram.bot.core.model.constants.PageConstants;
import it.vrad.motivational.telegram.bot.core.model.enums.pages.PageEnum;
import lombok.Getter;

@Getter
public enum AdminPage implements PageEnum {
    LOAD_FILE_PHRASES(
            PageConstants.AdminPage.Button.LoadFilePhrases.TITLE_PROPERTY,
            PageConstants.AdminPage.Button.LoadFilePhrases.BUTTON_REFERENCE
    ),

    PREVIOUS(PageConstants.InitialPage.TITLE_PROPERTY, PageConstants.InitialPage.PAGE_REFERENCE);

    private final String titlePropertyName;
    private final String reference;

    AdminPage(String titlePropertyName, String reference) {
        this.titlePropertyName = titlePropertyName;
        this.reference = reference;
    }
}
