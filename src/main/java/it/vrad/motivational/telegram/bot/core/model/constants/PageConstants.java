package it.vrad.motivational.telegram.bot.core.model.constants;

import it.vrad.motivational.telegram.bot.integration.telegram.util.CallbackUtility;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageConstants {

    // START ------> PAGES CONFIG <------
    public static class InitialPage {
        public static final String PAGE_REFERENCE = CallbackUtility.createPageReference("InitialPage");
        public static final String TITLE_PROPERTY = "motivational.telegram.bot.title.page.initial";
        public static final String MESSAGE_PROPERTY = CommandConstants.Initial.MESSAGE_PROPERTY;
        public static final String MESSAGE_PHOTO_CLASSPATH = "classpath:images/initial-page.jpg";
    }

    public static class InfoPage {
        public static final String PAGE_REFERENCE = CallbackUtility.createPageReference("InfoPage");
        public static final String TITLE_PROPERTY = "motivational.telegram.bot.title.page.info";
        public static final String MESSAGE_PROPERTY = "motivational.telegram.bot.messages.page.info.template";
        public static final String MESSAGE_PHOTO_CLASSPATH = "classpath:images/info-page.jpg";

    }

    public static class StatisticsPage {
        public static final String PAGE_REFERENCE = CallbackUtility.createPageReference("StatisticsPage");
        public static final String TITLE_PROPERTY = "motivational.telegram.bot.title.page.statistics";
        public static final String MESSAGE_PROPERTY = "motivational.telegram.bot.messages.page.statistics.template";
        public static final String MESSAGE_PHOTO_CLASSPATH = "classpath:images/statistics-page.jpg";

        public static final String FIELD_NOT_AVAILABLE_PLACEHOLDER = "-";
    }

    public static class AdminPage {
        public static final String PAGE_REFERENCE = CallbackUtility.createPageReference("AdminPage");
        public static final String TITLE_PROPERTY = "motivational.telegram.bot.title.page.admin";
        public static final String MESSAGE_PROPERTY = "motivational.telegram.bot.messages.page.admin.template";
        public static final String MESSAGE_PHOTO_CLASSPATH = "classpath:images/admin-page.jpg";

        public static class Button {
            public static class LoadFilePhrases {
                public static final String BUTTON_REFERENCE = CallbackUtility.createButtonReference("loadFilePhrases");
                public static final String TITLE_PROPERTY = "motivational.telegram.bot.title.page.admin.load-file-phrases";
            }
        }
    }
    // END ------> PAGES CONFIG <------
}
