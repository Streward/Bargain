package de.streward.bargain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by msteinfeld on 21.03.15.
 */
public class BargainDatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Bargain.db";

    //Table and Columns for Drinks
    public static final String TABLE_DRINKS = "drinks";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PRICE = "price";

    //Table and Columns for Invoice
    public static final String TABLE_INVOICE = "invoice";
    public static final String KEY_PRODUCT = "product_id";
    public static final String KEY_PRICE_ME = "price_me";
    public static final String KEY_PRICE_ROW = "price_row";



    public BargainDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DRINKS_TABLE = "CREATE TABLE " + TABLE_DRINKS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
                + KEY_PRICE + " DECIMAL" + ")";
        db.execSQL(CREATE_DRINKS_TABLE);
        String CREATE_INVOICE_TABLE = "CREATE TABLE " + TABLE_INVOICE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_PRODUCT + " INTEGER, "
                + KEY_PRICE_ME + " DECIMAL, " + KEY_PRICE_ROW + "DECIMAL, FOREIGN KEY (" + KEY_PRODUCT + ") REFERENCES " + TABLE_DRINKS + " (" + KEY_ID + "))";
        db.execSQL(CREATE_INVOICE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DRINKS);
        onCreate(db);
    }

    public void createDrink(Drink drink) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues drinkValues = new ContentValues();
        drinkValues.put(KEY_NAME, drink.getName());
        drinkValues.put(KEY_PRICE, drink.getPrice());
        long drinkId;
        drinkId = db.insert(TABLE_DRINKS, null, drinkValues);
    }

    public int getDrink(int id) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor;
        cursor = db.query(TABLE_DRINKS, new String[] { KEY_ID, KEY_NAME, KEY_PRICE},
                KEY_ID + "=?", new String[] {String.valueOf(id) }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        //Drink drink;
        //Drink drink = new Drink(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
        return 0;
    }
    public List<Drink> getAllDrinks() {
        List<Drink> drinkList = new ArrayList<Drink>();
        String selectQuery = "SELECT * FROM " + TABLE_DRINKS;

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d("Select", "Selecting all Drinks");
        if (cursor.moveToFirst()) {
            do {
                Drink drink = new Drink();
                Log.d("Select", cursor.getString(0));
                drink.setId(Integer.parseInt(cursor.getString(0)));
                drink.setName(cursor.getString(1));
                drink.setPrice(Float.parseFloat(cursor.getString(2)));
                drinkList.add(drink);
            }while (cursor.moveToNext());
        }
        return drinkList;
    }
    public int getDrinksCount() { return 0;
    }
    public int updateDrink() { return 0;}
    public void deleteDrink() { }
    public float getDrinkPrice(int drink_id){
        float price = 0;
        String selectQuery = "SELECT " + KEY_PRICE + " FROM " + TABLE_DRINKS + " WHERE " + KEY_ID + " = " + drink_id;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst())
            price = Float.parseFloat(cursor.getString(0));

        return price;

    }

    public List<Invoice> getAllLines() {
        List<Invoice> invoiceList = new ArrayList<Invoice>();
        String selectQuery = "SELECT * FROM " + TABLE_INVOICE;

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d("Select", "Selecting all Invoice Lines");
        if (cursor.moveToFirst()) {
            do {
                Invoice invoice = new Invoice();
                Log.d("Select", cursor.getString(0));
                invoice.setID(Integer.parseInt(cursor.getString(0)));
                invoice.setDrinkID(Integer.parseInt(cursor.getString(1)));
                invoice.setQty(Integer.parseInt(cursor.getString(2)));
                invoice.setPriceME(Float.parseFloat(cursor.getString(2)));
                invoice.setPriceRow(Float.parseFloat(cursor.getString(2)));
                invoiceList.add(invoice);
            }while (cursor.moveToNext());
        }
        return invoiceList;
    }

}
