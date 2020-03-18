package com.rrm.learnification.textlist;

public interface LearningItemDisplayStash {
    LearningItemDisplayStash noSave = new LearningItemDisplayStash() {
        private TextSource textSource;

        @Override
        public void saveText(TextSource textSource, String savedText) {
            this.textSource = textSource;
        }

        @Override
        public String savedText() {
            return textSource.get();
        }
    };

    void saveText(TextSource textSource, String savedText);

    String savedText();
}
