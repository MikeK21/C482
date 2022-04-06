package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

/** This class controls the AddProducts.fxml scene and allows user to add a product to the Products Table.
 RUNTIME ERROR
 An error I encountered in the code was that I could not figure out how to get the parts table to initialize for associated parts.
 I fixed this by creating an initialize method (initializeCurPartsInv) for both the curPartsTable (grabs whatever the current Parts table)
 and the newPartsTable (initializeNewPartsInv). This reaches out the Inventory class to pull current Parts and Associated Parts.
 FUTURE ENHANCEMENT
 A future enhancement I would like to make is to allow a parts be added directly here to the main Parts Inventory object. That
 way if a user wanted to add a part without having to go back to the main screen, they could do it right there and have it
 be replicated appropriately.
 */
public class AddProductsController implements Initializable {

    Inventory inv;

    @FXML
    private TextField partSearchBox;

    @FXML
    private TextField idField;

    @FXML
    private TableColumn<?, ?> curPartsPrice;

    @FXML
    private TableColumn<?, ?> newPartsPartName;

    @FXML
    private TableColumn<?, ?> curPartsPartID;

    @FXML
    private TableColumn<?, ?> curPartsPartName;

    @FXML
    private Button cancelBtn;

    @FXML
    private TableColumn<?, ?> newPartsInvCount;

    @FXML
    private TextField minField;

    @FXML
    private TableColumn<?, ?> curPartsInvCount;

    @FXML
    private TextField nameField;

    @FXML
    private Button partsAddBtn1;

    @FXML
    private TableColumn<?, ?> newPartsPrice;

    @FXML
    private Button partsAddBtn;

    @FXML
    private TableColumn<?, ?> newPartsPartID;

    @FXML
    private TextField priceField;

    @FXML
    private TableView<Part> curPartsTable;

    @FXML
    private TableView<Part> newPartsTable;

    @FXML
    private TextField invField;

    @FXML
    private TextField machIdField;

    @FXML
    private TextField maxField;

    @FXML
    private Button saveBtn;

    private ObservableList<Part> partInventory = FXCollections.observableArrayList();
    private ObservableList<Part> partInventorySearch = FXCollections.observableArrayList();

    /** This method initializes the Controller class. */
    public AddProductsController() {}

    @Override
    /** This method initializes the table creation calls and variables. */
    public void initialize(URL url, ResourceBundle resourceBundle) {


        initializeCurPartsInv(inv);

        curPartsPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        curPartsPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        curPartsInvCount.setCellValueFactory(new PropertyValueFactory<>("stock"));
        curPartsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        newPartsPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        newPartsPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        newPartsInvCount.setCellValueFactory(new PropertyValueFactory<>("stock"));
        newPartsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        curPartsTable.setItems(Inventory.getAllParts());

    }

    /** This method initializes the curPartsTable which are pre-existing parts. */
    private void initializeCurPartsInv(Inventory inv){

        curPartsTable.setItems(inv.getAllParts());
        curPartsTable.refresh();
    }

    /** This method initializes the newPartsTable which represents associated parts. */
    private void initializeNewPartsInv(Inventory inv){

        newPartsTable.setItems(inv.getAllParts());
        newPartsTable.refresh();

    }

    @FXML
    /** This method saves the Product and concludes any action on respective parts tables */
    void saveButton(ActionEvent event) throws IOException {

     /*
     Increment the ID based on what's currently in the product table.
     */
        int presetIdNext = 0;
        for (Product p : Inventory.getAllProducts()){
            while (presetIdNext <= p.getId()) {
                presetIdNext++;
            }
            idField.setText(String.valueOf(presetIdNext));
        }

        String idString = idField.getText();
        String priceString = priceField.getText();
        String stockString = invField.getText();
        String minString = minField.getText();
        String maxString = maxField.getText();

        try {
            int id = Integer.parseInt(idString);
            String name = nameField.getText();
            double price = Double.parseDouble(priceString);
            int stock = Integer.parseInt(stockString);
            int min = Integer.parseInt(minString);
            int max = Integer.parseInt(maxString);

            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Min Max Error");
                alert.setHeaderText("Min cannot be more than the Max!");
                alert.setContentText("Please correct this value and resubmit!");
                alert.show();
            }
            else if (stock > max || stock < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Inventory Error");
                alert.setHeaderText("Inventory must be between min and max value");
                alert.setContentText("Please correct this value and resubmit!");
                alert.show();
            }
            else {
                Product newProduct = new Product(id, name, price, stock, min, max);
                Inventory.insertProduct(newProduct);
                newProduct.setAllAssocProducts(newPartsTable.getItems());

                Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                Object scene = FXMLLoader.load(getClass().getResource("/view/main_screen.fxml"));
                stage.setScene(new Scene((Parent) scene));
                stage.show();
            }

        }
        catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Field Error");
            alert.setHeaderText("Please put in a number for numeric fields, and text for text fields");
            alert.setContentText("Please check values and resubmit!");
            alert.showAndWait();


        }

    }

    @FXML
    /** This method cancels the current screen and sends back to main screen. */
    void cancelPartButton(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will exit the screen. Are you sure?");
        alert.setTitle("Confirm Exit");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/view/main_screen.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }

    @FXML
    /** This adds parts to the associated parts table */
    void addPartButton(ActionEvent event) {

        try {
            Part selectPart = curPartsTable.getSelectionModel().getSelectedItem();
            if (curPartsTable.getSelectionModel().getSelectedItem().getId() > 0) {
                partInventory.add(selectPart);
                newPartsTable.setItems(partInventory);
                newPartsTable.refresh();
            }
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a part to add.");
            alert.setTitle("Part could not be added.");
            alert.showAndWait();
        }
    }

    @FXML
    /** This method removes parts from the associated parts table. */
    void removeAssociatedPartBtn(ActionEvent event) {

        Part selectPart = newPartsTable.getSelectionModel().getSelectedItem();
        partInventory.remove(selectPart);
        newPartsTable.setItems(partInventory);
        newPartsTable.refresh();
    }

    @FXML
    /** This method provides functionality for the search bar that filters text on keytype for parts tables */
    void filterParts(KeyEvent event) {

        if (!partSearchBox.getText().trim().isEmpty()) {
            for (Part p : Inventory.getAllParts()) {
                if (p.getName().contains(partSearchBox.getText().trim())) {
                    partInventorySearch.add(p);
                }
            }

            for (Part p : Inventory.getAllParts()) {
                if (String.valueOf(p.getId()).contains(partSearchBox.getText().trim())) {
                    if (!partInventorySearch.contains(p)) {
                        partInventorySearch.add(p);
                    }
                }
            }


            if (partInventorySearch.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("No Part Found!");
                alert.setHeaderText("Not Found");
                alert.setContentText("There's nothing to display!");
                alert.show();
            }

            curPartsTable.setItems(partInventorySearch);
            curPartsTable.refresh();
            ObservableList<Part> clearInventorySearch = FXCollections.observableArrayList();
            partInventorySearch = clearInventorySearch;
        }
        if (partSearchBox.getText().trim().isEmpty()) {
            curPartsTable.setItems(Inventory.getAllParts());
        }

    }


}
