package com.rrm.learnification.common;

class LearnificationText {
    final String given;
    final String expected;
    final String subHeading;

    LearnificationText(String given, String expected, String subHeading) {
        this.expected = expected;
        this.given = given;
        this.subHeading = subHeading;
    }
}
