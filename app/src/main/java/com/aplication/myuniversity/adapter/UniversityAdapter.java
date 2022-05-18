package com.aplication.myuniversity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.aplication.myuniversity.R;
import com.aplication.myuniversity.databinding.ItemUniversityBinding;
import com.aplication.myuniversity.diff_util.UniversityDiffCallback;
import com.aplication.myuniversity.model.University;
import com.aplication.myuniversity.utils.UrlUtils;

import java.util.ArrayList;
import java.util.List;

public class UniversityAdapter extends RecyclerView.Adapter<UniversityAdapter.UniversityViewHolder> {
    protected List<University> list = new ArrayList<University>();
    private final Context context;

    public UniversityAdapter(@NonNull Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public UniversityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemUniversityBinding binding =
                ItemUniversityBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new UniversityViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UniversityViewHolder holder, int position) {
        University university = list.get(position);
        holder.binding.textTitle.setText(university.getTitle()); // получаем название университета
        String textMinScores = context.getString(R.string.text_min_scores) + university.getMinimumScores();
        holder.binding.textMinScores.setText(textMinScores); // получаем минимальные баллы университета
        holder.binding.cardView.setOnClickListener(view -> {
            UrlUtils.openSite(context, university.getURL()); // открываем сайт университета
        });
    }

    @Override
    public int getItemCount() {
        int a = list.size();
        return list.size();
    }

    public void setList(List<University> list) {
        UniversityDiffCallback diffCallback = new UniversityDiffCallback(this.list, list);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.list.addAll(list);
        diffResult.dispatchUpdatesTo(this);
    }


    public class UniversityViewHolder extends RecyclerView.ViewHolder {
        public ItemUniversityBinding binding;

        public UniversityViewHolder(@NonNull ItemUniversityBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
