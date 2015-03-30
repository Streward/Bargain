package de.streward.bargain;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gestureDetector = new GestureDetector(
                new SwipeGestureDetector());


        ListView recent_list_view = (ListView) findViewById(R.id.recent_list_view);
        BargainDatabaseHelper db = new BargainDatabaseHelper(this);
        List<Drink> allDrinks = db.getAllDrinks();
        /*ArrayList<String> items = new ArrayList<String>();
        Log.d("Items","Populate List");
        for (Drink drink : allDrinks){
            items.add(drink.getName().toString());
            Log.d("Item", "Add Item");
        }*/

        TextView summary = (TextView) findViewById(R.id.price_summary);
        summary.setText(db.getSummary() + " €");

        //ListAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items);
        CustomListAdapter adapter = new CustomListAdapter(this, allDrinks);
        recent_list_view.setAdapter(adapter);

        recent_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView product_id = (TextView) view.findViewById(R.id.row_product_id);
                TextView listText = (TextView) view.findViewById(R.id.row_name);
                String item = listText.getText().toString();
                TextView listText2 = (TextView) view.findViewById(R.id.row_price);
                String item2 = listText2.getText().toString();
                String text = item + " - " + item2;
                Toast.makeText(getBaseContext(), text, Toast.LENGTH_LONG).show();
                BargainDatabaseHelper db = new BargainDatabaseHelper(getBaseContext());
                Invoice invoice = new Invoice();
                String drink_id = product_id.getText().toString();
                Log.d("SETDRINK",drink_id);
                invoice.setDrinkID(Integer.parseInt(drink_id));
                invoice.setPriceME(db.getDrinkPrice(Integer.parseInt(drink_id)));
                db.createInvoice(invoice);
                TextView summary = (TextView) findViewById(R.id.price_summary);
                String db_summary = db.getSummary();
                Log.d("SUMMARY", db_summary);
                summary.setText(db_summary + " €");
                db.close();

            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gestureDetector.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    private void onLeftSwipe() {
        Intent intent;
        intent = new Intent(this, InvoiceActivity.class);
        startActivity(intent);
    }

    private void onRightSwipe() {
        Intent intent;
        intent = new Intent(this, NewTransaction.class);
        startActivity(intent);
    }


    // Private class for gestures
    private class SwipeGestureDetector
            extends GestureDetector.SimpleOnGestureListener {
    // Swipe properties, you can change it to make the swipe
    // longer or shorter and speed
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 200;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2,
                           float velocityX, float velocityY) {
        try {
            float diffAbs = Math.abs(e1.getY() - e2.getY());
            float diff = e1.getX() - e2.getX();

            if (diffAbs > SWIPE_MAX_OFF_PATH)
                return false;

            // Left swipe
            if (diff > SWIPE_MIN_DISTANCE
                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                MainActivity.this.onLeftSwipe();

                // Right swipe
            } else if (-diff > SWIPE_MIN_DISTANCE
                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                MainActivity.this.onRightSwipe();
            }
        } catch (Exception e) {
            Log.e("YourActivity", "Error on gestures");
        }
        return false;
    }

}




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent;
            intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        if (id == R.id.action_new) {
            Intent intent;
            intent = new Intent(this, NewTransaction.class);
            startActivity(intent);
        }

        if (id == R.id.action_pay) {
            Intent intent;
            intent = new Intent(this, InvoiceActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void newTransaction(View view){
        Intent intent;
        intent = new Intent(this, NewTransaction.class);
        startActivity(intent);
    }
}
