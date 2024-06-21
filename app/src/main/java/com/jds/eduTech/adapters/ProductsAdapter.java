package com.jds.eduTech.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jds.eduTech.R;
import com.jds.eduTech.beans.TextValueUnits;

import java.util.HashMap;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {
    LayoutInflater mInflater;
    Context context;
    private List<TextValueUnits> productList;
    private HashMap<String, String> unitsMap = new HashMap<>();

    public ProductsAdapter(Context context, List<TextValueUnits> productList){
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.productList=productList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.products_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.productName.setText(productList.get(position).getText());
        holder.unitsText.setText(productList.get(position).getUnits());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public String getUnitsForProduct(String productId) {
        return unitsMap.get(productId);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        TextView unitsText;
        EditText units;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            units = itemView.findViewById(R.id.units);
            unitsText = itemView.findViewById(R.id.unitsText);

            units.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    String productId = productList.get(getAdapterPosition()).getValue();
                    String unitsEntered = s.toString();
                    unitsMap.put(productId, unitsEntered);
                }
            });
        }
    }
}
