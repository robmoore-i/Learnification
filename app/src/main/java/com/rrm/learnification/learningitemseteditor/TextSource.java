package com.rrm.learnification.learningitemseteditor;

interface TextSource {
    String get();

    boolean isEmpty();

    class StableTextSource implements TextSource {
        private final String s;

        StableTextSource(String s) {
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
