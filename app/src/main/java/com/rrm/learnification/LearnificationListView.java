package com.rrm.learnification;

class LearnificationListView {
    private static final String LOG_TAG = "LearnificationListView";

    private AndroidLogger logger;
    private MainActivityView mainActivityView;
    private LearnificationListViewAdaptor adapter;
    private RemoveItemOnSwipeCommand onSwipeCommand;

    LearnificationListView(AndroidLogger logger, MainActivityView mainActivityView) {
        this.logger = logger;
        this.mainActivityView = mainActivityView;
    }

    void populate(LearnificationRepository learnificationRepository) {
        logger.v(LOG_TAG, "populating learnification list");
        onSwipeCommand = new RemoveItemOnSwipeCommand(logger, learnificationRepository);
        adapter = mainActivityView.createLearnificationListDataBinding(onSwipeCommand, learnificationRepository);
    }

    void swipeOnItem(int index) {
        onSwipeCommand.onSwipe(adapter, index);
    }

    void addTextEntry(String textEntry) {
        logger.v(LOG_TAG, "adding a text entry to the learnification list '" + textEntry + "'");
        adapter.add(textEntry);
    }
}
