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
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class controls the ModifyProducts.fxml scene and allows user to add a product to the Products Table.
 RUNTIME ERROR
 An error I encountered in the code was that I could not figure out how to get the get the data to be handed off from the Main
 Screen Controller or be pulled from the Main Screen. I figured it out by putting in a setData method that can be called from
 the Main Screen Controller to properly set the existing data.
 FUTURE ENHANCEMENT
 A future enhancement I could make is to have a fourth table in this scene that shows each product with a drop down that show
 the associated parts on a per product level.
 */

public class ModifyProductsController implements Initializable {

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
    private TableColumn<?, ?> newPartsPrice;

    @FXML
    private TextField minField;

    @FXML
    private TableColumn<?, ?> curPartsInvCount;

    @FXML
    private TextField nameField;

    @FXML
    private Button partsAddBtn1;

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
    private TextField maxField;

    @FXML
    private Button saveBtn;

    private ObservableList<Part> partInventory = FXCollections.observableArrayList();
    private ObservableList<Part> partInventorySearch = FXCollections.observableArrayList();

    /** This initializes this controller class */
    public ModifyProductsController() {}


    @Override
    /** This method initializes the curPartsTable and newPartsTable and sets Table cell values */
    public void initialize(URL url, ResourceBundle rb) {

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

    /** @param product Sets up fields in this controller from other controllers by passing in valid product */
    public void setData(Product product) {

        if (product instanceof Product) {

            idField.setText(String.valueOf(product.getId()));
            nameField.setText(product.getName());
            invField.setText(String.valueOf(product.getStock()));
            priceField.setText(String.valueOf(product.getPrice()));
            maxField.setText(String.valueOf(product.getMax()));
            minField.setText(String.valueOf(product.getMin()));
            newPartsTable.setItems(product.getAllAssocProducts());

        }

        }
    /** This method initializes the curPartsTable inventory. */
    private void initializeCurPartsInv(Inventory inv){

        curPartsTable.setItems(inv.getAllParts());
        curPartsTable.refresh();
    }

    @FXML
    /** This method saves the current scene and validates values. */
    void savePartButton(ActionEvent event) throws IOException {


     /*
     Increment the ID based on what's currently in the product table.
     */

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
                alert.showAndWait();
            }
            else if (stock > max || stock < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Inventory Error");
                alert.setHeaderText("Inventory must be between min and max value");
                alert.setContentText("Please correct this value and resubmit!");
                alert.showAndWait();
            }
            else {

                Product newProduct = new Product(id, name, price, stock, min, max);
                newProduct.setAllAssocProducts(newPartsTable.getItems());
                try {
                    Inventory.updateProduct(newProduct);
                }
                catch(Exception e) { /* It's fine to not update the product if its not modified*/}

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
    /** This method cancels the current scene and sends back to the main screen. */
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
    /** This method adds parts to the associated parts table. */
    void addPartButton(ActionEvent event) {
      try{
          if (curPartsTable.getSelectionModel().getSelectedItem().getId() > 0) {
              Part selectPart = curPartsTable.getSelectionModel().getSelectedItem();
              newPartsTable.getItems().add(selectPart);
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


        if (!newPartsTable.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will remove the item. Are you sure?");
            alert.setTitle("Confirm Removal");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                newPartsTable.getItems().remove(selectPart);
            }
        }
    }

    @FXML
    /** This method filters parts on key press on the parts table. */
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
