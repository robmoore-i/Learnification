package com.rrm.learnification.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rrm.learnification.learningitemseteditor.LearningItemSetEditorActivity;
import com.rrm.learnification.learningitemseteditor.LearningItemSetEditorActivityBundle;
import com.rrm.learnification.learningitemstorage.LearningItemSqlTableClient;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.sqlitedatabase.LearnificationAppDatabase;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "MainActivity";
    private final AndroidLogger logger = new AndroidLogger();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, LearningItemSetEditorActivity.class);
        LearningItemSqlTableClient learningItemSqlTableClient = new LearningItemSqlTableClient(logger, new LearnificationAppDatabase(this));
        String learningItemSetName = learningItemSqlTableClient.mostPopulousLearningItemSetName();
        logger.i(LOG_TAG, "initial learning items are " + learningItemSqlTableClient.items().toString());
        logger.i(LOG_TAG, "initial learning item set name is being set to " + learningItemSetName);
        intent.putExtras(new LearningItemSetEditorActivityBundle(learningItemSetName).toBundle());
        startActivity(intent);
    }
}
