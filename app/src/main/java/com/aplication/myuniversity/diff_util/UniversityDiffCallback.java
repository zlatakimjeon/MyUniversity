package com.aplication.myuniversity.diff_util;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.aplication.myuniversity.model.University;

import java.util.List;

public class UniversityDiffCallback extends DiffUtil.Callback {
    private final List<University> oldList;
    private final List<University> newList;

    public UniversityDiffCallback(List<University> oldList, List<University> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return (oldList == null) ? 0 : oldList.size();
    }

    @Override
    public int getNewListSize() {
        return (newList == null) ? 0 : newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final University oldItem = oldList.get(oldItemPosition);
        final University newItem = newList.get(newItemPosition);

        return oldItem.getTitle().equals(newItem.getTitle());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}