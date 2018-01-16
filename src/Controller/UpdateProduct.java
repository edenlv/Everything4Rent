package Controller;

import View.Main;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import Model.*;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static Controller.AddProduct.LocalDateToString;

/**
 * Created by levye on 30/12/2017.
 */
public class UpdateProduct implements Initializable{
    public String currentProduct;
    public Label lbl_prodNumber;
    public ChoiceBox cb_category1;
    public TextField in_productName1;
    public TextField in_cost1;
    public TextField in_owner1;
    public TextField in_desc1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cb_category1.setItems(FXCollections.observableArrayList("Cars", "Real Estate", "Animals", "Yad2"));
    }


    public boolean setProduct(String productNumber){
        currentProduct = productNumber;
        int id = getNumber(currentProduct);
        if (id!=-1){
            String[] data = Model.getProduct(id);
            if (data==null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("No product found!");
                alert.show();
                return false;
            }

            if (data[4]!=null && !data[4].equals(Model.username)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("The product is not yours!");
                alert.show();
                return false;
            }
            if (data!=null) {
                lbl_prodNumber.setText("Product Number: " + String.valueOf(id));
                in_productName1.setText(data[1]);
                cb_category1.setValue(data[2]);
                in_cost1.setText(data[3]);
                in_desc1.setText(data[5]);
                in_owner1.setText(data[4]);

                return true;
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Product number not found!");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid product number entered!");
            alert.show();
        }

        return false;
    }

    public void onDeleteProduct(ActionEvent event){
        int id = getNumber(currentProduct);
        if (id!=-1){
            boolean ok = Model.deleteProduct(id);
            if (ok){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Product Number " + id + " was deleted successfully!");
                alert.setTitle("Delete Confirmation Window");
                alert.setHeaderText("Product Deleted");
                alert.show();
                Main.mainController.updateProductStage.close();
            }
        }
    }

    public void onUpdateProduct(ActionEvent actionEvent){
        int id = getNumber(currentProduct);
        String productName = in_productName1.getText();
        String category = (String)cb_category1.getValue();
        String cost = in_cost1.getText();
        String desc = in_desc1.getText();

        boolean ok = Model.updateProduct(id ,productName, category, Integer.valueOf(cost), desc);
        if (ok){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Product Number " + id + " was updated successfully!");
            alert.setTitle("Update Confirmation Window");
            alert.setHeaderText("Product Updated");
            alert.show();
            Main.mainController.updateProductStage.close();
        }
    }

    public int getNumber(String strNum){
        if (strNum==null) return -1;
        try{
            int number = Integer.valueOf(strNum);
            return number;
        } catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter a valid product ID!");
            alert.show();
        }
        return -1;
    }

}
