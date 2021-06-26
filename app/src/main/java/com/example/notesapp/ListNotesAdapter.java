package com.example.notesapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListNotesAdapter extends RecyclerView.Adapter<ListNotesAdapter.ViewHolder> {
    private Notes data;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public ListNotesAdapter(Notes data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ListNotesAdapter.ViewHolder holder,
                                 int position) {
        holder.setData(data.getNote(position));
    }

    @Override
    public int getItemCount() {
        return data.getSize();
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.onItemClickListener = itemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener itemLongClickListener) {
        this.onItemLongClickListener = itemLongClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.note_title);
            date = itemView.findViewById(R.id.note_date);

            title.setOnClickListener(v -> {
                if (onItemClickListener != null){
                    onItemClickListener.onItemClick(v, getAdapterPosition());
                }
            });

            date.setOnClickListener(v -> {
                if (onItemClickListener != null){
                    onItemClickListener.onItemClick(v, getAdapterPosition());
                }
            });

            title.setOnLongClickListener(v -> {
                if (onItemLongClickListener != null){
                    onItemLongClickListener.onItemLongClick(v, getAdapterPosition());
                }
                return false;
            });

            date.setOnLongClickListener(v -> {
                if (onItemLongClickListener != null){
                    onItemLongClickListener.onItemLongClick(v, getAdapterPosition());
                }
                return false;
            });
        }

        public void setData(Note note) {
            title.setText(note.getName());
            date.setText(note.getDateCreate());
        }
    }
}
