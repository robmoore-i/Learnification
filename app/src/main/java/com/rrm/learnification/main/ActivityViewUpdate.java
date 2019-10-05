package com.rrm.learnification.main;

abstract class ActivityViewUpdate {
    private final String id;

    ActivityViewUpdate(String id) {
        this.id = id;
    }

    public abstract void update(MainActivityView activityView);

    public String id() {
        return id;
    }
}
