package com.rrm.learnification;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    static final String CHANNEL_ID = "learnification";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        AndroidPackageContext androidPackageContext = new AndroidPackageContext(this.getApplicationContext());
        AndroidLogger androidLogger = new AndroidLogger();

        NotificationChannelInitialiser notificationChannelInitialiser = new NotificationChannelInitialiser(androidPackageContext, androidLogger);
        notificationChannelInitialiser.createNotificationChannel(MainActivity.CHANNEL_ID);

        LearnificationRepository learnificationRepository = PersistentLearnificationRepository.createInstance();

        displayLearningItems(learnificationRepository);

        AndroidLearnificationFactory androidLearnificationFactory = new AndroidLearnificationFactory(this, MainActivity.CHANNEL_ID, androidLogger);
        LearnificationTextGenerator learnificationTextGenerator = new LearnificationTextGenerator(new JavaRandomiser(), learnificationRepository);
        AndroidLearnificationPublisher androidLearnificationPublisher = new AndroidLearnificationPublisher(androidLearnificationFactory, NotificationIdGenerator.getInstance(), learnificationTextGenerator, NotificationManagerCompat.from(this));
        androidLearnificationPublisher.createLearnification();
    }

    private void displayLearningItems(LearnificationRepository learnificationRepository) {
        ListView listView = findViewById(R.id.learnificationsListView);
        listView.setEnabled(true);
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, learnificationRepository.learningItemsAsStringList());
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
