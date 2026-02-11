package com.bobur.quizforphysics;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReferenceAdapter extends RecyclerView.Adapter<ReferenceAdapter.VH> {

    private final List<Reference> list;

    public ReferenceAdapter(List<Reference> list) {
        this.list = list;
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView txt;

        public VH(@NonNull View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.txtReference);
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reference, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.txt.setText((position + 1) + ". " + list.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
