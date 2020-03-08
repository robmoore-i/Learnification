package com.rrm.learnification.settings.learnificationpromptstrategy;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.publication.LearnificationTextGenerator;
import com.rrm.learnification.random.JavaRandomiser;
import com.rrm.learnification.random.Randomiser;
import com.rrm.learnification.storage.ItemSupplier;

public interface LearnificationPromptStrategy {
    Randomiser randomiser = new JavaRandomiser();
    LearnificationPromptStrategy MIXED = new MixedLearnificationPromptStrategy(randomiser);
    LearnificationPromptStrategy RIGHT_TO_LEFT = new RightToLeftLearnificationPromptStrategy(randomiser);
    LearnificationPromptStrategy LEFT_TO_RIGHT = new LeftToRightLearnificationPromptStrategy(randomiser);

    static LearnificationPromptStrategy fromName(String strategyName) {
        if (LEFT_TO_RIGHT.name().equals(strategyName)) return LEFT_TO_RIGHT;
        if (RIGHT_TO_LEFT.name().equals(strategyName)) return RIGHT_TO_LEFT;
        if (MIXED.name().equals(strategyName)) return MIXED;
        // Default to left -> right
        return LEFT_TO_RIGHT;
    }

    String name();

    LearnificationTextGenerator toLearnificationTextGenerator(ItemSupplier<LearningItem> itemSupplier);

    class LeftToRightLearnificationPromptStrategy implements LearnificationPromptStrategy {
        private final Randomiser randomiser;

        LeftToRightLearnificationPromptStrategy(Randomiser randomiser) {
            this.randomiser = randomiser;
        }

        @Override
        public String name() {
            return "LEFT_TO_RIGHT";
        }

        @Override
        public LearnificationTextGenerator toLearnificationTextGenerator(ItemSupplier<LearningItem> itemSupplier) {
            return () -> randomiser.randomLeftToRightLearnificationQuestion(itemSupplier.itemsOrThrowIfEmpty());
        }
    }

    class RightToLeftLearnificationPromptStrategy implements LearnificationPromptStrategy {
        private final Randomiser randomiser;

        RightToLeftLearnificationPromptStrategy(Randomiser randomiser) {
            this.randomiser = randomiser;
        }

        @Override
        public String name() {
            return "RIGHT_TO_LEFT";
        }

        @Override
        public LearnificationTextGenerator toLearnificationTextGenerator(ItemSupplier<LearningItem> itemSupplier) {
            return () -> randomiser.randomRightToLeftLearnificationQuestion(itemSupplier.itemsOrThrowIfEmpty());
        }
    }

    class MixedLearnificationPromptStrategy implements LearnificationPromptStrategy {
        private final Randomiser randomiser;

        public MixedLearnificationPromptStrategy(Randomiser randomiser) {
            this.randomiser = randomiser;
        }

        @Override
        public String name() {
            return "MIXED";
        }

        @Override
        public LearnificationTextGenerator toLearnificationTextGenerator(ItemSupplier<LearningItem> itemSupplier) {
            return () -> randomiser.randomMixedLearnificationQuestion(itemSupplier.itemsOrThrowIfEmpty());
        }
    }
}
