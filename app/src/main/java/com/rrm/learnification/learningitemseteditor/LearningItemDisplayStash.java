package com.rrm.learnification.learningitemseteditor;

interface LearningItemDisplayStash {
    void saveText(TextSource textSource, String savedText);

    String savedText();
}
