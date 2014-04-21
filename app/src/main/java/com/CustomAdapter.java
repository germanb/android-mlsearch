package com;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mlsearch2.app.R;

public class CustomAdapter extends ArrayAdapter<Item> {

    private final Context context;
    private final ArrayList<Item> itemsArrayList;

    public CustomAdapter(Context context, ArrayList<Item> itemsArrayList) {

        super(context, R.layout.row, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.row, parent, false);

            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.label);
            holder.description =  (TextView) convertView.findViewById(R.id.value);
            holder.price = (TextView) convertView.findViewById(R.id.price);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.text.setText(itemsArrayList.get(position).getTitle());
        if(holder.description != null) {
            holder.description.setText(itemsArrayList.get(position).getDescription());
        }
        holder.price.setText(itemsArrayList.get(position).getPrice().toString());

        return convertView;
    }

    private static class ViewHolder {
        public TextView text;
        public TextView description;
        public TextView price;

    }

    public ArrayList<Item> getItemsArrayList(){
        return itemsArrayList;
    }
}