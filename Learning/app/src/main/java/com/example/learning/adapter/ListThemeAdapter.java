package com.example.learning.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learning.R;
import com.example.learning.model.Theme;

import java.util.List;

public class ListThemeAdapter extends RecyclerView.Adapter<ListThemeAdapter.ViewHolder> {

    private List<Theme> themes;
    private LayoutInflater inflater;
    private Context context;

    private OnThemeListener onThemeListener;

    public ListThemeAdapter(Context context, List<Theme> themes, OnThemeListener onThemeListener) {
        this.themes = themes;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.onThemeListener = onThemeListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_card_theme, parent, false);
        return new ViewHolder(view, onThemeListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Resources resources = context.getResources();
        String imageName = themes.get(position).getImage().split("\\.")[0];
        Log.d("Image", imageName);

        final int resourceId = resources.getIdentifier(imageName, "drawable", context.getPackageName());

        holder.imageTheme.setImageResource(resourceId);
        holder.textTheme.setText(themes.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return themes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageTheme;
        TextView textTheme;

        OnThemeListener onThemeListener;

        public ViewHolder(@NonNull View itemView, OnThemeListener onThemeListener) {
            super(itemView);
            imageTheme = itemView.findViewById(R.id.imageTheme);
            textTheme = itemView.findViewById(R.id.textTheme);
            this.onThemeListener = onThemeListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onThemeListener.onThemeClick(getAbsoluteAdapterPosition());
        }
    }

    public interface OnThemeListener {
        void onThemeClick(int position);
    }

}
