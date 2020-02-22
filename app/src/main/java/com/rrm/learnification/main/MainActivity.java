package com.rrm.learnification.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rrm.learnification.learningitemseteditor.LearningItemSetEditorActivity;
import com.rrm.learnification.test.AndroidTestObjectFactory;

public class MainActivity extends AppCompatActivity {
    private final AndroidTestObjectFactory androidTestObjectFactory = new AndroidTestObjectFactory(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, LearningItemSetEditorActivity.class));
    }

    public AndroidTestObjectFactory androidTestObjectFactory() {
        return androidTestObjectFactory;
    }
}
