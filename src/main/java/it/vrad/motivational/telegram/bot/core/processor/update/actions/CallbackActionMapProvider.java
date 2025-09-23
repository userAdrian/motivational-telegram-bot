package it.vrad.motivational.telegram.bot.core.processor.update.actions;

import it.vrad.motivational.telegram.bot.core.processor.update.actions.function.CallbackActionFunction;
import it.vrad.motivational.telegram.bot.core.model.constants.PageConstants;
import it.vrad.motivational.telegram.bot.core.facade.callback.AdminPageCallbackFacadeService;
import it.vrad.motivational.telegram.bot.core.facade.callback.InfoPageCallbackFacadeService;
import it.vrad.motivational.telegram.bot.core.facade.callback.InitialPageCallbackFacadeService;
import it.vrad.motivational.telegram.bot.core.facade.callback.StatisticsPageCallbackFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provider for building the callback action map.
 * Associates callback data with their corresponding action functions.
 */
@RequiredArgsConstructor
@Component
public class CallbackActionMapProvider {
    private final InitialPageCallbackFacadeService initialPageCallbackFacadeService;
    private final InfoPageCallbackFacadeService infoPageCallbackFacadeService;
    private final StatisticsPageCallbackFacadeService statisticsPageCallbackFacadeService;
    private final AdminPageCallbackFacadeService adminPageCallbackFacadeService;

    /**
     * Builds the callback action map, associating page references with lists of callback action functions.
     *
     * @return a map from page reference to list of {@link CallbackActionFunction}
     */
    public Map<String, List<CallbackActionFunction>> buildCallbackActionMap() {
        Map<String, List<CallbackActionFunction>> map = new HashMap<>();

        map.putAll(buildInitialPageActionMap());
        map.putAll(buildInfoPageActionMap());
        map.putAll(buildStatisticsPageActionMap());
        map.putAll(buildAdminPageActionMap());

        return map;
    }

    /**
     * Builds the action map for the Initial Page.
     */
    private Map<String, List<CallbackActionFunction>> buildInitialPageActionMap() {
        List<CallbackActionFunction> stepList = List.of(initialPageCallbackFacadeService::processCallbackQuery);

        return Map.of(PageConstants.InitialPage.PAGE_REFERENCE, stepList);
    }

    /**
     * Builds the action map for the Info Page.
     */
    private Map<String, List<CallbackActionFunction>> buildInfoPageActionMap() {
        List<CallbackActionFunction> stepList = List.of(infoPageCallbackFacadeService::processCallbackQuery);

        return Map.of(PageConstants.InfoPage.PAGE_REFERENCE, stepList);
    }

    /**
     * Builds the action map for the Statistics Page.
     */
    private Map<String, List<CallbackActionFunction>> buildStatisticsPageActionMap() {
        List<CallbackActionFunction> stepList = List.of(statisticsPageCallbackFacadeService::processCallbackQuery);

        return Map.of(PageConstants.StatisticsPage.PAGE_REFERENCE, stepList);
    }

    /**
     * Builds the action map for the Admin Page, including the load phrases file button.
     */
    private Map<String, List<CallbackActionFunction>> buildAdminPageActionMap() {
        List<CallbackActionFunction> stepListPage = List.of(adminPageCallbackFacadeService::processCallbackQuery);

        List<CallbackActionFunction> stepListLoadPhrasesFileButton =
                List.of(adminPageCallbackFacadeService::processLoadPhrasesFileButton);

        return Map.of(
                PageConstants.AdminPage.PAGE_REFERENCE, stepListPage,
                PageConstants.AdminPage.Button.LoadFilePhrases.BUTTON_REFERENCE, stepListLoadPhrasesFileButton
        );
    }
}
