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
    public ChoiceBox cb_category;
    public ChoiceBox cb_businessType;
    public TextField in_productName;
    public TextField in_cost;
    public DatePicker date_start;
    public DatePicker date_end;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cb_category.setItems(FXCollections.observableArrayList("Cars", "Real Estate", "Animals", "Yad2"));
        cb_businessType.setItems(FXCollections.observableArrayList("Rent", "Trade-In", "Donation"));
    }


    public boolean setProduct(String productNumber){
        currentProduct = productNumber;
        int id = getNumber(currentProduct);
        if (id!=-1){
            String[] data = Model.getProduct(id);

            if (data!=null) {
                lbl_prodNumber.setText("Product Number: " + String.valueOf(id));
                in_productName.setText(data[1]);
                cb_category.setValue(data[2]);
                cb_businessType.setValue(data[3]);
                in_cost.setText(data[4]);

                LocalDate start_date = LocalDate.parse(data[5], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                date_start.setValue(start_date);
                LocalDate end_date = LocalDate.parse(data[6], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                date_end.setValue(end_date);
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
        String productName = in_productName.getText();
        String category = (String)cb_category.getValue();
        String businessType = (String)cb_businessType.getValue();
        String cost = in_cost.getText();
        String endDate = LocalDateToString(date_end.getValue());
        String startDate = LocalDateToString(date_start.getValue());

        boolean ok = Model.updateProduct( id ,productName, category,businessType,Integer.valueOf(cost),null, startDate,endDate);
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
