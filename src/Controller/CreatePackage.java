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

/**
 * Created by levye on 10/01/2018.
 */
public class CreatePackage implements Initializable{

    private ArrayList<CheckBox> prods;

    public ChoiceBox cb_packBType;
    public DatePicker date_start;
    public DatePicker date_end;
    public TextField in_packName;
    public TextField in_desc;
    public MenuButton menu_prods;

    public void initialize(URL location, ResourceBundle resources) {
        cb_packBType.setItems(FXCollections.observableArrayList("Trade-In", "Loan", "Donation"));

        ArrayList<String[]> list = Model.product_choose();
        this.prods = new ArrayList<>();
        ArrayList<CustomMenuItem> items = new ArrayList<>();

        for (int i=0; i<list.size(); i++){
            String[] aux = list.get(i);
            String toShow = aux[0] + " - " + aux[1];
            CheckBox cb = new CheckBox(toShow);
            CustomMenuItem item = new CustomMenuItem(cb);
            items.add(item);
            item.setHideOnClick(false);
            prods.add(cb);
        }

        if (items.size()>0) menu_prods.getItems().setAll(items);
    }


    public void onCreatePackage(ActionEvent event){
        try {
            String packName = in_packName.getText();
            String businessType = (String) cb_packBType.getValue();
            String startDate = date_start.getValue().toString();
            String endDate = date_end.getValue().toString();
            String description = in_desc.getText();

            ArrayList<String> prodIDs = new ArrayList<>();

            prods.stream().forEach(
                    cb -> {
                        if (cb.isSelected()) prodIDs.add(cb.getText().split(" - ")[0]);
                    }
            );

            if (prodIDs.size() == 0 || packName.length() == 0 || businessType == null || businessType.length() == 0 || startDate.length() == 0 || endDate.length() == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Bad input");
                alert.show();
                return;
            }

            boolean isOK = Model.insert_pack(businessType, startDate, endDate, packName, description, prodIDs);
            if (isOK) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Package was created successfully!");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Error while creating new package to Database.");
                alert.show();
            }

            Main.mainController.createPackStage.close();

            System.out.println("Successfully created package");
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Bad input");
            alert.show();
        }

    }


}
