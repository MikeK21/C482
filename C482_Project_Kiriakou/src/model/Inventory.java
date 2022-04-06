package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;

/** This class controls the inventory of parts and products
 RUNTIME ERROR
 An error I encountered was trying to update parts/products by simply deleting and adding the part. That method did not allow me
 to check against any fields, so I created the update methods that check against a part or product id since it is immutable.
 FUTURE ENHANCEMENT
 A future enhancement I could make is to create a hashmap of Products to associated parts.
 */
public class Inventory {

    public static ObservableList<Part> allParts = FXCollections.observableArrayList();
    public static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** This method inserts a part to part inventory
     * @param part part
     * */
    public static void insertPart(Part part){
        try {
            allParts.add(part);
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Inventory Update Error");
            alert.setHeaderText("Inventory update failed. Please check fields and reattempt");
            alert.setContentText("Please correct values and resubmit!");
            alert.showAndWait();
        }
    }
    /** This method inserts a product to product inventory
     * @param product product
     * */
    public static void insertProduct(Product product){
        try {
            allProducts.add(product);
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Inventory Update Error");
            alert.setHeaderText("Inventory update failed. Please check fields and reattempt");
            alert.setContentText("Please correct values and resubmit!");
            alert.showAndWait();
        }
    }
    /** This method deletes a part from part inventory
     * @param part part
     * */
    public static void deletePart(Part part){
        try {
            allParts.remove(part);
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Inventory Update Error");
            alert.setHeaderText("Inventory update failed. Please check fields and reattempt");
            alert.setContentText("Please correct values and resubmit!");
            alert.showAndWait();
        }
    }
    /** This method deletes a product from product inventory
     * @param product product
     * */
    public static void deleteProduct(Product product){
        try {
            allProducts.remove(product);
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Inventory Update Error");
            alert.setHeaderText("Inventory update failed. Please check fields and reattempt");
            alert.setContentText("Please correct values and resubmit!");
            alert.showAndWait();
        }
    }
    /** This method updates a part to part inventory
     * @param part part
     * */
    public static void updatePart(Part part) {
        try {
            for (Part p : Inventory.getAllParts()) {
                if (p.getId() == part.getId()) {
                    deletePart(p);
                    insertPart(part);
                }
            }
        }
        catch (Exception e){/* Update is an attempt and not mandatory */}

    }
    /** This method inserts a product to product inventory
     * @param product product
     * */
    public static void updateProduct(Product product) {
        try {
            for (Product p : Inventory.getAllProducts()) {
                if (p.getId() == product.getId()) {
                    deleteProduct(p);
                    insertProduct(product);
                }
            }
        }
        catch (Exception e){ /* Update is an attempt and not mandatory */}

    }
    /** This method gets a part from part inventory
     * @return allParts return parts
     * */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    /** This method sets a part from part inventory
     * @return allProducts return products
     * */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }


}
