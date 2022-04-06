package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.util.Optional;

/** This class controls the ModifyParts.fxml scene and allows user to navigate to the modify parts scene which imports
 the selected product into the configurable fields.
 RUNTIME ERROR
An error I occurred here was in properly setting the part inventory for comparison against the min and max values in the
input verification alerts. I had set the comparison to be between Inventory.getAllParts().size() instead of the part's inventory itself
and was getting unexpected values back for that comparison. I fixed it by having the comparison evaluate against the "stock" field.
 FUTURE ENHANCEMENT
 A future enhancement I would like to make is to make the input verification appear on each field as it is typed rather than
 on submission. This way the user's get faster and more specific feedback.
 */
public class ModifyPartsController {

    @FXML
    private RadioButton inhouseRadioBtn;

    @FXML
    private TextField idField;

    @FXML
    private TextField priceField;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField minField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField invField;

    @FXML
    private TextField machIdField;

    @FXML
    private TextField maxField;

    @FXML
    private RadioButton outsourcedRadioBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private Label machNameLabel;


    /* Set up fields in this controller from other controllers by passing in valid product */
    public void setData(Part part) {

        if (part instanceof InHouse) {

            idField.setText(String.valueOf(part.getId()));
            nameField.setText(part.getName());
            invField.setText(String.valueOf(part.getStock()));
            priceField.setText(String.valueOf(part.getPrice()));
            maxField.setText(String.valueOf(part.getMax()));
            minField.setText(String.valueOf(part.getMin()));
            machIdField.setText(String.valueOf(((InHouse) part).getMachineID()));
            /* Inhouse specific fields */
            machNameLabel.setText("Machine ID");
            inhouseRadioBtn.setSelected(true);

        }

        else if (part instanceof OutsourcedPart) {

            idField.setText(String.valueOf(part.getId()));
            nameField.setText(part.getName());
            invField.setText(String.valueOf(part.getStock()));
            priceField.setText(String.valueOf(part.getPrice()));
            maxField.setText(String.valueOf(part.getMax()));
            minField.setText(String.valueOf(part.getMin()));
            machIdField.setText(String.valueOf(((OutsourcedPart) part).getCompanyName()));
            /* Outsource specific fields */
            outsourcedRadioBtn.setSelected(true);
            machNameLabel.setText("Company Name");



        }

    }

    @FXML
    void savePartButton(ActionEvent event) throws IOException {


        try {

            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            int stock = Integer.parseInt(invField.getText());
            int min = Integer.parseInt(minField.getText());
            int max = Integer.parseInt(maxField.getText());
            String radioOption = machIdField.getText();

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

            if (inhouseRadioBtn.isSelected()) {
                int machineID = Integer.parseInt(machIdField.getText());
                InHouse newInHouse = new InHouse(id, name, price, stock, min ,max, machineID);
                try {
                    Inventory.updatePart(newInHouse);
                }
                catch(Exception e) { /* It's fine to not update the product if its not modified*/}
            }


            if (outsourcedRadioBtn.isSelected()) {
                String companyName = radioOption;
                machNameLabel.setText("Company Name");
                machIdField.setPromptText("Company Name");
                OutsourcedPart newOutsourcedPart = new OutsourcedPart(id, name, price, stock, min, max, companyName);
                try {
                    Inventory.updatePart(newOutsourcedPart);
                }
                catch(Exception e) { /* It's fine to not update the product if its not modified*/}
            }

            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/view/main_screen.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();

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
    void selectInhouseRadio(ActionEvent event) {

        machNameLabel.setText("Machine ID");

    }

    @FXML
    void selectOutsourceRadio(ActionEvent event) {

        machNameLabel.setText("Company Name");

    }

}
