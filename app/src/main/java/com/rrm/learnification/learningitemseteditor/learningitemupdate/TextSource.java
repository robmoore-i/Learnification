package com.rrm.learnification.learningitemseteditor.learningitemupdate;

public interface TextSource {
    String get();

    boolean isEmpty();

    class StableTextSource implements TextSource {
        private final String s;

        public StableTextSource(String s) {
            this.s = s;
        }

        @Override
        public String get() {
            return s;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }
    }
}
