package com.jds.eduTech.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jds.eduTech.R;
import com.jds.eduTech.beans.Orders;

import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {
    LayoutInflater mInflater;
    Context context;
    private List<Orders> ordersList;
    private OnFullDetailsClickListener fullDetailsClickListener;

    public OrdersAdapter(Context context, List<Orders> ordersList){
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.ordersList=ordersList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.orders_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Orders order = ordersList.get(position);
        holder.orderId.setText(String.valueOf(order.getOrderId()));
        holder.orderNum.setText(order.getOrder_num());
        holder.schoolName.setText(order.getSchool_name());
        holder.headMasterName.setText(order.getHm_name());
        holder.supplierNumber.setText(order.getSupplierNumber());
        holder.supplierName.setText(order.getSupplierName());
        holder.headMasterContact.setText(order.getHm_contact_num());

        if(order.getInvoice_status() == 0){
            holder.status.setText("Pending");
        }else if(order.getInvoice_status() == 1){
            holder.status.setText("Completed");
        }else if(order.getInvoice_status() == 2){
            holder.status.setText("Acknowledged");
        }else if(order.getInvoice_status() == 3){
            holder.status.setText("Rejected");
        }

    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public interface OnFullDetailsClickListener {
        void onFullDetailsClick(int position);
    }

    private OnFullDetailsClickListener onFullDetailsClickListener;

    public void setOnFullDetailsClickListener(OnFullDetailsClickListener listener) {
        this.onFullDetailsClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderId;
        TextView orderNum;
        TextView schoolName;
        TextView headMasterName;
        TextView supplierName;
        TextView supplierNumber;
        TextView headMasterContact;
        TextView fullDetails;
        TextView status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.orderId);
            orderNum = itemView.findViewById(R.id.orderNum);
            schoolName = itemView.findViewById(R.id.schoolName);
            headMasterName = itemView.findViewById(R.id.headMasterName);
            supplierName = itemView.findViewById(R.id.supplierName);
            supplierNumber = itemView.findViewById(R.id.supplierNumber);
            headMasterContact = itemView.findViewById(R.id.headMasterContact);
            fullDetails = itemView.findViewById(R.id.fullDetails);
            status = itemView.findViewById(R.id.status);
            fullDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Pass the clicked order position to the onFullDetailsClick method
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onFullDetailsClickListener.onFullDetailsClick(position);
                    }
                }
            });
        }
    }
}
