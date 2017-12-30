package Controller;

import Model.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TableDialog implements Initializable{

    public TableView<Item> itemsTable;
    public TableColumn idColumn;
    public TableColumn productNameColumn;
    public TableColumn businessColumn;
    public TableColumn categoryColumn;
    public TableColumn startDateColumn;
    public TableColumn costColumn;
    public TableColumn endDateColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<String[]> tableModel = Model.getAllProducts();

        idColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<Item,String>("productName"));
        businessColumn.setCellValueFactory(new PropertyValueFactory<Item,String>("businessType"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<Item,String>("category"));
        costColumn.setCellValueFactory(new PropertyValueFactory<Item,String>("cost"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<Item,String>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<Item,String>("endDate"));

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
        private final SimpleStringProperty businessType;
        private final SimpleStringProperty cost;
        private final SimpleStringProperty startDate;
        private final SimpleStringProperty endDate;

        private Item(String id, String productName, String category, String businessType, double cost, String startDate, String endDate){
            this.id= new SimpleStringProperty(id);
            this.productName = new SimpleStringProperty(productName);
            this.category = new SimpleStringProperty(category);
            this.businessType = new SimpleStringProperty(businessType);
            this.cost = new SimpleStringProperty(String.valueOf(cost));
            this.startDate = new SimpleStringProperty(startDate);
            this.endDate = new SimpleStringProperty(endDate);
        }

        private Item(String[] data){
            this.id= new SimpleStringProperty(data[0]);
            this.productName = new SimpleStringProperty(data[1]);
            this.category = new SimpleStringProperty(data[2]);
            this.businessType = new SimpleStringProperty(data[3]);
            this.cost = new SimpleStringProperty(String.valueOf(data[4]));
            this.startDate = new SimpleStringProperty(data[5]);
            this.endDate = new SimpleStringProperty(data[6]);
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

        public String getBusinessType() {
            return businessType.get();
        }

        public SimpleStringProperty businessTypeProperty() {
            return businessType;
        }

        public void setBusinessType(String businessType) {
            this.businessType.set(businessType);
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

        public String getStartDate() {
            return startDate.get();
        }

        public SimpleStringProperty startDateProperty() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate.set(startDate);
        }

        public String getEndDate() {
            return endDate.get();
        }

        public SimpleStringProperty endDateProperty() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate.set(endDate);
        }
    }

}
