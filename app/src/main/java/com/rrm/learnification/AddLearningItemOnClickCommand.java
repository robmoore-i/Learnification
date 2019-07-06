package com.rrm.learnification;

class AddLearningItemOnClickCommand implements OnClickCommand {
    private final LearnificationRepository learnificationRepository;
    private final LearnificationListView learnificationListView;
    private MainActivityView mainActivityView;

    AddLearningItemOnClickCommand(MainActivityView mainActivityView, LearnificationRepository learnificationRepository, LearnificationListView learnificationListView) {
        this.mainActivityView = mainActivityView;
        this.learnificationRepository = learnificationRepository;
        this.learnificationListView = learnificationListView;
    }

    @Override
    public void onClick() {
        LearningItem learningItem = mainActivityView.getLearningItemTextInput();
        learnificationRepository.add(learningItem);
        learnificationListView.addTextEntry(learningItem.asSingleString());
    }
}
