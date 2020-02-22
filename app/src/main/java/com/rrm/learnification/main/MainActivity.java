package com.rrm.learnification.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rrm.learnification.learningitemseteditor.LearningItemSetEditorActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, LearningItemSetEditorActivity.class));
    }
}
