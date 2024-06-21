package com.jds.eduTech.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jds.eduTech.R;
import com.jds.eduTech.beans.Products;

import java.util.List;

public class OrderProductsAdapter extends RecyclerView.Adapter<OrderProductsAdapter.ViewHolder> {
    LayoutInflater mInflater;
    Context context;
    private List<Products> productList;
    int orderStatus;

    public OrderProductsAdapter(Context context, List<Products> productList,int orderStatus){
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.productList=productList;
        this.orderStatus=orderStatus;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.products_order_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.productName.setText(productList.get(position).getProduct_name());
        holder.price.setText(String.valueOf(productList.get(position).getPrice()));
        holder.quantity.setText(String.valueOf(productList.get(position).getQuantity())+ " "+productList.get(position).getUnits());

        if(orderStatus == 1){
            holder.receivedQuantityLayout.setVisibility(View.VISIBLE);
            holder.receivedQuantity.setText(String.valueOf(productList.get(position).getQuantity()));
        }else {
            holder.receivedQuantityLayout.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        TextView price;
        TextView quantity;
        LinearLayout receivedQuantityLayout;
        EditText receivedQuantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.productName);
            price = itemView.findViewById(R.id.price);
            quantity = itemView.findViewById(R.id.quantity);
            receivedQuantityLayout = itemView.findViewById(R.id.receivedQuantityLayout);
            receivedQuantity = itemView.findViewById(R.id.receivedQuantity);
        }
    }
}
