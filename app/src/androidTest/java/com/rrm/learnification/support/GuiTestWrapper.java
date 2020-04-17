package com.rrm.learnification.support;

import com.rrm.learnification.R;
import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.learningitemseteditor.LearningItemSetEditorActivity;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.storage.LearningItemSqlTableClient;
import com.rrm.learnification.test.AndroidTestObjectFactory;

import java.util.List;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class GuiTestWrapper {
    private static final String LOG_TAG = "GuiTestWrapper";

    private final LearningItemSetEditorActivity activity;

    private List<LearningItem> originalLearningItems;
    private LearningItemSqlTableClient learningItemSqlTableClient;
    private AndroidLogger logger;

    public GuiTestWrapper(LearningItemSetEditorActivity activity) {
        this.activity = activity;
    }

    public void beforeEach() {
        AndroidTestObjectFactory androidTestObjectFactory = new AndroidTestObjectFactory(activity);
        logger = androidTestObjectFactory.getLogger();
        learningItemSqlTableClient = new LearningItemSqlTableClient(new AndroidLogger(), androidTestObjectFactory.getLearnificationAppDatabase());
        originalLearningItems = learningItemSqlTableClient.items();
        learningItemSqlTableClient.clearEverything();
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText(R.string.refresh_learning_item_list)).perform(click());
        logger.i(LOG_TAG, "==== TEST START ====");
    }

    public void afterEach() {
        logger.i(LOG_TAG, "==== TEST FINISH ====");
        learningItemSqlTableClient.clearEverything();
        learningItemSqlTableClient.writeAll(originalLearningItems);
    }
}
