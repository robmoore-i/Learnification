package com.rrm.learnification.button;

/**
 * These objects are logical in that they are not necessarily physical all the time.
 * They can be attached or detached from real buttons that are present on the screen,
 * meaning that practically speaking, they can be associated with any button from the
 * XML definition of the UI.
 * <p>
 * This property also allows two logical buttons to occupy the same physical button,
 * provided that only one is attached at any one time. This is the property that the
 * logical button was created for.
 */
public interface LogicalButton {
    void attach();

    void detach();
}
