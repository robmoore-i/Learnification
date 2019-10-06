package com.rrm.learnification.main;

import com.rrm.learnification.common.AndroidLogger;
import com.rrm.learnification.common.ClearTextInputOnClickCommand;
import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.common.RemoveItemOnSwipeCommand;
import com.rrm.learnification.common.SetButtonStatusOnTextChangeListener;
import com.rrm.learnification.notification.AndroidNotificationFacade;
import com.rrm.learnification.publication.LearnificationPublishingService;
import com.rrm.learnification.publication.LearnificationScheduler;
import com.rrm.learnification.storage.ItemRepository;
import com.rrm.learnification.toolbar.AppToolbar;
import com.rrm.learnification.toolbar.ByLearnificationScheduleStatus;

class MainActivityEntryPoint {
    private final AndroidNotificationFacade notificationFacade;
    private final ItemRepository<LearningItem> itemRepository;
    private final AppToolbar appToolbar;
    private final LearningItemTextInput learningItemTextInput;
    private final AddLearningItemButton addLearningItemButton;
    private final LearningItemList learningItemList;
    private final LearnificationScheduler learnificationScheduler;

    MainActivityEntryPoint(
            AndroidLogger logger,
            MainActivityView mainActivityView,
            AndroidNotificationFacade notificationFacade,
            ItemRepository<LearningItem> itemRepository,
            LearnificationScheduler learnificationScheduler) {

        this.itemRepository = itemRepository;
        this.notificationFacade = notificationFacade;
        this.learnificationScheduler = learnificationScheduler;

        this.appToolbar = new AppToolbar(logger, mainActivityView);
        this.learningItemTextInput = new LearningItemTextInput(mainActivityView);
        this.addLearningItemButton = new AddLearningItemButton(logger, mainActivityView);
        this.learningItemList = new LearningItemList(logger, mainActivityView);

        mainActivityView.addToolbarViewUpdate(new ByLearnificationScheduleStatus(logger, learnificationScheduler));
    }

    void onMainActivityEntry() {
        initialiseView();

        createNotificationChannel();

        learnificationScheduler.scheduleImminentJob(LearnificationPublishingService.class);
    }

    private void initialiseView() {
        appToolbar.setTitle("Learnification");

        learningItemTextInput.setOnTextChangeListener(new SetButtonStatusOnTextChangeListener(addLearningItemButton));
        addLearningItemButton.addOnClickHandler(new AddLearningItemOnClickCommand(learningItemTextInput, itemRepository, learningItemList));
        addLearningItemButton.addOnClickHandler(new ClearTextInputOnClickCommand(learningItemTextInput));

        learningItemList.bindTo(itemRepository);
        learningItemList.setOnSwipeCommand(new RemoveItemOnSwipeCommand(itemRepository));
    }

    private void createNotificationChannel() {
        notificationFacade.createNotificationChannel(AndroidNotificationFacade.CHANNEL_ID);
    }
}
