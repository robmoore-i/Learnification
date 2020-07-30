package com.rrm.learnification.button;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.View;

public class ButtonColour {
    public static final ButtonColour READY_TO_BE_CLICKED = new ButtonColour(Color.parseColor("#32CD32"));
    public static final ButtonColour GRAYED_OUT = new ButtonColour(Color.GRAY);
    public static final ButtonColour FINGER_DOWN = new ButtonColour(Color.GREEN);

    private final int rawColour;

    public ButtonColour(int rawColour) {
        this.rawColour = rawColour;
    }

    public void applyTo(View button) {
        button.getBackground().setColorFilter(rawColour, PorterDuff.Mode.MULTIPLY);
        button.setTag(rawColour);
    }

    public int intValue() {
        return rawColour;
    }
}
