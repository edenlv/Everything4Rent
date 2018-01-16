package Controller;

import Model.Model;
import View.Main;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by levye on 30/12/2017.
 */
public class UpdatePackage implements Initializable{

    public String currentPackage;
    private ArrayList<CheckBox> prods;
    public Label lbl_prodNumber;
    public ChoiceBox cb_packBType;
    public DatePicker date_start;
    public DatePicker date_end;
    public TextField in_packName;
    public TextField in_desc;
//    public MenuButton menu_prods;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        cb_packCategory.setItems(FXCollections.observableArrayList("Cars", "Real Estate", "Animals", "Yad2"));
        cb_packBType.setItems(FXCollections.observableArrayList("Trade-In", "Loan", "Donation"));

        ArrayList<String[]> list = Model.product_choose();
        this.prods = new ArrayList<>();
        ArrayList<CustomMenuItem> items = new ArrayList<>();

//        for (int i=0; i<list.size(); i++){
//            String[] aux = list.get(i);
//            String toShow = aux[0] + " - " + aux[1];
//            CheckBox cb = new CheckBox(toShow);
//            CustomMenuItem item = new CustomMenuItem(cb);
//            items.add(item);
//            item.setHideOnClick(false);
//            prods.add(cb);
//        }
//
//        if (items.size()>0) menu_prods.getItems().setAll(items);
    }


    public boolean setProduct(String productNumber){
        currentPackage = productNumber;
        int id = getNumber(currentPackage);
        if (id!=-1){
            ArrayList<String[]> data = Model.packsearch(currentPackage,null,null,null,null,null,null,null);

            if (data!=null && data.size()>0) {
                String[] prod = data.get(0);

                if (!prod[5].equals(Model.username)){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Can't show you this package. It may not be yours.");
                    alert.show();
                    return false;
                }

                lbl_prodNumber.setText("Package Number: " + String.valueOf(id));
                cb_packBType.setValue(prod[1]);
                String[] start = prod[2].split("-");
                date_start.setValue(LocalDate.of(Integer.parseInt(start[0]),Integer.parseInt(start[1]),Integer.parseInt(start[2])));

                String[] end = prod[2].split("-");
                date_end.setValue(LocalDate.of(Integer.parseInt(end[0]),Integer.parseInt(end[1]),Integer.parseInt(end[2])));

                in_packName.setText(prod[6]);
                in_desc.setText(prod[7]);
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
        int id = getNumber(currentPackage);
        if (id!=-1){
            boolean ok = Model.deletePackage(id);
            if (ok){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Product Number " + id + " was deleted successfully!");
                alert.setTitle("Delete Confirmation Window");
                alert.setHeaderText("Product Deleted");
                alert.show();
                Main.mainController.updatePackageStage.close();
            }
        }
    }

    public void onUpdateProduct(ActionEvent actionEvent){
        int id = getNumber(currentPackage);
        String packName = in_packName.getText();
        String businessType = (String) cb_packBType.getValue();
        String startDate = date_start.getValue().toString();
        String endDate = date_end.getValue().toString();
        String description = in_desc.getText();

        boolean ok = Model.updatePackage(id,businessType,startDate,endDate,packName,description);
        if (ok){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Package Number " + id + " was updated successfully!");
            alert.setTitle("Update Confirmation Window");
            alert.setHeaderText("Package Updated");
            alert.show();
            Main.mainController.updatePackageStage.close();
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
