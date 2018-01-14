package Controller;

import View.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import Model.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class TableDialog_Pack implements Initializable{
    public TableView<TableDialog_Pack.Item> itemsTable;

    public TextField in_packID;
    public TextField in_packID_loan;
    public TextField in_packID_tradein;

    public TableColumn idColumn;
    public TableColumn productNameColumn;
    public TableColumn bTypeCol;
    public TableColumn descColumn;
    public TableColumn startDateCol;
    public TableColumn endDateCol;
    public TableColumn costCol;
    public TableColumn ownerCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        idColumn.setCellValueFactory(new PropertyValueFactory<TableDialog.Item, String>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<TableDialog.Item,String>("productName"));
        bTypeCol.setCellValueFactory(new PropertyValueFactory<TableDialog.Item,String>("bType"));
        descColumn.setCellValueFactory(new PropertyValueFactory<TableDialog.Item,String>("description"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<TableDialog.Item,String>("startDate"));
        endDateCol.setCellValueFactory(new PropertyValueFactory<TableDialog.Item,String>("endDate"));
        costCol.setCellValueFactory(new PropertyValueFactory<TableDialog.Item,String>("cost"));
        ownerCol.setCellValueFactory(new PropertyValueFactory<TableDialog.Item,String>("owner"));
        ownerCol.setVisible(false);
    }

    public void setModel(ArrayList<String[]> tableModel){
        ObservableList<TableDialog_Pack.Item> data = FXCollections.observableArrayList();
        for (String[] row : tableModel){
            data.add(new TableDialog_Pack.Item(row));
        }
        itemsTable.setItems(data);
    }

    public void viewAllProducts(ActionEvent event){
        String packID = in_packID.getText();
        Main.mainController.openProductsTable(Model.productsearch(null,null,null,null,null,null,"1",packID));
    }

    public void loanPackage(ActionEvent event){
        String packIDtoLoan = in_packID_loan.getText();

        String owner1 = Model.getOwnerbypackID(packIDtoLoan);

        if (owner1.equals(Model.username)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You can't loan your own package");
            alert.show();
            return;
        }

        //TODO: call model method to loan
        boolean isOK = Model.insert_loan(packIDtoLoan, null, owner1, Model.username);

        if (isOK){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("You have successfully loaned package number " + packIDtoLoan + ".");
            alert.setHeaderText("Loan Successful!");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error while loaning package number " + packIDtoLoan + ".\n\n"+ "Please Contact Kushilemok.");
            alert.show();
        }
    }

    public void tradeinPackage(ActionEvent event){
        String pack1 = in_packID_tradein.getText();

        String owner1 = Model.getOwnerbypackID(pack1);
        String owner2 = Model.username;

        TextInputDialog dialog = new TextInputDialog();
        dialog.setContentText("Package ID for Trade-In");
        dialog.initOwner(Main.mainController.viewAllPacksStage);
        dialog.setHeaderText("Enter the package's number you would like to trade-in for");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String pack2 = result.get();
            String tmp_owner = Model.getOwnerbypackID(pack2);
            if (!Model.username.equals(tmp_owner)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot give a package that is not yours! wtf??");
                alert.show();
                return;
            }

            //TODO: call model method to loan
            boolean isOK = Model.insert_loan(pack1,pack2,owner1,owner2);

            if (isOK) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("You have successfully traded-in package number " + pack1 + ".");
                alert.setHeaderText("Trade-In Successful!");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Error while trading-in package number " + pack1 + ".\n\n" + "Please Contact Kushilemok.");
                alert.show();
            }
        }
    }

    public void setOwnerColVisible(boolean visible){
        ownerCol.setVisible(visible);
    }









    public static class Item {
        private final SimpleStringProperty id;
        private final SimpleStringProperty productName;
        private final SimpleStringProperty bType;
        private final SimpleStringProperty description;
        private final SimpleStringProperty startDate;
        private final SimpleStringProperty endDate;
        private final SimpleStringProperty cost;
        private final SimpleStringProperty owner;

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
            this.owner = new SimpleStringProperty(data[5]);
        }

        public String getOwner() {
            return owner.get();
        }

        public SimpleStringProperty ownerProperty() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner.set(owner);
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
