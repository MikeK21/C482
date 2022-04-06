package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/** This class controls the AddParts.fxml scene and allows user to add a part to the Parts Table.
 RUNTIME ERROR
 An error I encountered in the code was that the machine id/company name field wouldn't render because it was dependent
 on the radio button behind selected one way or another. I fixed this by electing a default radio button select (inhouse)
 in the initialize method.
 FUTURE ENHANCEMENT
 A future enhancement I would like to make is to prompt the user with an alert dialogue box to decide if inhouse/outsourced
 and then to present the proper fields immediately based on selection.
 */
public class AddPartsController implements Initializable {

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
    private ToggleGroup tgroup;

    @Override
    /** This method initializes the Parts table */
    public void initialize(URL url, ResourceBundle resourceBundle) {

        inhouseRadioBtn.isSelected();

    }

    /**@return machineLabel This method gets the machine label */
    public Label getMachineLabel() {
        return machineLabel;
    }

    /**@param label This method sets the machine label */
    public void setMachineLabel(String label) {
        this.machineLabel = machineLabel;
        machineLabel.setText(label);
    }

    @FXML
    private Label machineLabel;

    @FXML
    private Label machNameLabel;


    @FXML
    /** This method saves a part in the scene on action */
    void savePartButton(ActionEvent event) throws IOException {

     /*
     Increment the ID based on what's currently in the product table.
     */
        int presetIdNext = 0;
        for (Part p : Inventory.getAllParts()){
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
        String radioOptionString = machIdField.getText();

        try {

            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            int stock = Integer.parseInt(invField.getText());
            int min = Integer.parseInt(minField.getText());
            int max = Integer.parseInt(maxField.getText());

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
                Inventory.insertPart(newInHouse);

                Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                Object scene = FXMLLoader.load(getClass().getResource("/view/main_screen.fxml"));
                stage.setScene(new Scene((Parent) scene));
                stage.show();

            }
            else if (outsourcedRadioBtn.isSelected()) {
                machNameLabel.setText("Company Name");
                machIdField.setPromptText("Company Name");
                String companyName = machIdField.getText();
                OutsourcedPart newOutsourcedPart = new OutsourcedPart(id, name, price, stock, min, max, companyName);
                Inventory.insertPart(newOutsourcedPart);

                Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                Object scene = FXMLLoader.load(getClass().getResource("/view/main_screen.fxml"));
                stage.setScene(new Scene((Parent) scene));
                stage.show();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Inhouse or Outsourced Not Selected");
                alert.setContentText("You are required to select Inhouse or Outsourced");
                alert.showAndWait();
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
    /** This method cancels a part in the scene on action and
     displays a CONFIRMATION alert so user's can retract their action
     */
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
    /** This method selects the inhouse radio button on action.
     Because this is selected, it sets Inhouse specific variables.
     */
    private void selectInhouseRadio(ActionEvent event) {

        boolean isInhouseSelect = true;
        machNameLabel.setText("Machine ID");
        machIdField.setPromptText("Machine ID");

    }

    @FXML
    /** This method selects the Outsource radio button on action.
      Because this is selected, it sets Outsource specific variables.
     */
    private void selectOutsourceRadio(ActionEvent event) {

        boolean isOutsourceSelect = true;
        machNameLabel.setText("Company Name");
        machIdField.setPromptText("Company Name");

    }

}
