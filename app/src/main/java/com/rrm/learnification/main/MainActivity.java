package com.rrm.learnification.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rrm.learnification.learningitemseteditor.LearningItemSetEditorActivity;
import com.rrm.learnification.learningitemseteditor.LearningItemSetEditorActivityBundle;
import com.rrm.learnification.storage.LearnificationAppDatabase;
import com.rrm.learnification.storage.LearningItemSqlTableClient;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, LearningItemSetEditorActivity.class);
        String learningItemSetName = new LearningItemSqlTableClient(new LearnificationAppDatabase(this)).mostPopulousLearningItemSetName();
        intent.putExtras(new LearningItemSetEditorActivityBundle(learningItemSetName).toBundle());
        startActivity(intent);
    }
}
