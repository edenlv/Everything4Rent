package Controller;

import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by levye on 10/01/2018.
 */
public class CreatePackage implements Initializable{

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

        CheckBox cb0 = new CheckBox("x");
        CustomMenuItem item0 = new CustomMenuItem(cb0);
        CheckBox cb1 = new CheckBox("y");
        CustomMenuItem item1 = new CustomMenuItem(cb1);
        item0.setHideOnClick(false);
        item1.setHideOnClick(false);
        menu_prods.getItems().setAll(item0,item1);
    }
}
