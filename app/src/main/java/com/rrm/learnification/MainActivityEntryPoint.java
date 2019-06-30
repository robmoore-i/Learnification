package com.rrm.learnification;

import android.support.v4.app.NotificationManagerCompat;

class MainActivityEntryPoint {
    private final MainActivity activity;
    private final AndroidLogger logger;
    private final LearnificationRepository learnificationRepository;
    private final AndroidLearnificationFactory androidLearnificationFactory;
    private final LearnificationTextGenerator learnificationTextGenerator;
    private final NotificationIdGenerator notificationIdGenerator;
    private final NotificationManagerCompat notificationManager;
    private final LearnificationButton learnificationButton;
    private final MainActivityView mainActivityView;
    private final LearnificationListView learnificationListView;

    MainActivityEntryPoint(MainActivity activity) {
        this.activity = activity;
        this.logger = new AndroidLogger();
        final AndroidStorage androidStorage = new AndroidStorage(logger, activity);
        this.learnificationRepository = new PersistentLearnificationRepository(logger, new FromFileLearnificationStorage(logger, androidStorage));
        this.androidLearnificationFactory = new AndroidLearnificationFactory(logger, new AndroidNotificationFactory(this.activity));
        this.learnificationTextGenerator = new LearnificationTextGenerator(new JavaRandomiser(), learnificationRepository);
        this.notificationIdGenerator = NotificationIdGenerator.getInstance();
        this.notificationManager = NotificationManagerCompat.from(activity);
        this.mainActivityView = new AndroidMainActivityView(activity);
        this.learnificationButton = new LearnificationButton(logger, mainActivityView);
        this.learnificationListView = new LearnificationListView(logger, mainActivityView);
    }

    void onMainActivityEntry() {
        learnificationListView.populate(learnificationRepository);

        learnificationButton.setOnClickHandler(new AddLearningItemOnClickCommand(mainActivityView, learnificationRepository, learnificationListView));

        createNotificationChannel();

        publishInitialLearnification();
    }

    private void publishInitialLearnification() {
        AndroidLearnificationPublisher androidLearnificationPublisher = new AndroidLearnificationPublisher(
                androidLearnificationFactory,
                notificationIdGenerator,
                learnificationTextGenerator,
                notificationManager
        );

        androidLearnificationPublisher.createLearnification();
    }

    private void createNotificationChannel() {
        AndroidNotificationContext androidNotificationContext = new AndroidNotificationContext(this.activity.getApplicationContext());
        NotificationChannelInitialiser notificationChannelInitialiser = new NotificationChannelInitialiser(logger, androidNotificationContext);
        notificationChannelInitialiser.createNotificationChannel();
    }
}
