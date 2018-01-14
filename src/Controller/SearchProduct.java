package Controller;

import View.Main;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import Model.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SearchProduct implements Initializable{

    public TextField in_productName2;
    public ChoiceBox cb_category2;
    public TextField in_cost2;
    public TextField in_desc2;
    public TextField in_id2;
    public TextField in_owner2;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cb_category2.setItems(FXCollections.observableArrayList("Cars", "Real Estate", "Animals", "Yad2"));
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


    public void onSearchProduct(ActionEvent event){
        String productName = in_productName2.getText();
        String category = (String)cb_category2.getValue();
        String cost = in_cost2.getText();
        String desc = in_desc2.getText();
        String id = in_id2.getText();
        String owner = in_owner2.getText();

        ArrayList<String[]> data = Model.productsearch(id,productName,category,cost,owner,desc);


        Main.mainController.openProductsTable(data);

    }
}