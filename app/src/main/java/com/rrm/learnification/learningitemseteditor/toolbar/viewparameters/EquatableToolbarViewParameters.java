package com.rrm.learnification.learningitemseteditor.toolbar.viewparameters;

import android.support.annotation.Nullable;

import java.util.Objects;

abstract class EquatableToolbarViewParameters implements ToolbarViewParameters {
    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof EquatableToolbarViewParameters)) return false;
        EquatableToolbarViewParameters other = (EquatableToolbarViewParameters) obj;
        return this.getName().equals(other.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
