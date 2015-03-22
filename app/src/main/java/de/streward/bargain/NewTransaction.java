package de.streward.bargain;

import android.content.Intent;
import android.database.DatabaseErrorHandler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;


public class NewTransaction extends ActionBarActivity {

    AutoCompleteTextView autocompletetextview;

    String[] arr = {"Cuba Libre", "Bier", "Jägermeister"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_transaction);

        autocompletetextview = (AutoCompleteTextView)
                findViewById(R.id.autocomplete_drinks);

        ArrayAdapter adapter = new ArrayAdapter
        (this, android.R.layout.select_dialog_item, arr);

        autocompletetextview.setThreshold(1);
        autocompletetextview.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void createDrink(View view){
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autocomplete_drinks);
        String name = autoCompleteTextView.getText().toString();
        EditText editText = (EditText) findViewById(R.id.input_price);
        Float price = Float.parseFloat(editText.getText().toString());

        BargainDatabaseHelper db = new BargainDatabaseHelper(this);

        db.createDrink(new Drink(name, price));
        Log.d("Insert", "Inserting...");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
