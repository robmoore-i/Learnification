package com.rrm.learnification.main;

import com.rrm.learnification.common.AndroidLogger;
import com.rrm.learnification.common.AppToolbar;
import com.rrm.learnification.common.ClearTextInputOnClickCommand;
import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.common.RemoveItemOnSwipeCommand;
import com.rrm.learnification.common.SetButtonStatusOnTextChangeListener;
import com.rrm.learnification.learnification.LearnificationPublisher;
import com.rrm.learnification.learnification.LearnificationTextGenerator;
import com.rrm.learnification.notification.AndroidNotificationFacade;
import com.rrm.learnification.random.Randomiser;
import com.rrm.learnification.storage.ItemRepository;

class MainActivityEntryPoint {
    private final AndroidNotificationFacade notificationFacade;
    private final LearnificationPublisher learnificationPublisher;
    private final ItemRepository<LearningItem> itemRepository;
    private final AppToolbar appToolbar;
    private final LearningItemTextInput learningItemTextInput;
    private final AddLearningItemButton addLearningItemButton;
    private final LearningItemList learningItemList;

    MainActivityEntryPoint(
            AndroidLogger logger,
            MainActivityView mainActivityView,
            AndroidNotificationFacade notificationFacade,
            Randomiser randomiser,
            ItemRepository<LearningItem> itemRepository
    ) {
        this.itemRepository = itemRepository;
        this.notificationFacade = notificationFacade;

        appToolbar = new AppToolbar(logger, mainActivityView);
        learningItemTextInput = new LearningItemTextInput(mainActivityView);
        addLearningItemButton = new AddLearningItemButton(logger, mainActivityView);
        learningItemList = new LearningItemList(logger, mainActivityView);

        this.learnificationPublisher = new LearnificationPublisher(
                logger,
                new LearnificationTextGenerator(randomiser, itemRepository),
                notificationFacade
        );
    }

    void onMainActivityEntry() {
        initialiseView();

        createNotificationChannel();

        learnificationPublisher.publishLearnification();
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
