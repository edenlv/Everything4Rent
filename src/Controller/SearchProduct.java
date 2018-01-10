package Controller;

import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

import static Controller.AddProduct.LocalDateToString;

public class SearchProduct implements Initializable{

    public TextField in_productName2;
    public ChoiceBox cb_category2;
    public ChoiceBox cb_businessType2;
    public TextField in_cost2;
    public DatePicker date_start2;
    public DatePicker date_end2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cb_category2.setItems(FXCollections.observableArrayList("Cars", "Real Estate", "Animals", "Yad2"));
        cb_businessType2.setItems(FXCollections.observableArrayList("Rent", "Trade-In", "Donation"));
    }

    public void search(){

        String productName = in_productName2.getText();
        String category = (String)cb_category2.getValue();
        String businessType = (String)cb_businessType2.getValue();
        String cost = in_cost2.getText();
        String endDate = LocalDateToString(date_end2.getValue());
        String startDate = LocalDateToString(date_start2.getValue());



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
