package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class controls the main_screen.fxml scene and allows user to navigate to the needed parts/products scenes,as well
 as give the ability to see both tables.
 RUNTIME ERROR
 An error I encountered in the code was that I could not get the searches to properly clear and refresh upon each search.
 When the respective tables would refresh, they would contain duplicates or not have all the original parts/products.
 I fixed this by creating a new ObservableList that was purposely empty and would serve to clear out the filtered list when
 the search bar was empty again as to make sure I am no longer filtering any items and not ensure I was restoring previous state.
 FUTURE ENHANCEMENT
 A future enhancement I would like to make is to make the search bars filter rather than be strictly search bars. This functionality
 is displayed in the Product scenes which filter on key type rather than require a search bar be hit.
 */

public class MainScreenController implements Initializable {

    Inventory inv;

    @FXML
    private TextField partSearchBox;
    @FXML
    private Label invLabel;
    @FXML
    private Button exitBtn;
    @FXML
    private TableView<Product> productsTable;
    @FXML
    private Button partsDelBtn;
    @FXML
    private TableColumn<?, ?> prodsPrice;
    @FXML
    private Button productsAddBtn;
    @FXML
    private TableColumn<?, ?> partsPrice;
    @FXML
    private TableColumn<?, ?> partsPartID;
    @FXML
    private Button productsDelBtn;
    @FXML
    private Button partsAddBtn;
    @FXML
    private TextField productsSearchBox;
    @FXML
    private Button partSearchBtn;
    @FXML
    private Button productSearchBtn;
    @FXML
    private TableColumn<?, ?> prodsInvCount;
    @FXML
    private TableColumn<?, ?> prodsProductName;
    @FXML
    private Button productsModBtn;
    @FXML
    private TableColumn<?, ?> prodsProductID;
    @FXML
    private TableColumn<?, ?> partsPartName;
    @FXML
    private Button partsModBtn;
    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableColumn<?, ?> partsInvCount;
    @FXML
    private String searchText;

    private ObservableList<Part> partInventory = FXCollections.observableArrayList();
    private ObservableList<Product> productInventory = FXCollections.observableArrayList();
    private ObservableList<Part> partInventorySearch = FXCollections.observableArrayList();
    private ObservableList<Product> productInventorySearch = FXCollections.observableArrayList();


     public MainScreenController() {}

    /*
    * Initializes the controller class
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        initializePartsInv(inv);

        partsPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInvCount.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        partsTable.setItems(Inventory.getAllParts());

        initializeProductsInv(inv);

        prodsProductID.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodsProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodsInvCount.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTable.setItems(Inventory.getAllProducts());

    }

    private void initializePartsInv(Inventory inv){

        partsTable.setItems(inv.getAllParts());
        partsTable.refresh();
    }

    private void initializeProductsInv(Inventory inv){

        productsTable.setItems(inv.getAllProducts());
        productsTable.refresh();

    }

    @FXML
    void clearText(ActionEvent event) {

    }

    @FXML
    void productSearchButton(ActionEvent event) {

        if (!productsSearchBox.getText().trim().isEmpty()) {
            for (Product p : Inventory.getAllProducts()) {
                if (p.getName().contains(productsSearchBox.getText().trim())) {
                    productInventorySearch.add(p);
                }
            }

            for (Product p : Inventory.getAllProducts()) {
                if (String.valueOf(p.getId()).contains(productsSearchBox.getText().trim())) {
                    if (!productInventorySearch.contains(p)) {
                        productInventorySearch.add(p);
                    }
                }
            }

            if (productInventorySearch.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("No Product Found!");
                alert.setHeaderText("Not Found");
                alert.setContentText("There's nothing to display!");
                alert.show();
            }

            productsTable.setItems(productInventorySearch);
            productsTable.refresh();
            ObservableList<Product> clearInventorySearch = FXCollections.observableArrayList();
            productInventorySearch = clearInventorySearch;
        }
        if (productsSearchBox.getText().trim().isEmpty()) {
            productsTable.setItems(Inventory.getAllProducts());
        }

    }

    @FXML
    void delProductButton(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the item. Are you sure?");
        alert.setTitle("Confirm Delete");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            //  if (!Inventory.getProtectAssocProducts().contains(productsTable.getSelectionModel().getSelectedItem())) {
            if (!(productsTable.getSelectionModel().getSelectedItem().allAssocProducts.size() > 0)) {
                Inventory.deleteProduct(productsTable.getSelectionModel().getSelectedItem());
            } else {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Associated Parts Error");
                alert2.setHeaderText("Can't delete a product with associated parts!");
                alert2.setContentText("Please remove associated parts before deleting product.");
                alert2.showAndWait();
            }
        }
    }

    @FXML
    void modProductButton(ActionEvent event) throws IOException {

        Parent root;
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/view/ModifyProducts.fxml"));
        root = loader.load();
        ModifyProductsController controller = loader.getController();
        if (productSelectedRow() != null){
            controller.setData(productSelectedRow());
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Product Selected!");
            alert.setHeaderText("Please select a product row!");
            alert.setContentText("There's nothing to display!");
            alert.show();
            }
        }


    @FXML
    void addProductButton(ActionEvent event) throws IOException {

        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/view/AddProducts.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();

    }

    @FXML
    void clearTextTwo(ActionEvent event) {

    }

    @FXML
    void partSearchButton(ActionEvent event) {

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

            partsTable.setItems(partInventorySearch);
            partsTable.refresh();
            ObservableList<Part> clearInventorySearch = FXCollections.observableArrayList();
            partInventorySearch = clearInventorySearch;
        }
        if (partSearchBox.getText().trim().isEmpty()) {
            partsTable.setItems(Inventory.getAllParts());
        }

    }

    @FXML
    void addPartButton(ActionEvent event) throws IOException {

        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/view/AddParts.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();

    }

    @FXML
    void modPartButton(ActionEvent event) throws IOException {

        Parent root;
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyParts.fxml"));
        root = loader.load();
        ModifyPartsController controller = loader.getController();
        if (partSelectedRow() != null){
            controller.setData(partSelectedRow());
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Product Selected!");
            alert.setHeaderText("Please select a product row!");
            alert.setContentText("There's nothing to display!");
            alert.show();
        }
    }

    @FXML
    void delPartButton(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the item. Are you sure?");
        alert.setTitle("Confirm Delete");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Inventory.deletePart(partsTable.getSelectionModel().getSelectedItem());
        }

    }

    @FXML
    void exitProgramButton(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will exit the program. Are you sure?");
        alert.setTitle("Confirm Exit");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }

    }


    public Product productSelectedRow () {

        Product handoffProduct = productsTable.getSelectionModel().getSelectedItem();
        return handoffProduct;

    }

    public Part partSelectedRow () {

        Part handoffPart = partsTable.getSelectionModel().getSelectedItem();
        return handoffPart;
    }

}
