package Controller;

import Model.Model;
import View.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class Home implements Initializable{
    public Label home_helloUser;
    public HBox hbox_loginReg;
    public HBox hbox_prod1;
    public HBox hbox_prod2;
    public HBox hbox_pack1;
    public HBox hbox_pack2;
    public Button btn_viewall;
    public HBox hbox_pack3;
    public Button btn_login;
    public Button btn_logout;
    public Button btn_reg;
    public ImageView imageview;
    public Button btn_updatePck;


    public Stage loginStage;
    public Stage registerStage;
    public Stage addProductStage;
    public Stage tableDialogStage;
    public Stage updateProductStage;
    public Stage searchProductStage;
    public Stage createPackStage;
    public Stage viewAllPacksStage;
    public Stage searchPackageStage;
    public Stage updatePackageStage;

    public MenuButton menuButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

//        CheckBox cb0 = new CheckBox("x");
//        CustomMenuItem item0 = new CustomMenuItem(cb0);
//        CheckBox cb1 = new CheckBox("y");
//        CustomMenuItem item1 = new CustomMenuItem(cb1);
//        item0.setHideOnClick(false);
//        item1.setHideOnClick(false);
//        menuButton.getItems().setAll(item0,item1);

        imageview.setImage(new Image("./View/logo.jpg"));
    }

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
        btn_login.setVisible(false);
        btn_reg.setVisible(false);
        btn_logout.setVisible(true);
        hbox_prod1.setVisible(true);
        hbox_prod2.setVisible(true);
//        hbox_pack1.setVisible(true);
        btn_updatePck.setVisible(true);
        hbox_pack2.setVisible(true);
        hbox_pack3.setVisible(true);
    }

    public void logout(){
        Model.username = null;
        home_helloUser.setText("");
        home_helloUser.setVisible(false);
        btn_login.setVisible(true);
        btn_reg.setVisible(true);
        btn_logout.setVisible(false);
        hbox_prod1.setVisible(false);
        hbox_prod2.setVisible(false);
//        hbox_pack1.setVisible(false);
        btn_updatePck.setVisible(false);
        hbox_pack2.setVisible(false);
        hbox_pack3.setVisible(false);
    }

    public void openViewAllProducts(ActionEvent event){
        openProductsTable(Model.getAllProducts());
    }

    public void openProductsTable(ArrayList<String[]> data){
        tableDialogStage = new Stage();
        tableDialogStage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
        FXMLLoader fxmlLoader = new FXMLLoader();
        int width=1200, height=600;
        URL url = getClass().getResource("/View/TableDialog.fxml");;
        tableDialogStage.setTitle("Your products");

        tableDialogStage.setOnCloseRequest(
                event1 -> {
                    Main.mainController.tableDialogStage = null;
                    Main.viewAllController = null;
                }
        );

        try {
            Parent root = fxmlLoader.load(url.openStream());
            Scene scene = new Scene(root, width, height);
            tableDialogStage.setScene(scene);

            tableDialogStage.show();
            Main.viewAllController = fxmlLoader.getController();
            Main.viewAllController.setModel(data);
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
        URL url = getClass().getResource("/View/UpdateProduct.fxml");
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

    public void onPressSearchProduct(ActionEvent actionEvent){
        openSearchProduct();
    }

    public void openSearchProduct(){
        searchProductStage = new Stage();
        searchProductStage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
        FXMLLoader fxmlLoader = new FXMLLoader();
        int width=600, height=550;
        URL url = getClass().getResource("/View/SearchProduct.fxml");
        searchProductStage.setTitle("Search for a product");

        try {
            Parent root = fxmlLoader.load(url.openStream());
            Scene scene = new Scene(root, width, height);
            searchProductStage.setScene(scene);

            Main.searchProductController = fxmlLoader.getController();

            searchProductStage.show();
        }
        catch(Exception e) {e.printStackTrace();}
    }

    /******************* PACKAGES ***************************/

    public void openCreatePackage(ActionEvent event){
        createPackStage = new Stage();
        createPackStage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
        FXMLLoader fxmlLoader = new FXMLLoader();
        int width=600, height=550;
        URL url = getClass().getResource("/View/CreatePackage.fxml");;
        createPackStage.setTitle("Register");

        try {
            Parent root = fxmlLoader.load(url.openStream());
            Scene scene = new Scene(root, width, height);
            createPackStage.setScene(scene);
            createPackStage.show();
            Main.createPackController = fxmlLoader.getController();
        }

        catch(Exception e) {e.printStackTrace();}
    }

    public void openViewAllPackages(ActionEvent event){
        openViewAllPackagesWindow(Model.getallpack());
    }

    public void openViewAllPackagesWindow(ArrayList<String[]> data){
        viewAllPacksStage = new Stage();
        viewAllPacksStage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader fxmlLoader = new FXMLLoader();
        int width=1200, height=600;
        URL url = getClass().getResource("/View/TableDialog_Pack.fxml");
        viewAllPacksStage.setTitle("Your Packages");

        viewAllPacksStage.setOnCloseRequest(
                event1 -> {
                    Main.mainController.viewAllPacksStage = null;
                    Main.viewAllPacksController = null;
                }
        );

        try {
            Parent root = fxmlLoader.load(url.openStream());
            Scene scene = new Scene(root, width, height);
            viewAllPacksStage.setScene(scene);
            viewAllPacksStage.show();
            Main.viewAllPacksController = fxmlLoader.getController();
            Main.viewAllPacksController.setModel(data);
            viewAllPacksStage.setResizable(false);
            scene.getStylesheets().add(new File("src\\View\\style.css").toURI().toURL().toExternalForm());
            Main.viewAllPacksController.setTradeVisible(false);
            Main.viewAllPacksController.setLoanVisible(false);
        }

        catch(Exception e) {e.printStackTrace();}
    }

    public void openUpdatePackage(String productNumber){
        updatePackageStage = new Stage();
        updatePackageStage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
        FXMLLoader fxmlLoader = new FXMLLoader();
        int width=600, height=550;
        URL url = getClass().getResource("/View/UpdatePackage.fxml");
        updatePackageStage.setTitle("Update your Package");

        try {
            Parent root = fxmlLoader.load(url.openStream());
            Scene scene = new Scene(root, width, height);
            updatePackageStage.setScene(scene);

            Main.updatePackageController = fxmlLoader.getController();
            boolean ok = Main.updatePackageController.setProduct(productNumber);
            if (ok) updatePackageStage.show();
        }
        catch(Exception e) {e.printStackTrace();}
    }

    public void openSearchPackage(ActionEvent event){
        searchPackageStage = new Stage();
        searchPackageStage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
        FXMLLoader fxmlLoader = new FXMLLoader();
        int width=600, height=550;
        URL url = getClass().getResource("/View/SearchPackage.fxml");
        searchPackageStage.setTitle("Search for a package");

        try {
            Parent root = fxmlLoader.load(url.openStream());
            Scene scene = new Scene(root, width, height);
            searchPackageStage.setScene(scene);

            Main.searchPackageController = fxmlLoader.getController();

            searchPackageStage.show();

        }



        catch(Exception e) {e.printStackTrace();}
    }

    public void onPressUpdatePackage(ActionEvent actionEvent){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setContentText("Package Number");
        dialog.initOwner(updatePackageStage);
        dialog.setHeaderText("Enter the package's number you would like to update");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) openUpdatePackage(result.get());
    }

    public void onShowMyLoans(ActionEvent event){

        ArrayList<String[]> data = Model.allmyloans();

        if (data.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No loans found!");
            alert.show();
            return;
        }

        openViewAllPackagesWindow(data);
        Main.viewAllPacksController.setOwnerColVisible(true);
        Main.viewAllPacksController.setLoanVisible(false);
        Main.viewAllPacksController.setTradeVisible(false);
    }



}
