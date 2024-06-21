package com.jds.eduTech.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jds.eduTech.R;
import com.jds.eduTech.beans.TextValue;

import java.util.ArrayList;
import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    LayoutInflater mInflater;
    Context context;
    private List<TextValue> categoriesList;
    private static ItemClickListener mClickListener;

    public CategoriesAdapter(Context context, List<TextValue> categoriesList){
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.categoriesList=categoriesList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.categories_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.categoryName.setText(categoriesList.get(position).getText());
        holder.categoryCode = categoriesList.get(position).getValue();

        holder.proceed.setOnClickListener(v->{
            notifyDataSetChanged();
            mClickListener.onItemClick(position,v, holder.categoryCode,categoriesList.get(position).getText());
        });
    }

    public void filterList(ArrayList<TextValue> filteredList){
        categoriesList = filteredList;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(int position, View v,String categoryCode,String categoryName);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView proceed;
        String categoryCode;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            proceed = itemView.findViewById(R.id.proceed);
        }
    }
}
