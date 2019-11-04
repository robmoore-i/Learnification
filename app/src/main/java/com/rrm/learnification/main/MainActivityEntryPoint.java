package com.rrm.learnification.main;

import com.rrm.learnification.button.ClearTextInputOnClickCommand;
import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.AndroidNotificationFacade;
import com.rrm.learnification.publication.LearnificationPublishingService;
import com.rrm.learnification.publication.LearnificationScheduler;
import com.rrm.learnification.storage.ItemRepository;
import com.rrm.learnification.textinput.SetButtonStatusOnTextChangeListener;
import com.rrm.learnification.textlist.RemoveItemOnSwipeCommand;
import com.rrm.learnification.toolbar.FastForwardScheduleButton;
import com.rrm.learnification.toolbar.LearnificationScheduleStatusUpdate;

class MainActivityEntryPoint {
    private final AndroidNotificationFacade notificationFacade;
    private final ItemRepository<LearningItem> itemRepository;
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

        this.learningItemTextInput = new LearningItemTextInput(mainActivityView);
        this.addLearningItemButton = new AddLearningItemButton(logger, mainActivityView);
        this.learningItemList = new LearningItemList(logger, mainActivityView);

        mainActivityView.addToolbarViewUpdate(new LearnificationScheduleStatusUpdate(logger, learnificationScheduler, new FastForwardScheduleButton(logger, mainActivityView)));
    }

    void onMainActivityEntry() {
        initialiseView();

        createNotificationChannel();

        learnificationScheduler.scheduleImminentJob(LearnificationPublishingService.class);
    }

    private void initialiseView() {
        learningItemTextInput.setOnTextChangeListener(new SetButtonStatusOnTextChangeListener(addLearningItemButton));
        learningItemTextInput.setOnSubmitTextCommand(new AddLearningItemOnSubmitCommand(addLearningItemButton));
        addLearningItemButton.addOnClickHandler(new AddLearningItemOnClickCommand(learningItemTextInput, itemRepository, learningItemList));
        addLearningItemButton.addOnClickHandler(new ClearTextInputOnClickCommand(learningItemTextInput));

        learningItemList.bindTo(itemRepository);
        learningItemList.setOnSwipeCommand(new RemoveItemOnSwipeCommand(itemRepository));
    }

    private void createNotificationChannel() {
        notificationFacade.createNotificationChannel(AndroidNotificationFacade.CHANNEL_ID);
    }
}
