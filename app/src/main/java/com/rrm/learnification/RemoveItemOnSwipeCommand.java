package com.rrm.learnification;

class RemoveItemOnSwipeCommand implements OnSwipeCommand {
    private static final String LOG_TAG = "RemoveItemOnSwipeCommand";

    private final LearnificationRepository learnificationRepository;
    private final AndroidLogger logger;

    RemoveItemOnSwipeCommand(AndroidLogger logger, LearnificationRepository learnificationRepository) {
        this.learnificationRepository = learnificationRepository;
        this.logger = logger;
    }

    @Override
    public void onSwipe(LearnificationListViewAdaptor adapter, int index) {
        logger.v(LOG_TAG, "removing a text entry from the learnification list at index " + index);
        adapter.remove(index);
        learnificationRepository.removeAt(index);
    }
}
