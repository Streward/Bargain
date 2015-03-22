package de.streward.bargain;

import android.content.Context;

/**
 * Created by msteinfeld on 22.03.15.
 */
public class Invoice {

    int _id;
    int _drink_id;
    int _qty;
    float _price_me;
    float _price_row;

    private Context mContext;

    public Invoice() {

    }

    public Invoice(Context context){
        mContext = context;

    }
    //generatorclasses for non existent Instance Attributes
    public float generatePriceME(int drink_id){
        BargainDatabaseHelper db = new BargainDatabaseHelper(mContext);
        return db.getDrinkPrice(drink_id);

    }

    public float generatePriceRow(float price_me, int qty){
        return qty * price_me;

    }

    //Constructors
    public Invoice(int drink_id, int qty){
        this._drink_id = drink_id;
        this._qty = qty;
        this._price_me = generatePriceME(drink_id);
        this._price_row = generatePriceRow(this._price_me, qty);
    }

    public Invoice(int drink_id, int qty, float price_me){
        this._drink_id = drink_id;
        this._qty = qty;
        this._price_me = price_me;
        this._price_row = generatePriceRow(this._price_me, qty);
    }

    //getter and Setter
    public void setID(int id) {
        this._id = id;
    }
    public int getID() {
        return this._id;
    }

    public void setDrinkID(int drink_id) {
        this._drink_id = drink_id;
    }
    public int getDrinkID() {
        return this._drink_id;
    }

    public void setQty(int qty) {
        this._qty = qty;
    }

    public void setPriceME(float price_me) {
        this._price_me = price_me;
    }
    public float getPriceME(){
        return this._price_me;
    }

    public void setPriceRow(float price_row) {
        this._price_row = price_row;
    }
    public float getPriceRow(){
        return this._price_row;
    }

}
