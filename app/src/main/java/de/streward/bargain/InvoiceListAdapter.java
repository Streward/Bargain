package de.streward.bargain;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by msteinfeld on 22.03.15.
 */
public class InvoiceListAdapter extends BaseAdapter{

    private Activity activity;
    private LayoutInflater inflater;
    private List<Invoice> invoiceItems;

    public InvoiceListAdapter(Activity activity, List<Invoice> invoiceItems) {
        this.activity = activity;
        this.invoiceItems = invoiceItems;
    }
    @Override
    public int getCount() {
        return invoiceItems.size();
    }

    @Override
    public Object getItem(int location) {
        return invoiceItems.get(location);
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
            convertView = inflater.inflate(R.layout.list_row_invoice, null);

        TextView name = (TextView) convertView.findViewById(R.id.row_name);
        TextView price = (TextView) convertView.findViewById(R.id.row_price);

        Invoice d = invoiceItems.get(position);
        Log.d("DrinkID",String.valueOf(d.getDrinkID()).toString());
        name.setText(String.valueOf(d.getDrinkID()).toString());
        price.setText(String.format("%.02f", d.getPriceME()) + " €");

        return convertView;
    }
}
