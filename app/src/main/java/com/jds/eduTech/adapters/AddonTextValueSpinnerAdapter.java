package com.jds.eduTech.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.jds.eduTech.R;
import com.jds.eduTech.beans.TextValue;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AddonTextValueSpinnerAdapter extends ArrayAdapter<TextValue> {

  private LayoutInflater layoutinflater;
  List<TextValue> items;

  public AddonTextValueSpinnerAdapter(Context context, List<TextValue> items) {
    super(context, 0, items);
    layoutinflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    this.items = items;
  }

  @NotNull
  @Override
  public View getView(int position, View convertView, @NotNull ViewGroup parent) {
    if(convertView == null) {
      convertView = layoutinflater.inflate(R.layout.spinner_store_type, parent, false);
    }
    TextView text = convertView.findViewById(R.id.text);
    TextValue item = getItem(position);
    if(item != null) {
      text.setText(item.getText());
    }
    return convertView;
  }

  @Override
  public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
    return getView(position, convertView, parent);
  }

  public List<TextValue> getItems() {
    return items;
  }

  public void setItems(List<TextValue> items) {
    this.items = items;
  }
}
