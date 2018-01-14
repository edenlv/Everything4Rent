package View;

import Controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Model.Model;

public class Main extends Application {
    public static Home mainController = null;
    public static LoginDialog loginController = null;
    public static RegisterDialog registerController = null;
    public static AddProduct addProductcontroller = null;
    public static TableDialog viewAllController = null;
    public static UpdateProduct updateProductController = null;
    public static SearchProduct searchProductController = null;
    public static CreatePackage createPackController = null;
    public static TableDialog_Pack viewAllPacksController = null;

    public static boolean debugMode = true;


    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("Home.fxml").openStream());
        primaryStage.setTitle("Everything4Rent System");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();

        mainController = (Home) fxmlLoader.getController();

        if (debugMode) {
            Model.login("Eden", "Sisma123");
            mainController.loginSuccessful("Eden");
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
