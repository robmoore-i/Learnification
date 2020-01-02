package com.rrm.learnification.settings;

enum LearnificationPromptStrategy {
    LEFT_TO_RIGHT,
    RIGHT_TO_LEFT,
    MIXED;

    public static LearnificationPromptStrategy fromName(String strategyName) {
        if ("left_to_right".equals(strategyName)) return LEFT_TO_RIGHT;
        if ("right_to_left".equals(strategyName)) return RIGHT_TO_LEFT;
        if ("mixed".equals(strategyName)) return MIXED;
        // Default to left -> right
        return LEFT_TO_RIGHT;
    }
}
