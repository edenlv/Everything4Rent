package Controller;

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

public class TableDialog_Pack implements Initializable{
    public TableView<TableDialog_Pack.Item> itemsTable;

    public TableColumn idColumn;
    public TableColumn productNameColumn;
    public TableColumn categoryColumn;
    public TableColumn bTypeCol;
    public TableColumn descColumn;
    public TableColumn startDateCol;
    public TableColumn endDateCol;
    public TableColumn costCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        idColumn.setCellValueFactory(new PropertyValueFactory<TableDialog.Item, String>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<TableDialog.Item,String>("productName"));
        bTypeCol.setCellValueFactory(new PropertyValueFactory<TableDialog.Item,String>("bType"));
        descColumn.setCellValueFactory(new PropertyValueFactory<TableDialog.Item,String>("description"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<TableDialog.Item,String>("startDate"));
        endDateCol.setCellValueFactory(new PropertyValueFactory<TableDialog.Item,String>("endDate"));
        costCol.setCellValueFactory(new PropertyValueFactory<TableDialog.Item,String>("cost"));

    }

    public void setModel(ArrayList<String[]> tableModel){
        ObservableList<TableDialog_Pack.Item> data = FXCollections.observableArrayList();
        for (String[] row : tableModel){
            data.add(new TableDialog_Pack.Item(row));
        }
        itemsTable.setItems(data);
    }

    public static class Item {
        private final SimpleStringProperty id;
        private final SimpleStringProperty productName;
        private final SimpleStringProperty bType;
        private final SimpleStringProperty description;
        private final SimpleStringProperty startDate;
        private final SimpleStringProperty endDate;
        private final SimpleStringProperty cost;

//        private Item(String id, String productName, String category, double cost, String desc, String packID, String owner) {
//            this.id = new SimpleStringProperty(id);
//            this.productName = new SimpleStringProperty(productName);
//            this.category = new SimpleStringProperty(category);
//            this.bType = new SimpleStringProperty(String.valueOf(cost));
//            this.description = new SimpleStringProperty(desc);
//            this.startDate = new SimpleStringProperty(packID);
//            this.endDate = new SimpleStringProperty(owner);
//        }

        private Item(String[] data) {
            this.id = new SimpleStringProperty(data[0]);
            this.productName = new SimpleStringProperty(data[6]);
            this.bType = new SimpleStringProperty(String.valueOf(data[1]));
            this.description = new SimpleStringProperty(data[7]);
            this.startDate = new SimpleStringProperty(data[2]);
            this.endDate = new SimpleStringProperty(data[3]);
            this.cost = new SimpleStringProperty(data[4]);
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

        public String getbType() {
            return bType.get();
        }

        public SimpleStringProperty bTypeProperty() {
            return bType;
        }

        public void setbType(String bType) {
            this.bType.set(bType);
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
