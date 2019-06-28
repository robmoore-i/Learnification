package com.rrm.learnification;

class AddLearningItemOnClickCommand implements OnClickCommand {
    private final LearnificationRepository learnificationRepository;
    private final LearnificationList learnificationList;
    private MainActivityView mainActivityView;

    AddLearningItemOnClickCommand(MainActivityView mainActivityView, LearnificationRepository learnificationRepository, LearnificationList learnificationList) {
        this.mainActivityView = mainActivityView;
        this.learnificationRepository = learnificationRepository;
        this.learnificationList = learnificationList;
    }

    @Override
    public void onClick() {
        LearningItem learningItem = mainActivityView.getTextInput();
        learnificationRepository.add(learningItem);
        learnificationList.addTextEntry(learningItem.asSingleString());
    }
}
