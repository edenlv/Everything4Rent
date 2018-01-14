package Controller;

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

    public ChoiceBox cb_packCategory;
    public ChoiceBox cb_packBType;
    public DatePicker date_start;
    public DatePicker date_end;
    public TextField in_packName;
    public TextField in_desc;
    public MenuButton menu_prods;

    public void initialize(URL location, ResourceBundle resources) {
        cb_packCategory.setItems(FXCollections.observableArrayList("Cars", "Real Estate", "Animals", "Yad2"));
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
        String packName = in_packName.getText();
        String category = (String) cb_packCategory.getValue();
        String businessType = (String) cb_packCategory.getValue();
        String startDate = date_start.getValue().toString();
        String endDate = date_end.getValue().toString();
        String description = in_desc.getText();

        ArrayList<String> prodIDs = new ArrayList<>();

        prods.stream().forEach(
                cb -> {
                    if (cb.isSelected()) prodIDs.add(cb.getText().split(" - ")[0]);
                }
        );



        System.out.println("success");

    }


}
