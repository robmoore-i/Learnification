package com.rrm.learnification;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

class MainActivityEntryPoint {
    private static final String LOG_TAG = "MainActivityEntryPoint";

    private final AppCompatActivity activity;
    private final AndroidLogger androidLogger;
    private final AndroidPackageContext androidPackageContext;
    private final LearnificationRepository learnificationRepository;
    private final AndroidLearnificationFactory androidLearnificationFactory;
    private final LearnificationTextGenerator learnificationTextGenerator;
    private final NotificationIdGenerator notificationIdGenerator;
    private final NotificationManagerCompat notificationManager;

    MainActivityEntryPoint(MainActivity activity) {
        this.activity = activity;
        this.androidLogger = new AndroidLogger();
        this.androidPackageContext = new AndroidPackageContext(this.activity.getApplicationContext());
        this.learnificationRepository = PersistentLearnificationRepository.createInstance();
        this.androidLearnificationFactory = new AndroidLearnificationFactory(this.activity, MainActivity.CHANNEL_ID, androidLogger);
        this.learnificationTextGenerator = new LearnificationTextGenerator(new JavaRandomiser(), learnificationRepository);
        this.notificationIdGenerator = NotificationIdGenerator.getInstance();
        this.notificationManager = NotificationManagerCompat.from(activity);
    }

    void onMainActivityEntry() {
        configureAddLearnificationButton();
        createNotificationChannel();
        populateLearnificationList();
        publishInitialLearnification();
    }

    private void configureAddLearnificationButton() {
        FloatingActionButton button = activity.findViewById(R.id.addLearnificationButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                androidLogger.v(LOG_TAG, "addLearnificationButton clicked");
                addLearnification();
            }
        });
    }

    private void addLearnification() {
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
        NotificationChannelInitialiser notificationChannelInitialiser = new NotificationChannelInitialiser(androidPackageContext, androidLogger);
        notificationChannelInitialiser.createNotificationChannel(MainActivity.CHANNEL_ID);
    }

    private void populateLearnificationList() {
        ListView listView = activity.findViewById(R.id.learnificationsListView);
        listView.setEnabled(true);
        ArrayAdapter adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, learnificationRepository.learningItemsAsStringList());
        listView.setAdapter(adapter);
    }
}
