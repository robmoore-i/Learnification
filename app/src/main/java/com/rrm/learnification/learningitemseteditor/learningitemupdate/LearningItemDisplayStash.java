package com.rrm.learnification.learningitemseteditor.learningitemupdate;

interface LearningItemDisplayStash {
    void saveText(TextSource textSource, String savedText);

    String savedText();
}
