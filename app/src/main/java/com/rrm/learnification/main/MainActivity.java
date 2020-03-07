package com.rrm.learnification.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rrm.learnification.learningitemseteditor.LearningItemSetEditorActivity;
import com.rrm.learnification.logger.AndroidLogger;

public class MainActivity extends AppCompatActivity {
    private final AndroidLogger logger = new AndroidLogger();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        LearnificationAppDatabase learnificationAppDatabase = new LearnificationAppDatabase(this);
//        LearningItemSqlTableInterface learningItemSqlTableInterface = new LearningItemSqlTableInterface();

        startActivity(new Intent(this, LearningItemSetEditorActivity.class));
    }
}
