package de.streward.bargain;

/**
 * Created by msteinfeld on 21.03.15.
 */
public class Drink {

    int _id;
    String _name;
    Float _price;

    public Drink() {

    }

    public Drink(String name, Float price) {
        this._name = name;
        this._price = price;
    }
    public int getID(){
        return this._id;
    }

    public void setId(int id){
        this._id = id;
    }

    public String getName() {
        return this._name;
    }

    public void setName(String name){
        this._name = name;
    }

    public Float getPrice() {
        return this._price;
    }

    public void setPrice(Float price) {
        this._price = price;
    }

}
