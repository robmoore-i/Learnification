package com.rrm.learnification;

import android.support.v4.app.NotificationManagerCompat;
import android.widget.ArrayAdapter;
import android.widget.ListView;

class MainActivityEntryPoint {
    private final MainActivity activity;
    private final AndroidLogger androidLogger;
    private final LearnificationRepository learnificationRepository;
    private final AndroidLearnificationFactory androidLearnificationFactory;
    private final LearnificationTextGenerator learnificationTextGenerator;
    private final NotificationIdGenerator notificationIdGenerator;
    private final NotificationManagerCompat notificationManager;
    private final LearnificationButton learnificationButton;

    private ArrayAdapter<String> listViewAdapter;

    MainActivityEntryPoint(MainActivity activity) {
        this.activity = activity;
        this.androidLogger = new AndroidLogger();
        this.learnificationRepository = PersistentLearnificationRepository.createInstance();
        this.androidLearnificationFactory = new AndroidLearnificationFactory(new AndroidLearnificationFactoryContext(this.activity), MainActivity.CHANNEL_ID, androidLogger);
        this.learnificationTextGenerator = new LearnificationTextGenerator(new JavaRandomiser(), learnificationRepository);
        this.notificationIdGenerator = NotificationIdGenerator.getInstance();
        this.notificationManager = NotificationManagerCompat.from(activity);
        this.learnificationButton = new LearnificationButton(androidLogger, new AndroidLearnificationButtonContext(activity));
    }

    void onMainActivityEntry() {
        LearnificationListViewBinding learnificationListViewBinding = populateLearnificationList();
        learnificationButton.createOnClickViewBindingToRepository(learnificationListViewBinding);
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
        NotificationChannelInitialiser notificationChannelInitialiser = new NotificationChannelInitialiser(androidNotificationContext, androidLogger);
        notificationChannelInitialiser.createNotificationChannel(MainActivity.CHANNEL_ID);
    }

    private LearnificationListViewBinding populateLearnificationList() {
        ListView listView = activity.findViewById(R.id.learnificationsListView);
        listView.setEnabled(true);
        listViewAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, learnificationRepository.learningItemsAsStringList());
        listView.setAdapter(listViewAdapter);
        return new LearnificationListViewBinding(learnificationRepository, listViewAdapter);
    }
}
