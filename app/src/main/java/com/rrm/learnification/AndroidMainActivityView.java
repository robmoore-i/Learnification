package com.rrm.learnification;

import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

class AndroidMainActivityView implements MainActivityView {
    private static final String LOG_TAG = "AndroidMainActivityView";

    private final MainActivity activity;
    private final AndroidLogger logger;

    AndroidMainActivityView(AndroidLogger logger, MainActivity activity) {
        this.logger = logger;
        this.activity = activity;
    }

    @Override
    public LearnificationListViewAdaptor createLearnificationListViewDataBinding(OnSwipeCommand onSwipeCommand, LearnificationRepository learnificationRepository) {
        RecyclerView recyclerView = activity.findViewById(R.id.learnifications_list);

        // RecyclerViews must have a layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        // Provide padding between the elements of the recycler list view
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(activity, linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setEnabled(true);

        // A bit of a hack: Makes sure that the recycler view height doesn't spill over the bottom of the screen,
        //                  because if it does this, then the items below the bottom become invisible.
        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        params.height = 500;
        recyclerView.setLayoutParams(params);

        // Set up data binding between the learnificationRepository and the RecyclerView.
        LearnificationListViewAdaptor adapter = new LearnificationListViewAdaptor(learnificationRepository.learningItems().stream().map(LearningItem::asSingleString).collect(Collectors.toList()));
        recyclerView.setAdapter(adapter);

        // Set up onSwipe behaviour using the onSwipeCommand
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int listViewIndex = viewHolder.getAdapterPosition();
                logger.v(LOG_TAG, "learning-item swiped at position " + listViewIndex + " to the " + swipeDirectionToString(swipeDir));
                onSwipeCommand.onSwipe(adapter, listViewIndex);
            }

            private String swipeDirectionToString(int swipeDir) {
                if (swipeDir == ItemTouchHelper.LEFT) {
                    return "left";
                } else if (swipeDir == ItemTouchHelper.RIGHT) {
                    return "right";
                } else {
                    return "neither left nor right";
                }
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView1, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return adapter;
    }

    @Override
    public LearningItem getLearningItemTextInput() {
        String left = ((EditText) activity.findViewById(R.id.left_input)).getText().toString();
        String right = ((EditText) activity.findViewById(R.id.right_input)).getText().toString();
        return new LearningItem(left, right);
    }

    @Override
    public void setLearnificationButtonOnClickListener(OnClickCommand onClickListener) {
        Button button = activity.findViewById(R.id.add_learning_item_button);
        button.setOnClickListener(view -> {
            logger.v(LOG_TAG, "add-learning-item-button clicked");
            onClickListener.onClick();
        });
    }


    @Override
    public void setPeriodicityPickerInputRangeInMinutes(int min, int max) {
        NumberPicker periodicityPicker = activity.findViewById(R.id.periodicity_picker);
        periodicityPicker.setMinValue(min);
        periodicityPicker.setMaxValue(max);
    }

    @Override
    public void setPeriodicityPickerOnValuePickedListener(OnValuePickedCommand onValuePickedCommand) {
        NumberPicker periodicityPicker = activity.findViewById(R.id.periodicity_picker);
        periodicityPicker.setOnScrollListener((view, scrollState) -> {
            if (scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {
                final int value = view.getValue();
                onValuePickedCommand.onValuePicked(value * 60);

                // Sometimes the picker reports a value that is off-by-one. If this is the case, then this delayed
                // task will pick up the difference and write that in instead.
                int delayMs = 1000;
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        int delayedValue = periodicityPicker.getValue();
                        if (Math.abs(delayedValue - value) == 1) {
                            onValuePickedCommand.onValuePicked(delayedValue * 60);
                        }
                    }
                }, delayMs);
            }
        });
    }

    @Override
    public void setPeriodicityPickerChoiceFormatter(NumberPicker.Formatter formatter) {
        NumberPicker periodicityPicker = activity.findViewById(R.id.periodicity_picker);
        periodicityPicker.setFormatter(formatter);
        periodicityPicker.setWrapSelectorWheel(false);

        // Use a little hack to ensure that formatter applies correctly on first render.
        // See: https://stackoverflow.com/questions/17708325/android-numberpicker-with-formatter-doesnt-format-on-first-rendering
        try {
            Method method = periodicityPicker.getClass().getDeclaredMethod("changeValueByOne", boolean.class);
            method.setAccessible(true);
            method.invoke(periodicityPicker, true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setPeriodicityPickerToValue(int pickerValue) {
        NumberPicker periodicityPicker = activity.findViewById(R.id.periodicity_picker);
        periodicityPicker.setValue(pickerValue);
    }
}
