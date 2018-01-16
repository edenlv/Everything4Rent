package Controller;

import Model.Model;
import View.Main;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SearchPackage implements Initializable{

    public ChoiceBox cb_packCategory;
    public ChoiceBox cb_packBType;
    public DatePicker date_start;
    public DatePicker date_end;
    public TextField in_packName;
    public TextField in_desc;
    public MenuButton menu_prods;
    public TextField in_packID;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        cb_packCategory.setItems(FXCollections.observableArrayList("Cars", "Real Estate", "Animals", "Yad2"));
        cb_packBType.setItems(FXCollections.observableArrayList("Trade-In", "Loan", "Donation"));




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
        String packName = in_packName.getText();
        String businessType = (String) cb_packBType.getValue();
        String startDate = null;
        if (date_start.getValue()!=null) startDate = date_start.getValue().toString();
        String endDate = null;
        if (date_end.getValue()!=null) endDate = date_end.getValue().toString();
        String description = in_desc.getText();
        String id = in_packID.getText();

        if (!checkIntegrity()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("ID must be number");
            alert.show();
            return;
        }

        ArrayList<String[]> data = Model.packsearch(id,packName,businessType,startDate,null,endDate,description,"0");

        Main.mainController.openViewAllPackagesWindow(data);
        Main.viewAllPacksController.setOwnerColVisible(true);

        if (Model.username!=null) {
            Main.viewAllPacksController.setLoanVisible(true);
            Main.viewAllPacksController.setTradeVisible(true);
        }

    }

    public boolean checkIntegrity(){
        String id = in_packID.getText();
        if (id==null || id.equals("")){return true;}
        try{
            int x = Integer.parseInt(id);
            return true;
        } catch (Exception e){ return false; }
    }


}
