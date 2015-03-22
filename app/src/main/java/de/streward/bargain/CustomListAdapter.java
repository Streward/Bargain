package de.streward.bargain;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by msteinfeld on 22.03.15.
 */
public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Drink> drinkItems;

    public CustomListAdapter(Activity activity, List<Drink> drinkItems) {
        this.activity = activity;
        this.drinkItems = drinkItems;
    }
    @Override
    public int getCount() {
        return drinkItems.size();
    }

    @Override
    public Object getItem(int location) {
        return drinkItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        TextView name = (TextView) convertView.findViewById(R.id.row_name);
        TextView price = (TextView) convertView.findViewById(R.id.row_price);

        Drink d = drinkItems.get(position);

        name.setText(d.getName());
        price.setText(String.format("%.02f", d.getPrice()) + " €");

        return convertView;
    }
}
