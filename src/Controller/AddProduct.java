package Controller;

import Model.Model;
import View.Main;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class AddProduct implements Initializable {
    public ChoiceBox cb_category;
    public TextField in_productName;
    public TextField in_cost;
    public TextField in_desc;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cb_category.setItems(FXCollections.observableArrayList("Cars", "Real Estate", "Animals", "Yad2"));
    }

    public void onCreateProduct(ActionEvent event){
        try {
            String productName = in_productName.getText();
            String category = (String) cb_category.getValue();
            int cost = Integer.parseInt(in_cost.getText());
            String desc = in_desc.getText();
            String owner = Model.username;

            if (productName.length()==0 || category.length()==0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Bad input");
                alert.show();
                return;
            }

            boolean success = Model.insert_prod(productName, category, cost, owner, desc);
            if (success) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Product was created successfully!");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Error while creating new product to Database.");
                alert.show();
            }

            Main.mainController.addProductStage.close();
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Bad input");
            alert.show();
        }
    }


    public static String LocalDateToString(LocalDate date){
        String[] dateStr =  date.toString().split("-");
        return dateStr[2]+"/"+dateStr[1]+"/"+dateStr[0];
    }



}
