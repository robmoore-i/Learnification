package com.rrm.learnification.notification;

class AndroidNotification {
    private final String packageName;
    private final String notificationType;

    AndroidNotification(String packageName, String notificationType) {
        this.packageName = packageName;
        this.notificationType = notificationType;
    }

    public boolean isLearnification() {
        return "com.rrm.learnification".equals(packageName) && NotificationType.LEARNIFICATION.equals(notificationType);
    }
}
