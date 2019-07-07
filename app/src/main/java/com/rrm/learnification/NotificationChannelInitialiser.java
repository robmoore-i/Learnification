package com.rrm.learnification;

class NotificationChannelInitialiser {
    static final String CHANNEL_ID = "learnification";

    private final AndroidNotificationFacade notificationFacade;

    NotificationChannelInitialiser(AndroidNotificationFacade notificationFacade) {
        this.notificationFacade = notificationFacade;
    }

    void createNotificationChannel() {
        notificationFacade.createNotificationChannel(CHANNEL_ID);
    }
}
