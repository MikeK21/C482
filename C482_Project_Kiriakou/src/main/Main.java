package main;

import controller.MainScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Part;
import model.Product;

/** Javadocs located in /javadocs/ */

/** This class instantiates the program by adding test data and launching the main screen
 RUNTIME ERROR
 An error I encountered in the code was that I had setup the sample data, however it was not launching when the program started.
 I had instantiated the product/part objects themselves but did not add them to the inventory. I fixed this by creating an insertpart
 method in the Inventory and calling it to set the sample data in inventory.
  FUTURE ENHANCEMENT
 A future enhancement I could make is to have the program accept a CSV file of parts and products so that data could be uploaded in bulk.
 */
public class Main extends Application {

    static boolean entered;

    @Override
    /** This method starts the initial scene for main screen and the application. */
    public void start(Stage primaryStage) throws Exception {

        Inventory inv = new Inventory();
        addTestData(inv);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main_screen.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();


    }
    /** This method adds sample data to the respective inventories */
    void addTestData(Inventory inv){

        Part a1 = new InHouse(1, "net", 10.0, 7, 3, 10, 1);
       inv.insertPart(a1);

        Part a2 = new InHouse(2, "hoop", 15.0, 11, 10, 20, 10);
        inv.insertPart(a2);

        Part a3 = new InHouse(3, "sand", 30.0, 10, 1, 12, 12);
        inv.insertPart(a3);

        Product b1 = new Product(1, "basket", 12.0, 5, 1, 10);
        inv.insertProduct(b1);

        Product b2 = new Product(2, "basketball", 10.0, 7, 4, 10);
        inv.insertProduct(b2);

    }
    /** @param args This method launches sample data arguments */
    public static void main(String[] args) {

        launch(args);
    }
}
