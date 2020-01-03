package com.rrm.learnification.textlist;

import com.rrm.learnification.storage.ItemRepository;

public class RemoveItemOnSwipeCommand implements OnSwipeCommand {
    private final ItemRepository itemRepository;

    public RemoveItemOnSwipeCommand(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public void onSwipe(EditableTextListViewAdaptor adapter, int index) {
        adapter.remove(index);
        itemRepository.removeAt(index);
    }
}
