package com.rrm.learnification.textlist;

public interface LearningItemDisplayStash {
    void saveText(TextSource textSource, String savedText);

    String savedText();
}
