package Controller;

import Model.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sun.java2d.pipe.SpanShapeRenderer;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TableDialog implements Initializable{

    public TableView<Item> itemsTable;

    public TableColumn idColumn;
    public TableColumn productNameColumn;
    public TableColumn categoryColumn;
    public TableColumn costColumn;
    public TableColumn descColumn;
    public TableColumn packIDColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<String[]> tableModel = Model.getAllProducts();

        idColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<Item,String>("productName"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<Item,String>("category"));
        costColumn.setCellValueFactory(new PropertyValueFactory<Item,String>("cost"));
        descColumn.setCellValueFactory(new PropertyValueFactory<Item,String>("description"));
        packIDColumn.setCellValueFactory(new PropertyValueFactory<Item,String>("packID"));

        ObservableList<Item> data = FXCollections.observableArrayList();

        for (String[] row : tableModel){
            data.add(new Item(row));
        }

        itemsTable.setItems(data);

        System.out.println(tableModel.size());
    }





    public static class Item{
        private final SimpleStringProperty id;
        private final SimpleStringProperty productName;
        private final SimpleStringProperty category;
        private final SimpleStringProperty cost;
        private final SimpleStringProperty description;
        private final SimpleStringProperty packID;

        private Item(String id, String productName, String category, double cost, String desc, String packID){
            this.id= new SimpleStringProperty(id);
            this.productName = new SimpleStringProperty(productName);
            this.category = new SimpleStringProperty(category);
            this.cost = new SimpleStringProperty(String.valueOf(cost));
            this.description = new SimpleStringProperty(desc);
            this.packID = new SimpleStringProperty(packID);

        }

        private Item(String[] data){
            this.id= new SimpleStringProperty(data[0]);
            this.productName = new SimpleStringProperty(data[1]);
            this.category = new SimpleStringProperty(data[2]);
            this.cost = new SimpleStringProperty(String.valueOf(data[3]));
            this.description = new SimpleStringProperty(data[4]);
            this.packID = new SimpleStringProperty(data[5]);
        }

        public String getId() {
            return id.get();
        }

        public SimpleStringProperty idProperty() {
            return id;
        }

        public void setId(String id) {
            this.id.set(id);
        }

        public String getProductName() {
            return productName.get();
        }

        public SimpleStringProperty productNameProperty() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName.set(productName);
        }

        public String getCategory() {
            return category.get();
        }

        public SimpleStringProperty categoryProperty() {
            return category;
        }

        public void setCategory(String category) {
            this.category.set(category);
        }


        public String getCost() {
            return cost.get();
        }

        public SimpleStringProperty costProperty() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost.set(cost);
        }

        public String getDescription() {
            return description.get();
        }

        public SimpleStringProperty descriptionProperty() {
            return description;
        }

        public void setDescription(String description) {
            this.description.set(description);
        }

        public String getPackID() {
            return packID.get();
        }

        public SimpleStringProperty packIDProperty() {
            return packID;
        }

        public void setPackID(String packID) {
            this.packID.set(packID);
        }
    }

}
