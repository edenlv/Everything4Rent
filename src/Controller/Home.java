package Controller;

import View.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.Optional;

public class Home {
    public Label home_helloUser;
    public HBox hbox_loginReg;
    public HBox hbox_prod1;
    public HBox hbox_prod2;


    public Stage loginStage;
    public Stage registerStage;
    public Stage addProductStage;
    public Stage tableDialogStage;
    public Stage updateProductStage;


    public void openLoginDialog(ActionEvent event){
        loginStage = new Stage();
        loginStage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
        FXMLLoader fxmlLoader = new FXMLLoader();
        int width=350, height=150;
        URL url = getClass().getResource("/View/LoginDialog.fxml");
        loginStage.setTitle("Login to System");


        try {
            Parent root = fxmlLoader.load(url.openStream());
            Main.loginController = fxmlLoader.getController();
            Scene scene = new Scene(root, width, height);
            loginStage.setScene(scene);
            loginStage.show();
        }
        catch(Exception e) {e.printStackTrace();}
    }


    public void openRegisterDialog(ActionEvent event){
        registerStage = new Stage();
        registerStage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
        FXMLLoader fxmlLoader = new FXMLLoader();
        int width=350, height=150;
        URL url = getClass().getResource("/View/RegisterDialog.fxml");;
        registerStage.setTitle("Register");

        try {
            Parent root = fxmlLoader.load(url.openStream());
            Main.registerController = fxmlLoader.getController();
            Scene scene = new Scene(root, width, height);
            registerStage.setScene(scene);
            registerStage.show();

        }
        catch(Exception e) {e.printStackTrace();}
    }

    public void openAddProductDialog(ActionEvent event){
        addProductStage = new Stage();
        addProductStage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
        FXMLLoader fxmlLoader = new FXMLLoader();
        int width=600, height=550;
        URL url = getClass().getResource("/View/AddProduct.fxml");;
        addProductStage.setTitle("Register");

        try {
            Parent root = fxmlLoader.load(url.openStream());
            Scene scene = new Scene(root, width, height);
            addProductStage.setScene(scene);
            addProductStage.show();
            Main.addProductcontroller = fxmlLoader.getController();
        }
        catch(Exception e) {e.printStackTrace();}
    }

    public void loginSuccessful(String username){
        home_helloUser.setText("Hello, " + username+"!");
        home_helloUser.setVisible(true);
        hbox_loginReg.setVisible(false);
        hbox_prod1.setVisible(true);
        hbox_prod2.setVisible(true);
    }

    public void openViewAllProducts(ActionEvent event){
        tableDialogStage = new Stage();
        tableDialogStage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
        FXMLLoader fxmlLoader = new FXMLLoader();
        int width=1200, height=600;
        URL url = getClass().getResource("/View/TableDialog.fxml");;
        tableDialogStage.setTitle("Your products");

        try {
            Parent root = fxmlLoader.load(url.openStream());
            Scene scene = new Scene(root, width, height);
            tableDialogStage.setScene(scene);
            tableDialogStage.show();
            Main.viewAllController = fxmlLoader.getController();
            tableDialogStage.setResizable(false);
            scene.getStylesheets().add(new File("src\\View\\style.css").toURI().toURL().toExternalForm());

        }
        catch(Exception e) {e.printStackTrace();}
    }

    public void openUpdateProduct(String productNumber){
        updateProductStage = new Stage();
        updateProductStage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
        FXMLLoader fxmlLoader = new FXMLLoader();
        int width=600, height=550;
        URL url = getClass().getResource("/View/UpdateProduct.fxml");;
        updateProductStage.setTitle("Update your product");

        try {
            Parent root = fxmlLoader.load(url.openStream());
            Scene scene = new Scene(root, width, height);
            updateProductStage.setScene(scene);

            Main.updateProductController = fxmlLoader.getController();
            boolean ok = Main.updateProductController.setProduct(productNumber);
            if (ok) updateProductStage.show();
        }
        catch(Exception e) {e.printStackTrace();}
    }

    public void onPressUpdateProduct(ActionEvent actionEvent){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setContentText("Product Number");
        dialog.initOwner(updateProductStage);
        dialog.setHeaderText("Enter the product's number you would like to update");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) openUpdateProduct(result.get());
    }


}
