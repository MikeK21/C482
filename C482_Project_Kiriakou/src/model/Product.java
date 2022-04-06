package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This class controls the Product class
 RUNTIME ERROR
 An error I encountered was trying to set associated parts directly in this class. I solved this by using the Inventory class
 directly which provides public static methods which are more useful to other controllers and methods that need to interact with
 associated parts.
 FUTURE ENHANCEMENT
 A future enhancement I could make is to create a Protected Products class that has deletion protection instead of having
 the handling happen directly in the controllers it's used in.
 */
/** This method provides the attributes for a Product object. */
public class Product {

    int id;
    String name;
    double price;
    int stock;
    int min;
    int max;
    public ObservableList<Part> allAssocProducts = FXCollections.observableArrayList();



    /**
     * @param id product id
     * @param name product name
     * @param price product price
     * @param stock product stock
     * @param min product min
     * @param max product max
     * This method sets the attributes for a Product object. */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;

    }
    /**
     * @return allAssocProducts
     * This method gets associate parts of a product.
     * */
    public ObservableList<Part> getAllAssocProducts() {
        return allAssocProducts;
    }
    /** This method sets the associated parts of a product
     * @param allAssocProducts associated parts of a products
     * */
    public void setAllAssocProducts(ObservableList<Part> allAssocProducts) {
        this.allAssocProducts = allAssocProducts;
    }
    /**
     * @return id
     * This method gets the product id. */
    public int getId() {
        return id;
    }
    /** This method sets the product id.
     * @param id product id
     * */
    public void setId(int id) {
        this.id = id;
    }
    /** This method gets the product name.
     * @return name
     * */
    public String getName() {
        return name;
    }
    /** This method sets the product name.
     * @param name product name
     * */
    public void setName(String name) {
        this.name = name;
    }
    /** This method gets the product price.
     * @return price product price
     * */
    public double getPrice() {
        return price;
    }
    /** This method sets the product price.
     * @param price price
     * */
    public void setPrice(double price) {
        this.price = price;
    }
    /** This method gets the product stock.
     * @return stock
     * */
    public int getStock() {
        return stock;
    }
    /** This method sets the product stock.
     * @param stock stock
     * */
    public void setStock(int stock) {
        this.stock = stock;
    }
    /** This method gets the product minimum.
     * @return min minimum
     * */
    public int getMin() {
        return min;
    }
    /** This method sets the product minimum
     * @param min minimum
     * */
    public void setMin(int min) {
        this.min = min;
    }
    /** This method gets the product maximum.
     * @return max maximum
     * */
    public int getMax() {
        return max;
    }
    /** This method sets the product maximum.
     * @param max maximum
     * */
    public void setMax(int max) { this.max = max; }
}
